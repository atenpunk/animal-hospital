/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.ui.report;

import co.th.aten.football.model.PlayersModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.springframework.richclient.dialog.ApplicationDialog;

/**
 *
 * @author Atenpunk
 */
public class PlayersDetailReportDialog extends ApplicationDialog {

//    private PosStockModel filter;
    private List<PlayersModel> playersModelList;

    public PlayersDetailReportDialog(List<PlayersModel> playersModelList) {
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
        PlayersDetailReport print = new PlayersDetailReport(playersModelList);
//        print.setFilter(filter);
        print.reportFactory();
//        print.getJrViewer().setName("report.pdf");
        panel.add(print.getJrViewer(), BorderLayout.CENTER);
        return panel;
    }
}
