package co.th.aten.network.web;

import java.io.Serializable;
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

import co.th.aten.network.control.StockCategoryControl;
import co.th.aten.network.entity.StockCatalog;
import co.th.aten.network.model.CategoryModel;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.StringUtil;

@SessionScoped
@Named
public class CategoryProductController implements Serializable{

	private static final long serialVersionUID = 5443351151396868724L;

	@Inject
	private Logger log;
	@Inject
	@DBDefault
	private EntityManager em;
	@Inject
	private CurrentUserManager currentUser;
	@Inject
	private StockCategoryControl stockCategoryControl;
	@Inject
	private MessageFactory factory;
	@Inject
	private Messages messages;

	private List<CategoryModel> catalogModelList;
	private CategoryModel selectedCatalogModel;
	private boolean chkAddActive;
	private String star01;
	private String star02;
	private String star03;

	@PostConstruct
	public void init(){
		log.info("init method CategoryProductController");
		genDataModel();
	}

	public void onKeyPress(){
		if(selectedCatalogModel!=null){
			if(selectedCatalogModel.getCode()!=null 
					&& selectedCatalogModel.getCode().trim().length()>0){
				star01 = "";
			}else{
				star01 = "*";
			}
			if(selectedCatalogModel.getThDesc()!=null 
					&& selectedCatalogModel.getThDesc().trim().length()>0){
				star02 = "";
			}else{
				star02 = "*";
			}
			if(selectedCatalogModel.getEnDesc()!=null 
					&& selectedCatalogModel.getEnDesc().trim().length()>0){
				star03 = "";
			}else{
				star03 = "*";
			}
			if(star01.equals("*") 
					|| star02.equals("*")
					|| star03.equals("*")){
				chkAddActive = true;
			}else{
				chkAddActive = false;
			}
		}
	}
	
	private void genDataModel(){
		try{
			catalogModelList = new ArrayList<CategoryModel>();
			List<StockCatalog> stockList = em.createQuery(" From StockCatalog ",StockCatalog.class).getResultList();
			if(stockList!=null && stockList.size()>0){
				for(StockCatalog stock:stockList){
					CategoryModel model = new CategoryModel();
					model.setCatalogId(StringUtil.n2b(stock.getCatalogId()));
					model.setCode(stock.getCatalogCode());
					model.setThDesc(stock.getThDesc());
					model.setEnDesc(stock.getEnDesc());
					catalogModelList.add(model);
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void add(){
		try{
			clear();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void confirmAdd(){
		try{
			int max = stockCategoryControl.catalogIdInsert();
			StockCatalog stockCatalog = new StockCatalog();
			stockCatalog.setCatalogId(max);
			stockCatalog.setCatalogCode(selectedCatalogModel.getCode());
			stockCatalog.setThDesc(selectedCatalogModel.getThDesc());
			stockCatalog.setEnDesc(selectedCatalogModel.getEnDesc());
			stockCatalog.setCreateBy(currentUser.getCurrentAccount().getUserId());
			stockCatalog.setCreateDate(new Date());
			stockCatalog.setUpdateBy(currentUser.getCurrentAccount().getUserId());
			stockCatalog.setUpdateDate(new Date());
			em.persist(stockCatalog);
			genDataModel();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void cancleAdd(){
		try{
			clear();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void edit(CategoryModel model){
		try{
			clear();
			selectedCatalogModel = model;
			onKeyPress();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void confirmEdit(){
		try{
			StockCatalog stockCatalog = em.find(StockCatalog.class, new Integer(selectedCatalogModel.getCatalogId()));
			stockCatalog.setCatalogCode(selectedCatalogModel.getCode());
			stockCatalog.setThDesc(selectedCatalogModel.getThDesc());
			stockCatalog.setEnDesc(selectedCatalogModel.getEnDesc());
			stockCatalog.setUpdateBy(currentUser.getCurrentAccount().getUserId());
			stockCatalog.setUpdateDate(new Date());
			em.merge(stockCatalog);
			genDataModel();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void cancleEdit(){
		try{
			clear();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void delete(CategoryModel model){
		try{
			StockCatalog stockCatalog = em.find(StockCatalog.class, new Integer(model.getCatalogId()));
			em.remove(stockCatalog);
			catalogModelList.remove(model);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private void clear(){
		try{
			star01 = "*";
			star02 = "*";
			star03 = "*";
			chkAddActive = true;
			selectedCatalogModel = new CategoryModel();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public List<CategoryModel> getCatalogModelList() {
		return catalogModelList;
	}

	public void setCatalogModelList(List<CategoryModel> catalogModelList) {
		this.catalogModelList = catalogModelList;
	}

	public CategoryModel getSelectedCatalogModel() {
		return selectedCatalogModel;
	}

	public void setSelectedCatalogModel(CategoryModel selectedCatalogModel) {
		this.selectedCatalogModel = selectedCatalogModel;
	}

	public boolean isChkAddActive() {
		return chkAddActive;
	}

	public void setChkAddActive(boolean chkAddActive) {
		this.chkAddActive = chkAddActive;
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
}
