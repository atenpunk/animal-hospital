package co.th.aten.network.web;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.entity.Customer;
import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.annotation.Authenticated;
import co.th.aten.network.util.StringUtil;

@ViewScoped
@Named
public class AddCustomerController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5443351151396868724L;

	@Inject
	Logger log;

	@Inject
	private CustomerControl customerControl;

	@Inject
	@Authenticated
	private Instance<UserLogin> user;

	@Inject
	private MessageFactory factory;
	@Inject
	private Messages messages;

	@Inject
	@DBDefault
	private EntityManager em;


	private String customerId;
	private String titleName;
	private String starTitleName;
	private String firstName;
	private String starFirstName;
	private String lastName;
	private String starLastName;
	private Date regisDate;
	private int upperLineId;
	private String upperLineCusId;
	private String upperLineName;
	private int flagUnder;
	private boolean chkSave;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
	private DecimalFormat df = new DecimalFormat("000000");
	@PostConstruct
	public void init(){
		log.info("init method AddCustomerController");
		regisDate = new Date();
		starTitleName = "*";
		starFirstName = "*";
		starLastName = "*";
		chkSave = true;
	}

	public void addCustomer(){
		log.info("##### addCustomer()");
		log.info("##### user.get().getUserId = "+user.get().getUserId());
		log.info("##### customerId = "+customerId);
		log.info("##### titleName = "+titleName);
		log.info("##### firstName = "+firstName);
		log.info("##### lastName = "+lastName);
		log.info("##### regisDate = "+sdf.format(regisDate));
		log.info("##### upperLineId = "+upperLineId);
		log.info("##### flagUnder = "+flagUnder);
		try{
			Customer cus = new Customer();
			int max = customerControl.customerIdInsert();
			cus.setId(max);
			customerId = "N"+df.format(max);
			cus.setCustomerId(customerId);
			cus.setUpperId(upperLineId);
			cus.setLowerLeftId(null);
			cus.setLowerRightId(null);
			cus.setDirectId(0);
			cus.setPositionId(5);
			cus.setScore(0);
			cus.setRegisDate(regisDate);
			cus.setTitleName(titleName);
			cus.setFirstName(firstName);
			cus.setLastName(lastName);
			cus.setStatus(0);
			cus.setCreateBy(user.get().getUserId());
			cus.setCreateDate(new Date());
			cus.setUpdateBy(user.get().getUserId());
			cus.setUpdateDate(new Date());
			em.persist(cus);
			Customer cusUpper = em.find(Customer.class, new Integer(upperLineId));
			if(flagUnder == 1){
				cusUpper.setLowerLeftId(max);
			}else if(flagUnder == 2){
				cusUpper.setLowerRightId(max);
			}
			em.merge(cusUpper);
			messages.info("Add line success.");
		}catch(Exception e){
			e.printStackTrace();
			messages.error("Add Line fail.");
		}
	}

	public void view(){
		try{
			log.info("####### view()");
			log.info("####### upperLineId = "+upperLineId);
			log.info("####### flagUnder = "+flagUnder);
			starTitleName = "*";
			starFirstName = "*";
			starLastName = "*";
			Customer customerUpper = em.find(Customer.class, new Integer(upperLineId));
			if(customerUpper!=null){
				upperLineCusId = customerUpper.getCustomerId();
				upperLineName = StringUtil.n2b(customerUpper.getTitleName())+" "+StringUtil.n2b(customerUpper.getFirstName())
						+" "+StringUtil.n2b(customerUpper.getLastName());
			}

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void onKeypress(){
		if(firstName!=null && firstName.trim().length()>0){
			starFirstName = " ";
		}else{
			starFirstName = "*";
		}
		if(titleName!=null && titleName.trim().length()>0){
			starTitleName = " ";
		}else{
			starTitleName = "*";
		}
		if(starTitleName.equals("*") || starFirstName.equals("*")){
			chkSave = true;
		}else{
			chkSave = false;
		}
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStarTitleName() {
		return starTitleName;
	}

	public void setStarTitleName(String starTitleName) {
		this.starTitleName = starTitleName;
	}

	public String getStarFirstName() {
		return starFirstName;
	}

	public void setStarFirstName(String starFirstName) {
		this.starFirstName = starFirstName;
	}

	public String getStarLastName() {
		return starLastName;
	}

	public void setStarLastName(String starLastName) {
		this.starLastName = starLastName;
	}

	public Date getRegisDate() {
		return regisDate;
	}

	public void setRegisDate(Date regisDate) {
		this.regisDate = regisDate;
	}

	public int getUpperLineId() {
		return upperLineId;
	}

	public void setUpperLineId(int upperLineId) {
		this.upperLineId = upperLineId;
	}

	public String getUpperLineName() {
		return upperLineName;
	}

	public void setUpperLineName(String upperLineName) {
		this.upperLineName = upperLineName;
	}

	public String getUpperLineCusId() {
		return upperLineCusId;
	}

	public void setUpperLineCusId(String upperLineCusId) {
		this.upperLineCusId = upperLineCusId;
	}

	public int getFlagUnder() {
		return flagUnder;
	}

	public void setFlagUnder(int flagUnder) {
		this.flagUnder = flagUnder;
	}

	public boolean getChkSave() {
		return chkSave;
	}

	public void setChkSave(boolean chkSave) {
		this.chkSave = chkSave;
	}

}
