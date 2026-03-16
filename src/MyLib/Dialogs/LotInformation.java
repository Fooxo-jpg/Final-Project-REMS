package MyLib.Dialogs;


import MyLib.Classes.Models.*;
import MyLib.Classes.Services.*;

import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import javax.swing.*;

public class LotInformation extends javax.swing.JDialog {
    private MyApp.BuyerDashboard buyerDashboard;
    private MyLib.Classes.Models.Property currentProperty;
    private final DecimalFormat df = new DecimalFormat("#,##0.00");
    
    private boolean isEditing = false;
    private int currentImgIndex = 1;
    private final int MAX_IMAGES = 5;
    private final String userRole;
    
    // CONSTRUCTOR
    public LotInformation(java.awt.Frame parent, boolean modal, MyLib.Classes.Models.Property prop, String role) {
        super(parent, modal);
        initComponents();
        toggleEditMode(false);
        updateGallery();
        
        if (parent instanceof MyApp.BuyerDashboard buyerDashboard1) {
            this.buyerDashboard = buyerDashboard1;
        }
        
        this.currentProperty = prop;
        this.userRole = role;
        
        // MAX is 6 sinong tanga gagawa ng limang bathrooms
        // dedma sa basher pag mahilig ka tumae
        setFilteredField(bathTxt, "\\d*", 5);
        setFilteredField(bedTxt, "\\d*", 5);
        setFilteredField(sizeTxt, "\\d*\\.?\\d*", 6);
        
        String typeString = "";
        if (prop instanceof SingleAttached)
            typeString = "Single-Attached";
        else if (prop instanceof SingleDetached)
            typeString = "Single-Detached";
        else if (prop instanceof Townhouse)
            typeString = "Townhouse";
        else
            typeString = "Select Type";
        
        houseTypeCb.setSelectedItem(typeString);
        houseTypeLbl.setText(typeString);
        
        sizeTxt.setText(String.valueOf(prop.getLotArea()));
        sizeLbl.setText(String.valueOf(prop.getLotArea() + " sqm"));
        
        bedTxt.setText(String.valueOf(prop.getNumBedrooms()));
        bedLbl.setText(String.valueOf(prop.getNumBedrooms()));
        
        bathTxt.setText(String.valueOf(prop.getNumBathrooms()));
        bathLbl.setText(String.valueOf(prop.getNumBathrooms()));
        
        updateGallery();
        
        locationLbl.setText(prop.getPropertyID());
        agentLbl.setText(prop.getAssignedAgent());
        
        pricePerSQMLbl.setText("PHP " + df.format(prop.getPricePerSQM()));
        totalValueTxt.setText("PHP " + df.format(prop.calculatePricePerSqFt()));
        
        this.bathTxt.setText(String.valueOf(prop.getNumBathrooms()));
        this.bedTxt.setText(String.valueOf(prop.getNumBedrooms()));
        
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
            btn1.setText("Inquire");
            btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Contact.png")));
            btn1.setToolTipText("Register to contact Agents");
            
            btn2.setText("Add to Favorites");
            btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Fave_Black.png")));
            btn1.setToolTipText("Register to add to Favorites");
        
        } else { // BUYER VIEW
            User currentUser = AuthService.getCurrentUser();

            btn1.setText("Inquire");
            btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Contact.png")));

            if (currentUser != null && currentUser.getFavorites().contains(currentProperty)) {
                btn2.setText("Favorited");
                btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Fave_BlackFill.png")));
            } else {
                btn2.setText("Add to Favorites");
                btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Fave_Black.png")));
            }
        }
        
        if (!role.equalsIgnoreCase("Admin")) {
            btn2.setVisible(role.equalsIgnoreCase("Buyer"));
        }
    }
    
    //HELPERS
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
    
    // HELPER FOR ADMIN EDIT PROPERTY
    private void toggleEditMode(boolean active) {
        this.isEditing = active;

        JComponent[] inputs = {houseTypeCb, sizeTxt, bedTxt, bathTxt};
        JLabel[] labels = {houseTypeLbl, sizeLbl, bedLbl, bathLbl};

        for (int i = 0; i < inputs.length; i++) {
            inputs[i].setVisible(active);
            labels[i].setVisible(!active);
        }

        btn2.setText(active ? "Save Changes" : "Edit Property");

        if (active) {
            sizeTxt.requestFocus();
        }

        houseTypeLbl.setText(houseTypeCb.getSelectedItem().toString());
        sizeLbl.setText(sizeTxt.getText() + " sqm");
        bedLbl.setText(bedTxt.getText());
        bathLbl.setText(bathTxt.getText());
    }

    private void updateGallery() {
        String type = houseTypeCb.getSelectedItem().toString();
        
        if (type.equals("Select House Type")) {
            imageLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Images/Logo.png")));
            return;
        }
        
        String folderName = type.replace(" ", "");
        String path = "/MyLib/Images/" + folderName + "/" + currentImgIndex + ".jpg";
        java.net.URL imgURL = getClass().getResource(path);

        if (imgURL != null) {
            javax.swing.ImageIcon icon = new javax.swing.ImageIcon(imgURL);
            java.awt.Image img = icon.getImage();

            int targetWidth = 250;
            int imgWidth = img.getWidth(null);
            int imgHeight = img.getHeight(null);
            
            if (imgWidth > 0) {
                int newHeight = (imgHeight * targetWidth) / imgWidth;
                java.awt.Image scaledImg = img.getScaledInstance(targetWidth, newHeight, java.awt.Image.SCALE_SMOOTH);
                imageLbl.setIcon(new javax.swing.ImageIcon(scaledImg));
                imageLbl.setText("");
            }
        } else {
            if (currentImgIndex > 1) {
                currentImgIndex = 1;
                updateGallery();
            } else {
                imageLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Images/Logo.png")));
            }
        }
    }
    
    private void setFilteredField(javax.swing.JTextField field, String regex, int maxLength) {
        ((javax.swing.text.AbstractDocument) field.getDocument()).setDocumentFilter(new javax.swing.text.DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, javax.swing.text.AttributeSet attrs)
                    throws javax.swing.text.BadLocationException {

                // Get what the text WOULD look like after the change
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String futureText = currentText.substring(0, offset) + text + currentText.substring(offset + length);

                // Check if it matches the pattern AND doesn't exceed length
                if (futureText.matches(regex) && futureText.length() <= maxLength) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
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
        Size = new javax.swing.JLabel();
        agentLbl = new javax.swing.JLabel();
        pricePerSQMLbl = new javax.swing.JLabel();
        Header1 = new javax.swing.JLabel();
        btn1 = new javax.swing.JButton();
        btn2 = new javax.swing.JButton();
        Bathrooms = new javax.swing.JLabel();
        Bedrooms = new javax.swing.JLabel();
        TotalValue = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        Header2 = new javax.swing.JLabel();
        sizeTxt = new javax.swing.JTextField();
        bathTxt = new javax.swing.JTextField();
        bedTxt = new javax.swing.JTextField();
        locationLbl = new javax.swing.JLabel();
        totalValueTxt = new javax.swing.JLabel();
        houseTypeCb = new javax.swing.JComboBox<>();
        imageLbl = new javax.swing.JLabel();
        Buttons = new javax.swing.JPanel();
        prevBtn = new javax.swing.JButton();
        nextBtn = new javax.swing.JButton();
        houseTypeLbl = new javax.swing.JLabel();
        sizeLbl = new javax.swing.JLabel();
        bathLbl = new javax.swing.JLabel();
        bedLbl = new javax.swing.JLabel();
        logo = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

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

        Size.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        Size.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        Size.setText("Lot Size:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        buyerView.add(Size, gridBagConstraints);

        agentLbl.setText("None");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        buyerView.add(agentLbl, gridBagConstraints);

        pricePerSQMLbl.setText("PHP 4000.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        buyerView.add(pricePerSQMLbl, gridBagConstraints);

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
        btn1.setText("Inquire");
        btn1.setIconTextGap(10);
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
        btn2.setIconTextGap(10);
        btn2.addActionListener(this::btn2ActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.5;
        buyerView.add(btn2, gridBagConstraints);

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

        sizeTxt.setText("0 sqm");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buyerView.add(sizeTxt, gridBagConstraints);

        bathTxt.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buyerView.add(bathTxt, gridBagConstraints);

        bedTxt.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        buyerView.add(bedTxt, gridBagConstraints);

        locationLbl.setText("Block 0 Lot 0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        buyerView.add(locationLbl, gridBagConstraints);

        totalValueTxt.setText("PHP 0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.weighty = 0.5;
        buyerView.add(totalValueTxt, gridBagConstraints);

        houseTypeCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select House Type", "Single-Attached", "Single-Detached", "Townhouse" }));
        houseTypeCb.addItemListener(this::houseTypeCbItemStateChanged);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        buyerView.add(houseTypeCb, gridBagConstraints);

        imageLbl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageLbl.setMaximumSize(new java.awt.Dimension(250, 250));
        imageLbl.setMinimumSize(new java.awt.Dimension(250, 250));
        imageLbl.setPreferredSize(new java.awt.Dimension(250, 250));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        buyerView.add(imageLbl, gridBagConstraints);

        java.awt.GridBagLayout ButtonsLayout = new java.awt.GridBagLayout();
        ButtonsLayout.columnWidths = new int[] {0, 10, 0, 10, 0};
        ButtonsLayout.rowHeights = new int[] {0};
        Buttons.setLayout(ButtonsLayout);

        prevBtn.setText("Previous");
        prevBtn.addActionListener(this::prevBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        Buttons.add(prevBtn, gridBagConstraints);

        nextBtn.setText("Next");
        nextBtn.addActionListener(this::nextBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        Buttons.add(nextBtn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        buyerView.add(Buttons, gridBagConstraints);

        houseTypeLbl.setText("jLabel1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        buyerView.add(houseTypeLbl, gridBagConstraints);

        sizeLbl.setText("jLabel1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        buyerView.add(sizeLbl, gridBagConstraints);

        bathLbl.setText("jLabel2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        buyerView.add(bathLbl, gridBagConstraints);

        bedLbl.setText("jLabel3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        buyerView.add(bedLbl, gridBagConstraints);

        logo.setBackground(new java.awt.Color(36, 5, 2));

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Images/Logo.png"))); // NOI18N

        javax.swing.GroupLayout logoLayout = new javax.swing.GroupLayout(logo);
        logo.setLayout(logoLayout);
        logoLayout.setHorizontalGroup(
            logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
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
                .addComponent(buyerView, javax.swing.GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(buyerView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // ADMIN [EDIT PROPERTY] || BUYER [ADD TO FAVORITES]
    private void btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn2ActionPerformed
        if (userRole.equalsIgnoreCase("Admin")) {
            if (!isEditing) {
                toggleEditMode(true);
            } else {
                String selectedType = houseTypeCb.getSelectedItem().toString();
                
                // CONDITION: MUST HAVE HOUSE TYPE, DI KA NAMAN PWEDE MAGBENTA NG BAHAY KUNG WALANG BAHAY DIBA
                // MULTO YARN?
                if (selectedType.equals("Select House Type")) {
                    JOptionPane.showMessageDialog(this,
                            "Please select a valid House Type before listing/saving.",
                            "Validation Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Object[] options = {"Save Changes", "Cancel Changes"};
                int n = JOptionPane.showOptionDialog(this,
                        "Ready to list " + currentProperty.getPropertyID() + " with these details?",
                        "Confirm Publication",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null, options, options[0]);
                
                if (n == 0) { // SAVE CHANGES
                    try {
                        double newLotArea = Double.parseDouble(sizeTxt.getText());
                        int newBaths = Integer.parseInt(bathTxt.getText());
                        int newBeds = Integer.parseInt(bedTxt.getText());
                        
                        if (sizeTxt.getText().isEmpty() || Double.parseDouble(sizeTxt.getText()) <= 0) {
                            JOptionPane.showMessageDialog(this, "Lot Size must be greater than zero.");
                            return;
                        }

                        if (bathTxt.getText().isEmpty() || bedTxt.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Please specify the number of rooms.");
                            return;
                        }
                        
                        Property newProp;
                        switch (selectedType) {
                            case "Single-Attached" -> newProp = new SingleAttached(currentProperty.getPropertyID(), currentProperty.getStatus());
                            case "Single-Detached" -> newProp = new SingleDetached(currentProperty.getPropertyID(), currentProperty.getStatus());
                            case "Townhouse" -> newProp = new Townhouse(currentProperty.getPropertyID(), currentProperty.getStatus());
                            default -> {
                                newProp = new Property(currentProperty.getPropertyID(), "Available");
                            }
                        }
                        
                        newProp.setLotArea(newLotArea);
                        newProp.setNumBathrooms(newBaths);
                        newProp.setNumBedrooms(newBeds);
                        
                        String listerName = AuthService.getCurrentUser().getFirstName();
                        if (listerName == null || listerName.isEmpty()) {
                            listerName = AuthService.getCurrentUser().getEmail();
                        }
                        
                        newProp.setAssignedAgent(listerName);
                        newProp.setListed(true);
                        
                        PropertyService.updateProperty(newProp);
                        this.currentProperty = newProp;
                        
                        double total = currentProperty.calculatePricePerSqFt();
                        totalValueTxt.setText("PHP " + df.format(total));
                        agentLbl.setText(listerName);
                        
                        houseTypeLbl.setText(selectedType);
                        sizeLbl.setText(newLotArea + " sqm");
                        bedLbl.setText(String.valueOf(newBeds));
                        bathLbl.setText(String.valueOf(newBaths));
                        
                        JOptionPane.showMessageDialog(this, "Property Updated. [" + currentProperty.getPropertyID() + "]");
                        toggleEditMode(false);
                        this.dispose();
                        
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(this, "ERROR: Enter Valid Numbers.");
                    }
                } else { // CANCEL CHANGES
                    sizeTxt.setText(String.valueOf(currentProperty.getLotArea()));
                    bathTxt.setText(String.valueOf(currentProperty.getNumBathrooms()));
                    bedTxt.setText(String.valueOf(currentProperty.getNumBedrooms()));
                    
                    toggleEditMode(false);
                    JOptionPane.showMessageDialog(this, "Changes discarded.");
                }
            }
        } else if (userRole.equalsIgnoreCase("Buyer")) {
            User currentUser = AuthService.getCurrentUser();
            if (currentUser != null) {
                boolean isAlreadyFavorite = currentUser.getFavorites().contains(currentProperty);
                
                if (isAlreadyFavorite) {
                    currentUser.getFavorites().remove(currentProperty);
                    btn2.setText("Add to Favorites");
                    btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Fave_Black.png")));
                } else {
                    currentUser.getFavorites().add(currentProperty);
                    btn2.setText("Favorited");
                    btn2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Fave_BlackFill.png")));
                }
            }
        } else if (userRole.equalsIgnoreCase("Guest")) {
            showGuestRestriction();
        }
    }//GEN-LAST:event_btn2ActionPerformed

    // ADMIN [LIST] || BUYER [CONTACT]
    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        if (userRole.equalsIgnoreCase("Admin")) {
            boolean currentStatus = currentProperty.isListed();
            
            if (!currentStatus) { // LISTING THE PROPERTY
                String listerName = AuthService.getCurrentUser().getFirstName();
                if (listerName == null || listerName.isEmpty()) {
                    listerName = AuthService.getCurrentUser().getEmail();
                }
                
                currentProperty.setListed(true);
                currentProperty.setAssignedAgent(listerName);
                
                agentLbl.setText(listerName);
                btn1.setText("Unlist Property");
                btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/listBlack.png")));

                JOptionPane.showMessageDialog(this, "Property " + currentProperty.getPropertyID() + " is now listed!");

                this.dispose();
            } else {
                int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to unlist this property?", "Unlist Property", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    currentProperty.setListed(false);
                    currentProperty.setAssignedAgent("No Agent Assigned");
                    this.dispose();
                }
            }
        } else if (userRole.equalsIgnoreCase("Guest")) {
            showGuestRestriction();
        } else { // BUYER LOGIC HERE
            if (buyerDashboard != null) {
                buyerDashboard.showFinancialPanel(currentProperty);
                this.dispose();
            }
        }
    }//GEN-LAST:event_btn1ActionPerformed

    private void houseTypeCbItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_houseTypeCbItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            currentImgIndex = 1;
            updateGallery();
        }
    }//GEN-LAST:event_houseTypeCbItemStateChanged

    private void prevBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prevBtnActionPerformed
        if (currentImgIndex > 1) {
            currentImgIndex--;
        } else {
            currentImgIndex = MAX_IMAGES;
        }
        updateGallery();
    }//GEN-LAST:event_prevBtnActionPerformed

    private void nextBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nextBtnActionPerformed
        if (currentImgIndex < MAX_IMAGES) {
            currentImgIndex++;
        } else {
            currentImgIndex = 1;
        }
        updateGallery();
    }//GEN-LAST:event_nextBtnActionPerformed

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
    private javax.swing.JPanel Buttons;
    private javax.swing.JLabel Header1;
    private javax.swing.JLabel Header2;
    private javax.swing.JLabel HouseType;
    private javax.swing.JLabel Location;
    private javax.swing.JLabel PricePerSQM;
    private javax.swing.JLabel Size;
    private javax.swing.JLabel TotalValue;
    private javax.swing.JLabel agentLbl;
    private javax.swing.JLabel bathLbl;
    private javax.swing.JTextField bathTxt;
    private javax.swing.JLabel bedLbl;
    private javax.swing.JTextField bedTxt;
    private javax.swing.JButton btn1;
    private javax.swing.JButton btn2;
    private javax.swing.JPanel buyerView;
    private javax.swing.JComboBox<String> houseTypeCb;
    private javax.swing.JLabel houseTypeLbl;
    private javax.swing.JLabel imageLbl;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel locationLbl;
    private javax.swing.JPanel logo;
    private javax.swing.JButton nextBtn;
    private javax.swing.JButton prevBtn;
    private javax.swing.JLabel pricePerSQMLbl;
    private javax.swing.JLabel sizeLbl;
    private javax.swing.JTextField sizeTxt;
    private javax.swing.JLabel totalValueTxt;
    // End of variables declaration//GEN-END:variables
}
