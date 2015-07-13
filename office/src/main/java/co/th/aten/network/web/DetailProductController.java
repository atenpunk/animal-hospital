package co.th.aten.network.web;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.StockProductControl;
import co.th.aten.network.entity.StockCatalog;
import co.th.aten.network.entity.StockProduct;
import co.th.aten.network.entity.StockProductPK;
import co.th.aten.network.model.DropDownModel;
import co.th.aten.network.model.ProductModel;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.StringUtil;

@SessionScoped
@Named
public class DetailProductController implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5443351151396868724L;

	@Inject
	private Logger log;
	@Inject
	@DBDefault
	private EntityManager em;
	@Inject
	private CurrentUserManager currentUser;
	@Inject
	private StockProductControl stockProductControl;
	@Inject
	private MessageFactory factory;
	@Inject
	private Messages messages;

	private List<ProductModel> productModelList;
	private ProductModel selectedProductModel;
	private List<DropDownModel> catalogModelList;
	private int selectedCatalog;

	@PostConstruct
	public void init(){
		log.info("init method DetailProductController");
		if(productModelList==null){
			genDataModel();
		}
		if(catalogModelList==null){
			catalogModelList = new ArrayList<DropDownModel>();
			List<StockCatalog> stockCatalogList = em.createQuery("From StockCatalog",StockCatalog.class).getResultList();
			if(stockCatalogList!=null && stockCatalogList.size()>0){
				for(StockCatalog data:stockCatalogList){
					DropDownModel model = new DropDownModel();
					model.setIntKey(data.getCatalogId());
					model.setThLabel(StringUtil.n2b(data.getThDesc()));
					model.setEnLabel(StringUtil.n2b(data.getEnDesc()));
					catalogModelList.add(model);
				}
				selectedCatalog = catalogModelList.get(0).getIntKey();
			}
		}
	}
	
	private void genDataModel(){
		productModelList = new ArrayList<ProductModel>();
		List<StockProduct> productList = em.createQuery("From StockProduct " +
				" Order by stockProductPK.productId",StockProduct.class)
				.getResultList();
		if(productList!=null){
			for(StockProduct pro:productList){
				ProductModel model = new ProductModel();
				model.setProductId(StringUtil.n2b(pro.getStockProductPK().getProductId()));
				model.setCatalogId(StringUtil.n2b(pro.getStockProductPK().getCatalogId()));
				model.setProductCode(StringUtil.n2b(pro.getProductCode()));
				model.setProductThDesc(StringUtil.n2b(pro.getThDesc()));
				model.setProductEnDesc(StringUtil.n2b(pro.getEnDesc()));
				model.setUnit(StringUtil.n2b(pro.getUnit()));
				model.setPrice(StringUtil.n2b(pro.getPrice()).doubleValue());
				model.setPv(StringUtil.n2b(pro.getPv()).doubleValue());
				model.setPathImage(pro.getImageName()!=null?"/resources/product_img/"+pro.getImageName():null);
				productModelList.add(model);
			}
		}
	}
	
	private void clear(){
		try{
			selectedProductModel = new ProductModel();
			selectedProductModel.setProductId(0);
			selectedProductModel.setCatalogId(0);
			selectedProductModel.setProductCode("");
			selectedProductModel.setProductThDesc("");
			selectedProductModel.setProductEnDesc("");
			selectedProductModel.setUnit("");
			selectedProductModel.setPrice(0);
			selectedProductModel.setPv(0);
			selectedProductModel.setPathImage("");
			if(catalogModelList!=null && catalogModelList.size()>0)
				selectedCatalog = catalogModelList.get(0).getIntKey();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void addProduct(){
		log.info("--------------- addProduct() -------------");
		try{
			clear();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void confirmAddProduct(){
		log.info("--------------- confirmAddProduct() -------------");
		try{
			StockProductPK pk = new StockProductPK();
			int max = stockProductControl.productIdInsert();
			pk.setProductId(max);
			pk.setCatalogId(selectedCatalog);
			StockProduct stackProduct = new StockProduct();
			stackProduct.setStockProductPK(pk);
			stackProduct.setProductCode(selectedProductModel.getProductCode());
			stackProduct.setThDesc(selectedProductModel.getProductThDesc());
			stackProduct.setEnDesc(selectedProductModel.getProductThDesc());
			stackProduct.setUnit(selectedProductModel.getUnit());
			stackProduct.setPrice(new BigDecimal(selectedProductModel.getPrice()));
			stackProduct.setPv(new BigDecimal(selectedProductModel.getPv()));
			stackProduct.setBv(new BigDecimal(selectedProductModel.getBv()));
			stackProduct.setQty(0);
			stackProduct.setCompanyId(0);
			stackProduct.setPackageId(0);
			stackProduct.setImageName(null);
			stackProduct.setCreateBy(currentUser.getCurrentAccount().getUserId());
			stackProduct.setCreateDate(new Date());
			stackProduct.setUpdateBy(currentUser.getCurrentAccount().getUserId());
			stackProduct.setUpdateDate(new Date());
			em.persist(stackProduct);
			genDataModel();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void cancleAddProduct(){
		log.info("--------------- cancleAddProduct() -------------");
		try{
			clear();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void editProduct(ProductModel product){
		log.info("--------------- editProduct() -------------");
		try{
			clear();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void deleteProduct(ProductModel product){
		log.info("--------------- deleteProduct() -------------");
		try{
			StockProductPK pk = new StockProductPK();
			pk.setProductId(product.getProductId());
			pk.setCatalogId(product.getCatalogId());
			StockProduct stockProduct = em.find(StockProduct.class, pk);
			em.remove(stockProduct);
			productModelList.remove(product);
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public List<ProductModel> getProductModelList() {
		return productModelList;
	}

	public void setProductModelList(List<ProductModel> productModelList) {
		this.productModelList = productModelList;
	}

	public ProductModel getSelectedProductModel() {
		return selectedProductModel;
	}

	public void setSelectedProductModel(ProductModel selectedProductModel) {
		this.selectedProductModel = selectedProductModel;
	}

}
