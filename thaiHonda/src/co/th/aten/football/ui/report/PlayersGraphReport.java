/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.ui.report;

import co.th.aten.football.report.AbstractReport;
import java.io.File;
import java.util.List;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JRViewer;
import co.th.aten.football.model.PlayersGraphModel;

/**
 *
 * @author Atenpunk
 */
public class PlayersGraphReport extends AbstractReport {

    private JRViewer jrViewer;
    private String fileName = "reports/ReportPlayersDetail.jasper";
    private List<PlayersGraphModel> modelList;

    public PlayersGraphReport(List<PlayersGraphModel> modelList) {
        this.modelList = modelList;
    }

    @Override
    public void reportFactory() {
        try {
            System.out.println(fileName);
            jasperReport = (JasperReport) JRLoader.loadObject(new File(fileName));
            if (modelList == null) {
                System.out.println("modelList is null");
                jasperPrint = JasperFillManager.fillReport(jasperReport, filters, new JREmptyDataSource());
            } else {
                System.out.println("modelList.size() : "+modelList.size());
                jasperPrint = JasperFillManager.fillReport(jasperReport, filters, new JRBeanCollectionDataSource(modelList));
            }
            jrViewer = new JRViewer(jasperPrint);
        } catch (JRException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @return the jrViewer
     */
    public JRViewer getJrViewer() {
        return jrViewer;
    }

    /**
     * @param jrViewer the jrViewer to set
     */
    public void setJrViewer(JRViewer jrViewer) {
        this.jrViewer = jrViewer;
    }
}
