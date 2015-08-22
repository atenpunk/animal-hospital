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

import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.control.TransactionHeaderControl;
import co.th.aten.network.control.TransactionScoreMatchingControl;
import co.th.aten.network.control.TransactionScoreMatchingWeeklyControl;
import co.th.aten.network.control.TransactionScorePackageWeeklyControl;
import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.MemberPosition;
import co.th.aten.network.entity.TransactionScoreMatchingWeekly;
import co.th.aten.network.entity.TransactionScoreMatchingWeeklyPK;
import co.th.aten.network.entity.TransactionScorePackageWeekly;
import co.th.aten.network.entity.TransactionScorePackageWeeklyPK;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.util.StringUtil;

@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class ScheduledScoreWeeklyProcessor {

	@Inject
	@DBDefault
	private EntityManager em;
	@Resource
	private SessionContext sessionCtx;
	@Inject
	private CustomerControl customerControl;
	@Inject
	private TransactionScoreMatchingControl transactionScoreMatchingControl;
	@Inject
	private TransactionScoreMatchingWeeklyControl transactionScoreMatchingWeeklyControl;
	@Inject
	private TransactionHeaderControl transactionHeaderControl;
	@Inject
	private TransactionScorePackageWeeklyControl transactionScorePackageWeeklyControl;
	@Inject
	Logger log;

	private SimpleDateFormat sdfHH = new SimpleDateFormat("HH",Locale.US);
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);

	//	@Schedule(hour="*",minute = "*",second="0/10")
	@Schedule(dayOfWeek="Mon",hour="03",minute = "01",second="01")
	public void execute() {
		long startTime = System.currentTimeMillis();
		try{
			log.info("##################################################");
			log.info("##### Start Scheduled Score Weekly Processor #####");
			log.info("##################################################");
			Calendar date = Calendar.getInstance();
			date.add(Calendar.DATE, -7);
			date.set(Calendar.HOUR_OF_DAY, 0);
			date.set(Calendar.MINUTE, 0);
			date.set(Calendar.SECOND, 0);
			date.set(Calendar.MILLISECOND, 0);
			Calendar startDateTime = Calendar.getInstance();
			startDateTime.setTime(date.getTime());
			startDateTime.set(Calendar.HOUR_OF_DAY, 0);
			startDateTime.set(Calendar.MINUTE, 0);
			startDateTime.set(Calendar.SECOND, 0);
			startDateTime.set(Calendar.MILLISECOND, 0);
			Calendar endDateTime = Calendar.getInstance();
			endDateTime.add(Calendar.DATE, -1);
			endDateTime.set(Calendar.HOUR_OF_DAY, 23);
			endDateTime.set(Calendar.MINUTE, 59);
			endDateTime.set(Calendar.SECOND, 59);
			endDateTime.set(Calendar.MILLISECOND, 999);
			log.info("HH   = "+sdfHH.format(new Date()));
			log.info("DATE = "+sdf.format(date.getTime()));
			log.info("Start Date Time = "+sdf.format(startDateTime.getTime()));
			log.info("End Date Time   = "+sdf.format(endDateTime.getTime()));
			if(sdfHH.format(new Date()).equals("03")){
				List<Integer> memberList = em.createQuery("Select customerId From MemberCustomer Order By customerId asc "
						,Integer.class).getResultList();
				if(memberList!=null){
					log.info("Member List Size = "+memberList.size());				
					int maxRoundId = StringUtil.n2b(transactionScoreMatchingWeeklyControl.getMaxRoundId());
					maxRoundId = maxRoundId+1;
					for(Integer memId:memberList){
						long startTimeMem = System.currentTimeMillis();
						try{
							sessionCtx.getUserTransaction().begin();
							MemberCustomer member = em.find(MemberCustomer.class, memId);
							int myScoreTotal = transactionHeaderControl.myScoreTotal(member, date.getTime());
							int myScoreDate = transactionHeaderControl.myScoreDate(member, startDateTime.getTime(), endDateTime.getTime());
							String sqlUnderLeft = customerControl.genSqlUnder(StringUtil.n2b(member.getLowerLeftId()).intValue());
							String sqlUnderRight = customerControl.genSqlUnder(StringUtil.n2b(member.getLowerRightId()).intValue());
							int leftScoreTotal = transactionHeaderControl.sumScoreTotal(date.getTime(), sqlUnderLeft);
							int rightScoreTotal = transactionHeaderControl.sumScoreTotal(date.getTime(), sqlUnderRight);
							int leftPvDay = transactionHeaderControl.sumScoreDate(startDateTime.getTime(), endDateTime.getTime(), sqlUnderLeft);
							int rightPvDay = transactionHeaderControl.sumScoreDate(startDateTime.getTime(), endDateTime.getTime(), sqlUnderRight);
							int sumMatching = transactionScoreMatchingControl.sumMathing(member, date.getTime());
							//						int sumMatching = transactionScoreMatchingWeeklyControl.sumMathing(member, date.getTime());
							int oldPvLeft = leftScoreTotal - sumMatching;
							int oldPvRight = rightScoreTotal - sumMatching;
							int totalPvLeft = oldPvLeft+leftPvDay;
							int totalPvRight = oldPvRight+rightPvDay;
							int leftMat = totalPvLeft/1000;
							int rightMat = totalPvRight/1000;
							int matchingPvDay = leftMat<rightMat?(leftMat*1000):(rightMat*1000);
							TransactionScoreMatchingWeekly trxMatch = new TransactionScoreMatchingWeekly();
							TransactionScoreMatchingWeeklyPK trxMatchPk = new TransactionScoreMatchingWeeklyPK();
							trxMatchPk.setRoundId(maxRoundId);
							trxMatchPk.setCustomerId(member.getCustomerId());
							trxMatchPk.setTrxStartDate(startDateTime.getTime());
							trxMatchPk.setTrxEndDate(endDateTime.getTime());
							trxMatch.setTransactionScoreMatchingWeeklyPK(trxMatchPk);
							trxMatch.setOldPvLeft(new BigDecimal(oldPvLeft));
							trxMatch.setOldPvRight(new BigDecimal(oldPvRight));
							trxMatch.setDatePvLeft(new BigDecimal(leftPvDay));
							trxMatch.setDatePvRight(new BigDecimal(rightPvDay));
							trxMatch.setTotalPvLeft(new BigDecimal(totalPvLeft));
							trxMatch.setTotalPvRight(new BigDecimal(totalPvRight));
							trxMatch.setRemainingPvLeft(new BigDecimal(StringUtil.n2b(trxMatch.getTotalPvLeft()).doubleValue() - matchingPvDay));
							trxMatch.setRemainingPvRight(new BigDecimal(StringUtil.n2b(trxMatch.getTotalPvRight()).doubleValue() - matchingPvDay));
							trxMatch.setMatchingPv(new BigDecimal(matchingPvDay));
							trxMatch.setSelfDatePv(new BigDecimal(myScoreDate));
							trxMatch.setSelfTotalPv(new BigDecimal(myScoreTotal));			
							trxMatch.setTrxMatchingStatus(new Integer(0));
							trxMatch.setTrxMatchingFlag(new Integer(0));
							trxMatch.setCreateBy(new Integer(2));
							trxMatch.setCreateDate(new Date());
							trxMatch.setUpdateBy(new Integer(2));
							trxMatch.setUpdateDate(new Date());
							transactionScoreMatchingWeeklyControl.insertOrUpdate(trxMatch);
							List<Object[]> packageList = transactionHeaderControl.genProductPackgateByMember(member, startDateTime.getTime(), endDateTime.getTime());
							if(packageList!=null){
								for(Object[] ob:packageList){
									MemberCustomer memSugg = em.find(MemberCustomer.class, member.getRecommendId());
									if(memSugg!=null){
										int pv = StringUtil.n2b((BigDecimal)ob[3]).intValue();
										int pvPackage = 0;
										int maxPv = 0;
										if(pv>=9000){// SP
											MemberPosition position = em.find(MemberPosition.class, new Integer(5));
											maxPv = StringUtil.n2b(position.getRecommendAmount()).intValue();
											if(memSugg.getPositionId().getPositionId()==1){
												pvPackage = 4000;
											}else if(memSugg.getPositionId().getPositionId()==2){
												pvPackage = 8000;
											}else if(memSugg.getPositionId().getPositionId()==3
													|| memSugg.getPositionId().getPositionId()==4
													|| memSugg.getPositionId().getPositionId()==5){
												pvPackage = 12000;
											}
										}else if(pv>=7000){ // DP
											MemberPosition position = em.find(MemberPosition.class, new Integer(4));
											maxPv = StringUtil.n2b(position.getRecommendAmount()).intValue();
											if(memSugg.getPositionId().getPositionId()==1){
												pvPackage = 4000;
											}else if(memSugg.getPositionId().getPositionId()==2){
												pvPackage = 8000;
											}else if(memSugg.getPositionId().getPositionId()==3
													|| memSugg.getPositionId().getPositionId()==4
													|| memSugg.getPositionId().getPositionId()==5){
												pvPackage = 12000;
											}
										}else if(pv>=3000){ // PRO
											MemberPosition position = em.find(MemberPosition.class, new Integer(3));
											maxPv = StringUtil.n2b(position.getRecommendAmount()).intValue();
											if(memSugg.getPositionId().getPositionId()==1){
												pvPackage = 4000;
											}else if(memSugg.getPositionId().getPositionId()==2){
												pvPackage = 8000;
											}else if(memSugg.getPositionId().getPositionId()==3
													|| memSugg.getPositionId().getPositionId()==4
													|| memSugg.getPositionId().getPositionId()==5){
												pvPackage = 12000;
											}
										}else if(pv>=1000){ // EX
											MemberPosition position = em.find(MemberPosition.class, new Integer(2));
											maxPv = StringUtil.n2b(position.getRecommendAmount()).intValue();
											if(memSugg.getPositionId().getPositionId()==1){
												pvPackage = 2000;
											}else if(memSugg.getPositionId().getPositionId()==2
													|| memSugg.getPositionId().getPositionId()==3
													|| memSugg.getPositionId().getPositionId()==4
													|| memSugg.getPositionId().getPositionId()==5){
												pvPackage = 4000;
											}
										}else if(pv>=400){ // DIS
											MemberPosition position = em.find(MemberPosition.class, new Integer(1));
											maxPv = StringUtil.n2b(position.getRecommendAmount()).intValue();
											if(memSugg.getPositionId().getPositionId()==1
													|| memSugg.getPositionId().getPositionId()==2
													|| memSugg.getPositionId().getPositionId()==3
													|| memSugg.getPositionId().getPositionId()==4
													|| memSugg.getPositionId().getPositionId()==5){
												pvPackage = 1000;
											}
										}
										TransactionScorePackageWeekly trxPackage = new TransactionScorePackageWeekly();
										TransactionScorePackageWeeklyPK packagePk = new TransactionScorePackageWeeklyPK();
										packagePk.setTrxStartDate(startDateTime.getTime());
										packagePk.setTrxEndDate(endDateTime.getTime());
										packagePk.setSuggestId(StringUtil.n2b(member.getCustomerId()));
										packagePk.setCustomerId(memSugg.getCustomerId());								
										trxPackage.setTransactionScorePackageWeeklyPK(packagePk);
										trxPackage.setProductId(StringUtil.n2b((Integer)ob[2]));
										trxPackage.setPvPackage(new BigDecimal(pvPackage));
										trxPackage.setTrxPackageStatus(new Integer(0));
										trxPackage.setTrxPackageFlag(new Integer(1));// 1 = package bonus
										trxPackage.setCreateBy(new Integer(2));
										trxPackage.setCreateDate(new Date());
										trxPackage.setUpdateBy(new Integer(2));
										trxPackage.setUpdateDate(new Date());
										transactionScorePackageWeeklyControl.insertOrUpdate(trxPackage);
										if(pvPackage < maxPv){
											MemberCustomer memSuggUpper = em.find(MemberCustomer.class, memSugg.getRecommendId());
											while(memSuggUpper!=null 
													&& (memSuggUpper.getPositionId().getPositionId()!=3
													|| memSuggUpper.getPositionId().getPositionId()!=4
													|| memSuggUpper.getPositionId().getPositionId()!=5)){
												if(memSuggUpper.getRecommendId()!=null){
													memSuggUpper = em.find(MemberCustomer.class, memSuggUpper.getRecommendId());
												}else{
													break;
												}
											}
											if(memSuggUpper!=null){
												TransactionScorePackageWeekly trx = new TransactionScorePackageWeekly();
												TransactionScorePackageWeeklyPK trxPk = new TransactionScorePackageWeeklyPK();
												trxPk.setTrxStartDate(startDateTime.getTime());
												trxPk.setTrxEndDate(endDateTime.getTime());
												trxPk.setSuggestId(StringUtil.n2b(member.getCustomerId()));
												trxPk.setCustomerId(memSuggUpper.getCustomerId());								
												trx.setTransactionScorePackageWeeklyPK(trxPk);
												trx.setProductId(StringUtil.n2b((Integer)ob[2]));
												trx.setPvPackage(new BigDecimal(maxPv-pvPackage));
												trx.setTrxPackageStatus(new Integer(0));
												trx.setTrxPackageFlag(new Integer(2)); // 2 = โบนัสส่วนต่าง
												trx.setCreateBy(new Integer(2));
												trx.setCreateDate(new Date());
												trx.setUpdateBy(new Integer(2));
												trx.setUpdateDate(new Date());
												transactionScorePackageWeeklyControl.insertOrUpdate(trx);
											}
										}
									}
								}
							}
							sessionCtx.getUserTransaction().commit();
						}catch(Exception ex){
							sessionCtx.getUserTransaction().rollback();
							log.info("rollback member id = "+memId);
							ex.printStackTrace();
						}
						log.info("Package Member ID = "+memId+", Time = "+((System.currentTimeMillis()-startTimeMem)/1000d)+"s");
					}
				}
			}// end if HH = 03
		}catch(Exception e){
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		log.info("Scheduled Score Weekly Processor Time = "+((endTime-startTime)/1000d)+"s");
	}
}  