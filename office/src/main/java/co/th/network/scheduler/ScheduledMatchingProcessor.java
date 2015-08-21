package co.th.network.scheduler;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.ejb.Schedule;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.control.TransactionScoreMatchingControl;
import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.MemberPosition;
import co.th.aten.network.entity.TransactionScoreMatching;
import co.th.aten.network.entity.TransactionScoreMatchingPK;
import co.th.aten.network.util.StringUtil;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ScheduledMatchingProcessor {

	@PersistenceContext
	private EntityManager em;
	@Resource
	private SessionContext sessionCtx;
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
			//			if(sdfHH.format(new Date()).equals("00")){
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
				int maxRoundId = StringUtil.n2b(transactionScoreMatchingControl.getMaxRoundId());
				maxRoundId = maxRoundId+1;
				for(Integer memId:memberList){
					try{
						sessionCtx.getUserTransaction().begin();
						log.info("Matching Member ID = "+memId);
						MemberCustomer member = em.find(MemberCustomer.class, memId);
						int myScoreTotal = customerControl.myScoreTotal(member, cal.getTime());
						if(myScoreTotal >= 13000 
								&& StringUtil.n2b(member.getPositionId().getLevelId()) < 6){
							member.setPositionId(em.find(MemberPosition.class, new Integer(5)));// 5 = SP
						}else if(myScoreTotal >= 10000 
								&& StringUtil.n2b(member.getPositionId().getLevelId()) < 5){
							member.setPositionId(em.find(MemberPosition.class, new Integer(4)));// 4 = DP
						}else if(myScoreTotal >= 7000 
								&& StringUtil.n2b(member.getPositionId().getLevelId()) < 4){
							member.setPositionId(em.find(MemberPosition.class, new Integer(3)));// 3 = PRO
						}else if(myScoreTotal >= 3000 
								&& StringUtil.n2b(member.getPositionId().getLevelId()) < 3){
							member.setPositionId(em.find(MemberPosition.class, new Integer(2)));// 2 = EX
						}else if(myScoreTotal >= 1000 
								&& StringUtil.n2b(member.getPositionId().getLevelId()) < 2){
							member.setPositionId(em.find(MemberPosition.class, new Integer(1)));// 1 = DIS
						}
						int myScoreDate = customerControl.myScoreDate(member, cal.getTime());
						String sqlUnderLeft = customerControl.genSqlUnder(StringUtil.n2b(member.getLowerLeftId()).intValue());
						String sqlUnderRight = customerControl.genSqlUnder(StringUtil.n2b(member.getLowerRightId()).intValue());
						int leftScoreTotal = customerControl.sumScoreTotal(member, cal.getTime(), sqlUnderLeft);
						int rightScoreTotal = customerControl.sumScoreTotal(member, cal.getTime(), sqlUnderRight);
						int leftPvDay = customerControl.sumScoreDate(member, cal.getTime(), sqlUnderLeft);
						int rightPvDay = customerControl.sumScoreDate(member, cal.getTime(), sqlUnderRight);
						int sumMatching = transactionScoreMatchingControl.sumMathing(member, cal.getTime());
						int oldPvLeft = leftScoreTotal - sumMatching;
						int oldPvRight = rightScoreTotal - sumMatching;
						int totalPvLeft = oldPvLeft;
						int totalPvRight = oldPvRight;
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
							leftMat = totalPvLeft/1000;
							rightMat = totalPvRight/1000;
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
						trxMatchPk.setRoundId(maxRoundId);
						trxMatchPk.setCustomerId(member.getCustomerId());
						trxMatchPk.setTrxMatchingDate(cal.getTime());
						trxMatch.setTransactionScoreMatchingPK(trxMatchPk);
						trxMatch.setOldPvLeft(new BigDecimal(oldPvLeft));
						trxMatch.setOldPvRight(new BigDecimal(oldPvRight));
						trxMatch.setDatePvLeft(new BigDecimal(leftPvDay));
						trxMatch.setDatePvRight(new BigDecimal(rightPvDay));
						trxMatch.setTotalPvLeft(new BigDecimal(totalPvLeft));
						trxMatch.setTotalPvRight(new BigDecimal(totalPvRight));
						trxMatch.setRemainingPvLeft(new BigDecimal(StringUtil.n2b(trxMatch.getTotalPvLeft()).doubleValue() - matchingPvDay));
						trxMatch.setRemainingPvRight(new BigDecimal(StringUtil.n2b(trxMatch.getTotalPvRight()).doubleValue() - matchingPvDay));
						trxMatch.setMatchingPv(new BigDecimal(matchingPvDay));
						trxMatch.setMatchingUse(matchingUse);
						trxMatch.setMatchingBalance(matchingBalance);
						trxMatch.setSelfDatePv(new BigDecimal(myScoreDate));
						trxMatch.setSelfTotalPv(new BigDecimal(myScoreTotal));
//						trxMatch.setRecommendAmount(recommendAmount);
						trxMatch.setTrxMatchingStatus(new Integer(0));
						trxMatch.setTrxMatchingFlag(new Integer(0));
						trxMatch.setCreateBy(new Integer(2));
						trxMatch.setCreateDate(new Date());
						trxMatch.setUpdateBy(new Integer(2));
						trxMatch.setUpdateDate(new Date());
						transactionScoreMatchingControl.insertOrUpdate(trxMatch);
						member.setMatchingUse(positionMatching-matchingBalance);
						member.setScoreMy(myScoreTotal);
						member.setScoreLeft(StringUtil.n2b(trxMatch.getRemainingPvLeft()).intValue());
						member.setScoreRight(StringUtil.n2b(trxMatch.getRemainingPvRight()).intValue());
						member.setUpdateBy(new Integer(2));
						member.setUpdateDate(new Date());
						customerControl.insertOrUpdate(member);
						sessionCtx.getUserTransaction().commit();
					}catch(Exception ex){
						sessionCtx.getUserTransaction().rollback();
						ex.printStackTrace();
					}
				}
			}
			//			}// end if HH = 00
		}catch(Exception e){
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		log.info("Scheduled Matching Processor Time = "+((endTime-startTime)/1000d)+"s");
	}
}  