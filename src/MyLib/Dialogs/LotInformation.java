/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package MyLib.Dialogs;

import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author ymnis
 */
public class LotInformation extends javax.swing.JDialog {

    public LotInformation(JFrame jFrame, boolean par, String buyer) {
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buyerView = new javax.swing.JPanel();
        Location = new javax.swing.JLabel();
        HouseType = new javax.swing.JLabel();
        PricePerSQM = new javax.swing.JLabel();
        Agent = new javax.swing.JLabel();
        houseTypeLbl = new javax.swing.JLabel();
        Size = new javax.swing.JLabel();
        sizeLbl = new javax.swing.JLabel();
        priceSqmLbl = new javax.swing.JLabel();
        agentLbl = new javax.swing.JLabel();
        locationLbl = new javax.swing.JLabel();
        Header1 = new javax.swing.JLabel();
        contactBtn = new javax.swing.JButton();
        favBtn = new javax.swing.JButton();
        image = new javax.swing.JPanel();
        Bathrooms = new javax.swing.JLabel();
        bathroomsLbl = new javax.swing.JLabel();
        Bedrooms = new javax.swing.JLabel();
        bedroomLbl = new javax.swing.JLabel();
        TotalValue = new javax.swing.JLabel();
        totalValueLbl = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Header2 = new javax.swing.JLabel();
        logo = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        AdminView = new javax.swing.JPanel();
        Location1 = new javax.swing.JLabel();
        HouseType1 = new javax.swing.JLabel();
        PricePerSQM1 = new javax.swing.JLabel();
        Agent1 = new javax.swing.JLabel();
        houseTypeLbl1 = new javax.swing.JLabel();
        Size1 = new javax.swing.JLabel();
        sizeLbl1 = new javax.swing.JLabel();
        priceSqmLbl1 = new javax.swing.JLabel();
        agentLbl1 = new javax.swing.JLabel();
        locationLbl1 = new javax.swing.JLabel();
        Header3 = new javax.swing.JLabel();
        contactBtn1 = new javax.swing.JButton();
        favBtn1 = new javax.swing.JButton();
        image1 = new javax.swing.JPanel();
        Bathrooms1 = new javax.swing.JLabel();
        bathroomsLbl1 = new javax.swing.JLabel();
        Bedrooms1 = new javax.swing.JLabel();
        bedroomLbl1 = new javax.swing.JLabel();
        TotalValue1 = new javax.swing.JLabel();
        totalValueLbl1 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        Header4 = new javax.swing.JLabel();

        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel2Layout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        buyerView.setLayout(jPanel2Layout);

        Location.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Location.setText("Location:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        buyerView.add(Location, gridBagConstraints);

        HouseType.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        HouseType.setText("House Type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        buyerView.add(HouseType, gridBagConstraints);

        PricePerSQM.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        PricePerSQM.setText("Price/sqm:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        buyerView.add(PricePerSQM, gridBagConstraints);

        Agent.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Agent.setText("Agent:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        buyerView.add(Agent, gridBagConstraints);

        houseTypeLbl.setText("CORNERSTONE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        buyerView.add(houseTypeLbl, gridBagConstraints);

        Size.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Size.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Size.setText("Size:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        buyerView.add(Size, gridBagConstraints);

        sizeLbl.setText("0 sqm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        buyerView.add(sizeLbl, gridBagConstraints);

        priceSqmLbl.setText("PHP 4,000.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        buyerView.add(priceSqmLbl, gridBagConstraints);

        agentLbl.setText("Consultant");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        buyerView.add(agentLbl, gridBagConstraints);

        locationLbl.setText("Block 0 Lot 0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        buyerView.add(locationLbl, gridBagConstraints);

        Header1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Header1.setText("HOUSE INFORMATION");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        buyerView.add(Header1, gridBagConstraints);

        contactBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Contact.png"))); // NOI18N
        contactBtn.setText("Contact");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 47;
        gridBagConstraints.weightx = 0.5;
        buyerView.add(contactBtn, gridBagConstraints);

        favBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Fave_Black.png"))); // NOI18N
        favBtn.setText("Add to Favorites");
        favBtn.addActionListener(this::favBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        buyerView.add(favBtn, gridBagConstraints);

        javax.swing.GroupLayout imageLayout = new javax.swing.GroupLayout(image);
        image.setLayout(imageLayout);
        imageLayout.setHorizontalGroup(
            imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 237, Short.MAX_VALUE)
        );
        imageLayout.setVerticalGroup(
            imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 25;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        buyerView.add(image, gridBagConstraints);

        Bathrooms.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Bathrooms.setText("Bathrooms:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        buyerView.add(Bathrooms, gridBagConstraints);

        bathroomsLbl.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        buyerView.add(bathroomsLbl, gridBagConstraints);

        Bedrooms.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Bedrooms.setText("Bedrooms:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        buyerView.add(Bedrooms, gridBagConstraints);

        bedroomLbl.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        buyerView.add(bedroomLbl, gridBagConstraints);

        TotalValue.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        TotalValue.setText("Total Value:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        buyerView.add(TotalValue, gridBagConstraints);

        totalValueLbl.setText("PHP 0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        buyerView.add(totalValueLbl, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        buyerView.add(jSeparator1, gridBagConstraints);

        Header2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Header2.setText("EXTRA DETAILS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        buyerView.add(Header2, gridBagConstraints);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        logo.setBackground(new java.awt.Color(36, 5, 2));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Images/Logo.png"))); // NOI18N

        javax.swing.GroupLayout logoLayout = new javax.swing.GroupLayout(logo);
        logo.setLayout(logoLayout);
        logoLayout.setHorizontalGroup(
            logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 733, Short.MAX_VALUE)
        );
        logoLayout.setVerticalGroup(
            logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(logoLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        AdminView.setLayout(new java.awt.GridBagLayout());

        Location1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Location1.setText("Location:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        AdminView.add(Location1, gridBagConstraints);

        HouseType1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        HouseType1.setText("House Type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        AdminView.add(HouseType1, gridBagConstraints);

        PricePerSQM1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        PricePerSQM1.setText("Price/sqm:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        AdminView.add(PricePerSQM1, gridBagConstraints);

        Agent1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Agent1.setText("Agent:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        AdminView.add(Agent1, gridBagConstraints);

        houseTypeLbl1.setText("CORNERSTONE");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        AdminView.add(houseTypeLbl1, gridBagConstraints);

        Size1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Size1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Size1.setText("Size:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        AdminView.add(Size1, gridBagConstraints);

        sizeLbl1.setText("0 sqm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        AdminView.add(sizeLbl1, gridBagConstraints);

        priceSqmLbl1.setText("PHP 4,000.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        AdminView.add(priceSqmLbl1, gridBagConstraints);

        agentLbl1.setText("Consultant");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        AdminView.add(agentLbl1, gridBagConstraints);

        locationLbl1.setText("Block 0 Lot 0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        AdminView.add(locationLbl1, gridBagConstraints);

        Header3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Header3.setText("HOUSE INFORMATION");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        AdminView.add(Header3, gridBagConstraints);

        contactBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Edit_black.png"))); // NOI18N
        contactBtn1.setText("Edit Property");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 47;
        gridBagConstraints.weightx = 0.5;
        AdminView.add(contactBtn1, gridBagConstraints);

        favBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Fave_Black.png"))); // NOI18N
        favBtn1.setText("List");
        favBtn1.addActionListener(this::favBtn1ActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        AdminView.add(favBtn1, gridBagConstraints);

        javax.swing.GroupLayout image1Layout = new javax.swing.GroupLayout(image1);
        image1.setLayout(image1Layout);
        image1Layout.setHorizontalGroup(
            image1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 370, Short.MAX_VALUE)
        );
        image1Layout.setVerticalGroup(
            image1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 373, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 25;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        AdminView.add(image1, gridBagConstraints);

        Bathrooms1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Bathrooms1.setText("Bathrooms:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        AdminView.add(Bathrooms1, gridBagConstraints);

        bathroomsLbl1.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        AdminView.add(bathroomsLbl1, gridBagConstraints);

        Bedrooms1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Bedrooms1.setText("Bedrooms:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        AdminView.add(Bedrooms1, gridBagConstraints);

        bedroomLbl1.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        AdminView.add(bedroomLbl1, gridBagConstraints);

        TotalValue1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        TotalValue1.setText("Total Value:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        AdminView.add(TotalValue1, gridBagConstraints);

        totalValueLbl1.setText("PHP 0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
        AdminView.add(totalValueLbl1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        AdminView.add(jSeparator2, gridBagConstraints);

        Header4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Header4.setText("EXTRA DETAILS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        AdminView.add(Header4, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(AdminView, javax.swing.GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(405, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(133, Short.MAX_VALUE)
                    .addComponent(AdminView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void favBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favBtnActionPerformed

    }//GEN-LAST:event_favBtnActionPerformed

    private void favBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_favBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_favBtn1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            com.formdev.flatlaf.FlatLightLaf.setup();
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        UIManager.put("Button.arc", 15);
        UIManager.put("Component.arc", 15);

        java.awt.EventQueue.invokeLater(() -> {
            LotInformation dialog = new LotInformation(new javax.swing.JFrame(), true, "Buyer");
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AdminView;
    private javax.swing.JLabel Agent;
    private javax.swing.JLabel Agent1;
    private javax.swing.JLabel Bathrooms;
    private javax.swing.JLabel Bathrooms1;
    private javax.swing.JLabel Bedrooms;
    private javax.swing.JLabel Bedrooms1;
    private javax.swing.JLabel Header1;
    private javax.swing.JLabel Header2;
    private javax.swing.JLabel Header3;
    private javax.swing.JLabel Header4;
    private javax.swing.JLabel HouseType;
    private javax.swing.JLabel HouseType1;
    private javax.swing.JLabel Location;
    private javax.swing.JLabel Location1;
    private javax.swing.JLabel PricePerSQM;
    private javax.swing.JLabel PricePerSQM1;
    private javax.swing.JLabel Size;
    private javax.swing.JLabel Size1;
    private javax.swing.JLabel TotalValue;
    private javax.swing.JLabel TotalValue1;
    private javax.swing.JLabel agentLbl;
    private javax.swing.JLabel agentLbl1;
    private javax.swing.JLabel bathroomsLbl;
    private javax.swing.JLabel bathroomsLbl1;
    private javax.swing.JLabel bedroomLbl;
    private javax.swing.JLabel bedroomLbl1;
    private javax.swing.JPanel buyerView;
    private javax.swing.JButton contactBtn;
    private javax.swing.JButton contactBtn1;
    private javax.swing.JButton favBtn;
    private javax.swing.JButton favBtn1;
    private javax.swing.JLabel houseTypeLbl;
    private javax.swing.JLabel houseTypeLbl1;
    private javax.swing.JPanel image;
    private javax.swing.JPanel image1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel locationLbl;
    private javax.swing.JLabel locationLbl1;
    private javax.swing.JPanel logo;
    private javax.swing.JLabel priceSqmLbl;
    private javax.swing.JLabel priceSqmLbl1;
    private javax.swing.JLabel sizeLbl;
    private javax.swing.JLabel sizeLbl1;
    private javax.swing.JLabel totalValueLbl;
    private javax.swing.JLabel totalValueLbl1;
    // End of variables declaration//GEN-END:variables
}
