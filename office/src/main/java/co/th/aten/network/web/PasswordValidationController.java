package co.th.aten.network.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.seam.security.Identity;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.i18n.AppBundleKey;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.annotation.Authenticated;
import co.th.aten.network.util.HashUtil;
import co.th.aten.network.util.StringUtil;

@ViewScoped
@Named
public class PasswordValidationController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5443351151396868724L;

	@Inject
	Logger log;

	@Inject
	@Authenticated
	private Instance<UserLogin> user;

	@Inject
	@DBDefault
	private EntityManager em;

	@Inject
	private MessageFactory factory;
	@Inject
	private Messages messages;
	@Inject
	private Identity identity;

	private String xxxPassword = "";
	private String newPassword = "";
	private String confirmNewPossword = "";
	
	private boolean chkSuccess;
	private String memberId;
	private String memberName;
	
	@PostConstruct
	public void init(){
		log.info("init method PasswordValidationController");
		xxxPassword = "";
		newPassword = "";
		confirmNewPossword = "";
		chkSuccess = false;
		if(user.get().getCustomerId()!=null){
			memberId = user.get().getCustomerId().getCustomerMember();
			memberName = StringUtil.n2b(user.get().getCustomerId().getTitleName())
					+ " "
					+ StringUtil.n2b(user.get().getCustomerId().getFirstName())
					+ " "
					+ StringUtil.n2b(user.get().getCustomerId().getLastName());
		}
	}

	public void storeNewPassword() {
		log.info("####### storeNewPassword()");
		chkSuccess = false;
		try{
			HashUtil hashUtil = new HashUtil();
			if(newPassword!=null 
					&& !newPassword.trim().equals("") 
					&& newPassword.equals(confirmNewPossword)){
				if(user.get().getPassword().equals(hashUtil.hash(xxxPassword))){
					user.get().setPassword(hashUtil.hash(newPassword));
					em.merge(user.get());
					chkSuccess = true;
					identity.logout();
					messages.info(new AppBundleKey("error.label.editPasswordSuccess",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
				}else{
					messages.error(new AppBundleKey("error.label.passwordNotEquals",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
				}
			}else{
				messages.error(new AppBundleKey("error.label.newPasswordNotEqualsConfirm",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
			}
		}catch(Exception e){
			e.printStackTrace();
			messages.error(new AppBundleKey("error.label.editPasswordFail",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}
	}
	
	public String getXxxPassword() {
		return xxxPassword;
	}

	public void setXxxPassword(String xxxPassword) {
		this.xxxPassword = xxxPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmNewPossword() {
		return confirmNewPossword;
	}

	public void setConfirmNewPossword(String confirmNewPossword) {
		this.confirmNewPossword = confirmNewPossword;
	}

	public boolean getChkSuccess() {
		return chkSuccess;
	}

	public void setChkSuccess(boolean chkSuccess) {
		this.chkSuccess = chkSuccess;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

}
