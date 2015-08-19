package co.th.aten.network.web;

import java.io.OutputStream;
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
import org.richfaces.event.FileUploadEvent;
import org.richfaces.model.UploadedFile;

import co.th.aten.network.control.StockProductControl;
import co.th.aten.network.entity.StockCatalog;
import co.th.aten.network.entity.StockProduct;
import co.th.aten.network.entity.StockProductType;
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
	private List<DropDownModel> productTypeModelList;
	private int selectedProductType;
	private boolean chkAddActive;
	private UploadedImage uploadedImage;
	private String starTypeId;
	private String star00;
	private String star01;
	private String star02;
	private String star03;
	private int uploadImgCount;

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
		if(productTypeModelList==null){
			productTypeModelList = new ArrayList<DropDownModel>();
			List<StockProductType> stockProductTypeList = em.createQuery("From StockProductType",StockProductType.class).getResultList();
			if(stockProductTypeList!=null && stockProductTypeList.size()>0){
				for(StockProductType data:stockProductTypeList){
					DropDownModel model = new DropDownModel();
					model.setIntKey(data.getTypeId());
					model.setThLabel(StringUtil.n2b(data.getTypeDescTh()));
					model.setEnLabel(StringUtil.n2b(data.getTypeDescEn()));
					productTypeModelList.add(model);
				}
				DropDownModel model = new DropDownModel();
				model.setIntKey(-1);
				model.setThLabel("");
				model.setEnLabel("");
				productTypeModelList.add(0,model);
				selectedProductType = -1;
			}
		}
	}

	public void paint(OutputStream stream, Object object){
		try{
			if(uploadedImage!=null){
				stream.write(uploadedImage.getData());
				stream.close();
				uploadImgCount++;
			}
		}catch(Exception e){
			log.info("Error paint : "+e.getMessage());
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

	//	private byte[] resizeImage(byte[] originalImage, int width, int height) throws IOException {
	//        ByteArrayInputStream in = new ByteArrayInputStream(originalImage);
	//		BufferedImage img = ImageIO.read(in);
	//        BufferedImage resizedImage = new BufferedImage(width, height, 1);
	//        Graphics2D g = resizedImage.createGraphics();
	//        g.drawImage(img, 0, 0, width, height, null);
	//        g.dispose();
	//        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	//		ImageIO.write(resizedImage, "jpg", buffer);
	//        return buffer.toByteArray();
	//    }

	//	private byte[] resize(byte[] fileData, int width, int height) throws IOException{
	//		ByteArrayInputStream in = new ByteArrayInputStream(fileData);
	//		BufferedImage img = ImageIO.read(in);
	//		if(height == 0) {
	//			height = (width * img.getHeight())/ img.getWidth(); 
	//		}
	//		if(width == 0) {
	//			width = (height * img.getWidth())/ img.getHeight();
	//		}
	//		Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
	//		BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	//		imageBuff.getGraphics().drawImage(scaledImage, 0, 0, null, null);
	//		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	//		ImageIO.write(imageBuff, "jpg", buffer);
	//		return buffer.toByteArray();
	//	}

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
			if(selectedCatalog!=-1){
				star00 = "";
			}else{
				star00 = "*";
			}
			if(selectedProductType!=-1){
				starTypeId = "";
			}else{
				starTypeId = "*";
			}
			if(star00.equals("*")
					||star01.equals("*") 
					|| star02.equals("*")
					|| star03.equals("*")
					|| starTypeId.equals("*")){
				chkAddActive = true;
			}else{
				chkAddActive = false;
			}
		}
	}

	private void genDataModel(){
		productModelList = new ArrayList<ProductModel>();
		List<StockProduct> productList = em.createQuery("From StockProduct " +
				" Where productStatus = 1 " +
				" Order by productId",StockProduct.class)
				.getResultList();
		if(productList!=null){
			for(StockProduct pro:productList){
				ProductModel model = new ProductModel();
				model.setProductId(StringUtil.n2b(pro.getProductId()));
				model.setCatalogId(pro.getCatalogId()!=null?pro.getCatalogId().getCatalogId():0);
				model.setCatalogThDesc(pro.getCatalogId()!=null?pro.getCatalogId().getThDesc():"");
				model.setProductCode(StringUtil.n2b(pro.getProductCode()));
				model.setProductThDesc(StringUtil.n2b(pro.getThDesc()));
				model.setProductEnDesc(StringUtil.n2b(pro.getEnDesc()));
				model.setProductTypeId(StringUtil.n2b(pro.getProductType()));
				model.setQty(StringUtil.n2b(pro.getQty()));
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
			starTypeId = "*";
			star00 = "*";
			star01 = "*";
			star02 = "*";
			star03 = "*";
			uploadImgCount = 0;
			chkAddActive = true;
			uploadedImage = null;
			selectedProductModel = new ProductModel();
			if(catalogModelList!=null && catalogModelList.size()>0)
				selectedCatalog = catalogModelList.get(0).getIntKey();
			if(productTypeModelList!=null && productTypeModelList.size()>0)
				selectedProductType = productTypeModelList.get(0).getIntKey();
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
			stackProduct.setQty(selectedProductModel.getQty());
			stackProduct.setCompanyId(0);
			stackProduct.setPackageId(0);
			stackProduct.setProductStatus(1);// 1 = Active, 2 = Not Active
			stackProduct.setProductType(selectedProductType);
			stackProduct.setCreateBy(currentUser.getCurrentAccount().getUserId());
			stackProduct.setCreateDate(new Date());
			stackProduct.setUpdateBy(currentUser.getCurrentAccount().getUserId());
			stackProduct.setUpdateDate(new Date());
			if(uploadedImage!=null){
				stackProduct.setImageName(uploadedImage.getName());
				stackProduct.setImage(uploadedImage.getData());
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
			selectedProductModel = product;
			selectedCatalog = product.getCatalogId();
			selectedProductType = product.getProductTypeId();
			uploadedImage = product.getImage();
			onKeyPress();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void confirmEditProduct(){
		log.info("--------------- confirmEditProduct() -------------");
		try{
			StockProduct stackProduct = em.find(StockProduct.class, new Integer(selectedProductModel.getProductId()));
			if(selectedCatalog!=-1){
				stackProduct.setCatalogId(em.find(StockCatalog.class, new Integer(selectedCatalog)));
			}else{
				stackProduct.setCatalogId(null);
			}
			stackProduct.setProductCode(selectedProductModel.getProductCode());
			stackProduct.setThDesc(selectedProductModel.getProductThDesc());
			stackProduct.setEnDesc(selectedProductModel.getProductThDesc());
			stackProduct.setUnit(selectedProductModel.getUnit());
			stackProduct.setProductType(selectedProductType);
			stackProduct.setPrice(new BigDecimal(selectedProductModel.getPrice()));
			stackProduct.setPv(new BigDecimal(selectedProductModel.getPv()));
			stackProduct.setBv(new BigDecimal(selectedProductModel.getBv()));
			stackProduct.setQty(selectedProductModel.getQty());
			stackProduct.setCompanyId(0);
			stackProduct.setPackageId(0);
			stackProduct.setUpdateBy(currentUser.getCurrentAccount().getUserId());
			stackProduct.setUpdateDate(new Date());
			if(uploadedImage!=null){
				stackProduct.setImageName(uploadedImage.getName());
				stackProduct.setImage(uploadedImage.getData());
			}
			em.merge(stackProduct);
			genDataModel();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void cancleEditProduct(){
		log.info("--------------- cancleEditProduct() -------------");
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
			stockProduct.setProductStatus(new Integer(2));// 1 = Active, 2 Not Active
			em.merge(stockProduct);
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

	public int getUploadImgCount() {
		return uploadImgCount;
	}

	public void setUploadImgCount(int uploadImgCount) {
		this.uploadImgCount = uploadImgCount;
	}

	public long getTimeStamp(){  
		return System.currentTimeMillis();  
	}

	public String getStarTypeId() {
		return starTypeId;
	}

	public void setStarTypeId(String starTypeId) {
		this.starTypeId = starTypeId;
	}

	public List<DropDownModel> getProductTypeModelList() {
		return productTypeModelList;
	}

	public void setProductTypeModelList(List<DropDownModel> productTypeModelList) {
		this.productTypeModelList = productTypeModelList;
	}

	public int getSelectedProductType() {
		return selectedProductType;
	}

	public void setSelectedProductType(int selectedProductType) {
		this.selectedProductType = selectedProductType;
	}

}
