package co.th.aten.network.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.model.CustomerModel;
import co.th.aten.network.model.DetailModel;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.annotation.Authenticated;
import co.th.aten.network.util.StringUtil;

//@ViewScoped
@SessionScoped
@Named
public class CustomerController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5443351151396868724L;

	@Inject
	Logger log;

	//	@Inject
	//	private SubscriberService subscriberService;

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

	private List<CustomerModel> customerModelList;

	private CustomerModel customerModel_01;
	private CustomerModel customerModel_02;
	private CustomerModel customerModel_03;
	private CustomerModel customerModel_04;
	private CustomerModel customerModel_05;
	private CustomerModel customerModel_06;
	private CustomerModel customerModel_07;
	private CustomerModel customerModel_08;
	private CustomerModel customerModel_09;
	private CustomerModel customerModel_10;
	private CustomerModel customerModel_11;
	private CustomerModel customerModel_12;
	private CustomerModel customerModel_13;
	private CustomerModel customerModel_14;
	private CustomerModel customerModel_15;

	private String html;
	private long customerId;
	private String searchCustomer;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
	private DecimalFormat df = new DecimalFormat("0000000");

	@PostConstruct
	public void init(){
		log.info("init method CustomerController");

		//				customerModelList = new ArrayList<CustomerModel>();
		//				List<MemberCustomer> CustomerList = em.createQuery("From MemberCustomer",MemberCustomer.class).getResultList();
		//				if(CustomerList!=null){
		//					for(MemberCustomer customer:CustomerList){
		//						customerModelList.add(setDataCustomerModel(customer));
		//					}
		//				}

		if(user.get().getCustomerId()!=null){
			MemberCustomer customer = user.get().getCustomerId();
			if(customer!=null){
				customerId = customer.getCustomerId();
			}
			genTreeModel();
		}

	}
	
	public void search(){
		log.info("##### SEARCH ##### = "+searchCustomer);
		try{
			if(searchCustomer!=null && searchCustomer.trim().length()>0){
				MemberCustomer memSearch = (MemberCustomer)em.createQuery("From MemberCustomer Where customerMember =:customerMember ")
						.setParameter("customerMember", searchCustomer).getSingleResult();
				long cusId = memSearch.getCustomerId().longValue();
				log.info("##### cusId ##### = "+cusId);
				log.info("##### login ##### = "+user.get().getCustomerId().getCustomerId().intValue());
				if(cusId > user.get().getCustomerId().getCustomerId().intValue()){
					String sql = "From MemberCustomer " +
							" Where customerId = "+user.get().getCustomerId().getCustomerId().intValue();
					boolean chk = true;
					while(chk){
						log.info("SQL + "+sql);
						String subSql = "";
						List<MemberCustomer> customerList = em.createQuery(sql,MemberCustomer.class).getResultList();
						for(MemberCustomer cus:customerList){
							if(StringUtil.n2b(cus.getLowerLeftId())==cusId || StringUtil.n2b(cus.getLowerRightId())==cusId){
								customerId = cusId;
								genTreeModel();
								chk = false;
								break;
							}else{
								subSql += (cus.getLowerLeftId()==null?"":cus.getLowerLeftId().intValue()+",");
								subSql += (cus.getLowerRightId()==null?"":cus.getLowerRightId().intValue()+",");
							}
						}
						if(subSql.equals("")){
							// alert ERROR
							chk = false;
							break;
						}
						sql = "From MemberCustomer " +
								" Where customerId in ";
						subSql = subSql.substring(0, subSql.length()-1);
						subSql = "("+subSql+")";
						sql += subSql;
					}
				}else if(cusId == user.get().getCustomerId().getCustomerId().intValue()){
					customerId = cusId;
					genTreeModel();
				}else {
					// alert ERROR
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void genAutoTagTree(){
		html = "";
		StringBuffer str = new StringBuffer();
		str.append("<table>");
		str.append("<tr>");
		str.append("<td>");
		str.append("<div class=\"tree\">");
		for(int index01=1;index01<2;index01++){
			str.append("<ul>");
			str.append("<li>");
			str.append("<div>");
			str.append(genData());
			str.append("</div>");
			str.append("<ul>");
			for(int index02=2;index02<4;index02++){
				str.append("<li>");
				str.append("<div>");
				str.append(genData());
				str.append("</div>");
				str.append("<ul>");
				for(int index03=4;index03<6;index03++){
					str.append("<li>");
					str.append("<div>");
					str.append(genData());
					str.append("</div>");
					str.append("<ul>");
					for(int index04=6;index04<8;index04++){
						str.append("<li>");
						str.append("<div>");
						str.append(genData());
						str.append("</div>");
						//						str.append("<ul>");
						//						for(int index05=6;index05<8;index05++){
						//							str.append("<li>");
						//							str.append("<div>");
						//							str.append(genData());
						//							str.append("</div>");
						//							str.append("</li>");
						//						}
						//						str.append("</ul>");
						str.append("</li>");
					}
					str.append("</ul>");
					str.append("</li>");
				}
				str.append("</ul>");
				str.append("</li>");
			}			
			str.append("</ul>");
			str.append("</li>");
			str.append("</ul>");
		}
		str.append("</div>");
		str.append("</td>");
		str.append("</tr>");
		str.append("</table>");
		html = str.toString();
	}

	private String genData(){
		StringBuffer str = new StringBuffer();
		str.append("<a class=\"tooltip\" href=\"#\">");
		str.append("<img src=\"../resources/gfx/1.png\" width=\"55px\" height=\"55px\"/>");
		str.append("<br/>");
		str.append("<h>AAAAAAA</h>");
		str.append("<br/>");
		str.append("<h>BBBBBBB</h>");
		str.append("");
		str.append("");
		str.append("");
		str.append("");
		str.append("");
		str.append("</a>");
		return str.toString();
	}

	public void genTree(String flag,long cusId){
		log.info("flag = "+flag);
		log.info("cusId = "+cusId);
		try{
			if(flag!=null && flag.equals("0")){
				// top top
				customerId = user.get().getCustomerId().getCustomerId();
			}else if(flag!=null && flag.equals("1")){
				// left
				if(customerModel_01!=null && customerModel_01.getCustomerId()!=0){
					customerId = findLeftLow();
				}
			}else if(flag!=null && flag.equals("2")){
				// top
				if(customerModel_01!=null && customerModel_01.getUpperId()!=0){
					if(customerModel_01.getCustomerId()!=user.get().getCustomerId().getCustomerId()){
						customerId = customerModel_01.getUpperId();
					}
				}
			}else if(flag!=null && flag.equals("3")){
				// right
				if(customerModel_01!=null && customerModel_01.getCustomerId()!=0){
					customerId = findRightLow();
				}
			}else{
				customerId = cusId;
			}
			genTreeModel();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private int findLeftLow(){
		int leftLow = 0;
		int id = user.get().getCustomerId().getCustomerId();
		int chk = 0;
		while(leftLow==0){
			chk++;
			MemberCustomer customer = getCustomerById(id);
			if(customer.getLowerLeftId()==null){
				leftLow = customer.getCustomerId().intValue();
			}else{
				id = customer.getLowerLeftId();
			}
			if(chk>10000){
				leftLow = user.get().getCustomerId().getCustomerId();
				break;
			}
		}
		return leftLow;
	}

	private int findRightLow(){
		int leftLow = 0;
		int id = user.get().getCustomerId().getCustomerId();
		int chk = 0;
		while(leftLow==0){
			chk++;
			MemberCustomer customer = getCustomerById(id);
			if(customer.getLowerRightId()==null){
				leftLow = customer.getCustomerId().intValue();
			}else{
				id = customer.getLowerRightId();
			}
			if(chk>10000){
				leftLow = user.get().getCustomerId().getCustomerId();
				break;
			}
		}
		return leftLow;
	}

	public void genTreeModel(){
		try{
			log.info("------------------- genTreeModel() customerId = "+customerId);
			if(user.get().getCustomerId()!=null || customerId!=0){
				MemberCustomer customer = user.get().getCustomerId();
				if(customerId==0){
					customerId = customer.getCustomerId();
				}
			}
			customerModel_01 = null;
			customerModel_02 = null;
			customerModel_03 = null;
			customerModel_04 = null;
			customerModel_05 = null;
			customerModel_06 = null;
			customerModel_07 = null;
			customerModel_08 = null;
			customerModel_09 = null;
			customerModel_10 = null;
			customerModel_11 = null;
			customerModel_12 = null;
			customerModel_13 = null;
			customerModel_14 = null;
			customerModel_15 = null;
			customerModel_01 = setDataCustomerModel(getCustomerById(customerId));
			if(customerModel_01.getLowerLeftId()!=0){
				customerModel_02 = setDataCustomerModel(getCustomerById(customerModel_01.getLowerLeftId()));
				if(customerModel_02!=null){
					if(customerModel_02.getLowerLeftId()!=0){
						customerModel_04 = setDataCustomerModel(getCustomerById(customerModel_02.getLowerLeftId()));
						if(customerModel_04!=null){
							if(customerModel_04.getLowerLeftId()!=0){
								customerModel_08 = setDataCustomerModel(getCustomerById(customerModel_04.getLowerLeftId()));
							}
							if(customerModel_04.getLowerRightId()!=0){
								customerModel_09 = setDataCustomerModel(getCustomerById(customerModel_04.getLowerRightId()));									
							}
						}
					}
					if(customerModel_02.getLowerRightId()!=0){
						customerModel_05 = setDataCustomerModel(getCustomerById(customerModel_02.getLowerRightId()));
						if(customerModel_05!=null){
							if(customerModel_05.getLowerLeftId()!=0){
								customerModel_10 = setDataCustomerModel(getCustomerById(customerModel_05.getLowerLeftId()));									
							}
							if(customerModel_05.getLowerRightId()!=0){
								customerModel_11 = setDataCustomerModel(getCustomerById(customerModel_05.getLowerRightId()));									
							}
						}
					}
				}
			}
			if(customerModel_01.getLowerRightId()!=0){
				customerModel_03 = setDataCustomerModel(getCustomerById(customerModel_01.getLowerRightId()));
				if(customerModel_03!=null){
					if(customerModel_03.getLowerLeftId()!=0){
						customerModel_06 = setDataCustomerModel(getCustomerById(customerModel_03.getLowerLeftId()));
						if(customerModel_06!=null){
							if(customerModel_06.getLowerLeftId()!=0){
								customerModel_12 = setDataCustomerModel(getCustomerById(customerModel_06.getLowerLeftId()));									
							}
							if(customerModel_06.getLowerRightId()!=0){
								customerModel_13 = setDataCustomerModel(getCustomerById(customerModel_06.getLowerRightId()));									
							}
						}
					}
					if(customerModel_03.getLowerRightId()!=0){
						customerModel_07 = setDataCustomerModel(getCustomerById(customerModel_03.getLowerRightId()));
						if(customerModel_07!=null){
							if(customerModel_07.getLowerLeftId()!=0){
								customerModel_14 = setDataCustomerModel(getCustomerById(customerModel_07.getLowerLeftId()));									
							}
							if(customerModel_07.getLowerRightId()!=0){
								customerModel_15 = setDataCustomerModel(getCustomerById(customerModel_07.getLowerRightId()));									
							}
						}							
					}
				}
			}

			if(customerModel_01==null){
				customerModel_01 = new CustomerModel();
				customerModel_01.setPositionImage("/resources/gfx/addmem.gif");
				customerModel_01.setFlagImg01("none");
				customerModel_01.setFlagImg02("block");
				customerModel_01.setFlagImg03("none");
			}
			if(customerModel_02==null){
				customerModel_02 = new CustomerModel();
				if(customerModel_01.getCustomerMember()!=null){
					customerModel_02.setPositionImage("/resources/gfx/addmem.gif");
					customerModel_02.setFlagImg01("block");
					customerModel_02.setFlagImg02("none");
					customerModel_02.setFlagImg03("none");
					customerModel_02.setUpperId(customerModel_01.getCustomerId());
				}else{
					customerModel_02.setPositionImage("/resources/gfx/balls_11.gif");
					customerModel_02.setFlagImg01("none");
					customerModel_02.setFlagImg02("block");
					customerModel_02.setFlagImg03("none");
				}
			}
			if(customerModel_03==null){
				customerModel_03 = new CustomerModel();
				if(customerModel_01.getCustomerMember()!=null){
					customerModel_03.setPositionImage("/resources/gfx/addmem.gif");
					customerModel_03.setFlagImg01("block");
					customerModel_03.setFlagImg02("none");
					customerModel_03.setFlagImg03("none");
					customerModel_03.setUpperId(customerModel_01.getCustomerId());
				}else{
					customerModel_03.setPositionImage("/resources/gfx/balls_11.gif");
					customerModel_03.setFlagImg01("none");
					customerModel_03.setFlagImg02("block");
					customerModel_03.setFlagImg03("none");
				}
			}
			if(customerModel_04==null){
				customerModel_04 = new CustomerModel();
				if(customerModel_02.getCustomerMember()!=null){
					customerModel_04.setPositionImage("/resources/gfx/addmem.gif");
					customerModel_04.setFlagImg01("block");
					customerModel_04.setFlagImg02("none");
					customerModel_04.setFlagImg03("none");
					customerModel_04.setUpperId(customerModel_02.getCustomerId());
				}else{
					customerModel_04.setPositionImage("/resources/gfx/balls_11.gif");
					customerModel_04.setFlagImg01("none");
					customerModel_04.setFlagImg02("block");
					customerModel_04.setFlagImg03("none");
				}
			}
			if(customerModel_05==null){
				customerModel_05 = new CustomerModel();
				if(customerModel_02.getCustomerMember()!=null){
					customerModel_05.setPositionImage("/resources/gfx/addmem.gif");
					customerModel_05.setFlagImg01("block");
					customerModel_05.setFlagImg02("none");
					customerModel_05.setFlagImg03("none");
					customerModel_05.setUpperId(customerModel_02.getCustomerId());
				}else{
					customerModel_05.setPositionImage("/resources/gfx/balls_11.gif");
					customerModel_05.setFlagImg01("none");
					customerModel_05.setFlagImg02("block");
					customerModel_05.setFlagImg03("none");
				}
			}
			if(customerModel_06==null){
				customerModel_06 = new CustomerModel();
				if(customerModel_03.getCustomerMember()!=null){
					customerModel_06.setPositionImage("/resources/gfx/addmem.gif");
					customerModel_06.setFlagImg01("block");
					customerModel_06.setFlagImg02("none");
					customerModel_06.setFlagImg03("none");
					customerModel_06.setUpperId(customerModel_03.getCustomerId());
				}else{
					customerModel_06.setPositionImage("/resources/gfx/balls_11.gif");
					customerModel_06.setFlagImg01("none");
					customerModel_06.setFlagImg02("block");
					customerModel_06.setFlagImg03("none");
				}
			}
			if(customerModel_07==null){
				customerModel_07 = new CustomerModel();
				if(customerModel_03.getCustomerMember()!=null){
					customerModel_07.setPositionImage("/resources/gfx/addmem.gif");
					customerModel_07.setFlagImg01("block");
					customerModel_07.setFlagImg02("none");
					customerModel_07.setFlagImg03("none");
					customerModel_07.setUpperId(customerModel_03.getCustomerId());
				}else{
					customerModel_07.setPositionImage("/resources/gfx/balls_11.gif");
					customerModel_07.setFlagImg01("none");
					customerModel_07.setFlagImg02("block");
					customerModel_07.setFlagImg03("none");
				}
			}
			if(customerModel_08==null){
				customerModel_08 = new CustomerModel();
				if(customerModel_04.getCustomerMember()!=null){
					customerModel_08.setPositionImage("/resources/gfx/addmem.gif");
					customerModel_08.setFlagImg01("block");
					customerModel_08.setFlagImg02("none");
					customerModel_08.setFlagImg03("none");
					customerModel_08.setUpperId(customerModel_04.getCustomerId());
				}else{
					customerModel_08.setPositionImage("/resources/gfx/balls_11.gif");
					customerModel_08.setFlagImg01("none");
					customerModel_08.setFlagImg02("block");
					customerModel_08.setFlagImg03("none");
				}
			}
			if(customerModel_09==null){
				customerModel_09 = new CustomerModel();
				if(customerModel_04.getCustomerMember()!=null){
					customerModel_09.setPositionImage("/resources/gfx/addmem.gif");
					customerModel_09.setFlagImg01("block");
					customerModel_09.setFlagImg02("none");
					customerModel_09.setFlagImg03("none");
					customerModel_09.setUpperId(customerModel_04.getCustomerId());
				}else{
					customerModel_09.setPositionImage("/resources/gfx/balls_11.gif");
					customerModel_09.setFlagImg01("none");
					customerModel_09.setFlagImg02("block");
					customerModel_09.setFlagImg03("none");
				}
			}
			if(customerModel_10==null){
				customerModel_10 = new CustomerModel();
				if(customerModel_05.getCustomerMember()!=null){
					customerModel_10.setPositionImage("/resources/gfx/addmem.gif");
					customerModel_10.setFlagImg01("block");
					customerModel_10.setFlagImg02("none");
					customerModel_10.setFlagImg03("none");
					customerModel_10.setUpperId(customerModel_05.getCustomerId());
				}else{
					customerModel_10.setPositionImage("/resources/gfx/balls_11.gif");
					customerModel_10.setFlagImg01("none");
					customerModel_10.setFlagImg02("block");
					customerModel_10.setFlagImg03("none");
				}
			}
			if(customerModel_11==null){
				customerModel_11 = new CustomerModel();
				if(customerModel_05.getCustomerMember()!=null){
					customerModel_11.setPositionImage("/resources/gfx/addmem.gif");
					customerModel_11.setFlagImg01("block");
					customerModel_11.setFlagImg02("none");
					customerModel_11.setFlagImg03("none");
					customerModel_11.setUpperId(customerModel_05.getCustomerId());
				}else{
					customerModel_11.setPositionImage("/resources/gfx/balls_11.gif");
					customerModel_11.setFlagImg01("none");
					customerModel_11.setFlagImg02("block");
					customerModel_11.setFlagImg03("none");
				}
			}
			if(customerModel_12==null){
				customerModel_12 = new CustomerModel();
				if(customerModel_06.getCustomerMember()!=null){
					customerModel_12.setPositionImage("/resources/gfx/addmem.gif");
					customerModel_12.setFlagImg01("block");
					customerModel_12.setFlagImg02("none");
					customerModel_12.setFlagImg03("none");
					customerModel_12.setUpperId(customerModel_06.getCustomerId());
				}else{
					customerModel_12.setPositionImage("/resources/gfx/balls_11.gif");
					customerModel_12.setFlagImg01("none");
					customerModel_12.setFlagImg02("block");
					customerModel_12.setFlagImg03("none");
				}
			}
			if(customerModel_13==null){
				customerModel_13 = new CustomerModel();
				if(customerModel_06.getCustomerMember()!=null){
					customerModel_13.setPositionImage("/resources/gfx/addmem.gif");
					customerModel_13.setFlagImg01("block");
					customerModel_13.setFlagImg02("none");
					customerModel_13.setFlagImg03("none");
					customerModel_13.setUpperId(customerModel_06.getCustomerId());
				}else{
					customerModel_13.setPositionImage("/resources/gfx/balls_11.gif");
					customerModel_13.setFlagImg01("none");
					customerModel_13.setFlagImg02("block");
					customerModel_13.setFlagImg03("none");
				}
			}
			if(customerModel_14==null){
				customerModel_14 = new CustomerModel();
				if(customerModel_07.getCustomerMember()!=null){
					customerModel_14.setPositionImage("/resources/gfx/addmem.gif");
					customerModel_14.setFlagImg01("block");
					customerModel_14.setFlagImg02("none");
					customerModel_14.setFlagImg03("none");
					customerModel_14.setUpperId(customerModel_07.getCustomerId());
				}else{
					customerModel_14.setPositionImage("/resources/gfx/balls_11.gif");
					customerModel_14.setFlagImg01("none");
					customerModel_14.setFlagImg02("block");
					customerModel_14.setFlagImg03("none");
				}
			}
			if(customerModel_15==null){
				customerModel_15 = new CustomerModel();
				if(customerModel_07.getCustomerMember()!=null){
					customerModel_15.setPositionImage("/resources/gfx/addmem.gif");
					customerModel_15.setFlagImg01("block");
					customerModel_15.setFlagImg02("none");
					customerModel_15.setFlagImg03("none");
					customerModel_15.setUpperId(customerModel_07.getCustomerId());
				}else{
					customerModel_15.setPositionImage("/resources/gfx/balls_11.gif");
					customerModel_15.setFlagImg01("none");
					customerModel_15.setFlagImg02("block");
					customerModel_15.setFlagImg03("none");
				}
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
				model.setRecommendId(customer.getRecommendId()!=null?customer.getRecommendId():0);
				model.setPositionId(customer.getPositionId()!=null?customer.getPositionId():0);
				if(model.getPositionId()==1){
					model.setPositionImage("/resources/gfx/1.png");
				}else if(model.getPositionId()==2){
					model.setPositionImage("/resources/gfx/2.png");
				}else if(model.getPositionId()==3){
					model.setPositionImage("/resources/gfx/3.png");
				}else if(model.getPositionId()==4){
					model.setPositionImage("/resources/gfx/4.png");
				}else if(model.getPositionId()==5){
					model.setPositionImage("/resources/gfx/5.png");
				}
				model.setScore(customer.getScore()!=null?customer.getScore():0);
				model.setRegisDate(customer.getRegisDate());
				model.setFirstName(StringUtil.n2b(customer.getTitleName())+" "+StringUtil.n2b(customer.getFirstName()));
				model.setLastName(customer.getLastName());
				model.setName(StringUtil.n2b(customer.getTitleName())+StringUtil.n2b(customer.getFirstName())+" "+StringUtil.n2b(customer.getLastName()));
				model.setStatus(customer.getStatus()!=null?customer.getStatus():0);
				model.setCreateBy(customer.getCreateBy()!=null?customer.getCreateBy():0);
				model.setCreateDate(customer.getCreateDate());
				model.setUpdateBy(customer.getUpdateBy()!=null?customer.getUpdateBy():0);
				model.setUpdateDate(customer.getUpdateDate());
				model.setFlagImg01("none");
				model.setFlagImg02("none");
				model.setFlagImg03("block");
				List<DetailModel> detailModelList = new ArrayList<DetailModel>();
				DetailModel detailModel = new DetailModel();
				detailModel.setText("ID");
				detailModel.setValue(model.getCustomerMember());
				detailModelList.add(detailModel);
				detailModel = new DetailModel();
				detailModel.setText("NAME");
				detailModel.setValue(model.getName());
				detailModelList.add(detailModel);
				detailModel = new DetailModel();
				detailModel.setText("REGIS");
				detailModel.setValue(model.getRegisDate()!=null?sdf.format(model.getRegisDate()):"");
				detailModelList.add(detailModel);
				model.setDetailModelList(detailModelList);
				return model;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;

	}

	private MemberCustomer getCustomerById(long customerId){
		try{
			MemberCustomer customer = em.find(MemberCustomer.class, new Integer(new BigDecimal(customerId).intValue()));
			return customer;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public List<CustomerModel> getCustomerModelList() {
		return customerModelList;
	}

	public void setCustomerModelList(List<CustomerModel> customerModelList) {
		this.customerModelList = customerModelList;
	}

	public CustomerModel getCustomerModel_01() {
		return customerModel_01;
	}

	public void setCustomerModel_01(CustomerModel customerModel_01) {
		this.customerModel_01 = customerModel_01;
	}

	public CustomerModel getCustomerModel_02() {
		return customerModel_02;
	}

	public void setCustomerModel_02(CustomerModel customerModel_02) {
		this.customerModel_02 = customerModel_02;
	}

	public CustomerModel getCustomerModel_03() {
		return customerModel_03;
	}

	public void setCustomerModel_03(CustomerModel customerModel_03) {
		this.customerModel_03 = customerModel_03;
	}

	public CustomerModel getCustomerModel_04() {
		return customerModel_04;
	}

	public void setCustomerModel_04(CustomerModel customerModel_04) {
		this.customerModel_04 = customerModel_04;
	}

	public CustomerModel getCustomerModel_05() {
		return customerModel_05;
	}

	public void setCustomerModel_05(CustomerModel customerModel_05) {
		this.customerModel_05 = customerModel_05;
	}

	public CustomerModel getCustomerModel_06() {
		return customerModel_06;
	}

	public void setCustomerModel_06(CustomerModel customerModel_06) {
		this.customerModel_06 = customerModel_06;
	}

	public CustomerModel getCustomerModel_07() {
		return customerModel_07;
	}

	public void setCustomerModel_07(CustomerModel customerModel_07) {
		this.customerModel_07 = customerModel_07;
	}

	public CustomerModel getCustomerModel_08() {
		return customerModel_08;
	}

	public void setCustomerModel_08(CustomerModel customerModel_08) {
		this.customerModel_08 = customerModel_08;
	}

	public CustomerModel getCustomerModel_09() {
		return customerModel_09;
	}

	public void setCustomerModel_09(CustomerModel customerModel_09) {
		this.customerModel_09 = customerModel_09;
	}

	public CustomerModel getCustomerModel_10() {
		return customerModel_10;
	}

	public void setCustomerModel_10(CustomerModel customerModel_10) {
		this.customerModel_10 = customerModel_10;
	}

	public CustomerModel getCustomerModel_11() {
		return customerModel_11;
	}

	public void setCustomerModel_11(CustomerModel customerModel_11) {
		this.customerModel_11 = customerModel_11;
	}

	public CustomerModel getCustomerModel_12() {
		return customerModel_12;
	}

	public void setCustomerModel_12(CustomerModel customerModel_12) {
		this.customerModel_12 = customerModel_12;
	}

	public CustomerModel getCustomerModel_13() {
		return customerModel_13;
	}

	public void setCustomerModel_13(CustomerModel customerModel_13) {
		this.customerModel_13 = customerModel_13;
	}

	public CustomerModel getCustomerModel_14() {
		return customerModel_14;
	}

	public void setCustomerModel_14(CustomerModel customerModel_14) {
		this.customerModel_14 = customerModel_14;
	}

	public CustomerModel getCustomerModel_15() {
		return customerModel_15;
	}

	public void setCustomerModel_15(CustomerModel customerModel_15) {
		this.customerModel_15 = customerModel_15;
	}

	public String getHtml() {
		return html;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getSearchCustomer() {
		return searchCustomer;
	}

	public void setSearchCustomer(String searchCustomer) {
		this.searchCustomer = searchCustomer;
	}

}
