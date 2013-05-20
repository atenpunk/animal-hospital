/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.hospital.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.springframework.richclient.application.Application;
import org.springframework.richclient.application.support.AbstractView;
import org.springframework.richclient.application.support.DefaultViewDescriptor;

/**
 *
 * @author Mai
 */
public class ReceiveSmartcardView extends AbstractView {

    @Override
    protected JComponent createControl() {
        String build = Application.instance().getDescriptor().getVersion() + " ( " + Application.instance().getDescriptor().getBuildId() + " )";
        ((DefaultViewDescriptor) this.getDescriptor()).setTitle(getMessage("header.welcome") + "  v." + build + " - " + getMessage("main.label.receiveSmartcard"));

        JPanel panel = new JPanel(new BorderLayout());
        InfoPanel infoPanel = new InfoPanel();
        panel.add(infoPanel, BorderLayout.NORTH);
        JPanel middlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbHeader = new JLabel();
        lbHeader.setFont(new java.awt.Font("Tahoma", 1, 14));
        lbHeader.setText(getMessage("main.label.receiveSmartcard"));
        middlePanel.add(lbHeader);

        panel.add(middlePanel, BorderLayout.CENTER);
        
        JPanel commandPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btClose = new JButton(getMessage("main.label.close"));
        btClose.setFont(new java.awt.Font("Tahoma", 1, 18));
        btClose.setPreferredSize(new java.awt.Dimension(200, 80));
        btClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                logger.info("close action");
                Application.instance().getWindowManager().getActiveWindow().getPage().getView("ReceiveSmartcardView").close();
            }
        });
        commandPanel.add(btClose);
        panel.add(commandPanel, BorderLayout.SOUTH);
        return panel;
    }
}
