package co.th.aten.network.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.control.TransactionHeaderControl;
import co.th.aten.network.control.TransactionScoreMatchingControl;
import co.th.aten.network.control.TransactionScorePackageControl;
import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.MemberPosition;
import co.th.aten.network.entity.TransactionScoreMatching;
import co.th.aten.network.entity.TransactionScoreMatchingPK;
import co.th.aten.network.entity.TransactionScorePackage;
import co.th.aten.network.entity.TransactionScorePackagePK;
import co.th.aten.network.i18n.AppBundleKey;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.StringUtil;

@Stateless
@Named
@TransactionManagement(TransactionManagementType.BEAN)
public class CalculateDailyController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5443351151396868724L;

	@PersistenceContext
	private EntityManager em;
	@Resource
	private SessionContext sessionCtx;
	@Inject
	private Messages messages;
	@Inject
	private CustomerControl customerControl;
	@Inject
	private TransactionScoreMatchingControl transactionScoreMatchingControl;
	@Inject
	private TransactionHeaderControl transactionHeaderControl;
	@Inject
	private TransactionScorePackageControl transactionScorePackageControl;
	@Inject
	Logger log;
	@Inject
	private CurrentUserManager currentUser;
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
	private SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
	private Date dateTime = new Date();

	public void execute() {
		long startTime = System.currentTimeMillis();
		try{
			log.info("####################################################");
			log.info("##### Start CalculateDailyController Processor #####");
			log.info("####################################################");
			if(dateTime!=null){
				Calendar date = Calendar.getInstance();
				date.setTime(dateTime);
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
				endDateTime.setTime(date.getTime());
				endDateTime.set(Calendar.HOUR_OF_DAY, 23);
				endDateTime.set(Calendar.MINUTE, 59);
				endDateTime.set(Calendar.SECOND, 59);
				endDateTime.set(Calendar.MILLISECOND, 999);
				log.info("DATE            = "+sdf.format(date.getTime()));
				log.info("Start Date Time = "+sdf.format(startDateTime.getTime()));
				log.info("End Date Time   = "+sdf.format(endDateTime.getTime()));
				List<Integer> memberList = em.createQuery("Select customerId From MemberCustomer Order By customerId asc "
						,Integer.class).getResultList();
				if(memberList!=null){
					log.info("Member List Size = "+memberList.size());
					boolean chkUpdate = false;
					int maxRoundId = StringUtil.n2b(transactionScoreMatchingControl.getRoundIdByDate(date.getTime()));
					if(maxRoundId==0){
						maxRoundId = StringUtil.n2b(transactionScoreMatchingControl.getMaxRoundId());
						maxRoundId = maxRoundId+1;
						chkUpdate = true;
					}
					log.info("maxRoundId = "+maxRoundId);
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
							int oldPvLeft = leftScoreTotal - sumMatching;
							int oldPvRight = rightScoreTotal - sumMatching;
							int totalPvLeft = oldPvLeft + leftPvDay;
							int totalPvRight = oldPvRight + rightPvDay;
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
							trxMatchPk.setTrxMatchingDate(date.getTime());
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
							trxMatch.setTrxMatchingStatus(new Integer(0));
							trxMatch.setTrxMatchingFlag(new Integer(0));
							if(chkUpdate){
								trxMatch.setCreateBy(currentUser.getCurrentAccount().getUserId());
								trxMatch.setCreateDate(new Date());
							}
							trxMatch.setUpdateBy(currentUser.getCurrentAccount().getUserId());
							trxMatch.setUpdateDate(new Date());
							transactionScoreMatchingControl.insertOrUpdate(trxMatch);
							// Delete Transaction Packgate เก่าออกก่อน
							transactionScorePackageControl.delete(date.getTime(), StringUtil.n2b(member.getCustomerId()));
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
										TransactionScorePackage trxPackage = new TransactionScorePackage();
										TransactionScorePackagePK packagePk = new TransactionScorePackagePK();
										packagePk.setTrxPackageDate(date.getTime());
										packagePk.setSuggestId(StringUtil.n2b(member.getCustomerId()));
										packagePk.setCustomerId(memSugg.getCustomerId());								
										trxPackage.setTransactionScorePackagePK(packagePk);
										trxPackage.setProductId(StringUtil.n2b((Integer)ob[2]));
										trxPackage.setPvPackage(new BigDecimal(pvPackage));
										trxPackage.setTrxPackageStatus(new Integer(0));
										trxPackage.setTrxPackageFlag(new Integer(1));// 1 = package bonus
										trxPackage.setCreateBy(currentUser.getCurrentAccount().getUserId());
										trxPackage.setCreateDate(new Date());
										trxPackage.setUpdateBy(currentUser.getCurrentAccount().getUserId());
										trxPackage.setUpdateDate(new Date());
										transactionScorePackageControl.insertOrUpdate(trxPackage);
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
												TransactionScorePackage trx = new TransactionScorePackage();
												TransactionScorePackagePK trxPk = new TransactionScorePackagePK();
												trxPk.setTrxPackageDate(date.getTime());
												trxPk.setSuggestId(StringUtil.n2b(member.getCustomerId()));
												trxPk.setCustomerId(memSuggUpper.getCustomerId());								
												trx.setTransactionScorePackagePK(trxPk);
												trx.setProductId(StringUtil.n2b((Integer)ob[2]));
												trx.setPvPackage(new BigDecimal(maxPv-pvPackage));
												trx.setTrxPackageStatus(new Integer(0));
												trx.setTrxPackageFlag(new Integer(2)); // 2 = โบนัสส่วนต่าง
												trx.setCreateBy(currentUser.getCurrentAccount().getUserId());
												trx.setCreateDate(new Date());
												trx.setUpdateBy(currentUser.getCurrentAccount().getUserId());
												trx.setUpdateDate(new Date());
												transactionScorePackageControl.insertOrUpdate(trx);
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
						log.info("CalculateDaily Member ID = "+memId+", Time = "+((System.currentTimeMillis()-startTimeMem)/1000d)+"s");
					}
				}
				messages.info(new AppBundleKey("error.label.calculate.finish",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()),sdfDate.format(dateTime));
			}else{
				messages.error(new AppBundleKey("error.label.calculate.dateNull",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
			}
		}catch(Exception e){
			e.printStackTrace();
			messages.error(new AppBundleKey("error.label.calculate.fail",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}
		log.info("CalculateDailyController Processor Time = "+((System.currentTimeMillis()-startTime)/1000d)+"s");
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	
	public static void main(String[]args){
		SimpleDateFormat sss = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
		System.out.println(sss.format(new Date()));
	}
}
