package co.th.aten.network.web;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.entity.AddressAmphures;
import co.th.aten.network.entity.AddressDistricts;
import co.th.aten.network.entity.AddressProvinces;
import co.th.aten.network.entity.MasterBank;
import co.th.aten.network.entity.MasterNationality;
import co.th.aten.network.entity.MasterOfficialDocument;
import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.i18n.AppBundleKey;
import co.th.aten.network.model.DropDownModel;
import co.th.aten.network.model.UploadedImage;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.annotation.Authenticated;
import co.th.aten.network.util.StringUtil;

//@ViewScoped
@SessionScoped
@Named
public class EditCustomerController implements Serializable{

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
	private MessageFactory factory;
	@Inject
	private Messages messages;
	@Inject
	private FacesContext facesContext;

	@Inject
	@DBDefault
	private EntityManager em;

	private long customerId;
	private String searchCustomer;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.US);

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
	private int nationality;
	private List<DropDownModel> nationalityList;
	private int officialDocumentId;
	private List<DropDownModel> officialDocumentList;
	private String starPersonalId;
	private String personalId;
	private String companyID;
	private String telephone;
	private String starMobile;
	private String mobile;
	private String lineId;
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
	private String provinceStr;
	private String amphurStr;
	private String districtStr;
	private boolean chkNationality;

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

	private boolean chkSave;
	private boolean chkSameAddress;
	
	// image member
	private UploadedImage uploadedImage;
	// document
	private UploadedImage uploadedApplication;
	private UploadedImage uploadedIdCard;
	private UploadedImage uploadedBookBank;

	@PostConstruct
	public void init(){
		log.info("init method EditCustomerController");
		clearData();
		long startTime = System.currentTimeMillis();
		chkSave = false;
		try{
			if(user.get().getCustomerId()!=null){
				MemberCustomer customer = user.get().getCustomerId();
				if(customer!=null){
					memberStr = customer.getCustomerMember();
					titleName = customer.getTitleName();
					starTitleName = "";
					firstName = customer.getFirstName();
					starFirstName = "";
					businessName = customer.getBusinessName();
					regisDate = customer.getRegisDate();
					sex = StringUtil.n2b(customer.getSex());
					starSex = "";
					starBirthDay = "";
					birthDay = customer.getBirthDay();
					nationalityList = new ArrayList<DropDownModel>();
					List<MasterNationality> masterNationalityList = em.createQuery("From MasterNationality Order By nationId asc",MasterNationality.class).getResultList();
					if(masterNationalityList!=null){
						for(MasterNationality nation:masterNationalityList){
							DropDownModel model = new DropDownModel();
							model.setIntKey(nation.getNationId());
							model.setThLabel(StringUtil.n2b(nation.getDescTh()));
							model.setEnLabel(StringUtil.n2b(nation.getDescEn()));
							nationalityList.add(model);
						}
						DropDownModel model = new DropDownModel();
						model.setIntKey(-1);
						model.setThLabel("");
						model.setEnLabel("");
						nationalityList.add(0,model);
						if(customer.getNationId()!=null){
							nationality = StringUtil.n2b(customer.getNationId().getNationId());
						}else{
							nationality = -1;
						}						
					}
					officialDocumentList = new ArrayList<DropDownModel>();
					List<MasterOfficialDocument> masterOfficialDocumentList = em.createQuery("From MasterOfficialDocument Order By offDocId asc",MasterOfficialDocument.class).getResultList();
					if(masterOfficialDocumentList!=null){
						for(MasterOfficialDocument officDoc:masterOfficialDocumentList){
							DropDownModel model = new DropDownModel();
							model.setIntKey(StringUtil.n2b(officDoc.getOffDocId()));
							model.setThLabel(StringUtil.n2b(officDoc.getDescTh()));
							model.setEnLabel(StringUtil.n2b(officDoc.getDescEn()));
							officialDocumentList.add(model);
						}
						DropDownModel model = new DropDownModel();
						model.setIntKey(-1);
						model.setThLabel("");
						model.setEnLabel("");
						officialDocumentList.add(0,model);
						if(customer.getOfficialDocumentId()!=null){
							officialDocumentId = StringUtil.n2b(customer.getOfficialDocumentId().getOffDocId());
						}else{
							officialDocumentId = -1;
						}						
					}					
					starPersonalId = "";
					personalId = customer.getPersonalId();
					companyID = customer.getCompanyID();
					telephone = customer.getTelephone();
					mobile = customer.getMobile();
					lineId = customer.getLineId();
					email = customer.getEmail();
					if(customer.getUpperId()!=null && customer.getUpperId()!=0){
						MemberCustomer customerUpper = em.find(MemberCustomer.class, customer.getUpperId());
						if(customerUpper!=null){
							upperLineMemberId = customerUpper.getCustomerMember();
							upperLineName = StringUtil.n2b(customerUpper.getTitleName())+" "+StringUtil.n2b(customerUpper.getFirstName())
									+" "+StringUtil.n2b(customerUpper.getLastName());
							side = (customerUpper.getLowerLeftId()!=null
									&&customerUpper.getLowerLeftId()==customer.getCustomerId())
									?1:2;
						}
					}else{
						upperLineMemberId = "";
						upperLineName = "";
						side = 0;
					}
					starUpperLineId = "";
					starUpperLineName = "";
					starSide = "";
					starRecomendId = "";
					starRecomendName = "";
					if(customer.getRecommendId()!=null && customer.getRecommendId()!=0){
						MemberCustomer customerRecomment = em.find(MemberCustomer.class, customer.getRecommendId());
						if(customerRecomment!=null){
							recomendStrId = customerRecomment.getCustomerMember();
							recomendName = StringUtil.n2b(customerRecomment.getTitleName())+" "+StringUtil.n2b(customerRecomment.getFirstName())
									+" "+StringUtil.n2b(customerRecomment.getLastName());
						}
					}else{
						recomendStrId = "";
						recomendId = 0;
						recomendName = "";
					}
					addressNo = customer.getAddressNo();
					addressBuilding = customer.getAddressBuilding();
					addressVillage = customer.getAddressVillage();
					addressLane = customer.getAddressLane();
					addressRoad = customer.getAddressRoad();
					starProvince = "";
					provinceId = customer.getProvinceId()!=null?customer.getProvinceId():-1;
					starAmphur = "";
					amphurId = customer.getAmphurId()!=null?customer.getAmphurId():-1;
					starDistrict = "";
					districtId = customer.getDistrictId()!=null?customer.getDistrictId():-1;
					if(districtId!=-1){
						AddressDistricts addressDistricts = em.find(AddressDistricts.class, new Integer(districtId));
						if(addressDistricts!=null && addressDistricts.getPostCode()!=null){
							addressPostCode = StringUtil.n2b(addressDistricts.getPostCode());
						}else{
							addressPostCode = StringUtil.n2b(customer.getPostCodeStr());
						}
					}
					provinceStr = StringUtil.n2b(customer.getProvinceStr());
					amphurStr = StringUtil.n2b(customer.getAmphurStr());
					districtStr = StringUtil.n2b(customer.getDistrictStr());					
					
//					chkSameAddress = (customer.getChkSameAddress()!=null && customer.getChkSameAddress()==1)?true:false;
//					addressNoSendDoc = customer.getAddressNoSendDoc();
//					addressBuildingSendDoc = customer.getAddressBuildingSendDoc();
//					addressVillageSendDoc = customer.getAddressVillageSendDoc();
//					addressLaneSendDoc = customer.getAddressLaneSendDoc();
//					addressRoadSendDoc = customer.getAddressRoadSendDoc();
//					starProvinceSendDoc = "";
//					provinceIdSendDoc = customer.getProvinceIdSendDoc()!=null?customer.getProvinceIdSendDoc():-1;
//					starAmphurSendDoc = "";
//					amphurIdSendDoc = customer.getAmphurIdSendDoc()!=null?customer.getAmphurIdSendDoc():-1;
//					starDistrictSendDoc = "";
//					districtIdSendDoc = customer.getDistrictIdSendDoc()!=null?customer.getDistrictIdSendDoc():-1;
//					if(districtIdSendDoc!=-1){
//						AddressDistricts addressDistricts = em.find(AddressDistricts.class, new Integer(districtIdSendDoc));
//						if(addressDistricts!=null){
//							addressPostCodeSendDoc = StringUtil.n2b(addressDistricts.getPostCode());
//						}
//					}
					
					bankId = customer.getBankId()!=null?customer.getBankId():-1;
					branch = customer.getBankBranch();
					accType = StringUtil.n2b(customer.getBankaccountType());
					accNo = customer.getBankaccountNo();
					accName = customer.getBankaccountName();
					remark = customer.getRemark();
					if(customer.getImageMember()!=null){
						uploadedImage = new UploadedImage();
						uploadedImage.setData(customer.getImageMember());
						uploadedImage.setLength(customer.getImageMember().length);
						uploadedImage.setName(StringUtil.n2b(customer.getImageMemberName()));
					}
					if(customer.getDocumentApplication()!=null){
						uploadedApplication = new UploadedImage();
						uploadedApplication.setData(customer.getDocumentApplication());
						uploadedApplication.setLength(customer.getDocumentApplication().length);
						uploadedApplication.setName(StringUtil.n2b(customer.getDocumentApplicationName()));
					}
					if(customer.getDocumentIdCard()!=null){
						uploadedIdCard = new UploadedImage();
						uploadedIdCard.setData(customer.getDocumentIdCard());
						uploadedIdCard.setLength(customer.getDocumentIdCard().length);
						uploadedIdCard.setName(StringUtil.n2b(customer.getDocumentIdCardName()));
					}
					if(customer.getDocumentBookBank()!=null){
						uploadedBookBank = new UploadedImage();
						uploadedBookBank.setData(customer.getDocumentBookBank());
						uploadedBookBank.setLength(customer.getDocumentBookBank().length);
						uploadedBookBank.setName(StringUtil.n2b(customer.getDocumentBookBankName()));
					}
					onChangeNationality();
					onKeypress();
				}else{
					chkSave = true;
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		long startTime2 = System.currentTimeMillis();
		provinceList = new ArrayList<DropDownModel>();
		provinceSendDocList = new ArrayList<DropDownModel>();
		List<AddressProvinces> provinList = em.createQuery("From AddressProvinces",AddressProvinces.class).getResultList();
		if(provinList!=null){
			for(AddressProvinces provin:provinList){
				DropDownModel model = new DropDownModel();
				model.setIntKey(provin.getProvinceId());
				model.setThLabel(provin.getProvinceName());
				model.setEnLabel(provin.getProvinceNameEng());
				provinceList.add(model);
				provinceSendDocList.add(model);
			}
			DropDownModel model = new DropDownModel();
			model.setIntKey(-1);
			model.setThLabel("");
			model.setEnLabel("");
			provinceList.add(0,model);
			provinceSendDocList.add(0,model);
		}
		amphurList = new ArrayList<DropDownModel>();
		amphurSendDocList = new ArrayList<DropDownModel>();
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
		}
		dataList = em.createQuery("From AddressAmphures Where provinceId=:provinceId"
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
		}



		districtList = new ArrayList<DropDownModel>();
		districtSendDocList = new ArrayList<DropDownModel>();
		List<AddressDistricts> dataDisList = em.createQuery("From AddressDistricts " +
				" Where amphurId=:amphurId " +
				" And provinceId=:provinceId "
				,AddressDistricts.class)
				.setParameter("amphurId", amphurId)
				.setParameter("provinceId", provinceId)
				.getResultList();
		if(dataDisList!=null){
			for(AddressDistricts data:dataDisList){
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
		}
		dataDisList = em.createQuery("From AddressDistricts " +
				" Where amphurId=:amphurId " +
				" And provinceId=:provinceId "
				,AddressDistricts.class)
				.setParameter("amphurId", amphurIdSendDoc)
				.setParameter("provinceId", provinceIdSendDoc)
				.getResultList();
		if(dataDisList!=null){
			for(AddressDistricts data:dataDisList){
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
		}
		long endTime2 = System.currentTimeMillis();
		log.info("SQL Time = "+((endTime2-startTime2)/1000d)+"s");

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
		}
		long endTime = System.currentTimeMillis();
		log.info("init method EditCustomerController Time = "+((endTime-startTime)/1000d)+"s");
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
	
	public void paint(OutputStream stream, Object object){
		try{
			if(uploadedImage!=null){
				stream.write(uploadedImage.getData());
				stream.close();
			}
		}catch(Exception e){
			log.info("Error paint : "+e.getMessage());
		}
	}
	public void listenerUploadAppication(FileUploadEvent event){
		try{
			UploadedFile uploadedFile = event.getUploadedFile();
			uploadedApplication = new UploadedImage();
			uploadedApplication.setLength(uploadedFile.getData().length);
			uploadedApplication.setName(uploadedFile.getName());
			uploadedApplication.setData(uploadedFile.getData());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void listenerUploadIdCard(FileUploadEvent event){
		try{
			UploadedFile uploadedFile = event.getUploadedFile();
			uploadedIdCard = new UploadedImage();
			uploadedIdCard.setLength(uploadedFile.getData().length);
			uploadedIdCard.setName(uploadedFile.getName());
			uploadedIdCard.setData(uploadedFile.getData());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void listenerUploadBookBank(FileUploadEvent event){
		try{
			UploadedFile uploadedFile = event.getUploadedFile();
			uploadedBookBank = new UploadedImage();
			uploadedBookBank.setLength(uploadedFile.getData().length);
			uploadedBookBank.setName(uploadedFile.getName());
			uploadedBookBank.setData(uploadedFile.getData());
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void listener(FileUploadEvent event){
		try{
			UploadedFile uploadedFile = event.getUploadedFile();
			uploadedImage = new UploadedImage();
			uploadedImage.setLength(uploadedFile.getData().length);
			uploadedImage.setName(uploadedFile.getName());
			uploadedImage.setData(uploadedFile.getData());
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
	
	private void clearData(){
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
		nationality = -1;
		officialDocumentId = -1;
		starPersonalId = "";
		personalId = "";
		companyID = "";
		telephone = "";
		mobile = "";
		lineId = "";
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
		provinceStr = "";
		amphurStr = "";
		districtStr = "";
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
		uploadedImage = null;
		uploadedApplication = null;
		uploadedIdCard = null;
		uploadedBookBank = null;
		amphurList = null;
		districtList = null;
		amphurSendDocList = null;
		districtSendDocList = null;
	}

	public void cancleEditMember(){
		log.info("##### cancleEditMember()");
		log.info("##### memberStr = "+memberStr);
		init();
//		clearData();
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

	public void onChangeNationality(){
		if(nationality == -1 || nationality == 1){
			chkNationality = true;
		}else{
			chkNationality = false;
		}
	}

	public void confirmEditMember(){
		log.info("##### confirmEditMember()");
		log.info("##### user.get().getUserId = "+user.get().getUserId());
		log.info("##### memberStr = "+memberStr);
		log.info("##### titleName = "+titleName);
		log.info("##### firstName = "+firstName);
		log.info("##### regisDate = "+regisDate!=null?sdf.format(regisDate):"");
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
			user.get().getCustomerId().setRegisDate(regisDate);
			user.get().getCustomerId().setTitleName(titleName);
			user.get().getCustomerId().setFirstName(firstName);
			user.get().getCustomerId().setBusinessName(businessName);
			user.get().getCustomerId().setSex(sex);
			user.get().getCustomerId().setBirthDay(birthDay);
			MasterNationality nation = em.find(MasterNationality.class, new Integer(nationality));
			user.get().getCustomerId().setNationId(nation);
			MasterOfficialDocument offDoc = em.find(MasterOfficialDocument.class, new Integer(officialDocumentId));
			user.get().getCustomerId().setOfficialDocumentId(offDoc);
			user.get().getCustomerId().setPersonalId(personalId);
			user.get().getCustomerId().setCompanyID(companyID);
			user.get().getCustomerId().setTelephone(telephone);
			user.get().getCustomerId().setMobile(mobile);
			user.get().getCustomerId().setLineId(lineId);
			user.get().getCustomerId().setEmail(email);
			user.get().getCustomerId().setAddressNo(addressNo);
			user.get().getCustomerId().setAddressBuilding(addressBuilding);
			user.get().getCustomerId().setAddressVillage(addressVillage);
			user.get().getCustomerId().setAddressLane(addressLane);
			user.get().getCustomerId().setAddressRoad(addressRoad);
			user.get().getCustomerId().setProvinceId(provinceId);
			user.get().getCustomerId().setAmphurId(amphurId);
			user.get().getCustomerId().setDistrictId(districtId);
			user.get().getCustomerId().setAddressNoSendDoc(addressNo);
			user.get().getCustomerId().setAddressBuildingSendDoc(addressBuilding);
			user.get().getCustomerId().setAddressVillageSendDoc(addressVillage);
			user.get().getCustomerId().setAddressLaneSendDoc(addressLane);
			user.get().getCustomerId().setAddressRoadSendDoc(addressRoad);
			user.get().getCustomerId().setProvinceIdSendDoc(provinceId);
			user.get().getCustomerId().setAmphurIdSendDoc(amphurId);
			user.get().getCustomerId().setDistrictIdSendDoc(districtId);
			
			user.get().getCustomerId().setProvinceStr(provinceStr);
			user.get().getCustomerId().setAmphurStr(amphurStr);
			user.get().getCustomerId().setDistrictStr(districtStr);
			user.get().getCustomerId().setPostCodeStr(addressPostCode);
			
			if(uploadedImage!=null){
				user.get().getCustomerId().setImageMember(uploadedImage.getData());
				user.get().getCustomerId().setImageMemberName(uploadedImage.getName());
			}
			if(uploadedApplication!=null){
				user.get().getCustomerId().setDocumentApplication(uploadedApplication.getData());
				user.get().getCustomerId().setDocumentApplicationName(uploadedApplication.getName());
			}
			if(uploadedIdCard!=null){
				user.get().getCustomerId().setDocumentIdCard(uploadedIdCard.getData());
				user.get().getCustomerId().setDocumentIdCardName(uploadedIdCard.getName());
			}
			if(uploadedBookBank!=null){
				user.get().getCustomerId().setDocumentBookBank(uploadedBookBank.getData());
				user.get().getCustomerId().setDocumentBookBankName(uploadedBookBank.getName());
			}
			
//			user.get().getCustomerId().setAddressNoSendDoc(addressNoSendDoc);
//			user.get().getCustomerId().setAddressBuildingSendDoc(addressBuildingSendDoc);
//			user.get().getCustomerId().setAddressVillageSendDoc(addressVillageSendDoc);
//			user.get().getCustomerId().setAddressLaneSendDoc(addressLaneSendDoc);
//			user.get().getCustomerId().setAddressRoadSendDoc(addressRoadSendDoc);
//			user.get().getCustomerId().setProvinceIdSendDoc(provinceIdSendDoc);
//			user.get().getCustomerId().setAmphurIdSendDoc(amphurIdSendDoc);
//			user.get().getCustomerId().setDistrictIdSendDoc(districtIdSendDoc);
			
			user.get().getCustomerId().setChkSameAddress(chkSameAddress?1:0);
			user.get().getCustomerId().setBankId(bankId);
			user.get().getCustomerId().setBankBranch(branch);
			user.get().getCustomerId().setBankaccountType(accType);
			user.get().getCustomerId().setBankaccountNo(accNo);
			user.get().getCustomerId().setBankaccountName(accName);
			user.get().getCustomerId().setRemark(remark);
			user.get().getCustomerId().setUpdateBy(user.get().getUserId());
			user.get().getCustomerId().setUpdateDate(new Date());
			em.merge(user.get().getCustomerId());
			user.get().setUserName(firstName);
			em.merge(user.get());
			messages.info(new AppBundleKey("error.label.editMemberSuccess",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}catch(Exception e){
			e.printStackTrace();
			messages.error(new AppBundleKey("error.label.editMemberFail",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}
	}

	public void onKeypress(){
		if(firstName!=null && firstName.trim().length()>0){
			starFirstName = " ";
		}else{
			starFirstName = "*";
		}
		if(personalId!=null && personalId.trim().length()>0 && officialDocumentId != -1){
			starPersonalId = " ";
		}else{
			starPersonalId = "*";
		}
		if(mobile!=null && mobile.trim().length()>0){
			starMobile = " ";
		}else{
			starMobile = "*";
		}
		if(starPersonalId.equals("*") || starFirstName.equals("*") || starMobile.equals("*")){
			chkSave = true;
		}else{
			chkSave = false;
		}
	}
	
	public void exportDocumentApplication() {
		try {
			byte[] pdf = uploadedApplication.getData();
			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			String extension = "";
			int extDot = uploadedApplication.getName().lastIndexOf('.');
			if (extDot > 0) {
				extension = uploadedApplication.getName().substring(extDot + 1);
			}
			if(extension.equals("pdf")){
				response.setContentType("application/pdf");
			}
			response.setContentLength(pdf.length);
			response.setHeader("Content-disposition", "inline; filename="
					+ uploadedApplication.getName() + "."+extension);
			ServletOutputStream out;
			out = response.getOutputStream();
			out.write(pdf);
			out.flush(); // new
			out.close();
			response.flushBuffer();
			facesContext.responseComplete(); // new
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exportDocumentIdCard() {
		try {
			byte[] pdf = uploadedIdCard.getData();
			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			String extension = "";
			int extDot = uploadedIdCard.getName().lastIndexOf('.');
			if (extDot > 0) {
				extension = uploadedIdCard.getName().substring(extDot + 1);
			}
			if(extension.equals("pdf")){
				response.setContentType("application/pdf");
			}
			response.setContentLength(pdf.length);
			response.setHeader("Content-disposition", "inline; filename="
					+ uploadedIdCard.getName() + "."+extension);
			ServletOutputStream out;
			out = response.getOutputStream();
			out.write(pdf);
			out.flush(); // new
			out.close();
			response.flushBuffer();
			facesContext.responseComplete(); // new
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void exportDocumentBookBank() {
		try {
			byte[] pdf = uploadedBookBank.getData();
			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			String extension = "";
			int extDot = uploadedBookBank.getName().lastIndexOf('.');
			if (extDot > 0) {
				extension = uploadedBookBank.getName().substring(extDot + 1);
			}
			if(extension.equals("pdf")){
				response.setContentType("application/pdf");
			}
			response.setContentLength(pdf.length);
			response.setHeader("Content-disposition", "inline; filename="
					+ uploadedBookBank.getName() + "."+extension);
			ServletOutputStream out;
			out = response.getOutputStream();
			out.write(pdf);
			out.flush(); // new
			out.close();
			response.flushBuffer();
			facesContext.responseComplete(); // new
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
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

	public String getUpperLineName() {
		return upperLineName;
	}

	public void setUpperLineName(String upperLineName) {
		this.upperLineName = upperLineName;
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

	public int getNationality() {
		return nationality;
	}

	public void setNationality(int nationality) {
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

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
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

	public List<DropDownModel> getNationalityList() {
		return nationalityList;
	}

	public void setNationalityList(List<DropDownModel> nationalityList) {
		this.nationalityList = nationalityList;
	}

	public String getProvinceStr() {
		return provinceStr;
	}

	public void setProvinceStr(String provinceStr) {
		this.provinceStr = provinceStr;
	}

	public String getAmphurStr() {
		return amphurStr;
	}

	public void setAmphurStr(String amphurStr) {
		this.amphurStr = amphurStr;
	}

	public String getDistrictStr() {
		return districtStr;
	}

	public void setDistrictStr(String districtStr) {
		this.districtStr = districtStr;
	}

	public boolean isChkNationality() {
		return chkNationality;
	}

	public void setChkNationality(boolean chkNationality) {
		this.chkNationality = chkNationality;
	}

	public UploadedImage getUploadedImage() {
		return uploadedImage;
	}

	public void setUploadedImage(UploadedImage uploadedImage) {
		this.uploadedImage = uploadedImage;
	}
	
	public long getTimeStamp(){  
		return System.currentTimeMillis();  
	}

	public int getOfficialDocumentId() {
		return officialDocumentId;
	}

	public void setOfficialDocumentId(int officialDocumentId) {
		this.officialDocumentId = officialDocumentId;
	}

	public List<DropDownModel> getOfficialDocumentList() {
		return officialDocumentList;
	}

	public void setOfficialDocumentList(List<DropDownModel> officialDocumentList) {
		this.officialDocumentList = officialDocumentList;
	}

	public String getStarMobile() {
		return starMobile;
	}

	public void setStarMobile(String starMobile) {
		this.starMobile = starMobile;
	}

	public UploadedImage getUploadedApplication() {
		return uploadedApplication;
	}

	public void setUploadedApplication(UploadedImage uploadedApplication) {
		this.uploadedApplication = uploadedApplication;
	}

	public UploadedImage getUploadedIdCard() {
		return uploadedIdCard;
	}

	public void setUploadedIdCard(UploadedImage uploadedIdCard) {
		this.uploadedIdCard = uploadedIdCard;
	}

	public UploadedImage getUploadedBookBank() {
		return uploadedBookBank;
	}

	public void setUploadedBookBank(UploadedImage uploadedBookBank) {
		this.uploadedBookBank = uploadedBookBank;
	}
}
