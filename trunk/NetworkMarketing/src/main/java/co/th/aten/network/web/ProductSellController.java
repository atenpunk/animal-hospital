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
	private double totalPrice;
	private double totalPv;

	@PostConstruct
	public void init(){
		log.info("init method ProductSellController");
		long startTime = System.currentTimeMillis();
		try{
			productSellModelList = new ArrayList<ProductModel>();
			productModelList = new ArrayList<ProductModel>();
			totalPrice = 0;
			totalPv = 0;
			List<StockProduct> productList = em.createQuery("From StockProduct",StockProduct.class).getResultList();
			if(productList!=null && productList.size()>0){
				int index = 0;
				for(StockProduct pro:productList){
					ProductModel model = new ProductModel();
					model.setIndex(++index);
					model.setProductId(StringUtil.n2b(pro.getStockProductPK().getProductId()));
					model.setCatalogId(StringUtil.n2b(pro.getStockProductPK().getCatalogId()));
					model.setProductCode(StringUtil.n2b(pro.getProductCode()));
					model.setProductThDesc(StringUtil.n2b(pro.getThDesc()));
					model.setProductEnDesc(StringUtil.n2b(pro.getEnDesc()));
					model.setPrice(StringUtil.n2b(pro.getPrice()).doubleValue());
					model.setPv(StringUtil.n2b(pro.getPv()).doubleValue());
					model.setBv(StringUtil.n2b(pro.getBv()).doubleValue());
					model.setPathImage(pro.getImageName()!=null?"/resources/product_img/"+pro.getImageName():null);
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
				if(productSellModelList!=null){
					boolean chkDup = false;
					for(ProductModel pro:productSellModelList){
						if(pro.getProductId()==model.getProductId()
								&& pro.getCatalogId()==model.getCatalogId()){
							model.setQty(pro.getQty());
							model.setTotalPrice(pro.getTotalPrice());
							model.setTotalPv(pro.getTotalPv());
							chkDup = true;
							break;
						}
					}
					model.setQty(model.getQty()+1);
					model.setTotalPrice(model.getTotalPrice()+model.getPrice());
					model.setTotalPv(model.getTotalPv()+model.getPv());
					totalPrice += model.getPrice();
					totalPv += model.getPv();
					if(!chkDup){
						model.setIndex(productSellModelList.size()+1);
						productSellModelList.add(model);
					}
				}
			}
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

}
