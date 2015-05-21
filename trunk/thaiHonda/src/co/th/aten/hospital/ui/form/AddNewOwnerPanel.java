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
import co.th.aten.hospital.util.Util;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.table.DefaultTableModel;
import org.springframework.richclient.application.Application;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import javax.swing.*;
import javax.swing.text.*;
import org.joda.time.DateTime;
import org.joda.time.Months;
import org.joda.time.Years;

/**
 *
 * @author Atenpunk
 */
public class AddNewOwnerPanel extends javax.swing.JPanel {

    private final Log logger = LogFactory.getLog(getClass());
    private List<PetModel> petModelList;
    private OwnerManager ownerManager;
    private PetManager petManager;
    private TypeManager typeManager;
    private BreedManager breedManager;
    private SessionManager sessionManager;
    private File fileImg;

    /** Creates new form AddNewOwnerPanel */
    public AddNewOwnerPanel() {
        petModelList = new ArrayList<PetModel>();
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
        petTypeComboBox.setFont(new Font("Tahoma", 0, 11));
        petBreedComboBox.setFont(new Font("Tahoma", 0, 11));
        petTable.setFont(new Font("Tahoma", 0, 11));
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
        petTypeComboBox.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JComboBox jcmbType = (JComboBox) e.getSource();
                List<BreedModel> breedList = breedManager.getBreedListOrderByEngName(jcmbType.getSelectedIndex() + 1);
                if (breedList != null) {
                    petBreedComboBox.removeAllItems();
                    for (BreedModel model : breedList) {
                        petBreedComboBox.addItem(model.getEngName());
                    }
                    petBreedComboBox.setSelectedIndex(-1);
                    petBreedComboBox.setEditable(true);
                    new MainTest(petBreedComboBox);
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
        jScrollPane2 = new javax.swing.JScrollPane();
        petTable = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        imgLabel = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        namePetText = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        colorPetText = new javax.swing.JTextField();
        maleRadio = new javax.swing.JRadioButton();
        femaleRadio = new javax.swing.JRadioButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        petTypeComboBox = new javax.swing.JComboBox();
        petBreedComboBox = new javax.swing.JComboBox();
        jLabel11 = new javax.swing.JLabel();
        birthdayPet = new org.jdesktop.swingx.JXDatePicker();
        jLabel12 = new javax.swing.JLabel();
        agePetLabel = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

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

        petTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Type", "Breed", "Sex", "Color"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        petTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane2.setViewportView(petTable);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Pet");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(emailText, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                            .addComponent(nameText, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
                            .addComponent(phoneText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
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

        jButton3.setFont(new java.awt.Font("Tahoma", 0, 12));
        jButton3.setText("Clear");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 12));
        jButton4.setText("Add");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Load Image");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Birthday");

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 12));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel12.setText("Age");

        agePetLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        agePetLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        agePetLabel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(imgLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(maleRadio)
                            .addGap(18, 18, 18)
                            .addComponent(femaleRadio))
                        .addComponent(petBreedComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(petTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(namePetText, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(birthdayPet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(agePetLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(colorPetText)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(imgLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton5)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(maleRadio)
                                    .addComponent(femaleRadio))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(colorPetText, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(32, 32, 32))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(namePetText, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(petTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(petBreedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(birthdayPet, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(agePetLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(1, 1, 1)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(160, Short.MAX_VALUE))
        );

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12));
        jButton1.setText("Save");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 12));
        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(169, 169, 169)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(139, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void clearDataPet() {
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
    }

    private void clearDataOwner() {
        petModelList = new ArrayList<PetModel>();
        nameText.setText("");
        addressText.setText("");
        phoneText.setText("");
        emailText.setText("");
        DefaultTableModel model = (DefaultTableModel) petTable.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
        fileImg = null;
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

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        clearDataPet();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        clearDataOwner();
        clearDataPet();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        if (namePetText.getText() != null && namePetText.getText().trim().length() > 0) {
            PetModel model = new PetModel();
            model.setName(namePetText.getText());
            model.setType((String) petTypeComboBox.getSelectedItem());
            model.setBreed((String) petBreedComboBox.getSelectedItem());
            String sex = "";
            if (maleRadio.isSelected()) {
                sex = "Male";
            } else if (femaleRadio.isSelected()) {
                sex = "Female";
            }
            model.setSex(sex);
            model.setColor(colorPetText.getText());
            model.setImage(fileImg != null ? readImage(fileImg) : null);
            model.setBirthdayPet(birthdayPet.getDate());
            petModelList.add(model);
            DefaultTableModel modelTable = (DefaultTableModel) petTable.getModel();
            Object[] row = {model.getName(), model.getType(), model.getBreed(), model.getSex(), model.getColor()};
            modelTable.addRow(row);
            clearDataPet();
        } else {
            JOptionPane.showMessageDialog(this, "Please insert name pet");
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int saveConfirm = JOptionPane.showConfirmDialog(null, "Confirm save new owner?", "Confirm Save", JOptionPane.OK_OPTION | JOptionPane.CANCEL_OPTION);
        if (saveConfirm == JOptionPane.OK_OPTION) {
            if (nameText.getText() != null && nameText.getText().trim().length() > 0) {
                OwnerModel modelOwner = new OwnerModel();
                modelOwner.setId(ownerManager.getMaxOwnerId() + 1);
                modelOwner.setName(nameText.getText());
                modelOwner.setAddress(addressText.getText());
                modelOwner.setPhoneNumber(phoneText.getText());
                modelOwner.setEmail(emailText.getText());
                modelOwner.setCreateBy(sessionManager.getUser().getUserName());
                modelOwner.setCreateDate(new Date());
                modelOwner.setUpdateBy(sessionManager.getUser().getUserName());
                modelOwner.setUpdateDate(new Date());
                boolean chkInsertOwner = ownerManager.insertOwner(modelOwner);
                if (chkInsertOwner) {
                    for (PetModel petModel : petModelList) {
                        petModel.setId(petManager.getMaxOwnerId() + 1);
                        petModel.setOwnerId(modelOwner.getId());
                        petModel.setCreateBy(sessionManager.getUser().getUserName());
                        petModel.setCreateDate(new Date());
                        petModel.setUpdateBy(sessionManager.getUser().getUserName());
                        petModel.setUpdateDate(new Date());
                        petManager.insertPet(petModel);
                    }
                    clearDataOwner();
                    clearDataPet();
                    JOptionPane.showMessageDialog(this, "Save new owner complete");
                } else {
                    JOptionPane.showMessageDialog(this, "Save new owner Fail");
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
            if (file != null) {
                BufferedImage originalImage = ImageIO.read(file);
                int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
                BufferedImage resizeImageJpg = resizeImage(originalImage, type);
                ImageIcon imgPet = new ImageIcon(resizeImageJpg);
                imgLabel.setText("");
                imgLabel.setIcon(imgPet);
                fileImg = new File(file.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private static BufferedImage resizeImage(BufferedImage originalImage, int type) {
        BufferedImage resizedImage = new BufferedImage(146, 134, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 146, 134, null);
        g.dispose();
        return resizedImage;
    }

    private byte[] getByteResizeImage(File file) {
        try {
            BufferedImage originalImage = ImageIO.read(file);
            int type = originalImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : originalImage.getType();
            BufferedImage resizedImage = new BufferedImage(400, 350, type);
            Graphics2D g = resizedImage.createGraphics();
            g.drawImage(originalImage, 0, 0, 400, 350, null);
            g.dispose();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            ImageIO.write(resizedImage, "jpg", buffer);
            return buffer.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
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
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JRadioButton maleRadio;
    private javax.swing.JTextField namePetText;
    private javax.swing.JTextField nameText;
    private javax.swing.JComboBox petBreedComboBox;
    private javax.swing.JTable petTable;
    private javax.swing.JComboBox petTypeComboBox;
    private javax.swing.JTextField phoneText;
    // End of variables declaration//GEN-END:variables
}
