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

import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.StockCatalog;
import co.th.aten.network.entity.StockProduct;
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
	private MessageFactory factory;

	@Inject
	private Messages messages;

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
			if(currentUser.getCurrentAccount().getCustomerId()!=null){
				memSearch = currentUser.getCurrentAccount().getCustomerId();
				memberId = memSearch.getCustomerMember();
				memberName = StringUtil.n2b(memSearch.getTitleName())+StringUtil.n2b(memSearch.getFirstName())+" "+StringUtil.n2b(memSearch.getLastName());
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
		}catch(Exception e){
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		log.info("init method ProductSellController Time = "+((endTime-startTime)/1000d)+"s");
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
				memberName = StringUtil.n2b(memSearch.getTitleName())+StringUtil.n2b(memSearch.getFirstName())+" "+StringUtil.n2b(memSearch.getLastName());
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
	}

	public void saveOrder(){
		try{
			if(memSearch!=null && productSellModelList!=null && productSellModelList.size()>0){
				TransactionSellHeader trxSellHeader = new TransactionSellHeader();
				//				trxSellHeader.setTrxHeaderId(max);
				trxSellHeader.setTrxHeaderDatetime(new Date());
				trxSellHeader.setCustomerId(memSearch.getCustomerId());
				trxSellHeader.setTotalPrice(new BigDecimal(totalPrice));
				trxSellHeader.setTotalPv(new BigDecimal(totalPv));
				trxSellHeader.setTotalBv(new BigDecimal(0));
				trxSellHeader.setRemark(null);
				trxSellHeader.setTrxHeaderStatus(new Character('0'));
				trxSellHeader.setTrxHeaderFlag(new Character('0'));
				trxSellHeader.setCreateBy(currentUser.getCurrentAccount().getUserId());
				trxSellHeader.setCreateDate(new Date());
				trxSellHeader.setUpdateBy(currentUser.getCurrentAccount().getUserId());
				trxSellHeader.setUpdateDate(new Date());
				em.persist(trxSellHeader);
//				DecimalFormat df = new DecimalFormat("000000000");
//				trxSellHeader.setReceiptNo("R"+df.format(trxSellHeader.getTrxHeaderId().intValue()));
//				em.merge(trxSellHeader);

				for(ProductModel proModel:productSellModelList){
					TransactionSellDetail trxDetail = new TransactionSellDetail();
					//					trxDetailPk.setTrxDetailId(trxDetailId);
					trxDetail.setTrxHeaderId(trxSellHeader.getTrxHeaderId().intValue());
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
				productSellModelList = new ArrayList<ProductModel>();
				messages.info(new AppBundleKey("info.label.sell.completeSell",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
			}else{
				// Message page
				if(memSearch==null){
					messages.error(new AppBundleKey("error.label.sell.notFoundMember",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
				}else{
					messages.error(new AppBundleKey("error.label.sell.sellModelNull",FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage()));
				}
			}			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	//	public void onKeypressQty(ProductModel model){
	//		log.info("##### onKeypressQty #####");
	//		try{
	//			if(model!=null){
	//				totalPrice = 0;
	//				totalPv = 0;
	//				log.info("model.getProductId() : "+model.getProductId());
	//				log.info("model.getCatalogId() : "+model.getCatalogId());
	//				if(productSellModelList!=null){
	//					for(ProductModel pro:productSellModelList){
	//						if(pro.getProductId()==model.getProductId()
	//								&& pro.getCatalogId()==model.getCatalogId()){
	//							model.setTotalPrice(model.getQty()*model.getPrice());
	//							model.setTotalPv(model.getQty()*model.getPv());
	//						}
	//						totalPrice += model.getTotalPrice();
	//						totalPv += model.getTotalPv();
	//					}
	//					productSellModelList.set(model.getIndex()-1,model);
	//				}
	//			}
	//		}catch(Exception e){
	//			e.printStackTrace();
	//		}
	//	}


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
}
