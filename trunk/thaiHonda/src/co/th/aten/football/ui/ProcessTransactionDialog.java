/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * PleaseWaitDialog.java
 *
 * Created on 9 ก.ค. 2552, 16:09:43
 */
package co.th.aten.football.ui;

import javax.swing.JDialog;
import org.springframework.richclient.application.Application;
import org.springframework.richclient.application.ApplicationWindow;
import org.springframework.richclient.progress.BusyIndicator;
import org.springframework.richclient.progress.ProgressMonitor;

/**
 *
 * @author Aten
 */
public class ProcessTransactionDialog extends javax.swing.JDialog implements Runnable {

    private Thread t;
    private Runnable r;
    private boolean done;

    /** Creates new form PleaseWaitDialog */
    public ProcessTransactionDialog(java.awt.Frame parent, boolean modal, Runnable r,String msg) {
        super(parent, modal);
        this.setUndecorated(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.r = r;
        initComponents();
        jProgressBar1.setIndeterminate(true);
        String build = Application.instance().getDescriptor().getVersion() + " ( " + Application.instance().getDescriptor().getBuildId() + " )";
        jLabel3.setText("Animal Hospital v."+build);
        jLabel1.setText(msg);
        t = new Thread(this);
        done = false;
        t.start();

        setLocationRelativeTo(parent);
        setVisible(true);
    }

    @Override
    public void run() {
        Thread processing = new Thread(new Runnable() {

            @Override
            public void run() {
                Thread t = new Thread(r);
                t.start();
                try {
                    t.join();
                } catch (InterruptedException x) {
                    x.printStackTrace();
                }
                done = true;
            }
        });
        ApplicationWindow aw = Application.instance().getActiveWindow();
        final ProgressMonitor pm = aw.getStatusBar().getProgressMonitor();
        pm.taskStarted("Processing transaction...", -1);
        pm.setCanceled(true);
        BusyIndicator.showAt(aw.getControl());
        processing.start();
        while (!done) {
            
            try {
                Thread.sleep(100);
            } catch (InterruptedException x) {
                x.printStackTrace();
            }
        }

        pm.done();
        BusyIndicator.clearAt(aw.getControl());
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException x) {
//            x.printStackTrace();
//        }
        dispose();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);

        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        jLabel3.setText("...");
        jPanel1.add(jLabel3);

        jPanel4.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Please wait, Processing your transaction");
        jPanel3.add(jLabel1);

        jPanel4.add(jPanel3, java.awt.BorderLayout.CENTER);

        jPanel2.setLayout(new java.awt.BorderLayout());
        jPanel2.add(jProgressBar1, java.awt.BorderLayout.CENTER);

        jPanel4.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        getContentPane().add(jPanel4, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
