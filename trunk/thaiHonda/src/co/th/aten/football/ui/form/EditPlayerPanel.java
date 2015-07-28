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
import co.th.aten.football.model.PositionModel;
import co.th.aten.football.model.VideoModel;
import co.th.aten.football.service.PlayersManager;
import co.th.aten.football.service.PositionManager;
import co.th.aten.football.service.SessionManager;
import co.th.aten.football.service.VideoPlayersManager;
import co.th.aten.football.service.YearlyManager;
import co.th.aten.football.util.Sound;
import co.th.aten.football.util.Util;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
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
public class EditPlayerPanel extends javax.swing.JPanel {

    private final Log logger = LogFactory.getLog(getClass());
    private SessionManager sessionManager;
    private PlayersManager playersManager;
    private YearlyManager yearlyManager;
    private List<PlayersModel> playersModelList;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private DecimalFormat df = new DecimalFormat("#,##0");
    private File fileImg;
    private HashMap<String, Integer> mapPosition;
    private HashMap<Integer, Integer> mapIndexPosition;
    private PlayersModel playersModelSelected;
    private int row = -1;
    private PositionManager positionManager;
    private VideoPlayersManager videoPlayersManager;

    public EditPlayerPanel() {
        this.sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
        this.playersManager = (PlayersManager) Application.services().getService(PlayersManager.class);
        this.positionManager = (PositionManager) Application.services().getService(PositionManager.class);
        this.videoPlayersManager = (VideoPlayersManager) Application.services().getService(VideoPlayersManager.class);
        this.yearlyManager = (YearlyManager) Application.services().getService(YearlyManager.class);
        initComponents();
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        searchTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        searchTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        searchTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        searchTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer);
        searchTable.getColumnModel().getColumn(6).setCellRenderer(centerRenderer);
        searchTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);
        searchTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        searchTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        searchTable.getColumnModel().getColumn(2).setPreferredWidth(30);
        searchTable.getColumnModel().getColumn(3).setPreferredWidth(30);
        searchTable.getColumnModel().getColumn(4).setPreferredWidth(100);
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
                    JTable target = (JTable) e.getSource();
                    row = target.getSelectedRow();
                    setDataDetailPlayer();
                }
            }
        });

        numberText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                        && (character != '\b')) {
                    e.consume();
                }
            }
        });

        heightText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                        && (character != '\b')) {
                    e.consume();
                }
            }
        });

        weightText.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                        && (character != '\b')) {
                    e.consume();
                }
            }
        });

        clubText.setFont(new Font("Tahoma", 0, 11));
        namePlayerText.setFont(new Font("Tahoma", 0, 11));
        positionComboBox.setFont(new Font("Tahoma", 0, 11));
        birthdayPlayer.setFormats(new SimpleDateFormat("dd/MM/yyyy", Locale.US));
        birthdayPlayer.getMonthView().setDayForeground(Calendar.SATURDAY, Color.BLUE);
        birthdayPlayer.getMonthView().setDayForeground(Calendar.SUNDAY, Color.RED);
        birthdayPlayer.setDate(new Date());

        startContract.setFormats(new SimpleDateFormat("dd/MM/yyyy", Locale.US));
        startContract.getMonthView().setDayForeground(Calendar.SATURDAY, Color.BLUE);
        startContract.getMonthView().setDayForeground(Calendar.SUNDAY, Color.RED);
        startContract.setDate(new Date());

        endContract.setFormats(new SimpleDateFormat("dd/MM/yyyy", Locale.US));
        endContract.getMonthView().setDayForeground(Calendar.SATURDAY, Color.BLUE);
        endContract.getMonthView().setDayForeground(Calendar.SUNDAY, Color.RED);
        endContract.setDate(new Date());

        birthdayPlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Date startDate = birthdayPlayer.getDate();
                Date endDate = new Date();
                String agePlayer = Util.getYearMonth(startDate, endDate);
                agePlayerLabel.setText(agePlayer);
            }
        });

        birthdayPlayer.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                Date startDate = birthdayPlayer.getDate();
                Date endDate = new Date();
                String agePlayer = Util.getYearMonth(startDate, endDate);
                agePlayerLabel.setText(agePlayer);
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
                positionComboBox.addItem(model.getEngName());
            }
            positionComboBox.setSelectedIndex(-1);
            positionComboBox.setEditable(true);
        }

        clubText.setEnabled(false);
        namePlayerText.setEnabled(false);
        numberText.setEnabled(false);
        heightText.setEnabled(false);
        weightText.setEnabled(false);
        positionComboBox.setEnabled(false);
        agePlayerLabel.setEnabled(false);
        birthdayPlayer.setEnabled(false);
        startContract.setEnabled(false);
        endContract.setEnabled(false);
        openButton.setEnabled(false);
        saveButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    private void setDataDetailPlayer() {
        clearData();
        if (playersModelList != null && playersModelList.size() >= row) {
            playersModelSelected = playersModelList.get(row);
            clubText.setText(playersModelSelected.getClub());
            namePlayerText.setText(playersModelSelected.getPlayerName());
            numberText.setText(String.valueOf(playersModelSelected.getPlayerNumber()));
            heightText.setText(String.valueOf(playersModelSelected.getHeight()));
            weightText.setText(String.valueOf(playersModelSelected.getWeight()));
            if (playersModelSelected.getPositionId() > 0) {
                positionComboBox.setSelectedIndex(mapIndexPosition.get(playersModelSelected.getPositionId()));
            }
            Date startDate = birthdayPlayer.getDate();
            Date endDate = new Date();
            String agePlayer = Util.getYearMonth(startDate, endDate);
            agePlayerLabel.setText(agePlayer);
            birthdayPlayer.setDate(playersModelSelected.getBirthday());
            startContract.setDate(playersModelSelected.getContractStart());
            endContract.setDate(playersModelSelected.getContractEnd());
            try {
                if (playersModelSelected.getImage() != null && playersModelSelected.getImage().length() > 0) {
                    File fileImgPlayer = new File(System.getProperty("user.dir") + "/img" + File.separator + playersModelSelected.getImage());
                    if (fileImgPlayer != null) {
                        BufferedImage originalImage = ImageIO.read(fileImgPlayer);
                        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                        BufferedImage resizeImageJpg = resizeImage(originalImage, type, 165, 204);
                        ImageIcon img = new ImageIcon(resizeImageJpg);
                        imgLabel.setText("");
                        imgLabel.setIcon(img);
                    }
                }
            } catch (Exception ex) {
                logger.info("" + ex);
                ex.printStackTrace();
            }
            clubText.setEnabled(true);
            namePlayerText.setEnabled(true);
            numberText.setEnabled(true);
            heightText.setEnabled(true);
            weightText.setEnabled(true);
            positionComboBox.setEnabled(true);
            agePlayerLabel.setEnabled(true);
            birthdayPlayer.setEnabled(true);
            startContract.setEnabled(true);
            endContract.setEnabled(true);
            openButton.setEnabled(true);
            saveButton.setEnabled(true);
            deleteButton.setEnabled(true);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        numberText = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        namePlayerText = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        imgLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        openButton = new javax.swing.JButton();
        positionComboBox = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        agePlayerLabel = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        weightText = new javax.swing.JTextField();
        heightText = new javax.swing.JTextField();
        birthdayPlayer = new org.jdesktop.swingx.JXDatePicker();
        startContract = new org.jdesktop.swingx.JXDatePicker();
        endContract = new org.jdesktop.swingx.JXDatePicker();
        saveButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        clubText = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        searchText = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        searchTable = new javax.swing.JTable();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detail Player", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Contract Start");

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Birthday");

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("End");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Height");

        imgLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        imgLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgLabel.setText("NO IMAGE");
        imgLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Name");

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Play");

        openButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        openButton.setText("Upload Image");
        openButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openButtonActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Number");

        agePlayerLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        agePlayerLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        agePlayerLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Age");

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Weight");

        saveButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        deleteButton.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        deleteButton.setText("Delete");
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Club");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(openButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(imgLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(positionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(heightText, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(weightText, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(numberText, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(namePlayerText, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(birthdayPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(startContract, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(agePlayerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(endContract, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(0, 1, Short.MAX_VALUE)))
                        .addGap(82, 82, 82))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(clubText, javax.swing.GroupLayout.PREFERRED_SIZE, 274, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(imgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(openButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 41, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clubText, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(namePlayerText, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(numberText, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(heightText, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(weightText, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(birthdayPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(startContract, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(endContract, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(agePlayerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(positionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                "#", "Name", "Height", "Weight", "Play", "Birthday", "Contract Start", "Contract End"
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchText, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.getAccessibleContext().setAccessibleName("Player");
    }// </editor-fold>//GEN-END:initComponents

    private void clearData() {
        playersModelSelected = null;
        clubText.setText("");
        namePlayerText.setText("");
        numberText.setText("");
        heightText.setText("");
        weightText.setText("");
        positionComboBox.setSelectedIndex(-1);
        agePlayerLabel.setText("");
        birthdayPlayer.setDate(new Date());
        startContract.setDate(new Date());
        endContract.setDate(new Date());
        imgLabel.setText("NO IMAGE");
        imgLabel.setIcon(null);
        fileImg = null;

        clubText.setEnabled(false);
        namePlayerText.setEnabled(false);
        numberText.setEnabled(false);
        heightText.setEnabled(false);
        weightText.setEnabled(false);
        positionComboBox.setEnabled(false);
        agePlayerLabel.setEnabled(false);
        birthdayPlayer.setEnabled(false);
        startContract.setEnabled(false);
        endContract.setEnabled(false);
        openButton.setEnabled(false);
        saveButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        searchByKeyWord();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void openButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openButtonActionPerformed
        // TODO add your handling code here:
        Sound.getInstance().playClick();
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.showOpenDialog(null);
            File file = chooser.getSelectedFile();
            if (file != null) {
                BufferedImage originalImage = ImageIO.read(file);
                int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                BufferedImage resizeImageJpg = resizeImage(originalImage, type, 165, 204);
                ImageIcon imgPet = new ImageIcon(resizeImageJpg);
                imgLabel.setText("");
                imgLabel.setIcon(imgPet);
                fileImg = new File(file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_openButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        Sound.getInstance().playClick();
        if (namePlayerText.getText() != null && namePlayerText.getText().trim().length() > 0) {
            int saveConfirm = JOptionPane.showConfirmDialog(null, "Confirm save edit player?", "Confirm Edit", JOptionPane.OK_OPTION | JOptionPane.CANCEL_OPTION);
            if (saveConfirm == JOptionPane.OK_OPTION) {
                playersModelSelected.setClub(clubText.getText());
                playersModelSelected.setPlayerName(namePlayerText.getText());
                playersModelSelected.setPlayerNumber(numberText.getText().trim().length() > 0
                        ? Integer.parseInt(numberText.getText()) : 0);
                playersModelSelected.setHeight(heightText.getText().trim().length() > 0
                        ? Integer.parseInt(heightText.getText()) : 0);
                playersModelSelected.setWeight(weightText.getText().trim().length() > 0
                        ? Integer.parseInt(weightText.getText()) : 0);
                playersModelSelected.setPositionId(positionComboBox.getSelectedIndex() != -1
                        ? mapPosition.get((String) positionComboBox.getSelectedItem()) : 0);
                playersModelSelected.setPositionStr((String) positionComboBox.getSelectedItem());
                playersModelSelected.setBirthday(birthdayPlayer.getDate());
                playersModelSelected.setContractStart(startContract.getDate());
                playersModelSelected.setContractEnd(endContract.getDate());
                String fileName = playersModelSelected.getPlayerName() + (playersModelSelected.getPlayerNumber() != 0 ? ("_" + playersModelSelected.getPlayerNumber()) : "") + ".jpg";
                try {
                    if (fileImg != null) {
                        new File(System.getProperty("user.dir") + "/img/" + playersModelSelected.getImage()).delete();
                        File file = new File(System.getProperty("user.dir") + "/img/" + fileName);
                        InputStream is = null;
                        OutputStream os = null;
                        try {
                            is = new FileInputStream(fileImg);
                            os = new FileOutputStream(file);
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = is.read(buffer)) > 0) {
                                os.write(buffer, 0, length);
                            }
                        } finally {
                            is.close();
                            os.close();
                        }
                        playersModelSelected.setImage(fileName);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                playersModelSelected.setUpdateBy(sessionManager.getUser().getUserId());
                playersModelSelected.setUpdateDate(new Date());
                boolean chkUpdatePlayer = playersManager.editPlayers(playersModelSelected);
                if (chkUpdatePlayer) {
                    playersModelList.set(row, playersModelSelected);
                    DefaultTableModel modelTable = (DefaultTableModel) searchTable.getModel();
                    while (modelTable.getRowCount() > 0) {
                        modelTable.removeRow(0);
                    }
                    for (PlayersModel model : playersModelList) {
                        Object[] rowTable = {model.getPlayerNumber(), model.getPlayerName(), model.getHeight(), model.getWeight(), model.getPositionStr(), sdf.format(model.getBirthday()), sdf.format(model.getContractStart()), sdf.format(model.getContractEnd())};
                        modelTable.addRow(rowTable);
                    }
                    JOptionPane.showMessageDialog(this, "Save edit player complete");
                } else {
                    JOptionPane.showMessageDialog(this, "Save edit player Fail");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please insert name player");
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // TODO add your handling code here:
        Sound.getInstance().playClick();
        int deleteConfirm = JOptionPane.showConfirmDialog(null, "Confirm delete player?", "Confirm Delete", JOptionPane.OK_OPTION | JOptionPane.CANCEL_OPTION);
        if (deleteConfirm == JOptionPane.OK_OPTION) {
            boolean chkUpdatePlayer = playersManager.deletePlayer(playersModelSelected.getPlayerId());
            if (chkUpdatePlayer) {
                yearlyManager.deleteYearly(playersModelSelected.getPlayerId());
                new File(System.getProperty("user.dir") + "/img/" + playersModelSelected.getImage()).delete();
                if (playersModelSelected.getVideoModelList() != null && playersModelSelected.getVideoModelList().size() > 0) {
                    for (VideoModel videoModel : playersModelSelected.getVideoModelList()) {
                        new File(System.getProperty("user.dir") + "/video/" + videoModel.getVideoName()).delete();
                    }
                }
                videoPlayersManager.deleteVideoByPlayerId(playersModelSelected.getPlayerId());
                playersModelList.remove(row);
                DefaultTableModel modelTable = (DefaultTableModel) searchTable.getModel();
                while (modelTable.getRowCount() > 0) {
                    modelTable.removeRow(0);
                }
                for (PlayersModel model : playersModelList) {
                    Object[] rowTable = {model.getPlayerNumber(), model.getPlayerName(), model.getHeight(), model.getWeight(), model.getPositionStr(), sdf.format(model.getBirthday()), sdf.format(model.getContractStart()), sdf.format(model.getContractEnd())};
                    modelTable.addRow(rowTable);
                }
                clearData();
                JOptionPane.showMessageDialog(this, "Delete player complete");
            } else {
                JOptionPane.showMessageDialog(this, "Delete player Fail");
            }
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void searchByKeyWord() {
//        Runnable r = new Runnable() {
//            public void run() {
        clearData();
        playersModelList = playersManager.searchByKeyWord(searchText.getText());
        DefaultTableModel modelTable = (DefaultTableModel) searchTable.getModel();
        while (modelTable.getRowCount() > 0) {
            modelTable.removeRow(0);
        }
        if (playersModelList != null && playersModelList.size() > 0) {
            for (PlayersModel model : playersModelList) {
                Object[] rowTable = {model.getPlayerNumber(), model.getPlayerName(), model.getHeight(), model.getWeight(), model.getPositionStr(), sdf.format(model.getBirthday()), sdf.format(model.getContractStart()), sdf.format(model.getContractEnd())};
                modelTable.addRow(rowTable);
            }
            if (playersModelList.size() == 1) {
                row = 0;
                setDataDetailPlayer();
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
    private javax.swing.JLabel agePlayerLabel;
    private org.jdesktop.swingx.JXDatePicker birthdayPlayer;
    private javax.swing.JTextField clubText;
    private javax.swing.JButton deleteButton;
    private org.jdesktop.swingx.JXDatePicker endContract;
    private javax.swing.JTextField heightText;
    private javax.swing.JLabel imgLabel;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField namePlayerText;
    private javax.swing.JTextField numberText;
    private javax.swing.JButton openButton;
    private javax.swing.JComboBox positionComboBox;
    private javax.swing.JButton saveButton;
    private javax.swing.JButton searchButton;
    private javax.swing.JTable searchTable;
    private javax.swing.JTextField searchText;
    private org.jdesktop.swingx.JXDatePicker startContract;
    private javax.swing.JTextField weightText;
    // End of variables declaration//GEN-END:variables
}
