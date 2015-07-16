package co.th.aten.network.web;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;
import org.jboss.solder.logging.Logger;

import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.TransactionSellHeader;
import co.th.aten.network.entity.TransactionSellHeaderStatus;
import co.th.aten.network.entity.UserLogin;
import co.th.aten.network.model.TrxProductModel;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.report.AbstractReport;
import co.th.aten.network.report.ProductTrxReport;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.StringUtil;

@ViewScoped	
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

	private List<TrxProductModel> trxProductModelList;

	@PostConstruct
	public void init(){
		log.info("init method ProductTrxController");
		genModelList();
	}

	private void genModelList(){
		try{
			trxProductModelList = new ArrayList<TrxProductModel>();
			List<TransactionSellHeader> trxHeaderList = em.createQuery(" From TransactionSellHeader " +
					" Where customerId=:customerId " +
					" Order by trxHeaderDatetime desc ",TransactionSellHeader.class)
					.setParameter("customerId", currentUser.getCurrentAccount().getCustomerId().getCustomerId())
					.getResultList();
			if(trxHeaderList!=null){
				for(TransactionSellHeader trx:trxHeaderList){
					TrxProductModel model = new TrxProductModel();
					MemberCustomer customer = em.find(MemberCustomer.class,trx.getCustomerId());
					if(customer!=null){
						model.setMemberCode(customer.getCustomerMember());
						model.setMemberName(StringUtil.n2b(customer.getTitleName())+StringUtil.n2b(customer.getFirstName())+" "+StringUtil.n2b(customer.getLastName()));
					}
					model.setReceiptNo(StringUtil.n2b(trx.getReceiptNo()));
					model.setTotalPrice(StringUtil.n2b(trx.getTotalPrice()).doubleValue());
					model.setTotalPv(StringUtil.n2b(trx.getTotalPv()).doubleValue());
					model.setTrxDateTime(trx.getTrxHeaderDatetime());
					UserLogin userLogin = em.find(UserLogin.class, trx.getCreateBy());
					if(userLogin!=null && userLogin.getCustomerId()!=null)
						model.setMemberCodeKey(userLogin.getCustomerId().getCustomerMember());
					TransactionSellHeaderStatus status = em.find(TransactionSellHeaderStatus.class, trx.getTrxHeaderStatus());
					if(status!=null)
						model.setStatus(status.getStatusDesc());
					trxProductModelList.add(model);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void exportReport() {
		ServletContext servletContext = (ServletContext) facesContext
				.getExternalContext().getContext();
		try {
			HttpServletRequest request = (HttpServletRequest) facesContext
					.getExternalContext().getRequest();
			AbstractReport report = null;
			report = new ProductTrxReport();
			Map<String, Object> parameters = ((ProductTrxReport) report)
					.getParameter();
			parameters
			.put("PIC_DIR", servletContext
					.getRealPath("/resources/image/ptt_logo.gif"));
			parameters.put("printUser", currentUser.getCurrentAccount()
					.getUserName());
			parameters.put("showHeader", true);
			((ProductTrxReport) report).setModel(null);
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
}
