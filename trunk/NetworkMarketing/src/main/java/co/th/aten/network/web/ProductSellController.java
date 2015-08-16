package co.th.aten.network.web;

import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.control.TransactionReceiptControl;
import co.th.aten.network.entity.AddressAmphures;
import co.th.aten.network.entity.AddressDistricts;
import co.th.aten.network.entity.AddressProvinces;
import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.StockCatalog;
import co.th.aten.network.entity.StockProduct;
import co.th.aten.network.entity.TransactionMoney;
import co.th.aten.network.entity.TransactionMoneyStatus;
import co.th.aten.network.entity.TransactionSellDetail;
import co.th.aten.network.entity.TransactionSellHeader;
import co.th.aten.network.i18n.AppBundleKey;
import co.th.aten.network.model.DropDownModel;
import co.th.aten.network.model.ProductModel;
import co.th.aten.network.model.UploadedImage;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.StringUtil;

@SessionScoped
@Named
public class ProductSellController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5443351151396868724L;

	@Inject
	private Logger log;
	@Inject
	private CurrentUserManager currentUser;
	@Inject
	private TransactionReceiptControl transactionReceiptControl;
	@Inject
	private MessageFactory factory;
	@Inject
	private Messages messages;
	@Inject
	private CustomerControl customerControl;
	@Inject
	@DBDefault
	private EntityManager em;

	private List<ProductModel> productModelList;
	private List<ProductModel> productSellModelList;
	private List<DropDownModel> catalogModelList;
	private int selectedCatalog;
	private double totalPrice;
	private double totalPv;
	private Date dateTime;
	private String memberId;
	private String memberName;
	private MemberCustomer memSearch;
	private int sendStatus;
	private int buyStatus;
	// Addess
	private boolean chkUseAddress;
	private boolean chkNationality;
	private int nationId;
	private String addressNo;
	private String addressBuilding;
	private String addressVillage;
	private String addressLane;
	private String addressRoad;
	private int provinceId;
	private int amphurId;
	private int districtId;
	private String addressPostCode;
	private String provinceStr;
	private String amphurStr;
	private String districtStr;
	private List<DropDownModel> provinceList;
	private List<DropDownModel> amphurList;
	private List<DropDownModel> districtList;

	@PostConstruct
	public void init(){
		log.info("init method ProductSellController");
		long startTime = System.currentTimeMillis();
		try{

			if(catalogModelList==null){
				catalogModelList = new ArrayList<DropDownModel>();
				List<StockCatalog> stockCatalogList = em.createQuery("From StockCatalog",StockCatalog.class).getResultList();
				if(stockCatalogList!=null){
					for(StockCatalog data:stockCatalogList){
						DropDownModel model = new DropDownModel();
						model.setIntKey(data.getCatalogId());
						model.setThLabel(StringUtil.n2b(data.getThDesc()));
						model.setEnLabel(StringUtil.n2b(data.getEnDesc()));
						catalogModelList.add(model);
					}
					DropDownModel model = new DropDownModel();
					model.setIntKey(-1);
					model.setThLabel("");
					model.setEnLabel("");
					catalogModelList.add(0,model);
					selectedCatalog = -1;
				}
			}

			productSellModelList = new ArrayList<ProductModel>();
			productModelList = new ArrayList<ProductModel>();
			dateTime = new Date();
			totalPrice = 0;
			totalPv = 0;
			memSearch = null;
			memberId = "";
			memberName = "";
			sendStatus = 1;
			buyStatus = 1;
			if(currentUser.getCurrentAccount().getCustomerId()!=null){
				memSearch = currentUser.getCurrentAccount().getCustomerId();
				memberId = memSearch.getCustomerMember();
				memberName = customerControl.genNameMenber(memSearch);
				if(memSearch.getNationId()!=null){
					nationId = StringUtil.n2b(memSearch.getNationId().getNationId());
					if(StringUtil.n2b(memSearch.getNationId().getNationId()) == 1){
						chkNationality = true;
					}else{
						chkNationality = false;
					}
				}else{
					nationId = 0;
					chkNationality = true;
				}
			}
			List<StockProduct> productList = em.createQuery("From StockProduct",StockProduct.class).getResultList();
			if(productList!=null && productList.size()>0){
				for(StockProduct pro:productList){
					ProductModel model = new ProductModel();
					model.setProductId(StringUtil.n2b(pro.getProductId()));
					model.setCatalogId(pro.getCatalogId()!=null?pro.getCatalogId().getCatalogId():0);
					model.setProductCode(StringUtil.n2b(pro.getProductCode()));
					model.setProductThDesc(StringUtil.n2b(pro.getThDesc()));
					model.setProductEnDesc(StringUtil.n2b(pro.getEnDesc()));
					model.setPrice(StringUtil.n2b(pro.getPrice()).doubleValue());
					model.setPv(StringUtil.n2b(pro.getPv()).doubleValue());
					model.setBv(StringUtil.n2b(pro.getBv()).doubleValue());
					if(pro.getImage()!=null){
						UploadedImage image = new UploadedImage();
						image.setData(pro.getImage());
						image.setLength(pro.getImage().length);
						image.setName(StringUtil.n2b(pro.getImageName()));
						model.setImage(image);
					}
					productModelList.add(model);
				}
			}

			provinceList = new ArrayList<DropDownModel>();
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
		}catch(Exception e){
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		log.info("init method ProductSellController Time = "+((endTime-startTime)/1000d)+"s");
	}

	public void useAddress(){
		try{
			if(memSearch!=null && chkUseAddress){
				addressNo = StringUtil.n2b(memSearch.getAddressNo());
				addressBuilding = StringUtil.n2b(memSearch.getAddressBuilding());
				addressVillage = StringUtil.n2b(memSearch.getAddressVillage());
				addressLane = StringUtil.n2b(memSearch.getAddressLane());
				addressRoad = StringUtil.n2b(memSearch.getAddressRoad());
				provinceId = memSearch.getProvinceId()!=null?memSearch.getProvinceId():-1;
				amphurId = memSearch.getAmphurId()!=null?memSearch.getAmphurId():-1;
				districtId = memSearch.getDistrictId()!=null?memSearch.getDistrictId():-1;
				if(districtId!=-1){
					AddressDistricts addressDistricts = em.find(AddressDistricts.class, new Integer(districtId));
					if(addressDistricts!=null && addressDistricts.getPostCode()!=null){
						addressPostCode = StringUtil.n2b(addressDistricts.getPostCode());
					}else{
						addressPostCode = StringUtil.n2b(memSearch.getPostCodeStr());
					}
				}
				if(memSearch.getNationId()!=null){
					nationId = StringUtil.n2b(memSearch.getNationId().getNationId());
					if(StringUtil.n2b(memSearch.getNationId().getNationId()) == 1){
						chkNationality = true;
					}else{
						chkNationality = false;
					}
				}else{
					nationId = 0;
					chkNationality = true;
				}
				provinceStr = StringUtil.n2b(memSearch.getProvinceStr());
				amphurStr = StringUtil.n2b(memSearch.getAmphurStr());
				districtStr = StringUtil.n2b(memSearch.getDistrictStr());				
				provinceList = new ArrayList<DropDownModel>();
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
				}
				amphurList = new ArrayList<DropDownModel>();
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
				districtList = new ArrayList<DropDownModel>();
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
			}else{
				chkNationality = true;
				addressNo = "";
				addressBuilding = "";
				addressVillage = "";
				addressLane = "";
				addressRoad = "";
				provinceId = -1;
				amphurId = -1;
				districtId = -1;
				addressPostCode = "";
				provinceStr = "";
				amphurStr = "";
				districtStr = "";
				amphurList = null;
				districtList = null;
			}
		}catch(Exception ex){
			ex.printStackTrace();
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

	public void addCart(ProductModel model){
		log.info("##### addCart #####");
		try{
			if(model!=null){
				log.info("model.getProductId() : "+model.getProductId());
				log.info("model.getCatalogId() : "+model.getCatalogId());
				log.info("#####################");
				if(productSellModelList!=null){
					log.info("productSellModelList.size() : "+productSellModelList.size());
					boolean chkDup = false;
					for(ProductModel pro:productSellModelList){
						log.info("pro.getProductId()   : "+pro.getProductId());
						log.info("pro.getCatalogId()   : "+pro.getCatalogId());
						log.info("pro.getProductCode() : "+pro.getProductCode());
						log.info("pro.getQty()         : "+pro.getQty());
						if(pro.getProductId()==model.getProductId()
								&& pro.getCatalogId()==model.getCatalogId()){
							model.setQty(pro.getQty());
							model.setTotalPrice(pro.getTotalPrice());
							model.setTotalPv(pro.getTotalPv());
							chkDup = true;
							break;
						}
					}
					if(!chkDup){
						model.setQty(1);
						model.setTotalPrice(model.getPrice());
						model.setTotalPv(model.getPv());
					}else{
						model.setQty(model.getQty()+1);
						model.setTotalPrice(model.getTotalPrice()+model.getPrice());
						model.setTotalPv(model.getTotalPv()+model.getPv());
					}
					totalPrice += model.getPrice();
					totalPv += model.getPv();
					if(!chkDup){
						productSellModelList.add(model);
					}
					log.info("productSellModelList.size() : "+productSellModelList.size());
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void paintImg(OutputStream stream, Object object){
		try{
			stream.write(productModelList.get(((Integer)object)).getImage().getData());
			stream.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void onKeypress(){
		try{
			memSearch = null;
			if(memberId!=null && memberId.length()==7){
				memSearch = (MemberCustomer)em.createQuery("From MemberCustomer Where customerMember =:customerMember ")
						.setParameter("customerMember", memberId).getSingleResult();
				memberName = customerControl.genNameMenber(memSearch);
			}else{
				memberName = "";
			}
		}catch(Exception e){
			memberName = "";
			log.info("Error : "+e.getMessage());
		}
	}

	public void deleteOrder(ProductModel model){
		log.info("##### deleteOrder #####");
		try{
			if(model!=null){
				totalPrice = 0;
				totalPv = 0;
				log.info("model.getProductId() : "+model.getProductId());
				log.info("model.getCatalogId() : "+model.getCatalogId());
				if(productSellModelList!=null){
					log.info("productSellModelList.remove : "+productSellModelList.remove(model));
					for(ProductModel pro:productSellModelList){
						totalPrice += pro.getTotalPrice();
						totalPv += pro.getTotalPv();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void onChangeCatalog(){
		try{
			productModelList = new ArrayList<ProductModel>();
			List<StockProduct> productList = null;
			if(selectedCatalog == -1){
				productList = em.createQuery("From StockProduct",StockProduct.class).getResultList();
			}else{
				StockCatalog stockCatalog = em.find(StockCatalog.class, new Integer(selectedCatalog));
				productList = em.createQuery("From StockProduct " +
						" Where catalogId =:catalogId "
						,StockProduct.class)
						.setParameter("catalogId", stockCatalog)
						.getResultList();
			}
			if(productList!=null && productList.size()>0){
				for(StockProduct pro:productList){
					ProductModel model = new ProductModel();
					model.setProductId(StringUtil.n2b(pro.getProductId()));
					model.setCatalogId(pro.getCatalogId()!=null?pro.getCatalogId().getCatalogId():0);
					model.setProductCode(StringUtil.n2b(pro.getProductCode()));
					model.setProductThDesc(StringUtil.n2b(pro.getThDesc()));
					model.setProductEnDesc(StringUtil.n2b(pro.getEnDesc()));
					model.setPrice(StringUtil.n2b(pro.getPrice()).doubleValue());
					model.setPv(StringUtil.n2b(pro.getPv()).doubleValue());
					model.setBv(StringUtil.n2b(pro.getBv()).doubleValue());
					if(pro.getImage()!=null){
						UploadedImage image = new UploadedImage();
						image.setData(pro.getImage());
						image.setLength(pro.getImage().length);
						image.setName(StringUtil.n2b(pro.getImageName()));
						model.setImage(image);
					}
					productModelList.add(model);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void cancleOrder(){
		productSellModelList = new ArrayList<ProductModel>();
		totalPrice = 0;
		totalPv = 0;
	}

	public void saveOrder(){
		try{
			if(StringUtil.n2b(currentUser.getCurrentAccount().getCustomerId().geteMoney()).doubleValue() >= totalPrice){
				boolean chkAddressNull = false;
				if(sendStatus==2){
					if(chkNationality 
							&& provinceId!=-1 
							&& amphurId!=-1
							&& districtId!=-1
							&& addressPostCode!=null
							&& addressPostCode.length()>0){
						chkAddressNull = true;
					}
					if(!chkNationality
							&& provinceStr!=null
							&& provinceStr.length()>0
							&& amphurStr!=null
							&& amphurStr.length()>0
							&& districtStr!=null
							&& districtStr.length()>0
							&& addressPostCode!=null
							&& addressPostCode.length()>0){
						chkAddressNull = true;
					}
				}else{
					chkAddressNull = true;
				}
				if(chkAddressNull){
					if(memSearch!=null && productSellModelList!=null && productSellModelList.size()>0){
						TransactionSellHeader trxSellHeader = new TransactionSellHeader();
						trxSellHeader.setTrxHeaderDatetime(new Date());
						trxSellHeader.setReceiptNo(transactionReceiptControl.generateReceiptNo());
						trxSellHeader.setCustomerId(memSearch.getCustomerId());
						trxSellHeader.setTotalPrice(new BigDecimal(totalPrice));
						trxSellHeader.setTotalPv(new BigDecimal(totalPv));
						trxSellHeader.setTotalBv(new BigDecimal(0));
						trxSellHeader.setRemark(null);
						trxSellHeader.setTrxHeaderStatus(new Integer(1));
						trxSellHeader.setTrxHeaderFlag(new Integer(0));
						trxSellHeader.setSendStatus(sendStatus);
						trxSellHeader.setBuyStatus(buyStatus);
						trxSellHeader.setNationId(nationId);
						trxSellHeader.setAddressNo(addressNo);
						trxSellHeader.setAddressBuilding(addressBuilding);
						trxSellHeader.setAddressVillage(addressVillage);
						trxSellHeader.setAddressLane(addressLane);
						trxSellHeader.setAddressRoad(addressRoad);
						trxSellHeader.setProvinceId(provinceId);
						trxSellHeader.setAmphurId(amphurId);
						trxSellHeader.setDistrictId(districtId);
						trxSellHeader.setPostCode(addressPostCode);
						trxSellHeader.setProvinceLao(provinceStr);
						trxSellHeader.setAmphurLao(amphurStr);
						trxSellHeader.setDistrictLao(districtStr);						
						trxSellHeader.setCreateBy(currentUser.getCurrentAccount().getUserId());
						trxSellHeader.setCreateDate(new Date());
						trxSellHeader.setUpdateBy(currentUser.getCurrentAccount().getUserId());
						trxSellHeader.setUpdateDate(new Date());
						em.persist(trxSellHeader);
						for(ProductModel proModel:productSellModelList){
							TransactionSellDetail trxDetail = new TransactionSellDetail();
							trxDetail.setTrxHeaderId(trxSellHeader.getTrxHeaderId().intValue());
							StockProduct stockProduct = em.find(StockProduct.class, new Integer(proModel.getProductId()));
							stockProduct.setProductTotal(StringUtil.n2b(stockProduct.getProductTotal())-proModel.getQty());
							stockProduct.setUpdateBy(currentUser.getCurrentAccount().getUserId());
							stockProduct.setUpdateDate(new Date());
							em.merge(stockProduct);
							trxDetail.setProductId(proModel.getProductId());
							trxDetail.setCatalogId(proModel.getCatalogId());
							trxDetail.setPrice(new BigDecimal(proModel.getPrice()));
							trxDetail.setPv(new BigDecimal(proModel.getPv()));
							trxDetail.setBv(new BigDecimal(proModel.getBv()));
							trxDetail.setQty(new Integer(proModel.getQty()));
							trxDetail.setQtyAssign(new Integer(proModel.getQty()));
							trxDetail.setTotalPrice(new BigDecimal(proModel.getTotalPrice()));
							trxDetail.setTotalPv(new BigDecimal(proModel.getTotalPv()));
							trxDetail.setTotalBv(new BigDecimal(proModel.getTotalBv()));
							trxDetail.setRemark(null);
							trxDetail.setTrxDetailStatus(new Character('0'));
							trxDetail.setTrxDetailFlag(new Character('0'));
							trxDetail.setCreateBy(currentUser.getCurrentAccount().getUserId());
							trxDetail.setCreateDate(new Date());
							trxDetail.setUpdateBy(currentUser.getCurrentAccount().getUserId());
							trxDetail.setUpdateDate(new Date());
							em.persist(trxDetail);
						}

						TransactionMoney trxMoney = new TransactionMoney();
						trxMoney.setTrxMoneyDatetime(new Date());
						trxMoney.setCustomerId(currentUser.getCurrentAccount().getCustomerId().getCustomerId());
						trxMoney.setAmount(new BigDecimal(StringUtil.n2b(totalPrice)));
						TransactionMoneyStatus trxStatus = em.find(TransactionMoneyStatus.class, new Integer(3));
						trxMoney.setTrxMoneyStatus(trxStatus.getStatusId());// buy (-)
						trxMoney.setTrxMoneyFlag(new Integer(0));
						trxMoney.setRemark(trxStatus.getStatusDesc());
						trxMoney.setCreateBy(currentUser.getCurrentAccount().getUserId());
						trxMoney.setCreateDate(new Date());
						trxMoney.setUpdateBy(currentUser.getCurrentAccount().getUserId());
						trxMoney.setUpdateDate(new Date());
						double moneyTransferOld = StringUtil.n2b(currentUser.getCurrentAccount().getCustomerId().geteMoney()).doubleValue();
						currentUser.getCurrentAccount().getCustomerId().seteMoney(new BigDecimal(moneyTransferOld-StringUtil.n2b(totalPrice)));
						currentUser.getCurrentAccount().getCustomerId().setUpdateBy(currentUser.getCurrentAccount().getUserId());
						currentUser.getCurrentAccount().getCustomerId().setUpdateDate(new Date());
						trxMoney.setBalance(currentUser.getCurrentAccount().getCustomerId().geteMoney());
						em.persist(trxMoney);
						em.merge(currentUser.getCurrentAccount().getCustomerId());

						productSellModelList = new ArrayList<ProductModel>();
						totalPrice = 0;
						totalPv = 0;
						messages.info(new AppBundleKey("info.label.sell.completeSell",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
					}else{
						// Message page
						if(memSearch==null){
							messages.error(new AppBundleKey("error.label.sell.notFoundMember",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
						}else{
							messages.error(new AppBundleKey("error.label.sell.sellModelNull",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
						}
					}		
				}else{
					messages.error(new AppBundleKey("error.label.sell.inputAddress",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
				}				
			}else{
				messages.error(new AppBundleKey("error.label.sell.notEnoughMoney",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
			}
		}catch(Exception e){
			e.printStackTrace();
			messages.error(new AppBundleKey("error.label.sell.fail",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
		}
	}

	public long getTimeStamp(){  
		return System.currentTimeMillis();  
	}  

	public List<ProductModel> getProductModelList() {
		return productModelList;
	}

	public void setProductModelList(List<ProductModel> productModelList) {
		this.productModelList = productModelList;
	}

	public List<ProductModel> getProductSellModelList() {
		return productSellModelList;
	}

	public void setProductSellModelList(List<ProductModel> productSellModelList) {
		this.productSellModelList = productSellModelList;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public double getTotalPv() {
		return totalPv;
	}

	public void setTotalPv(double totalPv) {
		this.totalPv = totalPv;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
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

	public List<DropDownModel> getCatalogModelList() {
		return catalogModelList;
	}

	public void setCatalogModelList(List<DropDownModel> catalogModelList) {
		this.catalogModelList = catalogModelList;
	}

	public int getSelectedCatalog() {
		return selectedCatalog;
	}

	public void setSelectedCatalog(int selectedCatalog) {
		this.selectedCatalog = selectedCatalog;
	}

	public boolean isChkUseAddress() {
		return chkUseAddress;
	}

	public void setChkUseAddress(boolean chkUseAddress) {
		this.chkUseAddress = chkUseAddress;
	}

	public boolean isChkNationality() {
		return chkNationality;
	}

	public void setChkNationality(boolean chkNationality) {
		this.chkNationality = chkNationality;
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

	public int getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(int provinceId) {
		this.provinceId = provinceId;
	}

	public int getAmphurId() {
		return amphurId;
	}

	public void setAmphurId(int amphurId) {
		this.amphurId = amphurId;
	}

	public int getDistrictId() {
		return districtId;
	}

	public void setDistrictId(int districtId) {
		this.districtId = districtId;
	}

	public String getAddressPostCode() {
		return addressPostCode;
	}

	public void setAddressPostCode(String addressPostCode) {
		this.addressPostCode = addressPostCode;
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

	public List<DropDownModel> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<DropDownModel> provinceList) {
		this.provinceList = provinceList;
	}

	public List<DropDownModel> getAmphurList() {
		return amphurList;
	}

	public void setAmphurList(List<DropDownModel> amphurList) {
		this.amphurList = amphurList;
	}

	public List<DropDownModel> getDistrictList() {
		return districtList;
	}

	public void setDistrictList(List<DropDownModel> districtList) {
		this.districtList = districtList;
	}

	public int getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public int getBuyStatus() {
		return buyStatus;
	}

	public void setBuyStatus(int buyStatus) {
		this.buyStatus = buyStatus;
	}

}
