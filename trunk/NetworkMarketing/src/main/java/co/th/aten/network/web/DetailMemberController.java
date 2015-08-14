package co.th.aten.network.web;

import java.io.Serializable;
import java.util.ArrayList;
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
import co.th.aten.network.entity.MasterBank;
import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.i18n.AppBundleKey;
import co.th.aten.network.model.CustomerModel;
import co.th.aten.network.model.DetailModel;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.HashUtil;
import co.th.aten.network.util.StringUtil;

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
	private CustomerModel memberModel;

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
				MemberCustomer member = em.find(MemberCustomer.class, new Integer(memberId));
				List<UserLogin> users = em.createQuery("From UserLogin where loginName=:login ", UserLogin.class)
						.setParameter("login", member.getCustomerMember())
						.getResultList();
				UserLogin user = users.get(0);
				password = password.substring(5, password.length()-5);
				if(user.getPassword().equals(password)){
					checkPassword = true;
					memberModel = setDataCustomerModel(member);
				}
			}
			if(checkPassword){

			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private CustomerModel setDataCustomerModel(MemberCustomer customer){
		try{
			if(customer!=null){
				CustomerModel model = new CustomerModel();
				model.setCustomerId(customer.getCustomerId());
				model.setCustomerMember(customer.getCustomerMember());
				model.setUpperId(customer.getUpperId()!=null?customer.getUpperId():0);
				model.setLowerLeftId(customer.getLowerLeftId()!=null?customer.getLowerLeftId():0);
				model.setLowerRightId(customer.getLowerRightId()!=null?customer.getLowerRightId():0);
				if(model.getLowerLeftId()!=0 || model.getLowerRightId()!=0){
					model.setChkLower(true);
				}else{
					model.setChkLower(false);
				}
				model.setRecommendId(StringUtil.n2b(customer.getRecommendId()));
				MemberCustomer recommend = em.find(MemberCustomer.class, new Integer(StringUtil.n2b(customer.getRecommendId())));
				if(recommend!=null){
					model.setRecommendName(customerControl.genNameMenber(recommend));
					model.setRecommendCode(StringUtil.n2b(recommend.getCustomerMember()));
				}
				model.setPositionId(customer.getPositionId()!=null?customer.getPositionId().getPositionId():0);
				model.setPositionText(customer.getPositionId()!=null?customer.getPositionId().getEnName():"");
				model.setScore(customer.getScore()!=null?customer.getScore():0);
				model.setRegisDate(customer.getRegisDate());
				String firstName = customerControl.genNameMenber(customer);
				if(firstName!=null && firstName.length()>10){
					firstName = firstName.substring(0, 10);
				}
				model.setFirstName(firstName);
				model.setLastName(customer.getLastName());
				model.setName(customerControl.genNameMenber(customer));
				model.setStatus(customer.getStatus()!=null?customer.getStatus():0);

				model.setBankId(StringUtil.n2b(customer.getBankId()));
				MasterBank bank = em.find(MasterBank.class, new Integer(StringUtil.n2b(customer.getBankId())));
				if(bank!=null)
					model.setBankText(StringUtil.n2b(bank.getBankName()));
				model.setBranch(StringUtil.n2b(customer.getBankBranch()));
				model.setAccNo(StringUtil.n2b(customer.getBankaccountNo()));
				model.setAccName(StringUtil.n2b(customer.getBankaccountName()));

				model.setCreateBy(customer.getCreateBy()!=null?customer.getCreateBy():0);
				model.setCreateDate(customer.getCreateDate());
				model.setUpdateBy(customer.getUpdateBy()!=null?customer.getUpdateBy():0);
				model.setUpdateDate(customer.getUpdateDate());
				return model;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;

	}

	public boolean getCheckPassword() {
		return checkPassword;
	}

	public void setCheckPassword(boolean checkPassword) {
		this.checkPassword = checkPassword;
	}

	public CustomerModel getMemberModel() {
		return memberModel;
	}

	public void setMemberModel(CustomerModel memberModel) {
		this.memberModel = memberModel;
	}

}
