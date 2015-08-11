package co.th.aten.network.web;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.control.TransactionHeaderControl;
import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.StockProduct;
import co.th.aten.network.entity.TransactionSellDetail;
import co.th.aten.network.entity.TransactionSellHeader;
import co.th.aten.network.entity.TransactionSellHeaderStatus;
import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.model.DropDownModel;
import co.th.aten.network.model.ProductModel;
import co.th.aten.network.model.TrxProductModel;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.report.AbstractReport;
import co.th.aten.network.report.ProductTrxReport;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.BathText;
import co.th.aten.network.util.StringUtil;

@SessionScoped
@Named
public class ProductTrxController implements Serializable {

	private static final long serialVersionUID = 1L;
	@Inject
	private Logger log;
	@Inject
	@DBDefault
	private EntityManager em;
	@Inject
	private CurrentUserManager currentUser;
	@Inject
	private FacesContext facesContext;
	@Inject
	private CustomerControl customerControl;
	@Inject
	private TransactionHeaderControl transactionHeaderControl;

	private List<TrxProductModel> trxProductModelList;
	private List<DropDownModel> trxStatusList;
	private List<DropDownModel> trxStatusForDataTable;
	private int selectedTrxStatus;
	private Date startDate;
	private Date endDate;
	private String receiptNo;

	@PostConstruct
	public void init(){
		log.info("init method ProductTrxController");
		Calendar calStart = Calendar.getInstance();
		calStart.add(Calendar.DATE, -30);
		calStart.set(Calendar.HOUR_OF_DAY, 0);
		calStart.set(Calendar.MINUTE, 0);
		calStart.set(Calendar.SECOND, 0);
		calStart.set(Calendar.MILLISECOND, 0);
		startDate = calStart.getTime();
		Calendar calEnd = Calendar.getInstance();
		calEnd.set(Calendar.HOUR_OF_DAY, 23);
		calEnd.set(Calendar.MINUTE, 59);
		calEnd.set(Calendar.SECOND, 59);
		calEnd.set(Calendar.MILLISECOND, 999);
		endDate = calEnd.getTime();
		if(trxStatusList==null || trxStatusForDataTable==null){
			trxStatusList = new ArrayList<DropDownModel>();
			trxStatusForDataTable = new ArrayList<DropDownModel>();
			List<TransactionSellHeaderStatus> statusList = em.createQuery("From TransactionSellHeaderStatus order by statusId",TransactionSellHeaderStatus.class).getResultList();
			if(statusList!=null){
				for(TransactionSellHeaderStatus trxStatus:statusList){
					DropDownModel model = new DropDownModel();
					model.setIntKey(trxStatus.getStatusId());
					model.setThLabel(trxStatus.getStatusDesc());
					model.setEnLabel(trxStatus.getStatusDesc());
					trxStatusList.add(model);
					trxStatusForDataTable.add(model);
				}
				DropDownModel model = new DropDownModel();
				model.setIntKey(-1);
				model.setThLabel("");
				model.setEnLabel("");
				trxStatusList.add(0,model);
				selectedTrxStatus = -1;
			}
		}
		search();
	}

	public void search(){
		try{
			trxProductModelList = new ArrayList<TrxProductModel>();
			String statusSql = "";
			String receiptNoSql = "";
			if(selectedTrxStatus > 0){
				statusSql = " And trxHeaderStatus = "+selectedTrxStatus+" ";
			}
			if(receiptNo!=null && receiptNo.trim().length()>0){
				receiptNoSql = " And receiptNo = '"+StringUtil.checkSql(receiptNo)+"' ";
			}
			List<TransactionSellHeader> trxHeaderList = em.createQuery(" From TransactionSellHeader " +
					" Where trxHeaderDatetime Between :startDate And :endDate " +
					statusSql +
					receiptNoSql +
					" Order by trxHeaderDatetime desc ",TransactionSellHeader.class)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.getResultList();
			if(trxHeaderList!=null){
				for(TransactionSellHeader trx:trxHeaderList){
					TrxProductModel model = new TrxProductModel();
					model.setHeaderId(StringUtil.n2b(trx.getTrxHeaderId()));
					MemberCustomer customer = em.find(MemberCustomer.class,trx.getCustomerId());
					if(customer!=null){
						model.setMemberId(customer.getCustomerId().intValue());
						model.setMemberCode(customer.getCustomerMember());
						model.setMemberName(customerControl.genNameMenber(customer));
					}
					model.setReceiptNo(StringUtil.n2b(trx.getReceiptNo()));
					model.setTotalPrice(StringUtil.n2b(trx.getTotalPrice()).doubleValue());
					model.setTotalPv(StringUtil.n2b(trx.getTotalPv()).doubleValue());
					model.setTrxDateTime(trx.getTrxHeaderDatetime());
					UserLogin userLogin = em.find(UserLogin.class, trx.getCreateBy());
					if(userLogin!=null && userLogin.getCustomerId()!=null)
						model.setMemberCodeKey(userLogin.getCustomerId().getCustomerMember());
					TransactionSellHeaderStatus status = em.find(TransactionSellHeaderStatus.class, trx.getTrxHeaderStatus());
					if(status!=null){
						model.setStatusId(StringUtil.n2b(status.getStatusId()));
						model.setStatus(StringUtil.n2b(status.getStatusDesc()));
					}
					model.setTrxStatusList(trxStatusForDataTable);
					List<TransactionSellDetail> detailList = em.createQuery("From TransactionSellDetail Where trxHeaderId =:trxHeaderId ",TransactionSellDetail.class)
							.setParameter("trxHeaderId", trx.getTrxHeaderId())
							.getResultList();
					if(detailList!=null && detailList.size()>0){
						List<ProductModel> productModelList = new ArrayList<ProductModel>();
						int order = 0;
						for(TransactionSellDetail detail:detailList){
							ProductModel productModel = new ProductModel();
							StockProduct stockProduct = em.find(StockProduct.class, detail.getProductId());
							productModel.setOrder(++order);
							if(stockProduct!=null){
								productModel.setProductCode(StringUtil.n2b(stockProduct.getProductCode()));
								productModel.setProductThDesc(StringUtil.n2b(stockProduct.getThDesc()));
							}
							productModel.setQty(StringUtil.n2b(detail.getQty()).intValue());
							productModel.setPrice(StringUtil.n2b(detail.getPrice()).doubleValue());
							productModel.setTotalPrice(StringUtil.n2b(detail.getTotalPrice()).doubleValue());
							productModelList.add(productModel);
						}
						model.setProductModelList(productModelList);
					}

					trxProductModelList.add(model);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void saveEditStatus(TrxProductModel trxModel){
		try{
			log.info("saveEditStatus Trx Header ID = "+trxModel.getHeaderId());
			log.info("saveEditStatus Trx Status ID = "+trxModel.getStatusId());
			TransactionSellHeader trxHeader = em.find(TransactionSellHeader.class, new Integer(trxModel.getHeaderId()));
			trxHeader.setTrxHeaderStatus(trxModel.getStatusId());
			trxHeader.setUpdateBy(currentUser.getCurrentAccount().getUserId());
			trxHeader.setUpdateDate(new Date());
			em.merge(trxHeader);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

	public void exportReport(TrxProductModel trxProductModel) {
		try {
			HttpServletRequest request = (HttpServletRequest) facesContext
					.getExternalContext().getRequest();
			AbstractReport report = null;
			report = new ProductTrxReport();
			Map<String, Object> parameters = ((ProductTrxReport) report)
					.getParameter();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			parameters.put("printDate", sdf2.format(new Date()));
			parameters.put("printBy", currentUser.getCurrentAccount()
					.getUserName());
			if(trxProductModel.getTrxDateTime()!=null)
				parameters.put("paymentDate", sdf.format(trxProductModel.getTrxDateTime()));
			if(trxProductModel.getMemberCode()!=null)
				parameters.put("contracId", trxProductModel.getMemberCode());
			parameters.put("totalAmount", trxProductModel.getTotalPrice());
			if(trxProductModel.getMemberName()!=null)
				parameters.put("clientName", trxProductModel.getMemberName());
			parameters.put("address", transactionHeaderControl.getAddressByHeaderId(trxProductModel.getHeaderId()));
			if(trxProductModel.getReceiptNo()!=null)
				parameters.put("receiptNo", trxProductModel.getReceiptNo());
			parameters.put("totalAmountText", BathText.bahtText(trxProductModel.getTotalPrice()));
			((ProductTrxReport) report).setModel(trxProductModel.getProductModelList());
			report.fill();
			if (request != null) {
				request.getSession().setAttribute(
						ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
						report.getJasperPrint());
			}
			byte[] pdf = report.exportPDF();
			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			response.setContentType("application/pdf");
			response.setContentLength(pdf.length);
			response.setHeader("Content-disposition", "inline; filename="
					+ "TopupReportByDate" + ".pdf");
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

	public List<TrxProductModel> getTrxProductModelList() {
		return trxProductModelList;
	}

	public void setTrxProductModelList(List<TrxProductModel> trxProductModelList) {
		this.trxProductModelList = trxProductModelList;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<DropDownModel> getTrxStatusList() {
		return trxStatusList;
	}

	public void setTrxStatusList(List<DropDownModel> trxStatusList) {
		this.trxStatusList = trxStatusList;
	}

	public int getSelectedTrxStatus() {
		return selectedTrxStatus;
	}

	public void setSelectedTrxStatus(int selectedTrxStatus) {
		this.selectedTrxStatus = selectedTrxStatus;
	}

	public String getReceiptNo() {
		return receiptNo;
	}

	public void setReceiptNo(String receiptNo) {
		this.receiptNo = receiptNo;
	}
	
}
