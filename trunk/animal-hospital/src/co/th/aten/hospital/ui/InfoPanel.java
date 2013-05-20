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
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.richclient.application.Application;

/**
 *
 * @author Mai
 */
public class InfoPanel extends javax.swing.JPanel {

    private final Log logger = LogFactory.getLog(getClass());

    /** Creates new form MasterPanel */
    public InfoPanel() {
        initComponents();
//        updateLaneInfo();
//        updateChiefOnDuty();
        clock();
    }

    public void changeStaff() {
        SessionManager sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
        if (Locale.getDefault().equals(Locale.US)) {
//            String staff = session.getStaff().getCssfName() + " " + session.getStaff().getCssfSurname() + " ( " + session.getStaff().getStaffPK().getCssfStaffid() + " )";
//            lbCODValue.setText(staff);
        } else {
//            String staff = session.getStaff().getCssfLocalname() + " " + session.getStaff().getCssfLocalsurname() + " ( " + session.getStaff().getStaffPK().getCssfStaffid() + " )";
//            lbCODValue.setText(staff);
        }
    }

    public void updateJob() {
        SessionManager sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
//        SessionData session = sessionManager.getSession();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        Job job = session.getJob();
//        if (job != null) {
//            if (Locale.getDefault().equals(Locale.US)) {
//                String staff = session.getStaff().getCssfName() + " " + session.getStaff().getCssfSurname() + " ( " + session.getStaff().getStaffPK().getCssfStaffid() + " )";
//                lbStaffValue.setText(staff);
//            } else {
//                String staff = session.getStaff().getCssfLocalname() + " " + session.getStaff().getCssfLocalsurname() + " ( " + session.getStaff().getStaffPK().getCssfStaffid() + " )";
//                lbStaffValue.setText(staff);
//            }
//            lbBOJValue.setText(sdf.format(job.getJobPK().getCsjbBojDatetime()));
//        } else {
//            lbBOJValue.setText("-");
//            lbStaffValue.setText("-");
//        }
    }

    private void clock() {
//        ApplicationWindow aw = Application.instance().getActiveWindow();
//        final ProgressMonitor pm = aw.getStatusBar().getProgressMonitor();
//        pm.setCanceled(true);
//        pm.taskStarted("Timer...", -1);

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
//                return null;
            }
        }.start();
    }

    private void drawText(Graphics2D g2, int size, float opacity) {
        Composite oldComposite = g2.getComposite();
        float preAlpha = 1.0f;
        if (oldComposite instanceof AlphaComposite
                && ((AlphaComposite) oldComposite).getRule() == AlphaComposite.SRC_OVER) {
            preAlpha = ((AlphaComposite) oldComposite).getAlpha();
        }

        g2.setFont(getFont());
        FontMetrics metrics = g2.getFontMetrics();
        int ascent = metrics.getAscent();
        int heightDiff = (metrics.getHeight() - ascent) / 2;

        g2.setColor(Color.BLACK);

        double tx = 2.0;
        double ty = 2.0 + heightDiff - size;
        g2.translate(tx, ty);

        for (int i = -size; i <= size; i++) {
            for (int j = -size; j <= size; j++) {
                double distance = i * i + j * j;
                float alpha = opacity;
                if (distance > 0.0d) {
                    alpha = (float) (1.0f / ((distance * size) * opacity));
                }
                alpha *= preAlpha;
                if (alpha > 1.0f) {
                    alpha = 1.0f;
                }
                g2.setComposite(AlphaComposite.SrcOver.derive(alpha));
                g2.drawString("Test", i + size, j + size + ascent);
            }
        }

        g2.setComposite(oldComposite);
        g2.setColor(Color.WHITE);
        g2.drawString("Test", size, size + ascent);

        g2.translate(-tx, -ty);
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

        setMinimumSize(new java.awt.Dimension(800, 80));
        setPreferredSize(new java.awt.Dimension(800, 80));
        setLayout(new java.awt.BorderLayout());

        pnInformation.setBackground(new java.awt.Color(150, 255, 255));
        pnInformation.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        pnInformation.setMinimumSize(new java.awt.Dimension(1024, 130));
        pnInformation.setPreferredSize(new java.awt.Dimension(1024, 130));
        pnInformation.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbStaff.setFont(new java.awt.Font("Dialog", 0, 14));
        lbStaff.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("ui/messages"); // NOI18N
        lbStaff.setText(bundle.getString("info.staff")); // NOI18N
        lbStaff.setMaximumSize(new java.awt.Dimension(50, 17));
        lbStaff.setMinimumSize(new java.awt.Dimension(50, 17));
        pnInformation.add(lbStaff, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 20, 85, -1));

        lbBOJ.setFont(new java.awt.Font("Dialog", 0, 14));
        lbBOJ.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbBOJ.setText(bundle.getString("info.boj")); // NOI18N
        lbBOJ.setPreferredSize(new java.awt.Dimension(25, 15));
        pnInformation.add(lbBOJ, new org.netbeans.lib.awtextra.AbsoluteConstraints(455, 20, 80, 20));

        lbBOJValue.setFont(new java.awt.Font("Dialog", 1, 14));
        lbBOJValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbBOJValue.setText("-");
        lbBOJValue.setPreferredSize(new java.awt.Dimension(20, 15));
        pnInformation.add(lbBOJValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, 197, 20));

        lbStaffValue.setFont(new java.awt.Font("Dialog", 1, 14));
        lbStaffValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbStaffValue.setText("-");
        pnInformation.add(lbStaffValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 20, 260, -1));

        lbTimeValue.setFont(new java.awt.Font("Tahoma", 1, 36));
        lbTimeValue.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lbTimeValue.setText("07:50:50");
        pnInformation.add(lbTimeValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 30, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo.png"))); // NOI18N
        pnInformation.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, -1));

        lbDateValue.setFont(new java.awt.Font("Tahoma", 1, 24));
        lbDateValue.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbDateValue.setText("29/04/2552");
        pnInformation.add(lbDateValue, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 0, 160, -1));

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
