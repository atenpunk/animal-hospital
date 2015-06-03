/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.ui.report;

import co.th.aten.football.model.PlayersGraphModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import net.sf.jasperreports.view.JRSaveContributor;
import net.sf.jasperreports.view.save.JRPdfSaveContributor;
import net.sf.jasperreports.view.save.JRSingleSheetXlsSaveContributor;
import org.springframework.richclient.dialog.ApplicationDialog;

/**
 *
 * @author Atenpunk
 */
public class PlayersGraphReportDialog extends ApplicationDialog {

//    private PosStockModel filter;
    private List<PlayersGraphModel> playersModelList;

    public PlayersGraphReportDialog(List<PlayersGraphModel> playersModelList) {
//        this.filter = filter;
        this.playersModelList = playersModelList;
        this.getDialog().setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    @Override
    protected boolean onFinish() {
        return true;
    }

    @Override
    protected JComponent createDialogContentPane() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(1024, 800));
        setResizable(true);
        PlayersGraphReport print = new PlayersGraphReport(playersModelList);
//        print.setFilter(filter);
        print.reportFactory();

        List<JRSaveContributor> newSaveContributors = new LinkedList<JRSaveContributor>();
        JRSaveContributor[] saveContributors = print.getJrViewer().getSaveContributors();
        for (int i = 0; i < saveContributors.length; i++) {
            if (saveContributors[i] instanceof JRPdfSaveContributor) {
//                    || saveContributors[i] instanceof JRSingleSheetXlsSaveContributor) {
                System.out.println("saveContributors["+i+"] "+saveContributors[i].getDescription());
                newSaveContributors.add(saveContributors[i]);
            }
        }
        print.getJrViewer().setSaveContributors(newSaveContributors.toArray(new JRSaveContributor[0]));
        panel.add(print.getJrViewer(), BorderLayout.CENTER);
        return panel;
    }
}
