/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * AddNewOwnerPanel.java
 *
 * Created on 9 มิ.ย. 2556, 18:40:06
 */
package co.th.aten.hospital.ui.form;

import co.th.aten.hospital.model.OwnerModel;
import co.th.aten.hospital.model.TreatmentHistoryModel;
import co.th.aten.hospital.service.OwnerManager;
import co.th.aten.hospital.service.SessionManager;
import co.th.aten.hospital.service.TreatmentHistoryManager;
import co.th.aten.hospital.ui.ProcessTransactionDialog;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.springframework.richclient.application.Application;

/**
 *
 * @author Atenpunk
 */
public class TreatmentHistoryPanel extends javax.swing.JPanel {

//    private final Log logger = LogFactory.getLog(getClass());
    private OwnerManager ownerManager;
    private List<OwnerModel> ownerList;
    private OwnerModel modelSelected;
    private SessionManager sessionManager;
    private TreatmentHistoryManager treatmentHistoryManager;
    private int indexHistory;
    private SimpleDateFormat sdfDate;

    /** Creates new form EditOwnerPanel */
    public TreatmentHistoryPanel() {
        sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        this.ownerManager = (OwnerManager) Application.services().getService(OwnerManager.class);
        this.sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
        this.treatmentHistoryManager = (TreatmentHistoryManager) Application.services().getService(TreatmentHistoryManager.class);
        initComponents();

        historyTextArea.setFont(new Font("Tahoma", 0, 11));
        searchText.setFont(new Font("Tahoma", 0, 11));
        historyTable.setFont(new Font("Tahoma", 0, 11));
        
        searchText.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchByKeyWord();
                }
            }
        });

        searchTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    clearData();
                    indexHistory = 0;
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    if (ownerList != null && ownerList.size() >= row) {
                        modelSelected = ownerList.get(row);
                        namePet.setText(modelSelected.getPetModel().getName());
                        typePet.setText(modelSelected.getPetModel().getType());
                        breedPet.setText(modelSelected.getPetModel().getBreed());
                        sexPet.setText(modelSelected.getPetModel().getSex());
                        colorPet.setText(modelSelected.getPetModel().getColor());
                        try {
                            if (modelSelected.getPetModel().getImage() != null) {
                                InputStream in = new ByteArrayInputStream(modelSelected.getPetModel().getImage());
                                BufferedImage originalImage = ImageIO.read(in);
                                int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                                BufferedImage resizeImageJpg = resizeImage(originalImage, type);
                                ImageIcon imgPet = new ImageIcon(resizeImageJpg);
                                imgLabel.setText("");
                                imgLabel.setIcon(imgPet);
                            } else {
                                imgLabel.setText("NO IMAGE");
                                imgLabel.setIcon(null);
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        DefaultTableModel modelTable = (DefaultTableModel) historyTable.getModel();
                        while (modelTable.getRowCount() > 0) {
                            modelTable.removeRow(0);
                        }
                        List<TreatmentHistoryModel> historyList = treatmentHistoryManager.getHistoryListByPetId(modelSelected.getPetModel().getId());
                        if (historyList != null) {
                            for (TreatmentHistoryModel history : historyList) {
                                Object[] rowHis = {++indexHistory, sdfDate.format(history.getCreateDate()), history.getCreateBy(), history.getDetail()};
                                modelTable.addRow(rowHis);
                            }
                        }
                    }
                }
            }
        });
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        imgLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        namePet = new javax.swing.JLabel();
        typePet = new javax.swing.JLabel();
        breedPet = new javax.swing.JLabel();
        sexPet = new javax.swing.JLabel();
        colorPet = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        searchText = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        searchTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        historyTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        historyTextArea = new javax.swing.JTextArea();
        jButton1 = new javax.swing.JButton();

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pet", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        imgLabel.setFont(new java.awt.Font("Tahoma", 1, 18));
        imgLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgLabel.setText("NO IMAGE");
        imgLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Name");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Type");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Breed");

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Sex");

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Color");

        namePet.setFont(new java.awt.Font("Tahoma", 0, 12));
        namePet.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        namePet.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        typePet.setFont(new java.awt.Font("Tahoma", 0, 12));
        typePet.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        typePet.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        breedPet.setFont(new java.awt.Font("Tahoma", 0, 12));
        breedPet.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        breedPet.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        sexPet.setFont(new java.awt.Font("Tahoma", 0, 12));
        sexPet.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        sexPet.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        colorPet.setFont(new java.awt.Font("Tahoma", 0, 12));
        colorPet.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        colorPet.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(namePet, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(typePet, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(breedPet, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(sexPet, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(colorPet, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(namePet, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(typePet, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(breedPet, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(sexPet, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(imgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(colorPet, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(90, Short.MAX_VALUE))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        searchText.setToolTipText("Search by owner name, phone number,email, pet name, type, breed");
        searchText.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchTextActionPerformed(evt);
            }
        });

        searchButton.setFont(new java.awt.Font("Tahoma", 0, 12));
        searchButton.setText("Search");
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        searchTable.setFont(new java.awt.Font("Tahoma", 0, 12));
        searchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Owner Name", "Pet Name", "Type", "Breed"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(searchTable);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Treatment History", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

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

        historyTextArea.setColumns(20);
        historyTextArea.setRows(5);
        historyTextArea.setToolTipText("Fill Treatment");
        jScrollPane3.setViewportView(historyTextArea);

        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 674, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(163, 163, 163))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void clearData() {
        sexPet.setText("");
        namePet.setText("");
        typePet.setText("");
        breedPet.setText("");
        colorPet.setText("");
        historyTextArea.setText("");
        imgLabel.setText("NO IMAGE");
        imgLabel.setIcon(null);
        DefaultTableModel modelTable = (DefaultTableModel) historyTable.getModel();
        while (modelTable.getRowCount() > 0) {
            modelTable.removeRow(0);
        }
    }

    private void clearFillHistory() {
        historyTextArea.setText("");
    }

    private void searchTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        searchByKeyWord();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int saveConfirm = JOptionPane.showConfirmDialog(null, "Confirm save treatment history?", "Confirm Save", JOptionPane.OK_OPTION | JOptionPane.CANCEL_OPTION);
        if (saveConfirm == JOptionPane.OK_OPTION) {
            if (historyTextArea != null && !historyTextArea.getText().trim().equals("")) {
                TreatmentHistoryModel historyModel = new TreatmentHistoryModel();
                historyModel.setHistoryId(treatmentHistoryManager.getMaxHistoryId() + 1);
                historyModel.setPetId(modelSelected.getPetModel().getId());
                historyModel.setDetail(historyTextArea.getText());
                historyModel.setCreateBy(sessionManager.getUser().getUserName());
                historyModel.setCreateDate(new Date());
                historyModel.setUpdateBy(sessionManager.getUser().getUserName());
                historyModel.setUpdateDate(new Date());
                if (treatmentHistoryManager.insertHistory(historyModel)) {
                    DefaultTableModel modelTable = (DefaultTableModel) historyTable.getModel();
                    Object[] rowHis = {++indexHistory, sdfDate.format(historyModel.getCreateDate()), historyModel.getCreateBy(), historyModel.getDetail()};
                    modelTable.addRow(rowHis);
                    JOptionPane.showMessageDialog(this, "Save treatment history complete");
                } else {
                    JOptionPane.showMessageDialog(this, "Save treatment history fail, Please contact admin");
                }
                clearFillHistory();
            } else {
                JOptionPane.showMessageDialog(this, "Please fill treatment history");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void searchByKeyWord() {
        if (searchText.getText() != null && searchText.getText().trim().length() > 0) {
            modelSelected = null;
            Runnable r = new Runnable() {

                public void run() {
                    ownerList = ownerManager.searchByKeyWord(searchText.getText());
                    DefaultTableModel modelTable = (DefaultTableModel) searchTable.getModel();
                    while (modelTable.getRowCount() > 0) {
                        modelTable.removeRow(0);
                    }
                    if (ownerList != null) {
                        for (OwnerModel model : ownerList) {
                            Object[] row = {model.getName(), model.getPetModel().getName(), model.getPetModel().getType(), model.getPetModel().getBreed()};
                            modelTable.addRow(row);
                        }
                    }
                }
            };
            new ProcessTransactionDialog(new JFrame(), true, r, "ระบบกำลังทำงานกรุณารอสักครู่...");
        }
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(146, 134, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 146, 134, null);
        g.dispose();
        return resizedImage;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel breedPet;
    private javax.swing.JLabel colorPet;
    private javax.swing.JTable historyTable;
    private javax.swing.JTextArea historyTextArea;
    private javax.swing.JLabel imgLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel namePet;
    private javax.swing.JButton searchButton;
    private javax.swing.JTable searchTable;
    private javax.swing.JTextField searchText;
    private javax.swing.JLabel sexPet;
    private javax.swing.JLabel typePet;
    // End of variables declaration//GEN-END:variables
}
