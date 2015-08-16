package co.th.network.scheduler;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.control.TransactionScoreMatchingControl;
import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.TransactionScoreMatching;
import co.th.aten.network.entity.TransactionScoreMatchingPK;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.util.StringUtil;

@Stateless
public class ScheduledMatchingProcessor {

	@Inject
	@DBDefault
	private EntityManager em;

	@Inject
	private CustomerControl customerControl;
	@Inject
	private TransactionScoreMatchingControl transactionScoreMatchingControl;

	@Inject
	Logger log;

	//	@Schedule(hour="*",minute = "*",second="0/10")
	@Schedule(hour="00",minute = "01",second="01")
	public void execute() {
		long startTime = System.currentTimeMillis();
		try{
			log.info("##############################################");
			log.info("##### Start Scheduled Matching Processor #####");
			log.info("##############################################");
			SimpleDateFormat sdfHH = new SimpleDateFormat("HH",Locale.US);
			log.info("HH = "+sdfHH.format(new Date()));
			if(sdfHH.format(new Date()).equals("00")){
				List<Integer> memberList = em.createQuery("Select customerId From MemberCustomer Order By customerId asc "
						,Integer.class).getResultList();
				if(memberList!=null){
					log.info("Member List Size = "+memberList.size());
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DATE, -1);
					cal.set(Calendar.HOUR_OF_DAY, 0);
					cal.set(Calendar.MINUTE, 0);
					cal.set(Calendar.SECOND, 0);
					cal.set(Calendar.MILLISECOND, 0);
					for(Integer memId:memberList){
						try{
							log.info("Matching Member ID = "+memId);
							MemberCustomer member = em.find(MemberCustomer.class, memId);
							int myScoreTotal = customerControl.myScoreTotal(member);
							int leftScoreTotal = customerControl.leftScoreTotal(member);
							int rightScoreTotal = customerControl.rightScoreTotal(member);
							int leftPvDay = customerControl.leftScoreByDate(member, cal.getTime());
							int rightPvDay = customerControl.rightScoreByDate(member, cal.getTime());
							int leftMat = 0;
							int rightMat = 0;
							int matchingPvDay = 0;
							int matchingUse = 0;
							int matchingBalance = 0;
							int positionMatching = 0;
							if(member.getPositionId()!=null){
								positionMatching = StringUtil.n2b(member.getPositionId().getMatching());
							}
							matchingBalance = positionMatching - StringUtil.n2b(member.getMatchingUse());
							matchingBalance = matchingBalance<=0?positionMatching:matchingBalance;
							if(matchingBalance > 0){
								leftMat = leftPvDay/1000;
								rightMat = rightPvDay/1000;
								matchingPvDay = leftMat<rightMat?(leftMat*1000):(rightMat*1000);
								matchingUse = leftMat<rightMat?leftMat:rightMat;
								if(matchingUse > matchingBalance){
									matchingPvDay = matchingBalance*1000;
									matchingUse = matchingBalance;
								}
							}
							matchingBalance = matchingBalance-matchingUse;
							TransactionScoreMatching trxMatch = new TransactionScoreMatching();
							TransactionScoreMatchingPK trxMatchPk = new TransactionScoreMatchingPK();
							trxMatchPk.setCustomerId(member.getCustomerId());
							trxMatchPk.setTrxMatchingDate(cal.getTime());
							trxMatch.setTransactionScoreMatchingPK(trxMatchPk);
							trxMatch.setTotalPvLeft(new BigDecimal(leftPvDay));
							trxMatch.setTotalPvRight(new BigDecimal(rightPvDay));
							trxMatch.setTotalPvMatching(new BigDecimal(matchingPvDay));
							trxMatch.setMatchingUse(matchingUse);
							trxMatch.setMatchingBalance(matchingBalance);
							trxMatch.setTrxMatchingStatus(new Integer(0));
							trxMatch.setTrxMatchingFlag(new Integer(0));
							trxMatch.setCreateBy(new Integer(2));
							trxMatch.setCreateDate(new Date());
							trxMatch.setUpdateBy(new Integer(2));
							trxMatch.setUpdateDate(new Date());
							transactionScoreMatchingControl.insertOrUpdate(trxMatch);
							member.setMatchingUse(positionMatching-matchingBalance);
							member.setScoreMy(myScoreTotal);
							member.setScoreLeft(leftScoreTotal);
							member.setScoreRight(rightScoreTotal);
							member.setUpdateBy(new Integer(2));
							member.setUpdateDate(new Date());
							customerControl.insertOrUpdate(member);
						}catch(Exception ex){
							ex.printStackTrace();
						}
					}
				}
			}// end if HH = 00
		}catch(Exception e){
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		log.info("Scheduled Matching Processor Time = "+((endTime-startTime)/1000d)+"s");
	}
}  