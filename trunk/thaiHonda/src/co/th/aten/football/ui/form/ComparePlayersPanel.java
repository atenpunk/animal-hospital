/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 *
 * Created on 9 มิ.ย. 2556, 18:40:06
 */
package co.th.aten.football.ui.form;

import co.th.aten.football.model.PlayersModel;
import co.th.aten.football.service.PlayersManager;
import co.th.aten.football.service.SessionManager;
import co.th.aten.football.ui.report.ComparePlayersReportDialog;
import co.th.aten.football.ui.report.ViewReportComparePlayersRadarDlg;
import co.th.aten.football.util.Sound;
import com.jensoft.sw2d.core.view.View2D;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import org.springframework.richclient.application.Application;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Atenpunk
 */
public class ComparePlayersPanel extends javax.swing.JPanel {

    private final Log logger = LogFactory.getLog(getClass());
    private SessionManager sessionManager;
    private PlayersManager playersManager;
    private List<PlayersModel> playersModelList;
    private List<PlayersModel> comparePlayersModelList;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private DecimalFormat df = new DecimalFormat("#,##0");
    private int row = -1;
    private View2D viewRadar;

    public ComparePlayersPanel() {
        this.sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
        this.playersManager = (PlayersManager) Application.services().getService(PlayersManager.class);
        initComponents();
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        jScrollPane3.getViewport().setBackground(Color.WHITE);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        searchTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        searchTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        searchTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        searchTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        searchTable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        searchTable.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        searchTable.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        searchTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        compareTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        compareTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        compareTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        compareTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        compareTable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        compareTable.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        compareTable.getColumnModel().getColumn(7).setCellRenderer(rightRenderer);
        compareTable.getColumnModel().getColumn(0).setPreferredWidth(120);
        try {
            File fileImg = new File(System.getProperty("user.dir") + "/img" + File.separator + "search.png");
            if (fileImg != null) {
                BufferedImage originalImage = ImageIO.read(fileImg);
                int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                BufferedImage resizeImageJpg = resizeImage(originalImage, type, 30, 25);
                ImageIcon img = new ImageIcon(resizeImageJpg);
                searchButton.setText("");
                searchButton.setIcon(img);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        searchText.requestFocus();
        searchText.requestFocusInWindow();
        searchText.setFont(new Font("Tahoma", 0, 11));
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
                if (e.getClickCount() >= 2) {
                    if (comparePlayersModelList == null) {
                        comparePlayersModelList = new ArrayList<PlayersModel>();
                    }
                    Sound.getInstance().playClick();
                    if (comparePlayersModelList.size() < 5) {
                        JTable target = (JTable) e.getSource();
                        row = target.getSelectedRow();
                        DefaultTableModel modelTable = (DefaultTableModel) searchTable.getModel();
                        if (modelTable.getRowCount() > row) {
                            modelTable.removeRow(row);
                        }
                        DefaultTableModel modelCompareTable = (DefaultTableModel) compareTable.getModel();
                        if (playersModelList != null && playersModelList.size() > row) {
                            PlayersModel playersModel = playersModelList.get(row);
                            if (!equalsPlayersModel(comparePlayersModelList, playersModel)) {
                                Object[] rowTable = {"#" + playersModel.getPlayerNumber() + " " + playersModel.getPlayerName(),playersModel.getYearlyId(), df.format(playersModel.getGc()), df.format(playersModel.getAnnualSalary()), df.format(playersModel.getSigningFee()), df.format(playersModel.getSalaryMonth()), df.format(playersModel.getPlayingTime()), playersModel.getMatch()};
                                modelCompareTable.addRow(rowTable);
                                comparePlayersModelList.add(playersModel);
                                playersModelList.remove(playersModel);
                            } else {
                                playersModelList.remove(playersModel);
                            }
                        }
                        setDataDetailPlayer();
                        logger.info(" search playersModelList.size()" + playersModelList.size());
                    } else {
                        showDialog();
                    }
                }
            }
        });

        compareTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    Sound.getInstance().playClick();
                    JTable target = (JTable) e.getSource();
                    int rowCom = target.getSelectedRow();
                    DefaultTableModel modelTable = (DefaultTableModel) compareTable.getModel();
                    if (modelTable.getRowCount() > rowCom) {
                        modelTable.removeRow(rowCom);
                    }
                    DefaultTableModel modelSearchTable = (DefaultTableModel) searchTable.getModel();
                    if (comparePlayersModelList != null && comparePlayersModelList.size() > rowCom) {
                        PlayersModel playersModel = comparePlayersModelList.get(rowCom);
                        if (!equalsPlayersModel(playersModelList, playersModel)) {
                            Object[] rowTable = {"#" + playersModel.getPlayerNumber() + " " + playersModel.getPlayerName(),playersModel.getYearlyId(), df.format(playersModel.getGc()), df.format(playersModel.getAnnualSalary()), df.format(playersModel.getSigningFee()), df.format(playersModel.getSalaryMonth()), df.format(playersModel.getPlayingTime()), playersModel.getMatch()};
                            modelSearchTable.addRow(rowTable);
                            playersModelList.add(playersModel);
                            comparePlayersModelList.remove(playersModel);
                        } else {
                            comparePlayersModelList.remove(playersModel);
                        }
                    }
                    setDataDetailPlayer();

                    logger.info(" compare playersModelList.size()" + playersModelList.size());
                }
            }
        });
    }

    private boolean equalsPlayersModel(List<PlayersModel> modelList, PlayersModel model) {
        if (modelList != null && model != null) {
            for (PlayersModel m : modelList) {
                if (m.getPlayerId() == model.getPlayerId() && m.getYearlyId()==model.getYearlyId()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void showDialog() {
        JOptionPane.showMessageDialog(this, "Compare max 5 players");
    }

    private void setDataDetailPlayer() {
        clearData();
        redarPanal.setVisible(true);
        ViewReportComparePlayersRadarDlg reportRadar = new ViewReportComparePlayersRadarDlg(comparePlayersModelList, 1);
        viewRadar = reportRadar.createView2D();
        viewRadar.setSize(853, 262);
        redarPanal.setBackground(Color.WHITE);
        redarPanal.add(viewRadar, BorderLayout.CENTER);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        redarPanal = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        compareTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        searchText = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        searchTable = new javax.swing.JTable();
        report1Button = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Compare Players", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        redarPanal.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout redarPanalLayout = new javax.swing.GroupLayout(redarPanal);
        redarPanal.setLayout(redarPanalLayout);
        redarPanalLayout.setHorizontalGroup(
            redarPanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 853, Short.MAX_VALUE)
        );
        redarPanalLayout.setVerticalGroup(
            redarPanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );

        compareTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        compareTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Season", "GC", "Salary", "Signing Fee", "Monthly Salary", "Playing Time", "Matches"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        compareTable.setAutoscrolls(false);
        compareTable.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane3.setViewportView(compareTable);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3)
                    .addComponent(redarPanal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(redarPanal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Search", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        searchText.setToolTipText("Search by player name, number");

        searchButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        searchButton.setToolTipText("Search");
        searchButton.setBorder(null);
        searchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchButtonActionPerformed(evt);
            }
        });

        searchTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        searchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Season", "GC", "Salary", "Signing Fee", "Monthly Salary", "Playing Time", "Matches"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        searchTable.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(searchTable);

        report1Button.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        report1Button.setText("Report");
        report1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                report1ButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(report1Button)
                        .addGap(0, 109, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchText, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(report1Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.getAccessibleContext().setAccessibleName("Player");
    }// </editor-fold>//GEN-END:initComponents

    private void clearData() {
        redarPanal.setVisible(false);
        if (viewRadar != null) {
            redarPanal.remove(viewRadar);
        }
        redarPanal.setVisible(true);
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        searchByKeyWord();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void report1ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_report1ButtonActionPerformed
        // TODO add your handling code here:
        Sound.getInstance().playClick();
        Image imageRadar = null;;
        if (viewRadar != null) {
            viewRadar.setSize(853, 262);
            int w = (int) viewRadar.getBounds().getWidth();
            int h = (int) viewRadar.getBounds().getHeight();
            BufferedImage image = viewRadar.getImageView(w, h);
            imageRadar = image;
        }
        new ComparePlayersReportDialog(comparePlayersModelList,imageRadar).showDialog();
    }//GEN-LAST:event_report1ButtonActionPerformed

    private void searchByKeyWord() {
//        Runnable r = new Runnable() {
//            public void run() {
        clearData();
        playersModelList = playersManager.searchAllDataByKeyWord(searchText.getText());
        DefaultTableModel modelTable = (DefaultTableModel) searchTable.getModel();
        while (modelTable.getRowCount() > 0) {
            modelTable.removeRow(0);
        }
        if (playersModelList != null && playersModelList.size() > 0) {
            for (PlayersModel model : playersModelList) {
                Object[] rowTable = {"#" + model.getPlayerNumber() + " " + model.getPlayerName(),model.getYearlyId(), df.format(model.getGc()), df.format(model.getAnnualSalary()), df.format(model.getSigningFee()), df.format(model.getSalaryMonth()), df.format(model.getPlayingTime()), model.getMatch()};
                modelTable.addRow(rowTable);
            }
            Sound.getInstance().playNotify();
        } else {
            Sound.getInstance().playDing();
        }
        setDataDetailPlayer();
//            }
//        };
//        new ProcessTransactionDialog(new JFrame(), true, r, "Please wait the system is running...");
    }

    private static BufferedImage resizeImage(BufferedImage originalImage, int type, int width, int height) {
        BufferedImage resizedImage = new BufferedImage(width, height, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, width, height, null);
        g.dispose();
        return resizedImage;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable compareTable;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel redarPanal;
    private javax.swing.JButton report1Button;
    private javax.swing.JButton searchButton;
    private javax.swing.JTable searchTable;
    private javax.swing.JTextField searchText;
    // End of variables declaration//GEN-END:variables
}
