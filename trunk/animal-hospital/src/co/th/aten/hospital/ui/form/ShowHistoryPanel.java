/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ShowHistoryPanel.java
 *
 * Created on 21 มิ.ย. 2556, 0:23:58
 */
package co.th.aten.hospital.ui.form;

import co.th.aten.hospital.model.TreatmentHistoryModel;
import co.th.aten.hospital.service.SessionManager;
import co.th.aten.hospital.service.TreatmentHistoryManager;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.springframework.richclient.application.Application;

/**
 *
 * @author Atenpunk
 */
public class ShowHistoryPanel extends javax.swing.JPanel {

    private TreatmentHistoryManager treatmentHistoryManager;
    private SessionManager sessionManager;
    private SimpleDateFormat sdfDate;

    /** Creates new form ShowHistoryPanel */
    public ShowHistoryPanel() {
        sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        this.treatmentHistoryManager = (TreatmentHistoryManager) Application.services().getService(TreatmentHistoryManager.class);
        this.sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
        initComponents();

        petNameLabel.setFont(new Font("Tahoma", 0, 12));
        petTypeLabel.setFont(new Font("Tahoma", 0, 12));
        petTimeLabel.setFont(new Font("Tahoma", 0, 12));
        saveNameLabel.setFont(new Font("Tahoma", 0, 12));
        treatmentLabel.setFont(new Font("Tahoma", 0, 12));

        DefaultTableModel modelTable = (DefaultTableModel) historyTable.getModel();
        while (modelTable.getRowCount() > 0) {
            modelTable.removeRow(0);
        }
        int indexHistory = 0;
        List<TreatmentHistoryModel> historyList = treatmentHistoryManager.getHistoryListByPetId(sessionManager.getPetModel().getId());
        if (historyList != null) {
            for (TreatmentHistoryModel history : historyList) {
                Object[] rowHis = {++indexHistory, sdfDate.format(history.getCreateDate()), history.getCreateBy(), history.getDetail()};
                modelTable.addRow(rowHis);
            }
        }

        historyTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    clearData();
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    petNameLabel.setText(sessionManager.getPetModel().getName());
                    petTypeLabel.setText(sessionManager.getPetModel().getType());
                    petTimeLabel.setText(target.getValueAt(row, 1).toString());
                    saveNameLabel.setText(target.getValueAt(row, 2).toString());
                    treatmentLabel.setText(target.getValueAt(row, 3).toString());
                }
            }
        });
    }

    private void clearData() {
        petNameLabel.setText("");
        petTypeLabel.setText("");
        petTimeLabel.setText("");
        saveNameLabel.setText("");
        treatmentLabel.setText("");
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dateTimeLabel = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        petNameLabel = new javax.swing.JLabel();
        petTypeLabel = new javax.swing.JLabel();
        petTimeLabel = new javax.swing.JLabel();
        saveNameLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        treatmentLabel = new javax.swing.JTextArea();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Treatment history", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        historyTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        historyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Index", "Date Time", "Save Name", "Treatment"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(historyTable);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Pet Name");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Pet Type");

        dateTimeLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        dateTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        dateTimeLabel.setText("Date Time");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Save Name");

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Treatment");

        petNameLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        petNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        petNameLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        petTypeLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        petTypeLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        petTypeLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        petTimeLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        petTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        petTimeLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        saveNameLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        saveNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        saveNameLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        treatmentLabel.setColumns(20);
        treatmentLabel.setEditable(false);
        treatmentLabel.setRows(5);
        jScrollPane2.setViewportView(treatmentLabel);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(petNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(petTypeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(dateTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(petTimeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(10, 10, 10)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                            .addComponent(saveNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(petNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(petTypeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(dateTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(petTimeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(saveNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 130, Short.MAX_VALUE))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))))
                .addContainerGap())
        );

        jLabel1.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dateTimeLabel;
    private javax.swing.JTable historyTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel petNameLabel;
    private javax.swing.JLabel petTimeLabel;
    private javax.swing.JLabel petTypeLabel;
    private javax.swing.JLabel saveNameLabel;
    private javax.swing.JTextArea treatmentLabel;
    // End of variables declaration//GEN-END:variables
}