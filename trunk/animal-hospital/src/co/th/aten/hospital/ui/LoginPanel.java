/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * LoginPanel.java
 *
 * Created on 9 พ.ค. 2552, 17:10:17
 */
package co.th.aten.hospital.ui;

import co.th.aten.hospital.event.LoginSuccessEvent;
import co.th.aten.hospital.model.UserModel;
import co.th.aten.hospital.service.LoginManager;
import co.th.aten.hospital.service.MessageManager;
import co.th.aten.hospital.service.SessionManager;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.Locale;
import javax.swing.SwingWorker;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.richclient.application.Application;
import org.springframework.richclient.application.ApplicationWindow;
import org.springframework.richclient.progress.BusyIndicator;
import org.springframework.richclient.progress.ProgressMonitor;

/**
 *
 * @author Aten
 */
public class LoginPanel extends ImagePanel {

    private static final String screeen = "LOGIN PANEL";
    private final Log logger = LogFactory.getLog(getClass());
    private int keyBoardFocus;
    private boolean loginSuccess;
    private SessionManager sessionManager;
    private LoginManager loginManager;

    /** Creates new form LoginPanel */
    public LoginPanel(Image image) {
        super(image);
        this.sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
        this.loginManager = (LoginManager) Application.services().getService(LoginManager.class);
        initComponents();
//        txStaffNo.setDocument(new JTextFieldFilter(JTextFieldFilter.NUMERIC));
        keyBoardFocus = 0;
//        txStaffNo.selectAll();

    }

    public void clearScreen() {
        txStaffNo.setText("");
        txPassword.setText("");
    }

    private void doLogin() {
        this.btLogin.setEnabled(false);
        ApplicationWindow aw = Application.instance().getActiveWindow();
        final ProgressMonitor pm = aw.getStatusBar().getProgressMonitor();
        pm.taskStarted("Authenticating...", -1);
        pm.setCanceled(true);
        BusyIndicator.showAt(aw.getControl());

        new SwingWorker() {

            boolean success = false;

            @Override
            protected Object doInBackground() {
//                if (txStaffNo.getText().length() != 10) {
//                Toolkit.getDefaultToolkit().beep();
//                    String msg = "Error";
//                    Application.instance().getApplicationContext().publishEvent(new ShowMsgEvent(msg));
//                } else {
                try {
                    System.out.println("Staff Login Staff Id=" + txStaffNo.getText());
                    UserModel user = loginManager.login(txStaffNo.getText(), new String(txPassword.getPassword()));
                    if (user != null) {
                        logger.info("Program reply staff name=" + user.getUserName());
                        sessionManager.setUser(user);
                        success = true;
                    } else {
                        logger.info("Staff is null");
                        MessageManager messageManager = (MessageManager) Application.services().getService(MessageManager.class);
                        MessageSource ms = (MessageSource) Application.services().getService(MessageSource.class);
                        messageManager.showError(ms.getMessage("error.loginFail", null, Locale.US), 3);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void done() {
                pm.done();
                loginSuccess = success;
                btLogin.setEnabled(true);
                BusyIndicator.clearAt(Application.instance().getActiveWindow().getControl());

                if (loginSuccess) {
                    Application.instance().getApplicationContext().publishEvent(new LoginSuccessEvent(txStaffNo.getText()));
                }
            }
        }.execute();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new TransparentPanel();
        lbStaffNo = new javax.swing.JLabel();
        txStaffNo = new javax.swing.JTextField();
        lbPassword = new javax.swing.JLabel();
        txPassword = new javax.swing.JPasswordField();
        btLogin = new javax.swing.JButton();

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentShown(java.awt.event.ComponentEvent evt) {
                formComponentShown(evt);
            }
        });
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(186, 110));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        lbStaffNo.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lbStaffNo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ui/messages"); // NOI18N
        lbStaffNo.setText(bundle.getString("login.staffNo")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(21, 20, 21, 20);
        jPanel1.add(lbStaffNo, gridBagConstraints);

        txStaffNo.setFont(new java.awt.Font("Tahoma", 0, 14));
        txStaffNo.setMinimumSize(new java.awt.Dimension(120, 23));
        txStaffNo.setPreferredSize(new java.awt.Dimension(120, 30));
        txStaffNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txStaffNoActionPerformed(evt);
            }
        });
        txStaffNo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txStaffNoFocusGained(evt);
            }
        });
        txStaffNo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txStaffNoKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(txStaffNo, gridBagConstraints);

        lbPassword.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        lbPassword.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbPassword.setText(bundle.getString("login.password")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(21, 20, 21, 20);
        jPanel1.add(lbPassword, gridBagConstraints);

        txPassword.setFont(new java.awt.Font("Tahoma", 0, 14));
        txPassword.setMinimumSize(new java.awt.Dimension(120, 23));
        txPassword.setPreferredSize(new java.awt.Dimension(120, 30));
        txPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txPasswordActionPerformed(evt);
            }
        });
        txPassword.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txPasswordFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        jPanel1.add(txPassword, gridBagConstraints);

        btLogin.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        btLogin.setText(bundle.getString("main.label.login")); // NOI18N
        btLogin.setPreferredSize(new java.awt.Dimension(80, 50));
        btLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btLoginActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 87;
        gridBagConstraints.ipady = 47;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        jPanel1.add(btLogin, gridBagConstraints);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void txStaffNoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txStaffNoFocusGained
        // TODO add your handling code here:
        keyBoardFocus = 1;
}//GEN-LAST:event_txStaffNoFocusGained

    private void txPasswordFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txPasswordFocusGained
        // TODO add your handling code here:
        keyBoardFocus = 2;
}//GEN-LAST:event_txPasswordFocusGained

    private void btLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btLoginActionPerformed
        doLogin();
        // ButtonLogger.getInstance().button(screeen, "LOGIN");
}//GEN-LAST:event_btLoginActionPerformed

    private void formComponentShown(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_formComponentShown
        keyBoardFocus = 1;
        txStaffNo.grabFocus();
    }//GEN-LAST:event_formComponentShown

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        keyBoardFocus = 1;
        txStaffNo.grabFocus();
    }//GEN-LAST:event_formFocusGained

    private void txStaffNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txStaffNoActionPerformed
        txPassword.grabFocus();
    }//GEN-LAST:event_txStaffNoActionPerformed

    private void txStaffNoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txStaffNoKeyTyped

        String text = txStaffNo.getText();
        int length = text.length();
        if (evt.getKeyCode() != KeyEvent.VK_BACK_SPACE || evt.getKeyCode() != KeyEvent.VK_DELETE) {
            if (length == 10) {
                evt.consume();
            }
        }
    }//GEN-LAST:event_txStaffNoKeyTyped

    private void txPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txPasswordActionPerformed
        doLogin();
    }//GEN-LAST:event_txPasswordActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btLogin;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbStaffNo;
    private javax.swing.JPasswordField txPassword;
    private javax.swing.JTextField txStaffNo;
    // End of variables declaration//GEN-END:variables
}
