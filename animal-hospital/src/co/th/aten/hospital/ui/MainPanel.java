/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MainPanel.java
 *
 * Created on 23 ก.ค. 2552, 4:31:41
 */
package co.th.aten.hospital.ui;

import co.th.aten.hospital.dialog.AddNewOwnerDialog;
import co.th.aten.hospital.dialog.EditOwnerDialog;
import co.th.aten.hospital.event.LogoutEvent;
import java.awt.Image;
import javax.swing.JOptionPane;
import org.springframework.richclient.application.Application;

/**
 *
 * @author Aten
 */
public class MainPanel extends ImagePanel {

//    private VistaSearchDialog dialog;
    public MainPanel() {
    }

    /** Creates new form MainPanel */
    public MainPanel(Image image) {
        super(image);
        initComponents();
    }

    private void logout() {
        Application.instance().getApplicationContext().publishEvent(new LogoutEvent("POS"));
    }

    public void updateMiniMessage(String msg) {
        this.repaint();
    }

    public void grantAccess() {
//        SessionManager sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
//        int uClass = sessionManager.getSession().getUserMode();
//        System.out.println("User Class=" + uClass);
        btSearchCustomers.setEnabled(true);
        btTopup.setEnabled(false);
        btAddCustomers.setEnabled(true);
        btEditCustomers.setEnabled(true);
        btEdit.setEnabled(false);
        btAddStaff.setEnabled(false);
        btChangePassword.setEnabled(false);
        btChangeOwner.setEnabled(false);
        btSellStatement.setEnabled(false);
        btReport.setEnabled(true);
        btCheckBalance.setEnabled(false);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        btEdit = new javax.swing.JButton();
        btChangeOwner = new javax.swing.JButton();
        btReport = new javax.swing.JButton();
        btSearchCustomers = new javax.swing.JButton();
        btCheckBalance = new javax.swing.JButton();
        btTopup = new javax.swing.JButton();
        btAddCustomers = new javax.swing.JButton();
        btEditCustomers = new javax.swing.JButton();
        btSellStatement = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        btAddStaff = new javax.swing.JButton();
        btChangePassword = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        btEoj = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setDoubleBuffered(true);
        jScrollPane1.setFont(new java.awt.Font("Dialog", 0, 12));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(22, 22));

        jPanel2.setLayout(new java.awt.GridBagLayout());

        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ui/messages"); // NOI18N
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("main.label.transaction"))); // NOI18N
        jPanel5.setFont(new java.awt.Font("Dialog", 0, 12));
        jPanel5.setOpaque(false);
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("main.label.transaction"))); // NOI18N
        jPanel6.setFont(new java.awt.Font("Dialog", 0, 12));
        jPanel6.setOpaque(false);
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btEdit.setBackground(new java.awt.Color(200, 244, 11));
        btEdit.setFont(new java.awt.Font("Tahoma", 1, 18));
        btEdit.setText(bundle.getString("main.label.defaul")); // NOI18N
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });
        jPanel6.add(btEdit, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, 370, 80));

        btChangeOwner.setBackground(new java.awt.Color(235, 102, 255));
        btChangeOwner.setFont(new java.awt.Font("Dialog", 1, 18));
        btChangeOwner.setMnemonic('p');
        btChangeOwner.setText(bundle.getString("main.label.defaul")); // NOI18N
        btChangeOwner.setPreferredSize(new java.awt.Dimension(200, 80));
        btChangeOwner.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChangeOwnerActionPerformed(evt);
            }
        });
        jPanel6.add(btChangeOwner, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 120, 370, -1));

        btReport.setBackground(new java.awt.Color(32, 249, 226));
        btReport.setFont(new java.awt.Font("Dialog", 1, 18));
        btReport.setMnemonic('p');
        btReport.setText(bundle.getString("main.label.report")); // NOI18N
        btReport.setPreferredSize(new java.awt.Dimension(200, 80));
        btReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btReportActionPerformed(evt);
            }
        });
        jPanel6.add(btReport, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 210, 370, 80));

        btSearchCustomers.setBackground(new java.awt.Color(102, 255, 102));
        btSearchCustomers.setFont(new java.awt.Font("Dialog", 1, 18));
        btSearchCustomers.setMnemonic('s');
        btSearchCustomers.setText(bundle.getString("main.label.searchCustomers")); // NOI18N
        btSearchCustomers.setPreferredSize(new java.awt.Dimension(200, 80));
        btSearchCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSearchCustomersActionPerformed(evt);
            }
        });
        jPanel6.add(btSearchCustomers, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 370, -1));

        btCheckBalance.setBackground(new java.awt.Color(202, 124, 47));
        btCheckBalance.setFont(new java.awt.Font("Dialog", 1, 18));
        btCheckBalance.setMnemonic('p');
        btCheckBalance.setText(bundle.getString("main.label.defaul")); // NOI18N
        btCheckBalance.setPreferredSize(new java.awt.Dimension(200, 80));
        btCheckBalance.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCheckBalanceActionPerformed(evt);
            }
        });
        jPanel6.add(btCheckBalance, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 210, 370, -1));

        btTopup.setBackground(new java.awt.Color(255, 247, 102));
        btTopup.setFont(new java.awt.Font("Dialog", 1, 18));
        btTopup.setMnemonic('p');
        btTopup.setText(bundle.getString("main.label.defaul")); // NOI18N
        btTopup.setPreferredSize(new java.awt.Dimension(200, 80));
        btTopup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btTopupActionPerformed(evt);
            }
        });
        jPanel6.add(btTopup, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 370, -1));

        btAddCustomers.setBackground(new java.awt.Color(255, 153, 0));
        btAddCustomers.setFont(new java.awt.Font("Dialog", 1, 18));
        btAddCustomers.setMnemonic('a');
        btAddCustomers.setText(bundle.getString("main.label.addCustomers")); // NOI18N
        btAddCustomers.setPreferredSize(new java.awt.Dimension(200, 80));
        btAddCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddCustomersActionPerformed(evt);
            }
        });
        jPanel6.add(btAddCustomers, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 370, -1));

        btEditCustomers.setBackground(new java.awt.Color(255, 204, 153));
        btEditCustomers.setFont(new java.awt.Font("Dialog", 1, 18));
        btEditCustomers.setMnemonic('y');
        btEditCustomers.setText(bundle.getString("main.label.editCustomers")); // NOI18N
        btEditCustomers.setPreferredSize(new java.awt.Dimension(200, 80));
        btEditCustomers.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditCustomersActionPerformed(evt);
            }
        });
        jPanel6.add(btEditCustomers, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 30, 370, -1));

        btSellStatement.setBackground(new java.awt.Color(102, 255, 213));
        btSellStatement.setFont(new java.awt.Font("Dialog", 1, 18));
        btSellStatement.setMnemonic('p');
        btSellStatement.setText(bundle.getString("main.label.defaul")); // NOI18N
        btSellStatement.setPreferredSize(new java.awt.Dimension(200, 80));
        btSellStatement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSellStatementActionPerformed(evt);
            }
        });
        jPanel6.add(btSellStatement, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 210, 370, 80));

        jPanel5.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.weighty = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        jPanel2.add(jPanel5, gridBagConstraints);

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(bundle.getString("main.label.systemControl"))); // NOI18N
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btAddStaff.setBackground(new java.awt.Color(204, 204, 255));
        btAddStaff.setFont(new java.awt.Font("Dialog", 1, 18));
        btAddStaff.setMnemonic('h');
        btAddStaff.setText(bundle.getString("main.label.addStaff")); // NOI18N
        btAddStaff.setPreferredSize(new java.awt.Dimension(200, 80));
        btAddStaff.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddStaffActionPerformed(evt);
            }
        });
        jPanel7.add(btAddStaff, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 370, -1));

        btChangePassword.setBackground(new java.awt.Color(102, 102, 255));
        btChangePassword.setFont(new java.awt.Font("Dialog", 1, 18));
        btChangePassword.setText(bundle.getString("main.label.changePassword")); // NOI18N
        btChangePassword.setPreferredSize(new java.awt.Dimension(200, 80));
        btChangePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btChangePasswordActionPerformed(evt);
            }
        });
        jPanel7.add(btChangePassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 30, 370, -1));
        jPanel7.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(438, 62, -1, -1));

        btEoj.setFont(new java.awt.Font("Dialog", 1, 18));
        btEoj.setText(bundle.getString("main.label.logout")); // NOI18N
        btEoj.setPreferredSize(new java.awt.Dimension(200, 80));
        btEoj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEojActionPerformed(evt);
            }
        });
        jPanel7.add(btEoj, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 30, 370, -1));

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.weighty = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        jPanel2.add(jPanel7, gridBagConstraints);

        jScrollPane1.setViewportView(jPanel2);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btChangePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btChangePasswordActionPerformed
        //Application.instance().getWindowManager().getActiveWindow().getPage().showView("ReceiveSmartcardView");
//        new ObuReturnDialog().showDialog();
    }//GEN-LAST:event_btChangePasswordActionPerformed

    private void btAddCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddCustomersActionPerformed
        new AddNewOwnerDialog().showDialog();
    }//GEN-LAST:event_btAddCustomersActionPerformed

    private void btTopupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btTopupActionPerformed
//        TopupWizard.getInstance().execute();
    }//GEN-LAST:event_btTopupActionPerformed

    private void btEditCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditCustomersActionPerformed
//        ClaimWizard.getInstance().execute();
        new EditOwnerDialog().showDialog();
    }//GEN-LAST:event_btEditCustomersActionPerformed

    private void btEojActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEojActionPerformed
        boolean confirm = JOptionPane.showConfirmDialog(null, java.util.ResourceBundle.getBundle("ui/messages").getString("exit.question"), "Confirm Exit", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
        if (confirm) {
            logout();
        }
    }//GEN-LAST:event_btEojActionPerformed

    private void btSearchCustomersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSearchCustomersActionPerformed
//        ((SellTagWizard) Application.services().getService(SellTagWizard.class)).execute();
        //        SellTagWizard sellTagWizard = ((SessionManager)Application.services().getService(SessionManager.class)).getSession().getSellTagWizard();
//        if (sellTagWizard == null) {
//            sellTagWizard = new SellTagWizard();
//            ((SessionManager)Application.services().getService(SessionManager.class)).getSession().setSellTagWizard(sellTagWizard);
//        }
//        SellTagWizard.getInstance().execute();
//        SellEasyPassDialog dialog = new SellEasyPassDialog();
//        dialog.showDialog();
    }//GEN-LAST:event_btSearchCustomersActionPerformed

    private void btAddStaffActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddStaffActionPerformed
//        AddStaffWizard.getInstance().execute();
    }//GEN-LAST:event_btAddStaffActionPerformed

    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        // TODO add your handling code here:
//        new EditCustomerProfileDialog().showDialog();
    }//GEN-LAST:event_btEditActionPerformed

    private void btChangeOwnerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btChangeOwnerActionPerformed
//        ChangeOwnerWizard.getInstance().execute();
    }//GEN-LAST:event_btChangeOwnerActionPerformed

    private void btCheckBalanceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCheckBalanceActionPerformed
//        CheckBalanceWizard.getInstance().execute();
    }//GEN-LAST:event_btCheckBalanceActionPerformed

    private void btReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btReportActionPerformed
//        CancelStatementWizard.getInstance().execute();
    }//GEN-LAST:event_btReportActionPerformed

    private void btSellStatementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSellStatementActionPerformed
//        SellStatementWizard.getInstance().execute();
}//GEN-LAST:event_btSellStatementActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddCustomers;
    private javax.swing.JButton btAddStaff;
    private javax.swing.JButton btChangeOwner;
    private javax.swing.JButton btChangePassword;
    private javax.swing.JButton btCheckBalance;
    private javax.swing.JButton btEdit;
    private javax.swing.JButton btEditCustomers;
    private javax.swing.JButton btEoj;
    private javax.swing.JButton btReport;
    private javax.swing.JButton btSearchCustomers;
    private javax.swing.JButton btSellStatement;
    private javax.swing.JButton btTopup;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}