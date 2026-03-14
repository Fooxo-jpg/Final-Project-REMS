package MyLib.Dialogs;

import MyLib.Classes.Models.Buyer;
import MyLib.Classes.Models.User;
import MyLib.Classes.Services.AuthService;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class LotInformation extends javax.swing.JDialog {
    private final MyLib.Classes.Models.Property currentProperty;
    private String userRole;
    
    public LotInformation(java.awt.Frame parent, boolean modal, MyLib.Classes.Models.Property prop, String role) {
        super(parent, modal);
        initComponents();
        
        this.currentProperty = prop;
        this.userRole = role;
        
        locationLbl.setText(prop.getPropertyID());
        agentLbl.setText(prop.getAssignedAgent());
        
        if (role.equalsIgnoreCase("Admin")) { // ADMIN VIEW
            if (prop.isListed()) {
                btn1.setText("Unlist Property");
                btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/listBlack.png")));
            } else {
                btn1.setText("List Property");
                btn1.setIcon(new ImageIcon(getClass().getResource("/MyLib/Icons/listAddBlack.png")));
            }
            
            btn2.setText("Edit Property");
            btn2.setIcon(new ImageIcon(getClass().getResource("/MyLib/Icons/Edit_black.png")));
        
            String currentLogin = AuthService.getCurrentUser().getFirstName();
            if (currentLogin == null || currentLogin.isEmpty()) {
                currentLogin = AuthService.getCurrentUser().getEmail();
            }
            
            if (!prop.getAssignedAgent().equals("No Agent Assigned") && !prop.getAssignedAgent().equals(currentLogin)){
                btn1.setEnabled(false);
                btn2.setEnabled(false);
                btn1.setToolTipText("Only the Admin who listed this (" + prop.getAssignedAgent() + ") can edit it.");
                btn2.setToolTipText("Only the Admin who listed this (" + prop.getAssignedAgent() + ") can edit it.");
            } else {
                btn1.setEnabled(true);
                btn2.setEnabled(true);
                btn1.setToolTipText(null);
                btn2.setToolTipText(null);
            }
        
        } else if (role.equalsIgnoreCase("Guest")) {
            btn1.setText("Contact");
            btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Contact.png")));
            btn1.setToolTipText("Register to contact Agents");
            
            btn2.setText("Add to Favorites");
            btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Fave_Black.png")));
            btn1.setToolTipText("Register to add to Favorites");
        
        } else { // BUYER VIEW
            btn1.setText("Contact");
            btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Contact.png")));

            btn2.setText("Add to Favorites");
            btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Fave_Black.png")));
        }
    }
    
    public void showGuestRestriction(){
        Object[] options = {"Cancel", "Register"};
        int selection = javax.swing.JOptionPane.showOptionDialog(this,
                "Please login to perform this action.",
                "Access Restricted",
                javax.swing.JOptionPane.YES_NO_OPTION,
                javax.swing.JOptionPane.WARNING_MESSAGE,
                null, options, options[1]);

        if (selection == 1) {
            this.dispose();
            javax.swing.SwingUtilities.getWindowAncestor(this).dispose(); // Close Dashboard
            new MyLib.Dialogs.Register(null, true).setVisible(true);
        }
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
        btn1 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        agentLbl.setText("None");
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

        btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Contact.png"))); // NOI18N
        btn1.setText("Contact");
        btn1.addActionListener(this::btn1ActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 47;
        gridBagConstraints.weightx = 0.5;
        buyerView.add(btn1, gridBagConstraints);

        btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Fave_Black.png"))); // NOI18N
        btn2.setText("Add to Favorites");
        btn2.addActionListener(this::btn2ActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        buyerView.add(btn2, gridBagConstraints);

        javax.swing.GroupLayout imageLayout = new javax.swing.GroupLayout(image);
        image.setLayout(imageLayout);
        imageLayout.setHorizontalGroup(
            imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 267, Short.MAX_VALUE)
        );
        imageLayout.setVerticalGroup(
            imageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 433, Short.MAX_VALUE)
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

        logo.setBackground(new java.awt.Color(36, 5, 2));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Images/Logo.png"))); // NOI18N

        javax.swing.GroupLayout logoLayout = new javax.swing.GroupLayout(logo);
        logo.setLayout(logoLayout);
        logoLayout.setHorizontalGroup(
            logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        logoLayout.setVerticalGroup(
            logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 127, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(buyerView, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buyerView, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // ADMIN [EDIT PROPERTY]
    // BUYER [ADD TO FAVORITES]
    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
        if (userRole.equalsIgnoreCase("Admin")) {
            
        } else if (userRole.equalsIgnoreCase("Buyer")) {
            User currentUser = AuthService.getCurrentUser();
            
            if (currentUser instanceof Buyer buyer){ // Downcasting
                buyer.addFavorite(currentProperty);
                JOptionPane.showMessageDialog(this,
                        currentProperty.getPropertyID() + " added to Favorites!");
                this.dispose();
                
            }
        } else if (userRole.equalsIgnoreCase("Guest")) {
            showGuestRestriction();
            return;
        }
    }//GEN-LAST:event_btn2ActionPerformed

    // ADMIN [LIST]
    // BUYER [CONTACT]
    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        if (userRole.equalsIgnoreCase("Admin")) {
            boolean currentStatus = currentProperty.isListed();
            
            if (!currentStatus) {
                String listerName = AuthService.getCurrentUser().getFirstName();
                if (listerName == null || listerName.isEmpty()) {
                    listerName = AuthService.getCurrentUser().getEmail();
                }
                
                currentProperty.setAssignedAgent(listerName);
                currentProperty.setListed(true);
                javax.swing.JOptionPane.showMessageDialog(this, "Property Listed by " + listerName);
            } else {
                currentProperty.setListed(false);
                currentProperty.setAssignedAgent("No Agent Assigned");
                javax.swing.JOptionPane.showMessageDialog(this, "Property Unlisted.");
            }
            
            this.dispose();
        } else if (userRole.equalsIgnoreCase("Guest")) {
            showGuestRestriction();
            return;
        } else {
            // BUYER LOGIC
        }
    }//GEN-LAST:event_btn1ActionPerformed

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
            MyLib.Classes.Models.Property testProp = new MyLib.Classes.Models.Property("B1-L1", "Available");
            LotInformation dialog = new LotInformation(new javax.swing.JFrame(), true, testProp, "Admin");

            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Agent;
    private javax.swing.JLabel Bathrooms;
    private javax.swing.JLabel Bedrooms;
    private javax.swing.JLabel Header1;
    private javax.swing.JLabel Header2;
    private javax.swing.JLabel HouseType;
    private javax.swing.JLabel Location;
    private javax.swing.JLabel PricePerSQM;
    private javax.swing.JLabel Size;
    private javax.swing.JLabel TotalValue;
    private javax.swing.JLabel agentLbl;
    private javax.swing.JLabel bathroomsLbl;
    private javax.swing.JLabel bedroomLbl;
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn2;
    private javax.swing.JPanel buyerView;
    private javax.swing.JLabel houseTypeLbl;
    private javax.swing.JPanel image;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel locationLbl;
    private javax.swing.JPanel logo;
    private javax.swing.JLabel priceSqmLbl;
    private javax.swing.JLabel sizeLbl;
    private javax.swing.JLabel totalValueLbl;
    // End of variables declaration//GEN-END:variables
}
