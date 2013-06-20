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

import co.th.aten.hospital.model.BreedModel;
import co.th.aten.hospital.model.OwnerModel;
import co.th.aten.hospital.model.PetModel;
import co.th.aten.hospital.model.TypeModel;
import co.th.aten.hospital.service.BreedManager;
import co.th.aten.hospital.service.OwnerManager;
import co.th.aten.hospital.service.PetManager;
import co.th.aten.hospital.service.SessionManager;
import co.th.aten.hospital.service.TypeManager;
import co.th.aten.hospital.ui.ProcessTransactionDialog;
import co.th.aten.hospital.util.Util;
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
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.PlainDocument;
import org.springframework.richclient.application.Application;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 *
 * @author Atenpunk
 */
public class EditOwnerPanel extends javax.swing.JPanel {

    private final Log logger = LogFactory.getLog(getClass());
    private OwnerManager ownerManager;
    private PetManager petManager;
    private SessionManager sessionManager;
    private TypeManager typeManager;
    private BreedManager breedManager;
    private File fileImg;
    private List<OwnerModel> ownerList;
    private OwnerModel modelSelected;

    /** Creates new form EditOwnerPanel */
    public EditOwnerPanel() {
        fileImg = null;
        this.ownerManager = (OwnerManager) Application.services().getService(OwnerManager.class);
        this.petManager = (PetManager) Application.services().getService(PetManager.class);
        this.sessionManager = (SessionManager) Application.services().getService(SessionManager.class);
        this.typeManager = (TypeManager) Application.services().getService(TypeManager.class);
        this.breedManager = (BreedManager) Application.services().getService(BreedManager.class);
        initComponents();

        nameText.setFont(new Font("Tahoma", 0, 11));
        addressText.setFont(new Font("Tahoma", 0, 11));
        phoneText.setFont(new Font("Tahoma", 0, 11));
        emailText.setFont(new Font("Tahoma", 0, 11));
        namePetText.setFont(new Font("Tahoma", 0, 11));
        colorPetText.setFont(new Font("Tahoma", 0, 11));
        searchText.setFont(new Font("Tahoma", 0, 11));
        petTypeComboBox.setFont(new Font("Tahoma", 0, 11));
        petBreedComboBox.setFont(new Font("Tahoma", 0, 11));
        birthdayPet.setFormats("dd/MM/yyyy");
        birthdayPet.setLocale(Locale.US);
        birthdayPet.getMonthView().setDayForeground(Calendar.SATURDAY, Color.BLUE);
        birthdayPet.getMonthView().setDayForeground(Calendar.SUNDAY, Color.RED);
        birthdayPet.setDate(new Date());

        birthdayPet.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                Date startDate = birthdayPet.getDate();
                Date endDate = new Date();
                String agePet = Util.getYearMonth(startDate, endDate);
                System.out.println(agePet);
                agePetLabel.setText(agePet);
            }
        });

        searchText.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchByKeyWord();
                }
            }
        });

        List<TypeModel> typeList = typeManager.getTypeList();
        if (typeList != null) {
            for (TypeModel model : typeList) {
                petTypeComboBox.addItem(model.getEngName());
            }
            petTypeComboBox.setSelectedIndex(-1);
            petTypeComboBox.setEditable(true);
            new MainTest(petTypeComboBox);
        }

        List<BreedModel> breedList = breedManager.getBreedListOrderByEngName(-1);
        if (breedList != null) {
            for (BreedModel model : breedList) {
                petBreedComboBox.addItem(model.getEngName());
            }
            petBreedComboBox.setSelectedIndex(-1);
            petBreedComboBox.setEditable(true);
            new MainTest(petBreedComboBox);
        }

        searchTable.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    clearData();
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
//                    int column = target.getSelectedColumn();
                    if (ownerList != null && ownerList.size() >= row) {
                        modelSelected = ownerList.get(row);
                        nameText.setText(modelSelected.getName());
                        addressText.setText(modelSelected.getAddress());
                        phoneText.setText(modelSelected.getPhoneNumber());
                        emailText.setText(modelSelected.getEmail());
                        namePetText.setText(modelSelected.getPetModel().getName());
                        petTypeComboBox.setSelectedItem(modelSelected.getPetModel().getType());
                        petBreedComboBox.setSelectedItem(modelSelected.getPetModel().getBreed());
                        birthdayPet.setDate(modelSelected.getPetModel().getBirthdayPet());
                        Date startDate = birthdayPet.getDate();
                        Date endDate = new Date();
                        String agePet = Util.getYearMonth(startDate, endDate);
                        agePetLabel.setText(agePet);
                        if (modelSelected.getPetModel().getSex().toLowerCase().equals("male")) {
                            maleRadio.setSelected(true);
                        } else if (modelSelected.getPetModel().getSex().toLowerCase().equals("female")) {
                            femaleRadio.setSelected(true);
                        }
                        colorPetText.setText(modelSelected.getPetModel().getColor());
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
                        fileImg = null;
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nameText = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        addressText = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        phoneText = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        emailText = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        imgLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        namePetText = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        maleRadio = new javax.swing.JRadioButton();
        femaleRadio = new javax.swing.JRadioButton();
        jButton5 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        petTypeComboBox = new javax.swing.JComboBox();
        petBreedComboBox = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        birthdayPet = new org.jdesktop.swingx.JXDatePicker();
        jLabel12 = new javax.swing.JLabel();
        agePetLabel = new javax.swing.JLabel();
        colorPetText = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        searchText = new javax.swing.JTextField();
        searchButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        searchTable = new javax.swing.JTable();

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Owner", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Name");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Address");

        addressText.setColumns(20);
        addressText.setRows(5);
        jScrollPane1.setViewportView(addressText);

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Phone Number");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Email");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(emailText, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                    .addComponent(nameText, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                    .addComponent(phoneText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneText, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(emailText, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(65, Short.MAX_VALUE))
        );

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

        maleRadio.setText("Male");
        maleRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maleRadioActionPerformed(evt);
            }
        });

        femaleRadio.setText("Female");
        femaleRadio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                femaleRadioActionPerformed(evt);
            }
        });

        jButton5.setText("Load Image");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12));
        jButton1.setText("Edit");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Birthday");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Age");

        agePetLabel.setFont(new java.awt.Font("Tahoma", 0, 12));
        agePetLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        agePetLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(imgLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(namePetText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                            .addComponent(petTypeComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, 399, Short.MAX_VALUE)
                            .addComponent(petBreedComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, 399, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(maleRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(femaleRadio, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(birthdayPet, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(agePetLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(colorPetText, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(2, 2, 2)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(namePetText, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(petTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(petBreedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(birthdayPet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 1, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(agePetLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(maleRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(femaleRadio, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(colorPetText, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(imgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton5)))
                .addGap(22, 22, 22))
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
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 619, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1150, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchButton, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void clearData() {
        maleRadio.setSelected(false);
        femaleRadio.setSelected(false);
        namePetText.setText("");
        petTypeComboBox.setSelectedIndex(-1);
        petBreedComboBox.setSelectedIndex(-1);
        colorPetText.setText("");
        agePetLabel.setText("");
        birthdayPet.setDate(new Date());
        imgLabel.setText("NO IMAGE");
        imgLabel.setIcon(null);
        fileImg = null;

        nameText.setText("");
        addressText.setText("");
        phoneText.setText("");
        emailText.setText("");
    }

    private void maleRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maleRadioActionPerformed
        // TODO add your handling code here:
        maleRadio.setSelected(true);
        femaleRadio.setSelected(false);
    }//GEN-LAST:event_maleRadioActionPerformed

    private void femaleRadioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_femaleRadioActionPerformed
        // TODO add your handling code here:
        femaleRadio.setSelected(true);
        maleRadio.setSelected(false);
    }//GEN-LAST:event_femaleRadioActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int saveConfirm = JOptionPane.showConfirmDialog(null, "Confirm edit data?", "Confirm Edit", JOptionPane.OK_OPTION | JOptionPane.CANCEL_OPTION);
        if (saveConfirm == JOptionPane.OK_OPTION) {
            if (nameText.getText() != null && nameText.getText().trim().length() > 0) {
                OwnerModel modelOwner = new OwnerModel();
                modelOwner.setId(modelSelected.getId());
                modelOwner.setName(nameText.getText());
                modelOwner.setAddress(addressText.getText());
                modelOwner.setPhoneNumber(phoneText.getText());
                modelOwner.setEmail(emailText.getText());
                modelOwner.setUpdateBy(sessionManager.getUser().getUserName());
                modelOwner.setUpdateDate(new Date());
                ownerManager.updateOwner(modelOwner);
                PetModel petModel = new PetModel();
                petModel.setId(modelSelected.getPetModel().getId());
                petModel.setOwnerId(modelSelected.getId());
                petModel.setName(namePetText.getText());
                petModel.setType((String) petTypeComboBox.getSelectedItem());
                petModel.setBreed((String) petBreedComboBox.getSelectedItem());
                petModel.setBirthdayPet(birthdayPet.getDate());
                String sex = "";
                if (maleRadio.isSelected()) {
                    sex = "Male";
                } else if (femaleRadio.isSelected()) {
                    sex = "Female";
                }
                petModel.setSex(sex);
                petModel.setColor(colorPetText.getText());
                petModel.setImage(fileImg != null ? readImage(fileImg) : modelSelected.getPetModel().getImage());
                petModel.setUpdateBy(sessionManager.getUser().getUserName());
                petModel.setUpdateDate(new Date());
                if (petManager.updatePet(petModel)) {
                    clearData();
                    JOptionPane.showMessageDialog(this, "Edit data complete");
                } else {
                    JOptionPane.showMessageDialog(this, "Edit Data Error");
                }

            } else {
                JOptionPane.showMessageDialog(this, "Please insert name owner");
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.showOpenDialog(null);
            File file = chooser.getSelectedFile();
            BufferedImage originalImage = ImageIO.read(file);
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizeImageJpg = resizeImage(originalImage, type);
            ImageIcon imgPet = new ImageIcon(resizeImageJpg);
            imgLabel.setText("");
            imgLabel.setIcon(imgPet);
            fileImg = new File(file.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void searchTextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchTextActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_searchTextActionPerformed

    private void searchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchButtonActionPerformed
        // TODO add your handling code here:
        searchByKeyWord();
    }//GEN-LAST:event_searchButtonActionPerformed

    private void searchByKeyWord() {
        if (searchText.getText() != null && searchText.getText().trim().length() > 0) {
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

    private byte[] readImage(File file) {
        try {
            InputStream is = new FileInputStream(file);
            // Get the size of the file
            long length = file.length();
            // You cannot create an array using a long type.
            // It needs to be an int type.
            // Before converting to an int type, check
            // to ensure that file is not larger than Integer.MAX_VALUE.
            if (length > Integer.MAX_VALUE) {
                // File is too large
            }
            // Create the byte array to hold the data
            byte[] bytes = new byte[(int) length];
            // Read in the bytes
            int offset = 0;
            int numRead = 0;
            while (offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
                offset += numRead;
            }
            // Ensure all the bytes have been read in
            if (offset < bytes.length) {
                throw new IOException("Could not completely read file " + file.getName());
            }
            // Close the input stream and return bytes
            is.close();
            return bytes;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private class MainTest extends PlainDocument {

        JComboBox comboBox;
        ComboBoxModel model;
        JTextComponent editor;
        // flag to indicate if setSelectedItem has been called
        // subsequent calls to remove/insertString should be ignored
        boolean selecting = false;

        public MainTest(final JComboBox comboBox) {
            this.comboBox = comboBox;
            model = comboBox.getModel();
            editor = (JTextComponent) comboBox.getEditor().getEditorComponent();
            editor.setDocument(this);
            comboBox.addActionListener(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    if (!selecting) {
                        highlightCompletedText(0);
                    }
                }
            });
            editor.addKeyListener(new KeyAdapter() {

                @Override
                public void keyPressed(KeyEvent e) {
                    if (comboBox.isDisplayable()) {
                        comboBox.setPopupVisible(true);
                    }
                }
            });
            // Handle initially selected object
            Object selected = comboBox.getSelectedItem();
            if (selected != null) {
                setText(selected.toString());
            }
            highlightCompletedText(0);
        }

        @Override
        public void remove(int offs, int len) throws BadLocationException {
            // return immediately when selecting an item
            if (selecting) {
                return;
            }
            super.remove(offs, len);
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
            // return immediately when selecting an item
            if (selecting) {
                return;
            }
            // insert the string into the document
            super.insertString(offs, str, a);
            // lookup and select a matching item
            Object item = lookupItem(getText(0, getLength()));
            if (item != null) {
                setSelectedItem(item);
            } else {
                // keep old item selected if there is no match
                item = getText(0, getLength());
                // imitate no insert (later on offs will be incremented by str.length(): selection won't move forward)
//                offs = offs - str.length();
                // provide feedback to the user that his input has been received but can not be accepted
//                comboBox.getToolkit().beep(); // when available use: UIManager.getLookAndFeel().provideErrorFeedback(comboBox);
            }
            setText(item.toString());
            // select the completed part
            highlightCompletedText(offs + str.length());
        }

        private void setText(String text) {
            try {
                // remove all text and insert the completed string
                super.remove(0, getLength());
                super.insertString(0, text, null);
            } catch (BadLocationException e) {
                throw new RuntimeException(e.toString());
            }
        }

        private void highlightCompletedText(int start) {
            editor.setCaretPosition(getLength());
            editor.moveCaretPosition(start);
        }

        private void setSelectedItem(Object item) {
            selecting = true;
            model.setSelectedItem(item);
            selecting = false;
        }

        private Object lookupItem(String pattern) {
            Object selectedItem = model.getSelectedItem();
            // only search for a different item if the currently selected does not match
            if (selectedItem != null && startsWithIgnoreCase(selectedItem.toString(), pattern)) {
                return selectedItem;
            } else {
                // iterate over all items
                for (int i = 0, n = model.getSize(); i < n; i++) {
                    Object currentItem = model.getElementAt(i);
                    // current item starts with the pattern?
                    if (startsWithIgnoreCase(currentItem.toString(), pattern)) {
                        return currentItem;
                    }
                }
            }
            // no item starts with the pattern => return null
            return null;
        }

        // checks if str1 starts with str2 - ignores case
        private boolean startsWithIgnoreCase(String str1, String str2) {
            return str1.toUpperCase().startsWith(str2.toUpperCase());
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea addressText;
    private javax.swing.JLabel agePetLabel;
    private org.jdesktop.swingx.JXDatePicker birthdayPet;
    private javax.swing.JTextField colorPetText;
    private javax.swing.JTextField emailText;
    private javax.swing.JRadioButton femaleRadio;
    private javax.swing.JLabel imgLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton maleRadio;
    private javax.swing.JTextField namePetText;
    private javax.swing.JTextField nameText;
    private javax.swing.JComboBox petBreedComboBox;
    private javax.swing.JComboBox petTypeComboBox;
    private javax.swing.JTextField phoneText;
    private javax.swing.JButton searchButton;
    private javax.swing.JTable searchTable;
    private javax.swing.JTextField searchText;
    // End of variables declaration//GEN-END:variables
}
