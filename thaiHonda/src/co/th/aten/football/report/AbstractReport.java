package co.th.aten.football.report;

import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public abstract class AbstractReport {

    private int reportId;
    protected Map<String, Object> filters;
    protected JasperPrint jasperPrint;
    protected JasperReport jasperReport;

    public abstract void reportFactory();

    public AbstractReport() {
        filters = new HashMap<String, Object>();
        filters.put("logo_file", "img/logo_thai_honda.jpg");
    }

    public int getReportId() {
        return reportId;
    }

    public void setReportId(int reportId) {
        this.reportId = reportId;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }

    public JasperPrint getJasperPrint() {
        return jasperPrint;
    }

    public void setJasperPrint(JasperPrint jasperPrint) {
        this.jasperPrint = jasperPrint;
    }
}
