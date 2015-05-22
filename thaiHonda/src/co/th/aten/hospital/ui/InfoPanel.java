/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MasterPanel.java
 *
 * Created on 8 พ.ค. 2552, 19:47:47
 */
package co.th.aten.hospital.ui;

import co.th.aten.hospital.service.SessionManager;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.richclient.application.Application;

/**
 *
 * @author Aten
 */
public class InfoPanel extends javax.swing.JPanel {

    private final Log logger = LogFactory.getLog(getClass());

    /** Creates new form MasterPanel */
    public InfoPanel() {
        initComponents();
        clock();
    }

    public void updateJob() {
        SessionManager sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        if (sessionManager.getUser() != null) {
            lbStaffValue.setText(sessionManager.getUser().getUserName());
            lbBOJValue.setText(sdf.format(new Date()));
        } else {
            lbBOJValue.setText("-");
            lbStaffValue.setText("-");
        }
    }

    private void clock() {
        new Thread() {

            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                while (true) {
                    try {
                        lbDateValue.setText(sdf.format(Calendar.getInstance().getTime()));
                        lbTimeValue.setText(sdf2.format(Calendar.getInstance().getTime()));
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        logger.error(ex);
                    }
                }
            }
        }.start();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnInformation = new javax.swing.JPanel();
        lbStaff = new javax.swing.JLabel();
        lbBOJ = new javax.swing.JLabel();
        lbBOJValue = new javax.swing.JLabel();
        lbStaffValue = new javax.swing.JLabel();
        lbTimeValue = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbDateValue = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(800, 120));
        setPreferredSize(new java.awt.Dimension(800, 120));
        setLayout(new java.awt.BorderLayout());

        pnInformation.setBackground(new java.awt.Color(255, 255, 255));
        pnInformation.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnInformation.setMinimumSize(new java.awt.Dimension(1024, 190));
        pnInformation.setPreferredSize(new java.awt.Dimension(1024, 190));
        pnInformation.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbStaff.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lbStaff.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ui/messages"); // NOI18N
        lbStaff.setText(bundle.getString("info.staff")); // NOI18N
        lbStaff.setMaximumSize(new java.awt.Dimension(50, 17));
        lbStaff.setMinimumSize(new java.awt.Dimension(50, 17));
        pnInformation.add(lbStaff, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 20, 70, -1));

        lbBOJ.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        lbBOJ.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbBOJ.setText(bundle.getString("info.boj")); // NOI18N
        lbBOJ.setPreferredSize(new java.awt.Dimension(25, 15));
        pnInformation.add(lbBOJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 50, 120, 20));

        lbBOJValue.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbBOJValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbBOJValue.setText("-");
        lbBOJValue.setPreferredSize(new java.awt.Dimension(20, 15));
        pnInformation.add(lbBOJValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 50, 170, 20));

        lbStaffValue.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lbStaffValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbStaffValue.setText("-");
        pnInformation.add(lbStaffValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 20, 280, -1));

        lbTimeValue.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lbTimeValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTimeValue.setText("07:50:50");
        pnInformation.add(lbTimeValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 40, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo_thai_honda.jpg"))); // NOI18N
        pnInformation.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, 170, 100));

        lbDateValue.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbDateValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbDateValue.setText("29/04/2552");
        pnInformation.add(lbDateValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 10, 160, -1));

        add(pnInformation, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbBOJ;
    private javax.swing.JLabel lbBOJValue;
    private javax.swing.JLabel lbDateValue;
    private javax.swing.JLabel lbStaff;
    private javax.swing.JLabel lbStaffValue;
    private javax.swing.JLabel lbTimeValue;
    private javax.swing.JPanel pnInformation;
    // End of variables declaration//GEN-END:variables
}
