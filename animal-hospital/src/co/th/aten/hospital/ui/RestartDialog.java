/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package co.th.aten.hospital.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import org.springframework.richclient.dialog.ApplicationDialog;

/**
 *
 * @author Mai
 */
public class RestartDialog extends ApplicationDialog{

    private boolean iscancel = false;
    @Override
    protected JComponent createButtonBar() {
         JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
         JButton cancel = new JButton("Cancel");
         cancel.setEnabled(false);
         cancel.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                iscancel= true;                
            }
        });
        panel.add(cancel);
        return panel;
    }

    @Override
    protected boolean onFinish() {
        return true;
    }

    @Override
    protected JComponent createDialogContentPane() {
        JPanel panel = new JPanel(new BorderLayout());
        int ss= 10;
        final JLabel label = new JLabel(getMessage("msg.restartInSec",new Object[]{ss}));
        iscancel = false;
        panel.add(label,BorderLayout.CENTER);
        setTitle("TCA/POS Application update service");
        new SwingWorker() {
            int sec = 10;
            @Override
            protected void process(List chunks) {
                label.setText(getMessage("msg.restartInSec",new Object[]{sec}));
            }


            @Override
            protected Object doInBackground() throws Exception {
                while(sec > 0 && !iscancel){
                    logger.info("countingdown sec="+sec);
                    sec--;
                    publish(sec);
                    Thread.sleep(1000);
                }
                if( !iscancel){
                    Runtime.getRuntime().exec("java -jar updater.jar");
                    System.exit(0);
                }
                dispose();
                return null;
            }
        }.execute();

        return panel;
    }

}
