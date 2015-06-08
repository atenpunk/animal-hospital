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
import co.th.aten.football.model.VideoModel;
import co.th.aten.football.service.PlayersManager;
import co.th.aten.football.ui.report.ViewReportTestRadarDlg;
import co.th.aten.football.service.SessionManager;
import co.th.aten.football.service.VideoPlayersManager;
import co.th.aten.football.ui.ProcessTransactionDialog;
import co.th.aten.football.ui.report.PlayersDetailReportDialog;
import co.th.aten.football.ui.report.PlayersGraphReportDialog;
import co.th.aten.football.ui.report.ViewReportPieDlg;
import co.th.aten.football.util.Sound;
import co.th.aten.football.util.Util;
import com.jensoft.sw2d.core.view.View2D;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
public class DetailPlayerPanel extends javax.swing.JPanel {

    private final Log logger = LogFactory.getLog(getClass());
    private SessionManager sessionManager;
    private PlayersManager playersManager;
    private VideoPlayersManager videoPlayersManager;
    private List<PlayersModel> playersModelList;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
    private DecimalFormat df = new DecimalFormat("#,##0");
    private PlayersModel playersModelSelected;
    private int row = -1;
    private View2D viewRadar;
    private View2D viewPieTime;
    private View2D viewPieMatch;
    private View2D viewPieStart;

    public DetailPlayerPanel() {
        this.sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
        this.playersManager = (PlayersManager) Application.services().getService(PlayersManager.class);
        this.videoPlayersManager = (VideoPlayersManager) Application.services().getService(VideoPlayersManager.class);
        initComponents();
        editPlayer.setEnabled(false);
        inputGc.setBackground(null);
        inputAnnualSalary.setBackground(null);
        inputSigningFee.setBackground(null);
        inputSalaryMonth.setBackground(null);
        inputGoal.setBackground(null);
        inputPlayingTime.setBackground(null);
        inputMatch.setBackground(null);
        inputStarting.setBackground(null);
        inputWin.setBackground(null);
        inputLose.setBackground(null);
        inputDraw.setBackground(null);
        matchPieLabel.setVisible(false);
        playingTimePieLabel.setVisible(false);
        starterPieLabel.setVisible(false);
        jPanel4.setVisible(false);
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        searchTable.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        searchTable.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        searchTable.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        searchTable.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        searchTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        searchTable.getColumnModel().getColumn(0).setPreferredWidth(10);

        jScrollPane2.getViewport().setBackground(Color.WHITE);
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        videoTable.getColumnModel().getColumn(0).setPreferredWidth(10);
        videoTable.getColumnModel().getColumn(1).setPreferredWidth(120);
        videoTable.getColumnModel().getColumn(2).setPreferredWidth(10);
        videoTable.getColumnModel().getColumn(3).setPreferredWidth(10);
        videoTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        videoTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        videoTable.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        videoTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        videoTable.getColumnModel().getColumn(2).setCellRenderer(new ImageRenderer());
        videoTable.getColumnModel().getColumn(3).setCellRenderer(new ImageRenderer());
        videoTable.setRowHeight(27);
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
                    Sound.getInstance().playClick();
                    JTable target = (JTable) e.getSource();
                    row = target.getSelectedRow();
                    setDataDetailPlayer();
                }
            }
        });

        videoTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable target = (JTable) e.getSource();
                int rowVideo = target.getSelectedRow();
                int columnVideo = target.getSelectedColumn();
                if (columnVideo == 2) {
                    Sound.getInstance().playClick();
                    try {
                        if (playersModelSelected.getVideoModelList() != null && playersModelSelected.getVideoModelList().size() > rowVideo) {
                            Runtime.getRuntime().exec(System.getProperty("user.dir")
                                    + "/VLC/vlc.exe video/" + playersModelSelected.getVideoModelList().get(rowVideo).getVideoName());
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(DetailPlayerPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else if (columnVideo == 3) {
                    int saveConfirm = JOptionPane.showConfirmDialog(null, "Confirm delete video?", "Confirm Delete", JOptionPane.OK_OPTION | JOptionPane.CANCEL_OPTION);
                    if (saveConfirm == JOptionPane.OK_OPTION) {
                        if (playersModelSelected.getVideoModelList() != null && playersModelSelected.getVideoModelList().size() > rowVideo) {
                            boolean delVideo = videoPlayersManager.deleteVideoByVideoId(playersModelSelected.getVideoModelList().get(rowVideo).getVideoId());
                            if (delVideo) {
                                new File(System.getProperty("user.dir") + "/video/" + playersModelSelected.getVideoModelList().get(rowVideo).getVideoName()).delete();
                                playersModelSelected.getVideoModelList().remove(rowVideo);
                                setVideoDataTable(playersModelSelected.getVideoModelList());
                                showMessageDialog("Delete video complete");
                            } else {
                                showMessageDialog("Delete video Fail");
                            }
                        }
                    }
                }
            }
        });

        inputGc.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                        && (character != '\b')) {
                    e.consume();
                }
            }
        });
        inputAnnualSalary.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                        && (character != '\b')) {
                    e.consume();
                }
            }
        });
        inputSigningFee.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                        && (character != '\b')) {
                    e.consume();
                }
            }
        });
        inputSalaryMonth.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                        && (character != '\b')) {
                    e.consume();
                }
            }
        });
        inputGoal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                        && (character != '\b')) {
                    e.consume();
                }
            }
        });
        inputPlayingTime.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                        && (character != '\b')) {
                    e.consume();
                }
            }
        });
        inputMatch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                        && (character != '\b')) {
                    e.consume();
                }
            }
        });
        inputStarting.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                        && (character != '\b')) {
                    e.consume();
                }
            }
        });
        inputWin.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                        && (character != '\b')) {
                    e.consume();
                }
            }
        });
        inputLose.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                        && (character != '\b')) {
                    e.consume();
                }
            }
        });
        inputDraw.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char character = e.getKeyChar();
                if (((character < '0') || (character > '9'))
                        && (character != '\b')) {
                    e.consume();
                }
            }
        });
    }

    private void showMessageDialog(String mess) {
        JOptionPane.showMessageDialog(this, mess);
    }

    private void setDataDetailPlayer() {
        clearData();
        if (playersModelList != null && playersModelList.size() >= row) {
            editPlayer.setEnabled(true);
            playersModelSelected = playersModelList.get(row);
            redarPanal.setVisible(true);
            timePanel.setVisible(true);
            matchPanel.setVisible(true);
            startPanel.setVisible(true);
            matchPieLabel.setVisible(true);
            playingTimePieLabel.setVisible(true);
            starterPieLabel.setVisible(true);
            jPanel4.setVisible(true);
            ViewReportTestRadarDlg reportRadar = new ViewReportTestRadarDlg(playersModelSelected.getGc(), playersModelSelected.getMatch(), playersModelSelected.getPlayingTime(), 0);
            viewRadar = reportRadar.createView2D();
            viewRadar.setSize(409, 262);
            redarPanal.setBackground(Color.WHITE);
            redarPanal.add(viewRadar, BorderLayout.CENTER);
            int data01 = ((100 * playersModelSelected.getMatch()) / Configuration.getInt("Match"));
            int data02 = 100 - data01;
            ViewReportPieDlg reportPieMatch = new ViewReportPieDlg(data02, data01);
            viewPieMatch = reportPieMatch.createView2D();
            viewPieMatch.setSize(136, 136);
            matchPanel.setBackground(Color.WHITE);
            matchPanel.add(viewPieMatch, BorderLayout.CENTER);
            data01 = ((100 * playersModelSelected.getPlayingTime()) / Configuration.getInt("PlayingTime"));
            data02 = 100 - data01;
            ViewReportPieDlg reportPieTime = new ViewReportPieDlg(data02, data01);
            viewPieTime = reportPieTime.createView2D();
            viewPieTime.setSize(136, 136);
            timePanel.setBackground(Color.WHITE);
            timePanel.add(viewPieTime, BorderLayout.CENTER);
            data01 = ((100 * playersModelSelected.getStarter()) / Configuration.getInt("Match"));
            data02 = 100 - data01;
            ViewReportPieDlg reportPieStart = new ViewReportPieDlg(data02, data01);
            viewPieStart = reportPieStart.createView2D();
            viewPieStart.setSize(136, 136);
            startPanel.setBackground(Color.WHITE);
            startPanel.add(viewPieStart, BorderLayout.CENTER);
            nameLabel.setText("#" + playersModelSelected.getPlayerNumber() + " " + playersModelSelected.getPlayerName());
            String brid = "";
            String HeWe = playersModelSelected.getHeight() + "cm/" + playersModelSelected.getWeight() + "kg";
            String contractDate = "";
            if (playersModelSelected.getBirthday() != null) {
                brid = sdf.format(playersModelSelected.getBirthday());
                Date startDate = playersModelSelected.getBirthday();
                Date endDate = new Date();
                String year = Util.getYear(startDate, endDate);
                brid += " (" + year + ")";
            }
            if (playersModelSelected.getContractStart() != null && playersModelSelected.getContractEnd() != null) {
                contractDate = sdf.format(playersModelSelected.getContractStart()) + " - " + sdf.format(playersModelSelected.getContractEnd());
            }
            bridLabel.setText(brid);
            higthLabel.setText(HeWe);
            contracLabel.setText(contractDate);
            inputGc.setText(df.format(playersModelSelected.getGc()));
            inputAnnualSalary.setText(df.format(playersModelSelected.getAnnualSalary()));
            inputSigningFee.setText(df.format(playersModelSelected.getSigningFee()));
            inputSalaryMonth.setText(df.format(playersModelSelected.getSalaryMonth()));
            inputGoal.setText(df.format(playersModelSelected.getGoal()));
            inputPlayingTime.setText(df.format(playersModelSelected.getPlayingTime()));
            inputMatch.setText(df.format(playersModelSelected.getMatch()));
            inputStarting.setText(df.format(playersModelSelected.getStarter()));
            inputWin.setText(df.format(playersModelSelected.getWin()));
            inputLose.setText(df.format(playersModelSelected.getLose()));
            inputDraw.setText(df.format(playersModelSelected.getDraw()));
            if (playersModelSelected.getVideoModelList() != null && playersModelSelected.getVideoModelList().size() > 0) {
                setVideoDataTable(playersModelSelected.getVideoModelList());
            }
            try {
                if (playersModelSelected.getImage() != null && playersModelSelected.getImage().length() > 0) {
                    File fileImg = new File(System.getProperty("user.dir") + "/img" + File.separator + playersModelSelected.getImage());
                    if (fileImg != null) {
                        BufferedImage originalImage = ImageIO.read(fileImg);
                        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                        BufferedImage resizeImageJpg = resizeImage(originalImage, type, 182, 217);
                        ImageIcon img = new ImageIcon(resizeImageJpg);
//                                    ImageIcon icon = new ImageIcon(System.getProperty("user.dir") + "/img" + File.separator + playersModelSelected.getImage());
                        imgPlayer.setText("");
                        imgPlayer.setIcon(img);
                    }
                }
            } catch (Exception ex) {
                logger.info("" + ex);
                ex.printStackTrace();
            }

            // add data video

        }
    }

    private void setVideoDataTable(List<VideoModel> videoModelList) {
        try {
            if (videoModelList != null && videoModelList.size() > 0) {
                ImageIcon imgDelete = null;
                File fileDelete = new File(System.getProperty("user.dir") + "/img" + File.separator + "delete.png");
                if (fileDelete != null) {
                    BufferedImage originalImage = ImageIO.read(fileDelete);
                    int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                    BufferedImage resizeImageJpg = resizeImage(originalImage, type, 40, 40);
                    imgDelete = new ImageIcon(resizeImageJpg);
                }
                DefaultTableModel modelTable = (DefaultTableModel) videoTable.getModel();
                while (modelTable.getRowCount() > 0) {
                    modelTable.removeRow(0);
                }
                int no = 0;
                for (VideoModel modelVideo : videoModelList) {
                    Object[] rowTable = {++no, sdf.format(modelVideo.getCreateDate()), "", imgDelete};
                    modelTable.addRow(rowTable);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private class ImageRenderer extends DefaultTableCellRenderer {

        JLabel lbl = new JLabel();

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {
            if (column == 2) {
                lbl.setToolTipText("Play");
                ImageIcon imgPlay = null;
                File filePlay = new File(System.getProperty("user.dir") + "/img" + File.separator + "play.png");
                if (filePlay != null) {
                    try {
                        BufferedImage originalImage;
                        originalImage = ImageIO.read(filePlay);
                        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                        BufferedImage resizeImageJpg = resizeImage(originalImage, type, 27, 27);
                        imgPlay = new ImageIcon(resizeImageJpg);
                    } catch (IOException ex) {
                        Logger.getLogger(DetailPlayerPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                lbl.setIcon(imgPlay);
                lbl.setHorizontalAlignment(0);
                return lbl;
            } else if (column == 3) {
                lbl.setToolTipText("Delete");
                ImageIcon imgDelete = null;
                File filePlay = new File(System.getProperty("user.dir") + "/img" + File.separator + "delete.png");
                if (filePlay != null) {
                    try {
                        BufferedImage originalImage;
                        originalImage = ImageIO.read(filePlay);
                        int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                        BufferedImage resizeImageJpg = resizeImage(originalImage, type, 27, 27);
                        imgDelete = new ImageIcon(resizeImageJpg);
                    } catch (IOException ex) {
                        Logger.getLogger(DetailPlayerPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                lbl.setIcon(imgDelete);
                lbl.setHorizontalAlignment(0);
                return lbl;
            }
            lbl.setText((String) value);
            return lbl;
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
        redarPanal = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        imgPlayer = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        bridLabel = new javax.swing.JLabel();
        higthLabel = new javax.swing.JLabel();
        contracLabel = new javax.swing.JLabel();
        nameLabel1 = new javax.swing.JLabel();
        nameLabel2 = new javax.swing.JLabel();
        nameLabel3 = new javax.swing.JLabel();
        nameLabel4 = new javax.swing.JLabel();
        nameLabel5 = new javax.swing.JLabel();
        nameLabel6 = new javax.swing.JLabel();
        nameLabel7 = new javax.swing.JLabel();
        nameLabel8 = new javax.swing.JLabel();
        nameLabel9 = new javax.swing.JLabel();
        nameLabel10 = new javax.swing.JLabel();
        inputGc = new javax.swing.JTextField();
        inputAnnualSalary = new javax.swing.JTextField();
        inputSigningFee = new javax.swing.JTextField();
        inputSalaryMonth = new javax.swing.JTextField();
        inputGoal = new javax.swing.JTextField();
        inputPlayingTime = new javax.swing.JTextField();
        inputMatch = new javax.swing.JTextField();
        inputStarting = new javax.swing.JTextField();
        inputWin = new javax.swing.JTextField();
        inputLose = new javax.swing.JTextField();
        nameLabel11 = new javax.swing.JLabel();
        inputDraw = new javax.swing.JTextField();
        matchPanel = new javax.swing.JPanel();
        timePanel = new javax.swing.JPanel();
        startPanel = new javax.swing.JPanel();
        editPlayer = new javax.swing.JButton();
        matchPieLabel = new javax.swing.JLabel();
        playingTimePieLabel = new javax.swing.JLabel();
        starterPieLabel = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        videoTable = new javax.swing.JTable();
        addVideoButton = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        searchText = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        searchTable = new javax.swing.JTable();
        report1Button = new javax.swing.JButton();
        report2Button = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Detail Player", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        redarPanal.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout redarPanalLayout = new javax.swing.GroupLayout(redarPanal);
        redarPanal.setLayout(redarPanalLayout);
        redarPanalLayout.setHorizontalGroup(
            redarPanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 409, Short.MAX_VALUE)
        );
        redarPanalLayout.setVerticalGroup(
            redarPanalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 262, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        imgPlayer.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        imgPlayer.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imgPlayer.setText("NO IMAGE");
        imgPlayer.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nameLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        bridLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bridLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        higthLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        higthLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        contracLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        contracLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        nameLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nameLabel1.setText("GC");
        nameLabel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nameLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nameLabel2.setText("Playing Time");
        nameLabel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nameLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nameLabel3.setText("Annual Salary");
        nameLabel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nameLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nameLabel4.setText("Match");
        nameLabel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nameLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nameLabel5.setText("Signing Fee");
        nameLabel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nameLabel6.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nameLabel6.setText("Starter");
        nameLabel6.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nameLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nameLabel7.setText("Salary M");
        nameLabel7.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nameLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nameLabel8.setText("Win");
        nameLabel8.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nameLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nameLabel9.setText("Goal");
        nameLabel9.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        nameLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nameLabel10.setText("Lose");
        nameLabel10.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        inputGc.setEditable(false);
        inputGc.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        inputGc.setPreferredSize(new java.awt.Dimension(6, 19));

        inputAnnualSalary.setEditable(false);
        inputAnnualSalary.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        inputAnnualSalary.setPreferredSize(new java.awt.Dimension(6, 19));

        inputSigningFee.setEditable(false);
        inputSigningFee.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        inputSigningFee.setPreferredSize(new java.awt.Dimension(6, 19));

        inputSalaryMonth.setEditable(false);
        inputSalaryMonth.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        inputSalaryMonth.setPreferredSize(new java.awt.Dimension(6, 19));

        inputGoal.setEditable(false);
        inputGoal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        inputGoal.setPreferredSize(new java.awt.Dimension(6, 19));

        inputPlayingTime.setEditable(false);
        inputPlayingTime.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        inputPlayingTime.setPreferredSize(new java.awt.Dimension(6, 19));

        inputMatch.setEditable(false);
        inputMatch.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        inputMatch.setPreferredSize(new java.awt.Dimension(6, 19));

        inputStarting.setEditable(false);
        inputStarting.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        inputStarting.setPreferredSize(new java.awt.Dimension(6, 19));

        inputWin.setEditable(false);
        inputWin.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        inputWin.setPreferredSize(new java.awt.Dimension(6, 19));

        inputLose.setEditable(false);
        inputLose.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        inputLose.setPreferredSize(new java.awt.Dimension(6, 19));

        nameLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nameLabel11.setText("Draw");
        nameLabel11.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        inputDraw.setEditable(false);
        inputDraw.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        inputDraw.setPreferredSize(new java.awt.Dimension(6, 19));

        matchPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout matchPanelLayout = new javax.swing.GroupLayout(matchPanel);
        matchPanel.setLayout(matchPanelLayout);
        matchPanelLayout.setHorizontalGroup(
            matchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        matchPanelLayout.setVerticalGroup(
            matchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 136, Short.MAX_VALUE)
        );

        timePanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout timePanelLayout = new javax.swing.GroupLayout(timePanel);
        timePanel.setLayout(timePanelLayout);
        timePanelLayout.setHorizontalGroup(
            timePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        timePanelLayout.setVerticalGroup(
            timePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 136, Short.MAX_VALUE)
        );

        startPanel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout startPanelLayout = new javax.swing.GroupLayout(startPanel);
        startPanel.setLayout(startPanelLayout);
        startPanelLayout.setHorizontalGroup(
            startPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        startPanelLayout.setVerticalGroup(
            startPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 136, Short.MAX_VALUE)
        );

        editPlayer.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        editPlayer.setText("Edit");
        editPlayer.setBorder(null);
        editPlayer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editPlayerActionPerformed(evt);
            }
        });

        matchPieLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        matchPieLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        matchPieLabel.setText("Match");

        playingTimePieLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        playingTimePieLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playingTimePieLabel.setText("Playing Time");

        starterPieLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        starterPieLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        starterPieLabel.setText("Starter");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(contracLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(higthLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bridLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(imgPlayer, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(nameLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nameLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nameLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nameLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nameLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(inputSalaryMonth, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputSigningFee, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputAnnualSalary, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputGc, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputGoal, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(nameLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nameLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nameLabel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(nameLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(nameLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(nameLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(inputPlayingTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputMatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputStarting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputWin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputLose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(inputDraw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(editPlayer, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(matchPieLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                            .addComponent(matchPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(timePanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(playingTimePieLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(startPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(starterPieLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))))
                .addContainerGap(45, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputPlayingTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputMatch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputStarting, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputWin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputLose, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputDraw, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(editPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputGc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputAnnualSalary, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputSigningFee, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputSalaryMonth, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nameLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(inputGoal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(nameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(bridLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(higthLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(contracLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(timePanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(startPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(matchPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(matchPieLabel)
                    .addComponent(playingTimePieLabel)
                    .addComponent(starterPieLabel))
                .addGap(12, 12, 12))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Video"));

        videoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No.", "Upload Date", "Play", "Delete"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(videoTable);

        addVideoButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        addVideoButton.setText("Add Video");
        addVideoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addVideoButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(addVideoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(addVideoButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 104, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(redarPanal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(redarPanal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                "#", "Name", "Match", "Playing Time", "Goal", "Starter"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        searchTable.setGridColor(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(searchTable);

        report1Button.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        report1Button.setText("Report 1");
        report1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                report1ButtonActionPerformed(evt);
            }
        });

        report2Button.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        report2Button.setText("Report 2");
        report2Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                report2ButtonActionPerformed(evt);
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
                        .addGap(18, 18, 18)
                        .addComponent(report2Button)
                        .addGap(0, 215, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(searchText, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(searchButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(report1Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(report2Button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.getAccessibleContext().setAccessibleName("Player");
    }// </editor-fold>//GEN-END:initComponents

    private void clearData() {
        playersModelSelected = null;
        editPlayer.setEnabled(false);
        editPlayer.setText("Edit");
        inputGc.setEditable(false);
        inputAnnualSalary.setEditable(false);
        inputSigningFee.setEditable(false);
        inputSalaryMonth.setEditable(false);
        inputGoal.setEditable(false);
        inputPlayingTime.setEditable(false);
        inputMatch.setEditable(false);
        inputStarting.setEditable(false);
        inputWin.setEditable(false);
        inputLose.setEditable(false);
        inputDraw.setEditable(false);

        inputGc.setBackground(null);
        inputAnnualSalary.setBackground(null);
        inputSigningFee.setBackground(null);
        inputSalaryMonth.setBackground(null);
        inputGoal.setBackground(null);
        inputPlayingTime.setBackground(null);
        inputMatch.setBackground(null);
        inputStarting.setBackground(null);
        inputWin.setBackground(null);
        inputLose.setBackground(null);
        inputDraw.setBackground(null);

        redarPanal.setVisible(false);
        timePanel.setVisible(false);
        matchPanel.setVisible(false);
        startPanel.setVisible(false);
        if (viewRadar != null) {
            redarPanal.remove(viewRadar);
        }
        if (viewPieTime != null) {
            timePanel.remove(viewPieTime);
        }
        if (viewPieMatch != null) {
            matchPanel.remove(viewPieMatch);
        }
        if (viewPieStart != null) {
            startPanel.remove(viewPieStart);
        }
        imgPlayer.setText("NO IMAGE");
        imgPlayer.setIcon(null);
        nameLabel.setText("");
        bridLabel.setText("");
        higthLabel.setText("");
        contracLabel.setText("");
        inputGc.setText("");
        inputAnnualSalary.setText("");
        inputSigningFee.setText("");
        inputSalaryMonth.setText("");
        inputGoal.setText("");
        inputPlayingTime.setText("");
        inputMatch.setText("");
        inputStarting.setText("");
        inputWin.setText("");
        inputLose.setText("");
        inputDraw.setText("");

        matchPieLabel.setVisible(false);
        playingTimePieLabel.setVisible(false);
        starterPieLabel.setVisible(false);
        jPanel4.setVisible(false);

        DefaultTableModel modelTable = (DefaultTableModel) videoTable.getModel();
        while (modelTable.getRowCount() > 0) {
            modelTable.removeRow(0);
        }
    }

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        searchByKeyWord();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void editPlayerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editPlayerActionPerformed
        // TODO add your handling code here:
        Sound.getInstance().playClick();
        if (editPlayer.getText().equals("Edit")) {
            editPlayer.setText("Save");
            inputGc.setEditable(true);
            inputAnnualSalary.setEditable(true);
            inputSigningFee.setEditable(true);
            inputSalaryMonth.setEditable(true);
            inputGoal.setEditable(true);
            inputPlayingTime.setEditable(true);
            inputMatch.setEditable(true);
            inputStarting.setEditable(true);
            inputWin.setEditable(true);
            inputLose.setEditable(true);
            inputDraw.setEditable(true);

            inputGc.setText(inputGc.getText().replaceAll(",", ""));
            inputAnnualSalary.setText(inputAnnualSalary.getText().replaceAll(",", ""));
            inputSigningFee.setText(inputSigningFee.getText().replaceAll(",", ""));
            inputSalaryMonth.setText(inputSalaryMonth.getText().replaceAll(",", ""));
            inputGoal.setText(inputGoal.getText().replaceAll(",", ""));
            inputPlayingTime.setText(inputPlayingTime.getText().replaceAll(",", ""));
            inputMatch.setText(inputMatch.getText().replaceAll(",", ""));
            inputStarting.setText(inputStarting.getText().replaceAll(",", ""));
            inputWin.setText(inputWin.getText().replaceAll(",", ""));
            inputLose.setText(inputLose.getText().replaceAll(",", ""));
            inputDraw.setText(inputDraw.getText().replaceAll(",", ""));

            inputGc.setBackground(new Color(204, 255, 204));
            inputAnnualSalary.setBackground(new Color(204, 255, 204));
            inputSigningFee.setBackground(new Color(204, 255, 204));
            inputSalaryMonth.setBackground(new Color(204, 255, 204));
            inputGoal.setBackground(new Color(204, 255, 204));
            inputPlayingTime.setBackground(new Color(204, 255, 204));
            inputMatch.setBackground(new Color(204, 255, 204));
            inputStarting.setBackground(new Color(204, 255, 204));
            inputWin.setBackground(new Color(204, 255, 204));
            inputLose.setBackground(new Color(204, 255, 204));
            inputDraw.setBackground(new Color(204, 255, 204));
        } else {
//            Runnable r = new Runnable() {
//                public void run() {
            if (playersModelSelected != null) {
                playersModelSelected.setGc((inputGc.getText() != null && inputGc.getText().trim().length() > 0)
                        ? Double.parseDouble(inputGc.getText().replaceAll(",", "")) : 0d);
                playersModelSelected.setAnnualSalary((inputAnnualSalary.getText() != null && inputAnnualSalary.getText().trim().length() > 0)
                        ? Double.parseDouble(inputAnnualSalary.getText().replaceAll(",", "")) : 0d);
                playersModelSelected.setSigningFee((inputSigningFee.getText() != null && inputSigningFee.getText().trim().length() > 0)
                        ? Double.parseDouble(inputSigningFee.getText().replaceAll(",", "")) : 0d);
                playersModelSelected.setSalaryMonth((inputSalaryMonth.getText() != null && inputSalaryMonth.getText().trim().length() > 0)
                        ? Double.parseDouble(inputSalaryMonth.getText().replaceAll(",", "")) : 0d);
                playersModelSelected.setGoal((inputGoal.getText() != null && inputGoal.getText().trim().length() > 0)
                        ? Integer.parseInt(inputGoal.getText().replaceAll(",", "")) : 0);
                playersModelSelected.setPlayingTime((inputPlayingTime.getText() != null && inputPlayingTime.getText().trim().length() > 0)
                        ? Integer.parseInt(inputPlayingTime.getText().replaceAll(",", "")) : 0);
                playersModelSelected.setMatch((inputMatch.getText() != null && inputMatch.getText().trim().length() > 0)
                        ? Integer.parseInt(inputMatch.getText().replaceAll(",", "")) : 0);
                playersModelSelected.setStarter((inputStarting.getText() != null && inputStarting.getText().trim().length() > 0)
                        ? Integer.parseInt(inputStarting.getText().replaceAll(",", "")) : 0);
                playersModelSelected.setWin((inputWin.getText() != null && inputWin.getText().trim().length() > 0)
                        ? Integer.parseInt(inputWin.getText().replaceAll(",", "")) : 0);
                playersModelSelected.setLose((inputLose.getText() != null && inputLose.getText().trim().length() > 0)
                        ? Integer.parseInt(inputLose.getText().replaceAll(",", "")) : 0);
                playersModelSelected.setDraw((inputDraw.getText() != null && inputDraw.getText().trim().length() > 0)
                        ? Integer.parseInt(inputDraw.getText().replaceAll(",", "")) : 0);
                playersModelSelected.setUpdateBy(sessionManager.getUser().getUserId());
                playersModelSelected.setUpdateDate(new Date());
            }
            boolean update = playersManager.updatePlayers(playersModelSelected);
            if (update) {
                playersModelList.set(row, playersModelSelected);
                DefaultTableModel modelTable = (DefaultTableModel) searchTable.getModel();
                while (modelTable.getRowCount() > 0) {
                    modelTable.removeRow(0);
                }
                for (PlayersModel model : playersModelList) {
                    Object[] rowTable = {model.getPlayerNumber(), model.getPlayerName(), model.getMatch(), df.format(model.getPlayingTime()), model.getGoal(), model.getStarter()};
                    modelTable.addRow(rowTable);
                }
                setDataDetailPlayer();
            }
            editPlayer.setText("Edit");
            inputGc.setEditable(false);
            inputAnnualSalary.setEditable(false);
            inputSigningFee.setEditable(false);
            inputSalaryMonth.setEditable(false);
            inputGoal.setEditable(false);
            inputPlayingTime.setEditable(false);
            inputMatch.setEditable(false);
            inputStarting.setEditable(false);
            inputWin.setEditable(false);
            inputLose.setEditable(false);
            inputDraw.setEditable(false);

            inputGc.setBackground(null);
            inputAnnualSalary.setBackground(null);
            inputSigningFee.setBackground(null);
            inputSalaryMonth.setBackground(null);
            inputGoal.setBackground(null);
            inputPlayingTime.setBackground(null);
            inputMatch.setBackground(null);
            inputStarting.setBackground(null);
            inputWin.setBackground(null);
            inputLose.setBackground(null);
            inputDraw.setBackground(null);
//                }
//            };
//            new ProcessTransactionDialog(new JFrame(), true, r, "Please wait the system is running...");
        }
    }//GEN-LAST:event_editPlayerActionPerformed

    private void report1ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_report1ButtonActionPerformed
        // TODO add your handling code here:
        Sound.getInstance().playClick();
        new PlayersDetailReportDialog(playersModelList).showDialog();
    }//GEN-LAST:event_report1ButtonActionPerformed

    private void report2ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_report2ButtonActionPerformed
        // TODO add your handling code here:
        Sound.getInstance().playClick();
        List<PlayersGraphModel> playersGraphModel = null;

        if (playersModelList != null && playersModelList.size() > 0) {
            playersGraphModel = new ArrayList<PlayersGraphModel>();
            for (int i = 0; i < playersModelList.size(); i++) {
                PlayersModel model = playersModelList.get(i);
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
        }

        new PlayersGraphReportDialog(playersGraphModel).showDialog();
    }//GEN-LAST:event_report2ButtonActionPerformed

    private void addVideoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addVideoButtonActionPerformed
        // TODO add your handling code here:
        Sound.getInstance().playClick();
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.showOpenDialog(null);
            final File fileVideo = chooser.getSelectedFile();
            if (fileVideo != null) {
                Runnable r = new Runnable() {
                    public void run() {
                        SimpleDateFormat sdfFile = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
                        int lengthSplit = (fileVideo.getName().replaceAll("\\.", "#")).split("#").length;
                        String subFile = (fileVideo.getName().replaceAll("\\.", "#")).split("#")[lengthSplit - 1];
                        String fileName = playersModelSelected.getPlayerName() + "_" + sdfFile.format(new Date()) + "." + subFile;
                        fileName = fileName.replaceAll(" ", "_");
                        logger.info("Video File Name = " + fileName);
                        File fileNew = new File(System.getProperty("user.dir") + "/video/" + fileName);
                        InputStream is = null;
                        OutputStream os = null;
                        try {
                            is = new FileInputStream(fileVideo);
                            os = new FileOutputStream(fileNew);
                            byte[] buffer = new byte[1024];
                            int length;
                            while ((length = is.read(buffer)) > 0) {
                                os.write(buffer, 0, length);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                is.close();
                                os.close();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        VideoModel videoModel = new VideoModel();
                        int maxId = videoPlayersManager.getMaxVideoId();
                        videoModel.setVideoId(maxId + 1);
                        videoModel.setPlayerId(playersModelSelected.getPlayerId());
                        videoModel.setVideoName(fileName);
                        videoModel.setCreateBy(sessionManager.getUser().getUserId());
                        videoModel.setCreateDate(new Date());
                        videoModel.setUpdateBy(sessionManager.getUser().getUserId());
                        videoModel.setUpdateDate(new Date());
                        boolean insertVideo = videoPlayersManager.insertVideoPlayer(videoModel);
                        if (insertVideo) {
                            if (playersModelSelected.getVideoModelList() != null) {
                                playersModelSelected.getVideoModelList().add(videoModel);
                            } else {
                                List<VideoModel> listVideo = new ArrayList<VideoModel>();
                                listVideo.add(videoModel);
                                playersModelSelected.setVideoModelList(listVideo);
                            }
                            playersModelList.set(row, playersModelSelected);
                            setVideoDataTable(playersModelSelected.getVideoModelList());
                        }
                    }
                };
                new ProcessTransactionDialog(new JFrame(), true, r, "Please wait the system is running...");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_addVideoButtonActionPerformed

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
                Object[] rowTable = {model.getPlayerNumber(), model.getPlayerName(), model.getMatch(), df.format(model.getPlayingTime()), model.getGoal(), model.getStarter()};
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
    private javax.swing.JButton addVideoButton;
    private javax.swing.JLabel bridLabel;
    private javax.swing.JLabel contracLabel;
    private javax.swing.JButton editPlayer;
    private javax.swing.JLabel higthLabel;
    private javax.swing.JLabel imgPlayer;
    private javax.swing.JTextField inputAnnualSalary;
    private javax.swing.JTextField inputDraw;
    private javax.swing.JTextField inputGc;
    private javax.swing.JTextField inputGoal;
    private javax.swing.JTextField inputLose;
    private javax.swing.JTextField inputMatch;
    private javax.swing.JTextField inputPlayingTime;
    private javax.swing.JTextField inputSalaryMonth;
    private javax.swing.JTextField inputSigningFee;
    private javax.swing.JTextField inputStarting;
    private javax.swing.JTextField inputWin;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel matchPanel;
    private javax.swing.JLabel matchPieLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel nameLabel1;
    private javax.swing.JLabel nameLabel10;
    private javax.swing.JLabel nameLabel11;
    private javax.swing.JLabel nameLabel2;
    private javax.swing.JLabel nameLabel3;
    private javax.swing.JLabel nameLabel4;
    private javax.swing.JLabel nameLabel5;
    private javax.swing.JLabel nameLabel6;
    private javax.swing.JLabel nameLabel7;
    private javax.swing.JLabel nameLabel8;
    private javax.swing.JLabel nameLabel9;
    private javax.swing.JLabel playingTimePieLabel;
    private javax.swing.JPanel redarPanal;
    private javax.swing.JButton report1Button;
    private javax.swing.JButton report2Button;
    private javax.swing.JButton searchButton;
    private javax.swing.JTable searchTable;
    private javax.swing.JTextField searchText;
    private javax.swing.JPanel startPanel;
    private javax.swing.JLabel starterPieLabel;
    private javax.swing.JPanel timePanel;
    private javax.swing.JTable videoTable;
    // End of variables declaration//GEN-END:variables
}
