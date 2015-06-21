package co.th.aten.network.report;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import co.th.aten.network.model.DirectLineReportModel;


public class DirectLineReport extends AbstractReport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5326919701601476597L;

	private final String fileName = "/reports/DirectLineReport.jasper";

	private List<DirectLineReportModel> model;

	public DirectLineReport() {
		super();
	}

	@Override
	public void fill() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ServletContext servletContext = (ServletContext) facesContext
					.getExternalContext().getContext();

			File reportFile = new File(servletContext.getRealPath(fileName));
			System.out.println("report file="+reportFile.getAbsolutePath());
			jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
				if (model == null) {
					jasperPrint = JasperFillManager.fillReport(jasperReport,
							parameters, new JREmptyDataSource());
				} else {
					jasperPrint = JasperFillManager.fillReport(jasperReport,
							parameters, new JRBeanCollectionDataSource(model));
				}

		} catch (JRException e) {
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public List<DirectLineReportModel> getModel() {
		return model;
	}

	public void setModel(List<DirectLineReportModel> model) {
		this.model = model;
	}

	public Map<String, Object> getParameter() {
		return parameters;
	}

}
