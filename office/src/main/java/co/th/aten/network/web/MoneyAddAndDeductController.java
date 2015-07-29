package co.th.aten.network.web;

import java.io.Serializable;
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
	private double deductMoney;
	private double addMoney;

	@PostConstruct
	public void init(){
		log.info("init method MoneyAddAndDeductController");
		newDate = new Date();
	}

	public void search(){
		try{
			memberSearch = null;
			if(memberCode!=null){
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

	public double getDeductMoney() {
		return deductMoney;
	}

	public void setDeductMoney(double deductMoney) {
		this.deductMoney = deductMoney;
	}

	public double getAddMoney() {
		return addMoney;
	}

	public void setAddMoney(double addMoney) {
		this.addMoney = addMoney;
	}
	
	
}
