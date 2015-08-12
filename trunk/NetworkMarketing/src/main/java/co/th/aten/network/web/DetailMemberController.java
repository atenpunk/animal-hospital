package co.th.aten.network.web;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.i18n.AppBundleKey;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.HashUtil;

@ViewScoped
//@SessionScoped
@Named
public class DetailMemberController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5443351151396868724L;

	@Inject
	Logger log;
	@Inject
	private MessageFactory factory;
	@Inject
	private Messages messages;
	@Inject
	private CustomerControl customerControl;
	@Inject
	@DBDefault
	private EntityManager em;
	@Inject
	private CurrentUserManager currentUser;
	
	private boolean checkPassword;
	private MemberCustomer member;

	@PostConstruct
	public void init(){
		log.info("init method DetailMemberController");
		try{
			Map<String, String> params =FacesContext.getCurrentInstance().
					getExternalContext().getRequestParameterMap();
			String memberId = params.get("d");
			String password = params.get("g");
			log.info("memberId = "+memberId);
			log.info("password = "+password);
			checkPassword = false;
			if((memberId!=null && memberId.length()>0)
					&& (password!=null && password.length()>0)){
				member = em.find(MemberCustomer.class, new Integer(memberId));
				List<UserLogin> users = em.createQuery("From UserLogin where loginName=:login ", UserLogin.class)
						.setParameter("login", member.getCustomerMember())
						.getResultList();
				UserLogin user = users.get(0);
				password = password.substring(5, password.length()-5);
				if(user.getPassword().equals(password)){
					checkPassword = true;
				}
			}
			if(checkPassword){
				
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public boolean getCheckPassword() {
		return checkPassword;
	}

	public void setCheckPassword(boolean checkPassword) {
		this.checkPassword = checkPassword;
	}

	public MemberCustomer getMember() {
		return member;
	}

	public void setMember(MemberCustomer member) {
		this.member = member;
	}
}
