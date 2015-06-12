/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package co.th.aten.football.dialog;

import co.th.aten.football.ui.form.AddStaffPanel;
import co.th.aten.football.ui.form.ChangePasswordStaffPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import org.springframework.richclient.dialog.ApplicationDialog;

/**
 *
 * @author mai
 */
public class ChangePasswordStaffDialog extends ApplicationDialog {

    private ChangePasswordStaffPanel panel;

    public ChangePasswordStaffDialog() {
        this.getDialog().setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    @Override
    protected boolean onFinish() {
        dispose();
        return true;
    }

    @Override
    protected void onCancel() {
        super.onCancel();
        dispose();
    }

    public void closePanel(){
        super.onCancel();
        dispose();
    }
    
    @Override
    protected JComponent createButtonBar() {
        JPanel bpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bpanel.setBackground(Color.WHITE);
//        JButton btClose = new JButton("  Close  ");
//        btClose.setFont(new java.awt.Font("Tahoma", 1, 12));
//        btClose.setPreferredSize(new java.awt.Dimension(100, 25));
//        btClose.addActionListener(new ActionListener() {
//
//            public void actionPerformed(ActionEvent e) {
//                onCancel();
//            }
//        });
//        bpanel.add(btClose);
        return bpanel;
    }

    @Override
    protected JComponent createDialogContentPane() {
        setTitle("Change Password");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;
        System.out.println("--->>>>" + height + ", " + width);
        height = height - (int) (height * 0.63);
        width = width - (int) (width * 0.57);
        System.out.println("--->>>>" + height + ", " + width);
        Dimension dimension = new Dimension(width, height);
        if (panel == null) {
            panel = new ChangePasswordStaffPanel(this);
        }
        panel.setSize(dimension);
        panel.setMaximumSize(dimension);
        panel.setMinimumSize(dimension);
        panel.setPreferredSize(dimension);

        return panel;
    }
}
