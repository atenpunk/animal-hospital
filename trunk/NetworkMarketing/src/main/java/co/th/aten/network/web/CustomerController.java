package co.th.aten.network.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.entity.AddressAmphures;
import co.th.aten.network.entity.AddressDistricts;
import co.th.aten.network.entity.AddressProvinces;
import co.th.aten.network.entity.MasterBank;
import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.MemberPosition;
import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.i18n.AppBundleKey;
import co.th.aten.network.model.CustomerModel;
import co.th.aten.network.model.DetailModel;
import co.th.aten.network.model.DropDownModel;
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
	
	private String detailCustomerStr;
	private String detailRegisDate;
	private String detailPosRecomment;
	private String detailPosMatch;
	private String detailDocumentRegis;
	private String detailDocumentPersonalCard;
	private String detailDocumentBookBank;
//	private String detailPv;
//	private String detailPv;
//	private String detailPv;
//	private String detailPv;
//	private String detailPv;
//	private String detailPv;
//	private String detailPv;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);
	private DecimalFormat df = new DecimalFormat("0000000");

	// ######################## ADD MEMBER #########################
	@Inject
	private CustomerControl customerControl;
	private String memberStr;
	private String titleName;
	private String starTitleName;
	private String firstName;
	private String starFirstName;
	private String businessName;
	private Date regisDate;
	private int sex;
	private String starSex;
	private String starBirthDay;
	private Date birthDay;
	private String nationality;
	private String starPersonalId;
	private String personalId;
	private String companyID;
	private String telephone;
	private String mobile;
	private String fax;
	private String email;
	private String starUpperLineId;
	private String upperLineMemberId;
	private String starUpperLineName;
	private String upperLineName;
	private String starSide;
	private int side;
	private String starRecomendId;
	private int recomendId;
	private String recomendStrId;
	private String starRecomendName;
	private String recomendName;

	private String addressNo;
	private String addressBuilding;
	private String addressVillage;
	private String addressLane;
	private String addressRoad;
	private String starProvince;
	private int provinceId;
	private List<DropDownModel> provinceList;
	private String starAmphur;
	private int amphurId;
	private List<DropDownModel> amphurList;
	private String starDistrict;
	private int districtId;
	private List<DropDownModel> districtList;
	private String addressPostCode;

	private String addressNoSendDoc;
	private String addressBuildingSendDoc;
	private String addressVillageSendDoc;
	private String addressLaneSendDoc;
	private String addressRoadSendDoc;
	private String starProvinceSendDoc;
	private int provinceIdSendDoc;
	private List<DropDownModel> provinceSendDocList;
	private String starAmphurSendDoc;
	private int amphurIdSendDoc;
	private List<DropDownModel> amphurSendDocList;
	private String starDistrictSendDoc;
	private int districtIdSendDoc;
	private List<DropDownModel> districtSendDocList;
	private String addressPostCodeSendDoc;

	private int bankId;
	private List<DropDownModel> bankList;
	private String branch;
	private int accType;
	private String accNo;
	private String accName;
	private String remark;
	private int receiveDocument;
	private String starDocumentFully;
	private boolean chkDocumentFully;
	private Date dateDocumentFully;
	private boolean chkCopyPersonalCard;
	private Date dateCopyPersonalCard;
	private boolean chkCopyBookBank;
	private Date dateCopyBookBank;

	private long upperLineId;
	private long flagUnder;
	private boolean chkSave;
	private boolean chkSameAddress;
	// ######################## ADD MEMBER #########################

	@PostConstruct
	public void init(){
		log.info("init method CustomerController");
		chkSave = true;

		if(user.get().getCustomerId()!=null){
			MemberCustomer customer = user.get().getCustomerId();
			if(customer!=null){
				customerId = customer.getCustomerId();
			}
			genTreeModel();
		}

		provinceList = new ArrayList<DropDownModel>();
		districtList = new ArrayList<DropDownModel>();
		amphurList = new ArrayList<DropDownModel>();
		List<AddressProvinces> provinList = em.createQuery("From AddressProvinces",AddressProvinces.class).getResultList();
		if(provinList!=null){
			for(AddressProvinces provin:provinList){
				DropDownModel model = new DropDownModel();
				model.setIntKey(provin.getProvinceId());
				model.setThLabel(provin.getProvinceName());
				model.setEnLabel(provin.getProvinceNameEng());
				provinceList.add(model);
			}
			DropDownModel model = new DropDownModel();
			model.setIntKey(-1);
			model.setThLabel("");
			model.setEnLabel("");
			provinceList.add(0,model);
			provinceId = -1;
		}
		
		provinceSendDocList = new ArrayList<DropDownModel>();
		districtSendDocList = new ArrayList<DropDownModel>();
		amphurSendDocList = new ArrayList<DropDownModel>();
		List<AddressProvinces> provinSendDocList = em.createQuery("From AddressProvinces",AddressProvinces.class).getResultList();
		if(provinSendDocList!=null){
			for(AddressProvinces provin:provinSendDocList){
				DropDownModel model = new DropDownModel();
				model.setIntKey(provin.getProvinceId());
				model.setThLabel(provin.getProvinceName());
				model.setEnLabel(provin.getProvinceNameEng());
				provinceSendDocList.add(model);
			}
			DropDownModel model = new DropDownModel();
			model.setIntKey(-1);
			model.setThLabel("");
			model.setEnLabel("");
			provinceSendDocList.add(0,model);
			provinceIdSendDoc = -1;
		}

		bankList = new ArrayList<DropDownModel>();
		List<MasterBank> masterBankList = em.createQuery("From MasterBank",MasterBank.class).getResultList();
		if(masterBankList!=null){
			for(MasterBank data:masterBankList){
				DropDownModel model = new DropDownModel();
				model.setIntKey(data.getBankCode());
				model.setThLabel(data.getBankName());
				model.setEnLabel(data.getBankName());
				bankList.add(model);
			}
			DropDownModel model = new DropDownModel();
			model.setIntKey(-1);
			model.setThLabel("");
			model.setEnLabel("");
			bankList.add(0,model);
			bankId = -1;
		}
	}

	public void onChangeProvince(){
		amphurList = new ArrayList<DropDownModel>();
		if(provinceId!=-1){
			List<AddressAmphures> dataList = em.createQuery("From AddressAmphures Where provinceId=:provinceId"
					,AddressAmphures.class)
					.setParameter("provinceId", provinceId)
					.getResultList();
			if(dataList!=null){
				for(AddressAmphures data:dataList){
					DropDownModel model = new DropDownModel();
					model.setIntKey(data.getAmphurId());
					model.setThLabel(data.getAmphurName());
					model.setEnLabel(data.getAmphurNameEng());
					amphurList.add(model);
				}
				DropDownModel model = new DropDownModel();
				model.setIntKey(-1);
				model.setThLabel("");
				model.setEnLabel("");
				amphurList.add(0,model);
				amphurId = -1;
			}	
		}
	}

	public void onChangeAmphur(){
		districtList = new ArrayList<DropDownModel>();
		if(amphurId!=-1){
			List<AddressDistricts> dataList = em.createQuery("From AddressDistricts " +
					" Where amphurId=:amphurId " +
					" And provinceId=:provinceId "
					,AddressDistricts.class)
					.setParameter("amphurId", amphurId)
					.setParameter("provinceId", provinceId)
					.getResultList();
			if(dataList!=null){
				for(AddressDistricts data:dataList){
					DropDownModel model = new DropDownModel();
					model.setIntKey(data.getDistrictId());
					model.setThLabel(data.getDistrictName());
					model.setEnLabel(data.getDistrictNameEng());
					districtList.add(model);
				}
				DropDownModel model = new DropDownModel();
				model.setIntKey(-1);
				model.setThLabel("");
				model.setEnLabel("");
				districtList.add(0,model);
				districtId = -1;
			}
		}
	}

	public void onChangeDistrict(){
		addressPostCode = "";
		if(districtId!=-1){
			AddressDistricts addressDistricts = em.find(AddressDistricts.class, new Integer(districtId));
			if(addressDistricts!=null){
				addressPostCode = StringUtil.n2b(addressDistricts.getPostCode());
			}
		}		
	}
	
	public void onChangeProvinceSendDoc(){
		amphurSendDocList = new ArrayList<DropDownModel>();
		if(provinceIdSendDoc!=-1){
			List<AddressAmphures> dataList = em.createQuery("From AddressAmphures Where provinceId=:provinceId"
					,AddressAmphures.class)
					.setParameter("provinceId", provinceIdSendDoc)
					.getResultList();
			if(dataList!=null){
				for(AddressAmphures data:dataList){
					DropDownModel model = new DropDownModel();
					model.setIntKey(data.getAmphurId());
					model.setThLabel(data.getAmphurName());
					model.setEnLabel(data.getAmphurNameEng());
					amphurSendDocList.add(model);
				}
				DropDownModel model = new DropDownModel();
				model.setIntKey(-1);
				model.setThLabel("");
				model.setEnLabel("");
				amphurSendDocList.add(0,model);
				amphurIdSendDoc = -1;
			}	
		}
	}

	public void onChangeAmphurSendDoc(){
		districtSendDocList = new ArrayList<DropDownModel>();
		if(amphurIdSendDoc !=-1){
			List<AddressDistricts> dataList = em.createQuery("From AddressDistricts " +
					" Where amphurId=:amphurId " +
					" And provinceId=:provinceId "
					,AddressDistricts.class)
					.setParameter("amphurId", amphurIdSendDoc)
					.setParameter("provinceId", provinceIdSendDoc)
					.getResultList();
			if(dataList!=null){
				for(AddressDistricts data:dataList){
					DropDownModel model = new DropDownModel();
					model.setIntKey(data.getDistrictId());
					model.setThLabel(data.getDistrictName());
					model.setEnLabel(data.getDistrictNameEng());
					districtSendDocList.add(model);
				}
				DropDownModel model = new DropDownModel();
				model.setIntKey(-1);
				model.setThLabel("");
				model.setEnLabel("");
				districtSendDocList.add(0,model);
				districtIdSendDoc = -1;
			}
		}
	}

	public void onChangeDistrictSendDoc(){
		addressPostCodeSendDoc = "";
		if(districtIdSendDoc!=-1){
			AddressDistricts addressDistricts = em.find(AddressDistricts.class, new Integer(districtIdSendDoc));
			if(addressDistricts!=null){
				addressPostCodeSendDoc = StringUtil.n2b(addressDistricts.getPostCode());
			}
		}		
	}

	public void search(){
		long startTime = System.currentTimeMillis();
		log.info("##### SEARCH ##### = "+searchCustomer);
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
		try{
			if(searchCustomer!=null && searchCustomer.trim().length()>0){
				MemberCustomer memSearch = null;
				boolean chkNumber = false;
				try{
					Integer.parseInt(searchCustomer);
					chkNumber = true;
				}catch(Exception ex){
					log.info("Error : "+ex.getMessage());
				}
				if(chkNumber){
					memSearch = (MemberCustomer)em.createQuery("From MemberCustomer Where customerMember =:customerMember ")
							.setParameter("customerMember", searchCustomer).getSingleResult();
				}else{
					memSearch = (MemberCustomer)em.createQuery("From MemberCustomer Where firstName =:firstName ")
							.setParameter("firstName", searchCustomer).getSingleResult();
				}
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
					if(customerModel_01==null){
						messages.info(new AppBundleKey("error.label.searchOutLine",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
					}
				}else if(cusId == user.get().getCustomerId().getCustomerId().intValue()){
					customerId = cusId;
					genTreeModel();
				}else {
					// alert ERROR
					messages.info(new AppBundleKey("error.label.searchOutLine",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
				}
			}else{
				messages.info(new AppBundleKey("error.label.pleaseInputText",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
			}
		}catch(Exception e){
			log.info("Error : "+e.getMessage());
			messages.info(new AppBundleKey("error.label.searchOutLine",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}
		long endTime = System.currentTimeMillis();
		log.info("search() Time = "+((endTime-startTime)/1000d)+"s");
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
		long startTime = System.currentTimeMillis();
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
		long endTime = System.currentTimeMillis();
		log.info("genTree(String flag,long cusId) Time = "+((endTime-startTime)/1000d)+"s");
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
			
			detailCustomerStr = customerModel_01.getCustomerMember();
			detailRegisDate = customerModel_01.getRegisDate()!=null?sdf.format(customerModel_01.getRegisDate()):"";
			detailPosRecomment = "";
			if(customerModel_01.getPositionId()!=0){
				MemberPosition memberPosition = em.find(MemberPosition.class, new Integer(customerModel_01.getPositionId()));
				detailPosMatch = memberPosition.getEnName();
			}else{
				detailPosMatch = "";
			}
			detailDocumentRegis = customerModel_01.getDateDocumentFully()!=null?sdf.format(customerModel_01.getDateDocumentFully()):"";
			detailDocumentPersonalCard = customerModel_01.getDateCopyPersonalCard()!=null?sdf.format(customerModel_01.getDateCopyPersonalCard()):"";
			detailDocumentBookBank = customerModel_01.getDateCopyBookBank()!=null?sdf.format(customerModel_01.getDateCopyBookBank()):"";
			
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
				model.setFirstName(StringUtil.n2b(customer.getFirstName()));
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
				model.setDateDocumentFully(customer.getDateDocumentFully());
				model.setDateCopyPersonalCard(customer.getDateCopyPersonalCard());
				model.setDateCopyBookBank(customer.getDateCopyBookBank());
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

	// ########################### ADD MEMBER ##############################	
	public void viewAddMember(){
		try{
			log.info("####### viewAddMember() 1");
			log.info("####### upperLineId = "+upperLineId);
			log.info("####### flagUnder = "+flagUnder);
			starTitleName = "*";
			starFirstName = "*";

			accType = 1;
			MemberCustomer customerUpper = em.find(MemberCustomer.class, new Integer(new BigDecimal(upperLineId).intValue()));
			if(customerUpper!=null){
				upperLineMemberId = customerUpper.getCustomerMember();
				upperLineName = StringUtil.n2b(customerUpper.getTitleName())+" "+StringUtil.n2b(customerUpper.getFirstName())
						+" "+StringUtil.n2b(customerUpper.getLastName());
				side = new BigDecimal(flagUnder).intValue();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void findRecomend(){
		log.info("##### searchRecomend()");
		log.info("##### recomendStrId = "+recomendStrId);
		recomendName = "";
		if(recomendStrId!=null && recomendStrId.trim().length()>0){
			MemberCustomer customerRecommend = null;
			try{
				customerRecommend = (MemberCustomer)em.createQuery("From MemberCustomer " +
						" Where customerMember=:customerMember")
						.setParameter("customerMember", recomendStrId)
						.getSingleResult();
			}catch(Exception e){
				log.info("Error : "+e.getMessage());
			}
			if(customerRecommend!=null){
				recomendId = customerRecommend.getCustomerId();
				recomendName = StringUtil.n2b(customerRecommend.getTitleName())+" "+StringUtil.n2b(customerRecommend.getFirstName())
						+" "+StringUtil.n2b(customerRecommend.getLastName());
			}else{
				messages.info(new AppBundleKey("error.label.notFoundMember",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
			}
		}else{
			messages.info(new AppBundleKey("error.label.notKeyData",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}
	}

	public void cancleAddMember(){
		log.info("##### cancleAddMember()");
		log.info("##### customerId = "+customerId);
		upperLineId = 0;
		flagUnder = 0;
		chkSave = true;
		
		memberStr = "";
		titleName = "";
		starTitleName = "";
		firstName = "";
		starFirstName = "";
		businessName = "";
		regisDate = null;
		sex = 0;
		starSex = "";
		starBirthDay = "";
		birthDay = null;
		nationality = "";
		starPersonalId = "";
		personalId = "";
		companyID = "";
		telephone = "";
		mobile = "";
		fax = "";
		email = "";
		starUpperLineId = "";
		upperLineMemberId = "";
		starUpperLineName = "";
		upperLineName = "";
		starSide = "";
		side = 0;
		starRecomendId = "";
		recomendStrId = "";
		recomendId = 0;
		starRecomendName = "";
		recomendName = "";
		addressNo = "";
		addressBuilding = "";
		addressVillage = "";
		addressLane = "";
		addressRoad = "";
		starProvince = "";
		provinceId = -1;
		starAmphur = "";
		amphurId = -1;
		starDistrict = "";
		districtId = -1;
		addressPostCode = "";
		addressNoSendDoc = "";
		addressBuildingSendDoc = "";
		addressVillageSendDoc = "";
		addressLaneSendDoc = "";
		addressRoadSendDoc = "";
		starProvinceSendDoc = "";
		provinceIdSendDoc = -1;
		starAmphurSendDoc = "";
		amphurIdSendDoc = -1;
		starDistrictSendDoc = "";
		districtIdSendDoc = -1;
		addressPostCodeSendDoc = "";
		bankId = -1;
		branch = "";
		accType = 1;
		accNo = "";
		accName = "";
		remark = "";
		receiveDocument = 0;
		starDocumentFully = "";
		chkDocumentFully = false;
		dateDocumentFully = null;
		chkCopyPersonalCard = false;
		dateCopyPersonalCard = null;
		chkCopyBookBank = false;
		dateCopyBookBank = null;
		upperLineId = 0;
		flagUnder = 0;
		chkSave = false;
		amphurList = null;
		districtList = null;
		amphurSendDocList = null;
		districtSendDocList = null;
	}
	
	public void sameAddress(){
		log.info("##### sameAddress()");
		log.info("##### chkSameAddress = "+chkSameAddress);
		log.info("##### addressNo = "+addressNo);
		log.info("##### addressBuilding = "+addressBuilding);
		log.info("##### addressVillage = "+addressVillage);
		log.info("##### addressLane = "+addressLane);
		log.info("##### addressRoad = "+addressRoad);
		log.info("##### provinceId = "+provinceId);
		log.info("##### amphurId = "+amphurId);
		log.info("##### districtId = "+districtId);
		log.info("##### addressPostCode = "+addressPostCode);
		
		if(chkSameAddress){
			log.info("TRUE");
			addressNoSendDoc = addressNo;
			addressBuildingSendDoc = addressBuilding;
			addressVillageSendDoc = addressVillage;
			addressLaneSendDoc = addressLane;
			addressRoadSendDoc = addressRoad;
			starProvinceSendDoc = starProvince;
			provinceIdSendDoc = provinceId;
			onChangeProvinceSendDoc();
			starAmphurSendDoc = starAmphur;
			amphurIdSendDoc = amphurId;
			onChangeAmphurSendDoc();
			starDistrictSendDoc = starDistrict;
			districtIdSendDoc = districtId;
//			onChangeDistrictSendDoc();
			addressPostCodeSendDoc = addressPostCode;
		}else{
			log.info("FALSE");
			addressNoSendDoc = "";
			addressBuildingSendDoc = "";
			addressVillageSendDoc = "";
			addressLaneSendDoc = "";
			addressRoadSendDoc = "";
			starProvinceSendDoc = "";
			provinceIdSendDoc = -1;
			starAmphurSendDoc = "";
			amphurIdSendDoc = -1;
			starDistrictSendDoc = "";
			districtIdSendDoc = -1;
			addressPostCodeSendDoc = "";
		}
	}

	public void confirmAddMember(){
		log.info("##### addCustomer()");
		log.info("##### user.get().getUserId = "+user.get().getUserId());
		log.info("##### memberStr = "+memberStr);
		log.info("##### titleName = "+titleName);
		log.info("##### firstName = "+firstName);
		log.info("##### regisDate = "+regisDate!=null?sdf.format(regisDate):"");
		log.info("##### upperLineId = "+upperLineId);
		log.info("##### flagUnder = "+flagUnder);
		log.info("################################");
		log.info("##### chkSameAddress = "+chkSameAddress);
		log.info("##### addressNo = "+addressNo);
		log.info("##### addressBuilding = "+addressBuilding);
		log.info("##### addressVillage = "+addressVillage);
		log.info("##### addressLane = "+addressLane);
		log.info("##### addressRoad = "+addressRoad);
		log.info("##### provinceId = "+provinceId);
		log.info("##### amphurId = "+amphurId);
		log.info("##### districtId = "+districtId);
		log.info("##### addressPostCode = "+addressPostCode);
		try{
			MemberCustomer cusUpper = em.find(MemberCustomer.class, new Integer(new BigDecimal(upperLineId).intValue()));
			if((flagUnder==1 && cusUpper.getLowerLeftId()==null)
					|| (flagUnder==2 && cusUpper.getLowerRightId()==null)){
				MemberCustomer cus = new MemberCustomer();
				int max = customerControl.customerIdInsert();
				cus.setCustomerId(max);
				memberStr = df.format(max);
				cus.setCustomerMember(memberStr);
				cus.setUpperId(new BigDecimal(upperLineId).intValue());
				cus.setLowerLeftId(null);
				cus.setLowerRightId(null);
				cus.setRecommendId(recomendId);
				cus.setPositionId(5);
				cus.setScore(0);
				cus.setRegisDate(regisDate);
				cus.setTitleName(titleName);
				cus.setFirstName(firstName);
				
				cus.setSex(sex);
				cus.setBirthDay(birthDay);
				cus.setNationality(nationality);
				cus.setPersonalId(personalId);
				cus.setCompanyID(companyID);
				cus.setTelephone(telephone);
				cus.setMobile(mobile);
				cus.setFax(fax);
				cus.setEmail(email);
				cus.setAddressNo(addressNo);
				cus.setAddressBuilding(addressBuilding);
				cus.setAddressVillage(addressVillage);
				cus.setAddressLane(addressLane);
				cus.setAddressRoad(addressRoad);
				cus.setProvinceId(provinceId);
				cus.setAmphurId(amphurId);
				cus.setDistrictId(districtId);
				cus.setAddressNoSendDoc(addressNoSendDoc);
				cus.setAddressBuildingSendDoc(addressBuildingSendDoc);
				cus.setAddressVillageSendDoc(addressVillageSendDoc);
				cus.setAddressLaneSendDoc(addressLaneSendDoc);
				cus.setAddressRoadSendDoc(addressRoadSendDoc);
				cus.setProvinceIdSendDoc(provinceIdSendDoc);
				cus.setAmphurIdSendDoc(amphurIdSendDoc);
				cus.setDistrictIdSendDoc(districtIdSendDoc);
				cus.setChkSameAddress(chkSameAddress?1:0);
				cus.setBankId(bankId);
				cus.setBankBranch(branch);
				cus.setBankaccountType(accType);
				cus.setBankaccountNo(accNo);
				cus.setBankaccountName(accName);
				cus.setRemark(remark);
				cus.setReceiveDocument(receiveDocument);
				cus.setDateDocumentFully(chkDocumentFully?dateDocumentFully:null);
				cus.setDateCopyPersonalCard(chkCopyPersonalCard?dateCopyPersonalCard:null);
				cus.setDateCopyBookBank(chkCopyBookBank?dateCopyBookBank:null);
				
				cus.setStatus(0);
				cus.setCreateBy(user.get().getUserId());
				cus.setCreateDate(new Date());
				cus.setUpdateBy(user.get().getUserId());
				cus.setUpdateDate(new Date());
				em.persist(cus);
				if(flagUnder == 1){
					cusUpper.setLowerLeftId(max);
				}else if(flagUnder == 2){
					cusUpper.setLowerRightId(max);
				}
				em.merge(cusUpper);
//				messages.info(new AppBundleKey("error.label.addMemberSuccess",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
				genTreeModel();
			}
		}catch(Exception e){
			e.printStackTrace();
			messages.error(new AppBundleKey("error.label.addMemberFail",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}
		upperLineId = 0;
		flagUnder = 0;
		chkSave = true;
		
		memberStr = "";
		titleName = "";
		starTitleName = "";
		firstName = "";
		starFirstName = "";
		businessName = "";
		regisDate = null;
		sex = 0;
		starSex = "";
		starBirthDay = "";
		birthDay = null;
		nationality = "";
		starPersonalId = "";
		personalId = "";
		companyID = "";
		telephone = "";
		mobile = "";
		fax = "";
		email = "";
		starUpperLineId = "";
		upperLineMemberId = "";
		starUpperLineName = "";
		upperLineName = "";
		starSide = "";
		side = 0;
		starRecomendId = "";
		recomendStrId = "";
		recomendId = 0;
		starRecomendName = "";
		recomendName = "";
		addressNo = "";
		addressBuilding = "";
		addressVillage = "";
		addressLane = "";
		addressRoad = "";
		starProvince = "";
		provinceId = -1;
		starAmphur = "";
		amphurId = -1;
		starDistrict = "";
		districtId = -1;
		addressPostCode = "";
		addressNoSendDoc = "";
		addressBuildingSendDoc = "";
		addressVillageSendDoc = "";
		addressLaneSendDoc = "";
		addressRoadSendDoc = "";
		starProvinceSendDoc = "";
		provinceIdSendDoc = -1;
		starAmphurSendDoc = "";
		amphurIdSendDoc = -1;
		starDistrictSendDoc = "";
		districtIdSendDoc = -1;
		addressPostCodeSendDoc = "";
		bankId = -1;
		branch = "";
		accType = 1;
		accNo = "";
		accName = "";
		remark = "";
		receiveDocument = 0;
		starDocumentFully = "";
		chkDocumentFully = false;
		dateDocumentFully = null;
		chkCopyPersonalCard = false;
		dateCopyPersonalCard = null;
		chkCopyBookBank = false;
		dateCopyBookBank = null;
		upperLineId = 0;
		flagUnder = 0;
		chkSave = false;
		amphurList = null;
		districtList = null;
		amphurSendDocList = null;
		districtSendDocList = null;
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
	// ########################### ADD MEMBER ##############################

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

	public String getMemberStr() {
		return memberStr;
	}

	public void setMemberStr(String memberStr) {
		this.memberStr = memberStr;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public String getStarTitleName() {
		return starTitleName;
	}

	public void setStarTitleName(String starTitleName) {
		this.starTitleName = starTitleName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getStarFirstName() {
		return starFirstName;
	}

	public void setStarFirstName(String starFirstName) {
		this.starFirstName = starFirstName;
	}

	public Date getRegisDate() {
		return regisDate;
	}

	public void setRegisDate(Date regisDate) {
		this.regisDate = regisDate;
	}

	public long getUpperLineId() {
		return upperLineId;
	}

	public void setUpperLineId(long upperLineId) {
		this.upperLineId = upperLineId;
	}

	public String getUpperLineName() {
		return upperLineName;
	}

	public void setUpperLineName(String upperLineName) {
		this.upperLineName = upperLineName;
	}

	public long getFlagUnder() {
		return flagUnder;
	}

	public void setFlagUnder(long flagUnder) {
		this.flagUnder = flagUnder;
	}

	public boolean getChkSave() {
		return chkSave;
	}

	public void setChkSave(boolean chkSave) {
		this.chkSave = chkSave;
	}

	public CustomerControl getCustomerControl() {
		return customerControl;
	}

	public void setCustomerControl(CustomerControl customerControl) {
		this.customerControl = customerControl;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getStarSex() {
		return starSex;
	}

	public void setStarSex(String starSex) {
		this.starSex = starSex;
	}

	public String getStarBirthDay() {
		return starBirthDay;
	}

	public void setStarBirthDay(String starBirthDay) {
		this.starBirthDay = starBirthDay;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getStarPersonalId() {
		return starPersonalId;
	}

	public void setStarPersonalId(String starPersonalId) {
		this.starPersonalId = starPersonalId;
	}

	public String getPersonalId() {
		return personalId;
	}

	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}

	public String getCompanyID() {
		return companyID;
	}

	public void setCompanyID(String companyID) {
		this.companyID = companyID;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStarUpperLineId() {
		return starUpperLineId;
	}

	public void setStarUpperLineId(String starUpperLineId) {
		this.starUpperLineId = starUpperLineId;
	}

	public String getUpperLineMemberId() {
		return upperLineMemberId;
	}

	public void setUpperLineMemberId(String upperLineMemberId) {
		this.upperLineMemberId = upperLineMemberId;
	}

	public String getStarUpperLineName() {
		return starUpperLineName;
	}

	public void setStarUpperLineName(String starUpperLineName) {
		this.starUpperLineName = starUpperLineName;
	}

	public String getStarSide() {
		return starSide;
	}

	public void setStarSide(String starSide) {
		this.starSide = starSide;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public String getStarRecomendId() {
		return starRecomendId;
	}

	public void setStarRecomendId(String starRecomendId) {
		this.starRecomendId = starRecomendId;
	}

	public String getStarRecomendName() {
		return starRecomendName;
	}

	public void setStarRecomendName(String starRecomendName) {
		this.starRecomendName = starRecomendName;
	}

	public String getRecomendName() {
		return recomendName;
	}

	public void setRecomendName(String recomendName) {
		this.recomendName = recomendName;
	}

	public String getAddressNo() {
		return addressNo;
	}

	public void setAddressNo(String addressNo) {
		this.addressNo = addressNo;
	}

	public String getAddressBuilding() {
		return addressBuilding;
	}

	public void setAddressBuilding(String addressBuilding) {
		this.addressBuilding = addressBuilding;
	}

	public String getAddressVillage() {
		return addressVillage;
	}

	public void setAddressVillage(String addressVillage) {
		this.addressVillage = addressVillage;
	}

	public String getAddressLane() {
		return addressLane;
	}

	public void setAddressLane(String addressLane) {
		this.addressLane = addressLane;
	}

	public String getAddressRoad() {
		return addressRoad;
	}

	public void setAddressRoad(String addressRoad) {
		this.addressRoad = addressRoad;
	}

	public String getStarProvince() {
		return starProvince;
	}

	public void setStarProvince(String starProvince) {
		this.starProvince = starProvince;
	}

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public String getStarDistrict() {
		return starDistrict;
	}

	public void setStarDistrict(String starDistrict) {
		this.starDistrict = starDistrict;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getStarAmphur() {
		return starAmphur;
	}

	public void setStarAmphur(String starAmphur) {
		this.starAmphur = starAmphur;
	}

	public int getAmphurId() {
		return amphurId;
	}

	public void setAmphurId(int amphurId) {
		this.amphurId = amphurId;
	}

	public String getAddressPostCode() {
		return addressPostCode;
	}

	public void setAddressPostCode(String addressPostCode) {
		this.addressPostCode = addressPostCode;
	}

	public String getAddressNoSendDoc() {
		return addressNoSendDoc;
	}

	public void setAddressNoSendDoc(String addressNoSendDoc) {
		this.addressNoSendDoc = addressNoSendDoc;
	}

	public String getAddressBuildingSendDoc() {
		return addressBuildingSendDoc;
	}

	public void setAddressBuildingSendDoc(String addressBuildingSendDoc) {
		this.addressBuildingSendDoc = addressBuildingSendDoc;
	}

	public String getAddressVillageSendDoc() {
		return addressVillageSendDoc;
	}

	public void setAddressVillageSendDoc(String addressVillageSendDoc) {
		this.addressVillageSendDoc = addressVillageSendDoc;
	}

	public String getAddressLaneSendDoc() {
		return addressLaneSendDoc;
	}

	public void setAddressLaneSendDoc(String addressLaneSendDoc) {
		this.addressLaneSendDoc = addressLaneSendDoc;
	}

	public String getAddressRoadSendDoc() {
		return addressRoadSendDoc;
	}

	public void setAddressRoadSendDoc(String addressRoadSendDoc) {
		this.addressRoadSendDoc = addressRoadSendDoc;
	}

	public String getStarProvinceSendDoc() {
		return starProvinceSendDoc;
	}

	public void setStarProvinceSendDoc(String starProvinceSendDoc) {
		this.starProvinceSendDoc = starProvinceSendDoc;
	}

	public int getProvinceIdSendDoc() {
		return provinceIdSendDoc;
	}

	public void setProvinceIdSendDoc(int provinceIdSendDoc) {
		this.provinceIdSendDoc = provinceIdSendDoc;
	}

	public String getStarDistrictSendDoc() {
		return starDistrictSendDoc;
	}

	public void setStarDistrictSendDoc(String starDistrictSendDoc) {
		this.starDistrictSendDoc = starDistrictSendDoc;
	}

	public int getDistrictIdSendDoc() {
		return districtIdSendDoc;
	}

	public void setDistrictIdSendDoc(int districtIdSendDoc) {
		this.districtIdSendDoc = districtIdSendDoc;
	}

	public String getStarAmphurSendDoc() {
		return starAmphurSendDoc;
	}

	public void setStarAmphurSendDoc(String starAmphurSendDoc) {
		this.starAmphurSendDoc = starAmphurSendDoc;
	}

	public int getAmphurIdSendDoc() {
		return amphurIdSendDoc;
	}

	public void setAmphurIdSendDoc(int amphurIdSendDoc) {
		this.amphurIdSendDoc = amphurIdSendDoc;
	}

	public String getAddressPostCodeSendDoc() {
		return addressPostCodeSendDoc;
	}

	public void setAddressPostCodeSendDoc(String addressPostCodeSendDoc) {
		this.addressPostCodeSendDoc = addressPostCodeSendDoc;
	}

	public int getBankId() {
		return bankId;
	}

	public void setBankId(int bankId) {
		this.bankId = bankId;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public int getAccType() {
		return accType;
	}

	public void setAccType(int accType) {
		this.accType = accType;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getAccName() {
		return accName;
	}

	public void setAccName(String accName) {
		this.accName = accName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getReceiveDocument() {
		return receiveDocument;
	}

	public void setReceiveDocument(int receiveDocument) {
		this.receiveDocument = receiveDocument;
	}

	public String getStarDocumentFully() {
		return starDocumentFully;
	}

	public void setStarDocumentFully(String starDocumentFully) {
		this.starDocumentFully = starDocumentFully;
	}

	public boolean getChkDocumentFully() {
		return chkDocumentFully;
	}

	public void setChkDocumentFully(boolean chkDocumentFully) {
		this.chkDocumentFully = chkDocumentFully;
	}

	public Date getDateDocumentFully() {
		return dateDocumentFully;
	}

	public void setDateDocumentFully(Date dateDocumentFully) {
		this.dateDocumentFully = dateDocumentFully;
	}

	public boolean getChkCopyPersonalCard() {
		return chkCopyPersonalCard;
	}

	public void setChkCopyPersonalCard(boolean chkCopyPersonalCard) {
		this.chkCopyPersonalCard = chkCopyPersonalCard;
	}

	public Date getDateCopyPersonalCard() {
		return dateCopyPersonalCard;
	}

	public void setDateCopyPersonalCard(Date dateCopyPersonalCard) {
		this.dateCopyPersonalCard = dateCopyPersonalCard;
	}

	public boolean getChkCopyBookBank() {
		return chkCopyBookBank;
	}

	public void setChkCopyBookBank(boolean chkCopyBookBank) {
		this.chkCopyBookBank = chkCopyBookBank;
	}

	public Date getDateCopyBookBank() {
		return dateCopyBookBank;
	}

	public void setDateCopyBookBank(Date dateCopyBookBank) {
		this.dateCopyBookBank = dateCopyBookBank;
	}

	public List<DropDownModel> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<DropDownModel> provinceList) {
		this.provinceList = provinceList;
	}

	public List<DropDownModel> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DropDownModel> districtList) {
		this.districtList = districtList;
	}

	public List<DropDownModel> getAmphurList() {
		return amphurList;
	}

	public void setAmphurList(List<DropDownModel> amphurList) {
		this.amphurList = amphurList;
	}

	public List<DropDownModel> getProvinceSendDocList() {
		return provinceSendDocList;
	}

	public void setProvinceSendDocList(List<DropDownModel> provinceSendDocList) {
		this.provinceSendDocList = provinceSendDocList;
	}

	public List<DropDownModel> getDistrictSendDocList() {
		return districtSendDocList;
	}

	public void setDistrictSendDocList(List<DropDownModel> districtSendDocList) {
		this.districtSendDocList = districtSendDocList;
	}

	public List<DropDownModel> getAmphurSendDocList() {
		return amphurSendDocList;
	}

	public void setAmphurSendDocList(List<DropDownModel> amphurSendDocList) {
		this.amphurSendDocList = amphurSendDocList;
	}

	public List<DropDownModel> getBankList() {
		return bankList;
	}

	public void setBankList(List<DropDownModel> bankList) {
		this.bankList = bankList;
	}

	public boolean getChkSameAddress() {
		return chkSameAddress;
	}

	public void setChkSameAddress(boolean chkSameAddress) {
		this.chkSameAddress = chkSameAddress;
	}

	public String getRecomendStrId() {
		return recomendStrId;
	}

	public void setRecomendStrId(String recomendStrId) {
		this.recomendStrId = recomendStrId;
	}

	public String getDetailCustomerStr() {
		return detailCustomerStr;
	}

	public void setDetailCustomerStr(String detailCustomerStr) {
		this.detailCustomerStr = detailCustomerStr;
	}

	public String getDetailRegisDate() {
		return detailRegisDate;
	}

	public void setDetailRegisDate(String detailRegisDate) {
		this.detailRegisDate = detailRegisDate;
	}

	public String getDetailPosRecomment() {
		return detailPosRecomment;
	}

	public void setDetailPosRecomment(String detailPosRecomment) {
		this.detailPosRecomment = detailPosRecomment;
	}

	public String getDetailPosMatch() {
		return detailPosMatch;
	}

	public void setDetailPosMatch(String detailPosMatch) {
		this.detailPosMatch = detailPosMatch;
	}

	public String getDetailDocumentRegis() {
		return detailDocumentRegis;
	}

	public void setDetailDocumentRegis(String detailDocumentRegis) {
		this.detailDocumentRegis = detailDocumentRegis;
	}

	public String getDetailDocumentPersonalCard() {
		return detailDocumentPersonalCard;
	}

	public void setDetailDocumentPersonalCard(String detailDocumentPersonalCard) {
		this.detailDocumentPersonalCard = detailDocumentPersonalCard;
	}

	public String getDetailDocumentBookBank() {
		return detailDocumentBookBank;
	}

	public void setDetailDocumentBookBank(String detailDocumentBookBank) {
		this.detailDocumentBookBank = detailDocumentBookBank;
	}
	
	
}
