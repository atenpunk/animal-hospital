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
import co.th.aten.network.entity.TransactionMoneyStatus;
import co.th.aten.network.i18n.AppBundleKey;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.HashUtil;
import co.th.aten.network.util.StringUtil;

@ViewScoped	
@Named
public class MoneyTransferController implements Serializable {

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

	private MemberCustomer memberTransfer;
	private MemberCustomer memberReceive;
	private Date newDate;
	private String memberCodeTransfer;
	private String memberNameTransfer;
	private String memberCodeReceive;
	private String memberNameReceive;
	private Double transferAmount;
	private String passOfTransfer;

	@PostConstruct
	public void init(){
		log.info("init method MoneyTransferController");
		newDate = new Date();
		try{
			memberTransfer = currentUser.getCurrentAccount().getCustomerId();
			memberReceive = null;
			memberCodeTransfer = memberTransfer.getCustomerMember();
			memberNameTransfer = customerControl.genNameMenber(memberTransfer);
			memberCodeReceive = "";
			memberNameReceive = "";
			transferAmount = null;
			passOfTransfer = "";
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void searchReceive(){
		try{
			memberReceive = null;
			if(memberCodeReceive!=null && memberCodeReceive.length()>0){
				memberReceive = (MemberCustomer)em.createQuery("From MemberCustomer Where customerMember =:customerMember ")
						.setParameter("customerMember", memberCodeReceive).getSingleResult();
				memberNameReceive = customerControl.genNameMenber(memberReceive);
			}else{
				memberNameReceive = "";
				messages.error(new AppBundleKey("error.label.money.inputMemberCode",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
			}
		}catch(Exception e){
			memberNameReceive = "";
			log.info("Error : "+e.getMessage());
			messages.error(new AppBundleKey("error.label.money.notFoundMemberCode",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}
	}

	public void confirmMoney(){
		try{
			HashUtil hashUtil = new HashUtil();
			if(currentUser.getCurrentAccount().getPassword().equals(hashUtil.hash(passOfTransfer))){
				if(StringUtil.n2b(memberTransfer.geteMoney()).doubleValue() >= StringUtil.n2b(transferAmount).doubleValue()){
					TransactionMoney trxMoneyTransfer = new TransactionMoney();
					trxMoneyTransfer.setTrxMoneyDatetime(new Date());
					trxMoneyTransfer.setCustomerId(memberTransfer.getCustomerId());
					trxMoneyTransfer.setAmount(new BigDecimal(StringUtil.n2b(transferAmount)));
					TransactionMoneyStatus trxStatus = em.find(TransactionMoneyStatus.class, new Integer(4));
					trxMoneyTransfer.setTrxMoneyStatus(trxStatus.getStatusId());// transfer (-)
					trxMoneyTransfer.setTrxMoneyFlag(new Integer(0));
					trxMoneyTransfer.setRemark(trxStatus.getStatusDesc());
					trxMoneyTransfer.setCreateBy(currentUser.getCurrentAccount().getUserId());
					trxMoneyTransfer.setCreateDate(new Date());
					trxMoneyTransfer.setUpdateBy(currentUser.getCurrentAccount().getUserId());
					trxMoneyTransfer.setUpdateDate(new Date());
					trxMoneyTransfer.setReceiveCumtomerId(memberReceive.getCustomerId());
					double moneyTransferOld = StringUtil.n2b(memberTransfer.geteMoney()).doubleValue();
					memberTransfer.seteMoney(new BigDecimal(moneyTransferOld-StringUtil.n2b(transferAmount)));
					memberTransfer.setUpdateBy(currentUser.getCurrentAccount().getUserId());
					memberTransfer.setUpdateDate(new Date());
					trxMoneyTransfer.setBalance(memberTransfer.geteMoney());
					em.persist(trxMoneyTransfer);
					em.merge(memberTransfer);
					currentUser.getCurrentAccount().getCustomerId().seteMoney(memberTransfer.geteMoney());

					TransactionMoney trxMoneyReceive = new TransactionMoney();
					trxMoneyReceive.setTrxMoneyDatetime(new Date());
					trxMoneyReceive.setCustomerId(memberReceive.getCustomerId());
					trxMoneyReceive.setAmount(new BigDecimal(StringUtil.n2b(transferAmount)));
					TransactionMoneyStatus trxStatusReceive = em.find(TransactionMoneyStatus.class, new Integer(5));
					trxMoneyReceive.setTrxMoneyStatus(trxStatusReceive.getStatusId());// receive (+)
					trxMoneyReceive.setTrxMoneyFlag(new Integer(0));
					trxMoneyReceive.setRemark(trxStatusReceive.getStatusDesc());
					trxMoneyReceive.setCreateBy(currentUser.getCurrentAccount().getUserId());
					trxMoneyReceive.setCreateDate(new Date());
					trxMoneyReceive.setUpdateBy(currentUser.getCurrentAccount().getUserId());
					trxMoneyReceive.setUpdateDate(new Date());
					double moneyReceiveOld = StringUtil.n2b(memberReceive.geteMoney()).doubleValue();
					memberReceive.seteMoney(new BigDecimal(moneyReceiveOld+StringUtil.n2b(transferAmount)));
					memberReceive.setUpdateBy(currentUser.getCurrentAccount().getUserId());
					memberReceive.setUpdateDate(new Date());
					trxMoneyReceive.setBalance(memberReceive.geteMoney());
					em.persist(trxMoneyReceive);
					em.merge(memberReceive);

					memberTransfer = null;
					memberReceive = null;
					passOfTransfer = "";
					messages.info(new AppBundleKey("info.label.money.successTransfer",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
				}else{
					messages.error(new AppBundleKey("error.label.money.notEnoughTransfer",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
				}
			}else{
				messages.error(new AppBundleKey("error.label.money.passwordNotEquals",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
			}
		}catch(Exception e){
			e.printStackTrace();
			messages.error(new AppBundleKey("error.label.money.failTransfer",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}
	}

	public Date getNewDate() {
		return newDate;
	}

	public void setNewDate(Date newDate) {
		this.newDate = newDate;
	}

	public MemberCustomer getMemberTransfer() {
		return memberTransfer;
	}

	public void setMemberTransfer(MemberCustomer memberTransfer) {
		this.memberTransfer = memberTransfer;
	}

	public MemberCustomer getMemberReceive() {
		return memberReceive;
	}

	public void setMemberReceive(MemberCustomer memberReceive) {
		this.memberReceive = memberReceive;
	}

	public String getMemberCodeTransfer() {
		return memberCodeTransfer;
	}

	public void setMemberCodeTransfer(String memberCodeTransfer) {
		this.memberCodeTransfer = memberCodeTransfer;
	}

	public String getMemberNameTransfer() {
		return memberNameTransfer;
	}

	public void setMemberNameTransfer(String memberNameTransfer) {
		this.memberNameTransfer = memberNameTransfer;
	}

	public String getMemberCodeReceive() {
		return memberCodeReceive;
	}

	public void setMemberCodeReceive(String memberCodeReceive) {
		this.memberCodeReceive = memberCodeReceive;
	}

	public String getMemberNameReceive() {
		return memberNameReceive;
	}

	public void setMemberNameReceive(String memberNameReceive) {
		this.memberNameReceive = memberNameReceive;
	}

	public Double getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(Double transferAmount) {
		this.transferAmount = transferAmount;
	}

	public String getPassOfTransfer() {
		return passOfTransfer;
	}

	public void setPassOfTransfer(String passOfTransfer) {
		this.passOfTransfer = passOfTransfer;
	}

}
