package co.th.aten.network.web;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.faces.bean.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.swing.ImageIcon;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.StockProduct;
import co.th.aten.network.model.ProductModel;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.StringUtil;

@Stateless
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

	@PostConstruct
	public void init(){
		log.info("init method ProductSellController");
		long startTime = System.currentTimeMillis();
		productModelList = new ArrayList<ProductModel>();
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
		long endTime = System.currentTimeMillis();
		log.info("init method ProductSellController Time = "+((endTime-startTime)/1000d)+"s");
	}
	
	public List<ProductModel> getProductModelList() {
		return productModelList;
	}

	public void setProductModelList(List<ProductModel> productModelList) {
		this.productModelList = productModelList;
	}
	
	
}
