package co.th.aten.network.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.model.MoneyReportModel;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.StringUtil;

@ViewScoped	
@Named
public class MoneyReportTransactionController implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private Logger log;
	@Inject
	@DBDefault
	private EntityManager em;
	@Inject
	private CurrentUserManager currentUser;
	@Inject
	private FacesContext facesContext;
	@Inject
	private CustomerControl customerControl;
	@Inject
	private Messages messages;

	private List<MoneyReportModel> moneyReportModelList;
	private Date startDate;
	private Date endDate;

	private double totalDeduct;
	private double totalAdd;

	@PostConstruct
	public void init(){
		log.info("init method MoneyReportTransactionController");
		Calendar calStart = Calendar.getInstance();
		calStart.set(Calendar.DAY_OF_MONTH,
				calStart.getActualMinimum(Calendar.DAY_OF_MONTH));
		calStart.set(Calendar.HOUR_OF_DAY, 0);
		calStart.set(Calendar.MINUTE, 0);
		calStart.set(Calendar.SECOND, 0);
		calStart.set(Calendar.MILLISECOND, 0);
		startDate = calStart.getTime();
		Calendar calEnd = Calendar.getInstance();
		calEnd.set(Calendar.DAY_OF_MONTH,
				calEnd.getActualMaximum(Calendar.DAY_OF_MONTH));
		calEnd.set(Calendar.HOUR_OF_DAY, 23);
		calEnd.set(Calendar.MINUTE, 59);
		calEnd.set(Calendar.SECOND, 59);
		calEnd.set(Calendar.MILLISECOND, 999);
		endDate = calEnd.getTime();
		search();
	}

	public void search(){
		try{
			totalDeduct = 0;
			totalAdd = 0;
			moneyReportModelList = new ArrayList<MoneyReportModel>();
			String sql = "Select mem.customerMember, mem.titleName, mem.firstName, mem.lastName, " +
					" trx.trxMoneyDatetime, trx.amount, trx.balance, trx.trxMoneyStatus, " +
					" trx.remark, us.userName " +
					" From TransactionMoney trx, MemberCustomer mem, UserLogin us " +
					" Where trx.trxMoneyDatetime Between :startDate And :endDate " +
					" And trx.customerId = mem.customerId " +
					" And trx.createBy = us.userId " +
					" And trx.customerId =:customerId "+
					" Order By trx.trxMoneyDatetime desc ";
			List<Object[]> objList = em.createQuery(sql,Object[].class)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("customerId", currentUser.getCurrentAccount().getCustomerId().getCustomerId())
					.getResultList();
			if(objList!=null && objList.size()>0){
				int order = 0;
				for(Object[] ob:objList){
					MoneyReportModel model = new MoneyReportModel();
					model.setOrder(++order);
					model.setMemberCode(StringUtil.n2b((String)ob[0]));
					String nameMem = StringUtil.n2b((String)ob[1])+" "+StringUtil.n2b((String)ob[2])+" "+StringUtil.n2b((String)ob[3]);
					model.setMemberName(nameMem);
					model.setDate((Date)ob[4]);
					if(StringUtil.n2b((Integer)ob[7]).intValue()==1
							|| StringUtil.n2b((Integer)ob[7]).intValue()==3
							|| StringUtil.n2b((Integer)ob[7]).intValue()==4){
						model.setDeduct(StringUtil.n2b((BigDecimal)ob[5]).doubleValue());
					}else{
						model.setAdd(StringUtil.n2b((BigDecimal)ob[5]).doubleValue());
					}
					model.setBalance(StringUtil.n2b((BigDecimal)ob[6]).doubleValue());
					model.setRemark(StringUtil.n2b((String)ob[8]));
					model.setTrxBy(StringUtil.n2b((String)ob[9]));
					model.setTrxTime((Date)ob[4]);
					totalDeduct += model.getDeduct();
					totalAdd += model.getAdd();
					moneyReportModelList.add(model);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public List<MoneyReportModel> getMoneyReportModelList() {
		return moneyReportModelList;
	}

	public void setMoneyReportModelList(List<MoneyReportModel> moneyReportModelList) {
		this.moneyReportModelList = moneyReportModelList;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public double getTotalDeduct() {
		return totalDeduct;
	}

	public void setTotalDeduct(double totalDeduct) {
		this.totalDeduct = totalDeduct;
	}

	public double getTotalAdd() {
		return totalAdd;
	}

	public void setTotalAdd(double totalAdd) {
		this.totalAdd = totalAdd;
	}

}
