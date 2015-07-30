package co.th.aten.network.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.TransactionMoney;
import co.th.aten.network.i18n.AppBundleKey;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.StringUtil;

@ViewScoped	
@Named
public class MoneyAddAndDeductController implements Serializable {

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

	private MemberCustomer memberSearch;
	private Date newDate;
	private String memberCode;
	private String memberName;
	private Double deductMoney;
	private Double addMoney;
	private String remark;

	@PostConstruct
	public void init(){
		log.info("init method MoneyAddAndDeductController");
		newDate = new Date();
		memberSearch = null;
		memberCode = "";
		memberName = "";
		deductMoney = null;
		addMoney = null;
		remark = "";
	}

	public void search(){
		try{
			memberSearch = null;
			if(memberCode!=null && memberCode.length()>0){
				memberSearch = (MemberCustomer)em.createQuery("From MemberCustomer Where customerMember =:customerMember ")
						.setParameter("customerMember", memberCode).getSingleResult();
				memberName = StringUtil.n2b(memberSearch.getTitleName())+StringUtil.n2b(memberSearch.getFirstName())+" "+StringUtil.n2b(memberSearch.getLastName());
			}else{
				memberName = "";
				messages.error(new AppBundleKey("error.label.money.inputMemberCode",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
			}
		}catch(Exception e){
			memberName = "";
			log.info("Error : "+e.getMessage());
			messages.error(new AppBundleKey("error.label.money.notFoundMemberCode",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}
	}

	public void confirmMoney(){
		try{
			TransactionMoney trxMoney = new TransactionMoney();
			trxMoney.setTrxMoneyDatetime(new Date());
			trxMoney.setCustomerId(memberSearch.getCustomerId());
			if(deductMoney!=null && deductMoney > 0){
				trxMoney.setAmount(new BigDecimal(StringUtil.n2b(deductMoney)));
				trxMoney.setTrxMoneyStatus(new Integer(1));
			}else{
				trxMoney.setAmount(new BigDecimal(StringUtil.n2b(addMoney)));
				trxMoney.setTrxMoneyStatus(new Integer(2));
			}
			trxMoney.setTrxMoneyFlag(new Integer(0));
			trxMoney.setRemark(remark);
			trxMoney.setCreateBy(currentUser.getCurrentAccount().getUserId());
			trxMoney.setCreateDate(new Date());
			trxMoney.setUpdateBy(currentUser.getCurrentAccount().getUserId());
			trxMoney.setUpdateDate(new Date());
			double moneyOld = StringUtil.n2b(memberSearch.geteMoney()).doubleValue();
			if(deductMoney!=null && deductMoney > 0){
				memberSearch.seteMoney(new BigDecimal(moneyOld-StringUtil.n2b(deductMoney)));
			}else{
				memberSearch.seteMoney(new BigDecimal(moneyOld+StringUtil.n2b(addMoney)));
			}
			trxMoney.setBalance(memberSearch.geteMoney());
			em.persist(trxMoney);
			em.merge(memberSearch);
			memberSearch = null;
			messages.info(new AppBundleKey("info.label.money.success",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}catch(Exception e){
			e.printStackTrace();
			messages.error(new AppBundleKey("error.label.money.fail",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}
	}

	public Date getNewDate() {
		return newDate;
	}

	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}

	public String getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(String memberCode) {
		this.memberCode = memberCode;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Double getDeductMoney() {
		return deductMoney;
	}

	public void setDeductMoney(Double deductMoney) {
		this.deductMoney = deductMoney;
	}

	public Double getAddMoney() {
		return addMoney;
	}

	public void setAddMoney(Double addMoney) {
		this.addMoney = addMoney;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public MemberCustomer getMemberSearch() {
		return memberSearch;
	}

	public void setMemberSearch(MemberCustomer memberSearch) {
		this.memberSearch = memberSearch;
	}

}
