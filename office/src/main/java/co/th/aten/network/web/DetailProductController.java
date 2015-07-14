package co.th.aten.network.web;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.Messages;
import org.jboss.solder.logging.Logger;
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import co.th.aten.network.control.StockProductControl;
import co.th.aten.network.entity.StockCatalog;
import co.th.aten.network.entity.StockProduct;
import co.th.aten.network.model.DropDownModel;
import co.th.aten.network.model.ProductModel;
import co.th.aten.network.model.UploadedImage;
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
	private boolean chkAddActive;
	private UploadedImage uploadedImage;
	private String star00;
	private String star01;
	private String star02;
	private String star03;
	private String star04;
	private String star05;

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
				DropDownModel model = new DropDownModel();
				model.setIntKey(-1);
				model.setThLabel("");
				model.setEnLabel("");
				catalogModelList.add(0,model);
				selectedCatalog = -1;
			}
		}
	}

	public void paint(OutputStream stream, Object object){
		try{
			stream.write(resize(uploadedImage.getData(),150,150));
			stream.close();
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

	private byte[] resize(byte[] fileData, int width, int height) throws IOException{
		ByteArrayInputStream in = new ByteArrayInputStream(fileData);
		BufferedImage img = ImageIO.read(in);
		if(height == 0) {
			height = (width * img.getHeight())/ img.getWidth(); 
		}
		if(width == 0) {
			width = (height * img.getWidth())/ img.getHeight();
		}
		Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0,0,0), null);
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		ImageIO.write(imageBuff, "jpg", buffer);
		return buffer.toByteArray();
	}

	public void listener(FileUploadEvent event){
		try{
			UploadedFile uploadedFile = event.getUploadedFile();
			log.info("uploadedFile.getData().length : "+uploadedFile.getData().length);
			log.info("uploadedFile.getName() : "+uploadedFile.getName());
			log.info("uploadedFile.getData() : "+uploadedFile.getData());
			uploadedImage = new UploadedImage();
			uploadedImage.setLength(uploadedFile.getData().length);
			uploadedImage.setName(uploadedFile.getName());
			uploadedImage.setData(uploadedFile.getData());

			//			FacesContext facesContext = FacesContext.getCurrentInstance();
			//			ServletContext servletContext = (ServletContext) facesContext
			//					.getExternalContext()
			//					.getContext();
			//			log.info("servletContext.getRealPath : "+servletContext.getRealPath("/"));
			//			FileOutputStream fileOuputStream = 
			//					new FileOutputStream(servletContext.getRealPath("/")+"/resources/image/"+uploadedFile.getName()); 
			//			fileOuputStream.write(uploadedFile.getData());
			//			fileOuputStream.close();
			//			if(selectedProductModel.getPathImage()!=null){
			//				try{
			//					new File(servletContext.getRealPath("/")+selectedProductModel.getPathImage()).delete();
			//				}catch(Exception ex){
			//					ex.printStackTrace();
			//				}
			//			}
			//			selectedProductModel.setPathImage("/resources/image/"+uploadedFile.getName());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void onChangeCatalog(){
		if(selectedCatalog!=-1){
			star00 = "";
		}else{
			star00 = "*";
		}
		if(star00.equals("*")
				||star01.equals("*") 
				|| star02.equals("*")
				|| star03.equals("*")
				|| star04.equals("*")
				|| star05.equals("*")){
			chkAddActive = true;
		}else{
			chkAddActive = false;
		}
	}

	public void onKeyPress(){
		if(selectedProductModel!=null){
			if(selectedProductModel.getProductCode()!=null 
					&& selectedProductModel.getProductCode().trim().length()>0){
				star01 = "";
			}else{
				star01 = "*";
			}
			if(selectedProductModel.getProductThDesc()!=null 
					&& selectedProductModel.getProductThDesc().trim().length()>0){
				star02 = "";
			}else{
				star02 = "*";
			}
			if(selectedProductModel.getUnit()!=null 
					&& selectedProductModel.getUnit().trim().length()>0){
				star03 = "";
			}else{
				star03 = "*";
			}
			if(selectedProductModel.getPrice()>0){
				star04 = "";
			}else{
				star04 = "*";
			}
			if(selectedProductModel.getPv()>0){
				star05 = "";
			}else{
				star05 = "*";
			}
			if(star00.equals("*")
					||star01.equals("*") 
					|| star02.equals("*")
					|| star03.equals("*")
					|| star04.equals("*")
					|| star05.equals("*")){
				chkAddActive = true;
			}else{
				chkAddActive = false;
			}
		}
	}

	private void genDataModel(){
		productModelList = new ArrayList<ProductModel>();
		List<StockProduct> productList = em.createQuery("From StockProduct " +
				" Order by productId",StockProduct.class)
				.getResultList();
		if(productList!=null){
			for(StockProduct pro:productList){
				ProductModel model = new ProductModel();
				model.setProductId(StringUtil.n2b(pro.getProductId()));
				model.setCatalogId(pro.getCatalogId()!=null?pro.getCatalogId().getCatalogId():0);
				model.setProductCode(StringUtil.n2b(pro.getProductCode()));
				model.setProductThDesc(StringUtil.n2b(pro.getThDesc()));
				model.setProductEnDesc(StringUtil.n2b(pro.getEnDesc()));
				model.setUnit(StringUtil.n2b(pro.getUnit()));
				model.setPrice(StringUtil.n2b(pro.getPrice()).doubleValue());
				model.setPv(StringUtil.n2b(pro.getPv()).doubleValue());
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
	}

	private void clear(){
		try{
			star00 = "*";
			star01 = "*";
			star02 = "*";
			star03 = "*";
			star04 = "*";
			star05 = "*";
			chkAddActive = true;
			uploadedImage = null;
			selectedProductModel = new ProductModel();
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
			int max = stockProductControl.productIdInsert();
			StockProduct stackProduct = new StockProduct();
			stackProduct.setProductId(max);
			if(selectedCatalog!=-1)
				stackProduct.setCatalogId(em.find(StockCatalog.class, new Integer(selectedCatalog)));
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
			if(uploadedImage!=null){
				stackProduct.setImageName(uploadedImage.getName());
				stackProduct.setImage(resize(uploadedImage.getData(),200,200));
			}
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
			StockProduct stockProduct = em.find(StockProduct.class, new Integer(product.getProductId()));
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

	public boolean isChkAddActive() {
		return chkAddActive;
	}

	public void setChkAddActive(boolean chkAddActive) {
		this.chkAddActive = chkAddActive;
	}

	public String getStar00() {
		return star00;
	}

	public void setStar00(String star00) {
		this.star00 = star00;
	}

	public String getStar01() {
		return star01;
	}

	public void setStar01(String star01) {
		this.star01 = star01;
	}

	public String getStar02() {
		return star02;
	}

	public void setStar02(String star02) {
		this.star02 = star02;
	}

	public String getStar03() {
		return star03;
	}

	public void setStar03(String star03) {
		this.star03 = star03;
	}

	public String getStar04() {
		return star04;
	}

	public void setStar04(String star04) {
		this.star04 = star04;
	}

	public String getStar05() {
		return star05;
	}

	public void setStar05(String star05) {
		this.star05 = star05;
	}

	public UploadedImage getUploadedImage() {
		return uploadedImage;
	}

	public void setUploadedImage(UploadedImage uploadedImage) {
		this.uploadedImage = uploadedImage;
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
