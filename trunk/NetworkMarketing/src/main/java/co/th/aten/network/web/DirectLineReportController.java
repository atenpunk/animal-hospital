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

import co.th.aten.network.control.CustomerControl;
import co.th.aten.network.entity.MemberCustomer;
import co.th.aten.network.entity.MemberPosition;
import co.th.aten.network.model.DirectLineReportModel;
import co.th.aten.network.producer.DBDefault;
import co.th.aten.network.report.AbstractReport;
import co.th.aten.network.report.DirectLineReport;
import co.th.aten.network.security.CurrentUserManager;
import co.th.aten.network.util.StringUtil;

@ViewScoped	
@Named
public class DirectLineReportController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	Logger log;

	@Inject
	@DBDefault
	private EntityManager em;

	@Inject
	private CurrentUserManager currentUser;
	@Inject
	private CustomerControl customerControl;

	@Inject
	private FacesContext facesContext;

	private List<DirectLineReportModel> directLineReportModelList ;
	
//	private Date startRegisDate;
//	private Date endRegisDate;
//	private String customerIdSearch;
	
	@PostConstruct
	public void init(){
		log.info("init method DirectLineReportController");
		search();
	}

	public void search(){
		try{
			log.info("-------------- search");
			directLineReportModelList = new ArrayList<DirectLineReportModel>();
			List<MemberCustomer> CustomerList = em.createQuery("From MemberCustomer",MemberCustomer.class).getResultList();
			if(CustomerList!=null){
				int index = 0;
				for(MemberCustomer customer:CustomerList){
					DirectLineReportModel model = new DirectLineReportModel();
					model.setIndex(++index);
					model.setCustomerId(customer.getCustomerMember());
					model.setCustomerName(customerControl.genNameMenber(customer));
					if(customer.getPositionId()!=null){
						model.setPosition(StringUtil.n2b(customer.getPositionId().getEnName()));
					}
					model.setRegisDate(customer.getRegisDate());
					if(customer.getUpperId()!=null && customer.getUpperId().intValue()!=0){
						MemberCustomer upper = em.find(MemberCustomer.class, customer.getUpperId());
						model.setUpperLineId(upper.getCustomerMember());
					}
					directLineReportModelList.add(model);
				}
			}

		} catch (Exception e) {
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
			report = new DirectLineReport();

			Map<String, Object> parameters = ((DirectLineReport) report)
					.getParameter();

			parameters
			.put("PIC_DIR", servletContext
					.getRealPath("/resources/image/ptt_logo.gif"));

			parameters.put("printUser", currentUser.getCurrentAccount()
					.getUserName());
			parameters.put("showHeader", true);

			((DirectLineReport) report).setModel(directLineReportModelList);
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
			// log.error("IO Exception #0",e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void exportReportCSV() {

		// log.info("datalist =  "+dataList.size());
		ServletContext servletContext = (ServletContext) facesContext
				.getExternalContext().getContext();

		try {
			HttpServletRequest request = (HttpServletRequest) facesContext
					.getExternalContext().getRequest();

			AbstractReport report = null;
			report = new DirectLineReport();

			Map<String, Object> parameters = ((DirectLineReport) report)
					.getParameter();

			parameters
			.put("PIC_DIR", servletContext
					.getRealPath("/resources/image/ptt_logo.gif"));

			parameters.put("printUser", currentUser.getCurrentAccount()
					.getUserName());

			parameters.put("showHeader", false);

			((DirectLineReport) report)
			.setModel(directLineReportModelList);
			report.fill();

			if (request != null) {
				request.getSession().setAttribute(
						ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
						report.getJasperPrint());
			}
			byte[] csv = report.exportCSV();
			HttpServletResponse response = (HttpServletResponse) facesContext
					.getExternalContext().getResponse();
			response.setContentType("application/csv");
			response.setContentLength(csv.length);
			response.setHeader("Content-disposition", "inline; filename="
					+ "TopupReportByDate" + ".csv");

			ServletOutputStream out;

			out = response.getOutputStream();
			out.write(csv);
			out.flush(); // new
			out.close();
			response.flushBuffer();

			facesContext.responseComplete(); // new
		} catch (IOException e) {
			// log.error("IO Exception #0",e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<DirectLineReportModel> getDirectLineReportModelList() {
		return directLineReportModelList;
	}

	public void setDirectLineReportModelList(
			List<DirectLineReportModel> directLineReportModelList) {
		this.directLineReportModelList = directLineReportModelList;
	}
	
}
