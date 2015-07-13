package co.th.aten.network.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.StockProduct;
import co.th.aten.network.model.ProductModel;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.StringUtil;

@ViewScoped
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
	private MessageFactory factory;
	@Inject
	private Messages messages;
	
	private List<ProductModel> productModelList;

	@PostConstruct
	public void init(){
		log.info("init method DetailProductController");
		if(productModelList==null){
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
	}
	
	public void addProduct(){
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void editProduct(ProductModel product){
		try{
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void deleteProduct(ProductModel product){
		try{
			
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
	
}
