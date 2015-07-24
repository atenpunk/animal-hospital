/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 *
 * Created on 9 มิ.ย. 2556, 18:40:06
 */
package co.th.aten.football.ui.form;

import co.th.aten.football.Configuration;
import co.th.aten.football.model.PlayersGraphModel;
import co.th.aten.football.model.PlayersModel;
import co.th.aten.football.model.PositionModel;
import co.th.aten.football.service.PlayersManager;
import co.th.aten.football.service.PositionManager;
import co.th.aten.football.service.SessionManager;
import co.th.aten.football.service.YearlyManager;
import co.th.aten.football.ui.report.PlayersGraphReportDialog;
import co.th.aten.football.ui.report.ViewReportPieDlg;
import co.th.aten.football.ui.report.ViewReportTestRadarDlg;
import co.th.aten.football.util.Sound;
import co.th.aten.football.util.Util;
import com.jensoft.sw2d.core.view.View2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
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
public class ReportPlayersDetailPanel extends javax.swing.JPanel {

    private final Log logger = LogFactory.getLog(getClass());
    private SessionManager sessionManager;
    private PlayersManager playersManager;
    private YearlyManager yearlyManager;
    private List<PlayersModel> playersModelList;
    private List<PlayersModel> selectedPlayersModelList;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private DecimalFormat df = new DecimalFormat("#,##0");
    private File fileImg;
    private HashMap<String, Integer> mapPosition;
    private HashMap<Integer, Integer> mapIndexPosition;
    private PlayersModel playersModelSelected;
    private int row = -1;
    private PositionManager positionManager;

    public ReportPlayersDetailPanel() {
        this.sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
        this.playersManager = (PlayersManager) Application.services().getService(PlayersManager.class);
        this.positionManager = (PositionManager) Application.services().getService(PositionManager.class);
        this.yearlyManager = (YearlyManager) Application.services().getService(YearlyManager.class);
        initComponents();
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        jScrollPane3.getViewport().setBackground(Color.WHITE);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        searchTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        searchTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        searchTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        searchTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        searchTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        searchTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        searchTable.getColumnModel().getColumn(3).setPreferredWidth(50);

        reportTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        reportTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        reportTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        reportTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        reportTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        reportTable.getColumnModel().getColumn(2).setPreferredWidth(60);
        reportTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        try {
            File fileImgSearch = new File(System.getProperty("user.dir") + "/img" + File.separator + "search.png");
            if (fileImgSearch != null) {
                BufferedImage originalImage = ImageIO.read(fileImgSearch);
                int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                BufferedImage resizeImageJpg = resizeImage(originalImage, type, 30, 25);
                ImageIcon img = new ImageIcon(resizeImageJpg);
                searchButton.setText("");
                searchButton.setIcon(img);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        List<Integer> yearIdList = yearlyManager.getYearlyIdList();
        if (yearIdList != null && yearIdList.size() > 0) {
            seasonComboBox.addItem(null);
            for (Integer yearId : yearIdList) {
                seasonComboBox.addItem(yearId);
            }
            seasonComboBox.setSelectedIndex(1);
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

//        searchTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            public void valueChanged(ListSelectionEvent event) {
//                row = searchTable.getSelectedRow();
//                setDataDetailPlayer();
//            }
//        });

        searchTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    Sound.getInstance().playClick();
                    leftToRight(searchTable, reportTable);
                }
            }
        });

        reportTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    Sound.getInstance().playClick();
                    rightToLeft(searchTable, reportTable);
                }
            }
        });

        List<PositionModel> positionList = positionManager.getPositionList();
        if (positionList != null) {
            mapPosition = new HashMap<String, Integer>();
            mapIndexPosition = new HashMap<Integer, Integer>();
            int index = 0;
            for (PositionModel model : positionList) {
                mapPosition.put(model.getEngName(), model.getId());
                mapIndexPosition.put(model.getId(), index++);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        searchText = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        searchTable = new javax.swing.JTable();
        reportButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        seasonComboBox = new javax.swing.JComboBox();

        setBackground(new java.awt.Color(255, 255, 255));

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

        reportTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        reportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Season", "Play"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        reportTable.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(reportTable);

        jButton3.setText(">");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText(">>");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("<");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("<<");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        searchTable.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        searchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "#", "Name", "Season", "Play"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        searchTable.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane3.setViewportView(searchTable);

        reportButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        reportButton.setText("Report");
        reportButton.setToolTipText("Search");
        reportButton.setBorder(null);
        reportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Season");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(searchText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(seasonComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(seasonComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reportButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(69, 69, 69)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addContainerGap(168, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void clearData() {
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        searchByKeyWord();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Sound.getInstance().playClick();
        leftToRight(searchTable, reportTable);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        Sound.getInstance().playClick();
        leftToRightAll(searchTable, reportTable);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        Sound.getInstance().playClick();
        rightToLeft(searchTable, reportTable);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        Sound.getInstance().playClick();
        rightToLeftAll(searchTable, reportTable);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void reportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportButtonActionPerformed
        // TODO add your handling code here:
        Sound.getInstance().playClick();
        List<PlayersGraphModel> playersGraphModel = null;
        
        if (selectedPlayersModelList != null && selectedPlayersModelList.size() > 0) {
            playersGraphModel = new ArrayList<PlayersGraphModel>();
            for (int i = 0; i < selectedPlayersModelList.size(); i++) {
                PlayersModel model = selectedPlayersModelList.get(i);
                PlayersGraphModel modelGraph = new PlayersGraphModel();
                modelGraph.setGroup((i + 1));
                modelGraph.setPlayerId(model.getPlayerId());
                String nameNumber = "#" + model.getPlayerNumber() + " " + model.getPlayerName();
                String brid = "";
                String HeWe = model.getHeight() + "cm/" + model.getWeight() + "kg";
                String contractDate = "";
                if (model.getBirthday() != null) {
                    brid = sdf.format(model.getBirthday());
                    Date startDate = model.getBirthday();
                    Date endDate = new Date();
                    String year = Util.getYear(startDate, endDate);
                    brid += " (" + year + ")";
                }
                if (model.getContractStart() != null && model.getContractEnd() != null) {
                    contractDate = sdf.format(model.getContractStart()) + " - " + sdf.format(model.getContractEnd());
                }
                modelGraph.setPlayerNameNumber(nameNumber);
                modelGraph.setHeightWeight(HeWe);
                modelGraph.setBirthdayAge(brid);
                modelGraph.setContractStartEnd(contractDate);
                modelGraph.setPositionId(model.getPositionId());
                modelGraph.setPositionStr(model.getPositionStr());
                modelGraph.setImage(model.getImage());
                modelGraph.setGc(model.getGc());
                modelGraph.setAnnualSalary(model.getAnnualSalary());
                modelGraph.setSigningFee(model.getSigningFee());
                modelGraph.setSalaryMonth(model.getSalaryMonth());
                modelGraph.setGoal(model.getGoal());
                modelGraph.setPlayingTime(model.getPlayingTime());
                modelGraph.setMatch(model.getMatch());
                modelGraph.setWin(model.getWin());
                modelGraph.setLose(model.getLose());
                modelGraph.setDraw(model.getDraw());
                modelGraph.setStarter(model.getStarter());
                modelGraph.setSeason("Season "+model.getYearlyId());
                modelGraph.setCleanSheet(model.getCleanSheet());
                modelGraph.setSubstitution(model.getSubstitution());
                modelGraph.setClub(model.getClub());
                try {
                    ViewReportTestRadarDlg reportRadar = new ViewReportTestRadarDlg(model.getGc(), model.getMatch(), model.getPlayingTime(), 1);
                    View2D view = reportRadar.createView2D();
                    view.setSize(409, 262);
                    int w = (int) view.getBounds().getWidth();
                    int h = (int) view.getBounds().getHeight();
                    BufferedImage image = view.getImageView(w, h);
                    modelGraph.setInputRadar(image);
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
                try {
                    int data01 = ((100 * model.getMatch()) / Configuration.getInt("Match"));
                    int data02 = 100 - data01;
                    ViewReportPieDlg reportPieMatch = new ViewReportPieDlg(data02, data01);
                    View2D view = reportPieMatch.createView2D();
                    view.setSize(136, 136);
                    int w = (int) view.getBounds().getWidth();
                    int h = (int) view.getBounds().getHeight();
                    BufferedImage image = view.getImageView(w, h);
                    modelGraph.setInputPieMatch(image);
                    modelGraph.setPerMatch(data01 + "%");
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
                try {
                    int data01 = ((100 * model.getPlayingTime()) / Configuration.getInt("PlayingTime"));
                    int data02 = 100 - data01;
                    ViewReportPieDlg reportPieTime = new ViewReportPieDlg(data02, data01);
                    View2D view = reportPieTime.createView2D();
                    view.setSize(136, 136);
                    int w = (int) view.getBounds().getWidth();
                    int h = (int) view.getBounds().getHeight();
                    BufferedImage image = view.getImageView(w, h);
                    modelGraph.setInputPiePlayingTime(image);
                    modelGraph.setPerPlayingTime(data01 + "%");
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
                try {
                    int data01 = ((100 * model.getStarter()) / Configuration.getInt("Match"));
                    int data02 = 100 - data01;
                    ViewReportPieDlg reportPieStart = new ViewReportPieDlg(data02, data01);
                    View2D view = reportPieStart.createView2D();
                    view.setSize(136, 136);
                    int w = (int) view.getBounds().getWidth();
                    int h = (int) view.getBounds().getHeight();
                    BufferedImage image = view.getImageView(w, h);
                    modelGraph.setInputPieStarter(image);
                    modelGraph.setPerStarter(data01 + "%");
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
                if (modelGraph.getImage() != null) {
                    modelGraph.setImgPlayer(System.getProperty("user.dir") + "/img" + File.separator + modelGraph.getImage());
                }
                playersGraphModel.add(modelGraph);
            }
            new PlayersGraphReportDialog(playersGraphModel).showDialog();
        }else{
            JOptionPane.showMessageDialog(this, "Please choose players");
        }
        
    }//GEN-LAST:event_reportButtonActionPerformed

    private void leftToRight(JTable tableLeft, JTable tableRight) {
        if (selectedPlayersModelList == null) {
            selectedPlayersModelList = new ArrayList<PlayersModel>();
        }
        int[] indexArr = tableLeft.getSelectedRows();
        System.out.println(indexArr);
        if (indexArr != null) {
            for (int i = indexArr.length - 1; i >= 0; i--) {
                int index = indexArr[i];
                if (tableLeft != null && tableLeft.getRowCount() > 0 && index >= 0 && index <= tableLeft.getRowCount() - 1) {
                    DefaultTableModel model = (DefaultTableModel) tableLeft.getModel();
                    DefaultTableModel model2 = (DefaultTableModel) tableRight.getModel();
                    Vector rowData = model.getDataVector();
                    Vector tmp = (Vector) rowData.get(index);
                    rowData.remove(tmp);
                    model2.addRow(tmp);
                    PlayersModel playersModel = playersModelList.get(index);
                    selectedPlayersModelList.add(playersModel);
                    playersModelList.remove(playersModel);
                }
                tableLeft.updateUI();
                tableRight.updateUI();
            }
        }
    }

    private void rightToLeft(JTable tableLeft, JTable tableRight) {
        if (selectedPlayersModelList == null) {
            selectedPlayersModelList = new ArrayList<PlayersModel>();
        }
        int[] indexArr = tableRight.getSelectedRows();
        System.out.println(indexArr.toString());
        if (indexArr != null) {
            for (int i = indexArr.length - 1; i >= 0; i--) {
                int index = indexArr[i];
                if (tableRight != null && tableRight.getRowCount() > 0 && index >= 0 && index <= tableRight.getRowCount() - 1) {
                    DefaultTableModel model = (DefaultTableModel) tableLeft.getModel();
                    DefaultTableModel model2 = (DefaultTableModel) tableRight.getModel();
                    Vector rowData = model2.getDataVector();
                    Vector tmp = (Vector) rowData.get(index);
                    model.addRow(tmp);
                    rowData.remove(tmp);
                    PlayersModel playersModel = selectedPlayersModelList.get(index);
                    playersModelList.add(playersModel);
                    selectedPlayersModelList.remove(playersModel);
                }
                tableLeft.updateUI();
                tableRight.updateUI();
            }
        }
    }

    private void leftToRightAll(JTable tableLeft, JTable tableRight) {
        if (selectedPlayersModelList == null) {
            selectedPlayersModelList = new ArrayList<PlayersModel>();
        }
        if (tableLeft != null && tableLeft.getRowCount() > 0) {
            DefaultTableModel model = (DefaultTableModel) tableLeft.getModel();
            DefaultTableModel model2 = (DefaultTableModel) tableRight.getModel();
            Vector rowData = model.getDataVector();
            Vector tmp;
            while (rowData.iterator().hasNext()) {
                System.out.println(rowData.iterator().next().toString());
                tmp = (Vector) rowData.iterator().next();
                model2.addRow(tmp);
                rowData.remove(rowData.iterator().next());
                PlayersModel playersModel = playersModelList.get(0);
                selectedPlayersModelList.add(playersModel);
                playersModelList.remove(playersModel);
            }
            tableLeft.updateUI();
        }
    }

    private void rightToLeftAll(JTable tableLeft, JTable tableRight) {
        if (selectedPlayersModelList == null) {
            selectedPlayersModelList = new ArrayList<PlayersModel>();
        }
        if (tableRight != null && tableRight.getRowCount() > 0) {
            DefaultTableModel model = (DefaultTableModel) tableLeft.getModel();
            DefaultTableModel model2 = (DefaultTableModel) tableRight.getModel();
            Vector rowData = model2.getDataVector();
            Vector tmp;
            while (rowData.iterator().hasNext()) {
                System.out.println(rowData.iterator().next().toString());
                tmp = (Vector) rowData.iterator().next();
                model.addRow(tmp);
                rowData.remove(rowData.iterator().next());
                PlayersModel playersModel = selectedPlayersModelList.get(0);
                playersModelList.add(playersModel);
                selectedPlayersModelList.remove(playersModel);
            }
            tableRight.updateUI();
        }
    }

    private void searchByKeyWord() {
//        Runnable r = new Runnable() {
//            public void run() {
        clearData();
        playersModelList = playersManager.searchAllDataByKeyWord(searchText.getText(),(Integer)seasonComboBox.getSelectedItem());
        DefaultTableModel modelTable = (DefaultTableModel) searchTable.getModel();
        while (modelTable.getRowCount() > 0) {
            modelTable.removeRow(0);
        }
        if (playersModelList != null && playersModelList.size() > 0) {
            for (PlayersModel model : playersModelList) {
                Object[] rowTable = {model.getPlayerNumber(), model.getPlayerName(), model.getYearlyId(), model.getPositionStr()};
                modelTable.addRow(rowTable);
            }
            Sound.getInstance().playNotify();
        } else {
            Sound.getInstance().playDing();
        }
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
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JButton reportButton;
    private javax.swing.JTable reportTable;
    private javax.swing.JButton searchButton;
    private javax.swing.JTable searchTable;
    private javax.swing.JTextField searchText;
    private javax.swing.JComboBox seasonComboBox;
    // End of variables declaration//GEN-END:variables
}
