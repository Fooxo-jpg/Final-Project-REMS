package MyLib.Panels;

import MyLib.Classes.Models.Property;
import MyLib.Classes.Services.AuthService;
import MyLib.Classes.Services.PropertyService;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author ymnis
 */
public final class OverviewPanel extends javax.swing.JPanel {

    public OverviewPanel() {
        initComponents();
        populateLots();
    }
    
    // HELPERS
    public void populateLots(){
        JPanel[] blocks = {BlockOnePanel, BlockTwoPanel, BlockThreePanel, BlockFourPanel, BlockFivePanel};
        String role = AuthService.getCurrentUser().getRole();
        
        for (int block = 0; block < blocks.length; block++) {
            blocks[block].removeAll();
            int blockNum = block + 1;
            
            for (int lot = 1; lot <= 20; lot++) {
                final int lotNum = lot;
                Property prop = PropertyService.getProperty(blockNum, lot);
                JButton lotBtn = new JButton("L" + lot);
                
                if (role.equalsIgnoreCase("Admin")) {
                    lotBtn.setEnabled(true);
                    if (prop.isListed()) {
                        lotBtn.setBackground(new java.awt.Color(144, 238, 144)); // Listed (Green)
                    } else {
                        lotBtn.setBackground(Color.LIGHT_GRAY);
                    }
                } else {
                    if (prop.isListed()) {
                        lotBtn.setEnabled(true);
                        switch (prop.getStatus().toUpperCase()) {
                            case "AVAILABLE" ->
                                lotBtn.setBackground(new java.awt.Color(144, 238, 144));
                            case "RESERVED" ->
                                lotBtn.setBackground(java.awt.Color.YELLOW);
                            case "SOLD" ->
                                lotBtn.setBackground(new java.awt.Color(255, 102, 102));
                        }
                    } else {
                        lotBtn.setEnabled(false);
                        lotBtn.setBackground(Color.LIGHT_GRAY);
                        lotBtn.setText("");
                    }
                }
                
                lotBtn.addActionListener(e -> {
                    System.out.println("Property ID: " + prop.getPropertyID() + " | Status: " + prop.getStatus());
                    selectionLabel.setText("Block " + blockNum + " Lot " + lotNum);
                });
                
                lotBtn.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(java.awt.event.MouseEvent e){
                        if (e.getClickCount() == 2) {
                            java.awt.Frame parentFrame = (java.awt.Frame) SwingUtilities.getWindowAncestor(OverviewPanel.this);
                            String userRole = AuthService.getCurrentUser().getRole();
                            
                            MyLib.Dialogs.LotInformation dialog = new MyLib.Dialogs.LotInformation(parentFrame, true, prop, userRole);
                            dialog.setVisible(true);
                            populateLots();
                        }
                    }
                });
                
                blocks[block].add(lotBtn);
            }
            blocks[block].revalidate();
            blocks[block].repaint();
        }
        
        updateStatistics();
    }
    
    private void updateStatistics() {
        int total = PropertyService.getTotalProperties();
        int available = PropertyService.getCountByStatus("Available");
        int reserved = PropertyService.getCountByStatus("Reserved");
        int sold = PropertyService.getCountByStatus("Sold");
        
        totalLbl.setText("Total: " + String.valueOf(total));
        availableLbl.setText("Available: " + String.valueOf(available));
        reservedLbl.setText("Reserved: " + String.valueOf(reserved));
        soldLbl.setText("Sold: " + String.valueOf(sold));
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jButton20 = new javax.swing.JButton();
        lotSelectorPanel = new javax.swing.JPanel();
        BlockFourPanel = new javax.swing.JPanel();
        BlockThreePanel = new javax.swing.JPanel();
        BlockFivePanel = new javax.swing.JPanel();
        BlockTwoPanel = new javax.swing.JPanel();
        BlockOnePanel = new javax.swing.JPanel();
        footerPanel = new javax.swing.JPanel();
        availableLbl = new javax.swing.JLabel();
        houseTypeLbl = new javax.swing.JLabel();
        lotSizeLbl = new javax.swing.JLabel();
        GSPLbl = new javax.swing.JLabel();
        selectionLabel = new javax.swing.JLabel();
        totalLbl = new javax.swing.JLabel();
        soldLbl = new javax.swing.JLabel();
        reservedLbl = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        jButton20.setText("jButton11");

        lotSelectorPanel.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel2Layout.rowHeights = new int[] {0, 10, 0, 10, 0};
        lotSelectorPanel.setLayout(jPanel2Layout);

        BlockFourPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BLOCK 4", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 15))); // NOI18N
        BlockFourPanel.setOpaque(false);
        BlockFourPanel.setLayout(new java.awt.GridLayout(10, 2, 3, 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 1.1;
        lotSelectorPanel.add(BlockFourPanel, gridBagConstraints);

        BlockThreePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BLOCK 3", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 15))); // NOI18N
        BlockThreePanel.setOpaque(false);
        BlockThreePanel.setLayout(new java.awt.GridLayout(10, 2, 3, 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 1.1;
        lotSelectorPanel.add(BlockThreePanel, gridBagConstraints);

        BlockFivePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BLOCK 5", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 15))); // NOI18N
        BlockFivePanel.setOpaque(false);
        BlockFivePanel.setLayout(new java.awt.GridLayout(10, 2, 3, 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 1.1;
        lotSelectorPanel.add(BlockFivePanel, gridBagConstraints);

        BlockTwoPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BLOCK 2", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 15))); // NOI18N
        BlockTwoPanel.setOpaque(false);
        BlockTwoPanel.setLayout(new java.awt.GridLayout(10, 2, 3, 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 1.1;
        lotSelectorPanel.add(BlockTwoPanel, gridBagConstraints);

        BlockOnePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "BLOCK 1", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 15))); // NOI18N
        BlockOnePanel.setOpaque(false);
        BlockOnePanel.setLayout(new java.awt.GridLayout(10, 2, 3, 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 1.1;
        lotSelectorPanel.add(BlockOnePanel, gridBagConstraints);

        footerPanel.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout footerPanelLayout = new java.awt.GridBagLayout();
        footerPanelLayout.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        footerPanelLayout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0};
        footerPanel.setLayout(footerPanelLayout);

        availableLbl.setBackground(new java.awt.Color(217, 111, 118));
        availableLbl.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        availableLbl.setText("Available: 0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        footerPanel.add(availableLbl, gridBagConstraints);

        houseTypeLbl.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        houseTypeLbl.setText("House Type:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        footerPanel.add(houseTypeLbl, gridBagConstraints);

        lotSizeLbl.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        lotSizeLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lotSizeLbl.setText("Lot Size: 0 sqm");
        lotSizeLbl.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        footerPanel.add(lotSizeLbl, gridBagConstraints);

        GSPLbl.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        GSPLbl.setText("GSP: PHP 0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        footerPanel.add(GSPLbl, gridBagConstraints);

        selectionLabel.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        selectionLabel.setText("Block 0 Lot 0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        footerPanel.add(selectionLabel, gridBagConstraints);

        totalLbl.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        totalLbl.setText("Total: 0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        footerPanel.add(totalLbl, gridBagConstraints);

        soldLbl.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        soldLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        soldLbl.setText("Sold: 0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        footerPanel.add(soldLbl, gridBagConstraints);

        reservedLbl.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        reservedLbl.setText("Reserved: 0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        footerPanel.add(reservedLbl, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel1.setText("CORNERSTONE ESTATE SUBDIVISION");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(footerPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
            .addComponent(lotSelectorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(lotSelectorPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(footerPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    protected javax.swing.JPanel BlockFivePanel;
    protected javax.swing.JPanel BlockFourPanel;
    protected javax.swing.JPanel BlockOnePanel;
    protected javax.swing.JPanel BlockThreePanel;
    protected javax.swing.JPanel BlockTwoPanel;
    private javax.swing.JLabel GSPLbl;
    private javax.swing.JLabel availableLbl;
    private javax.swing.JPanel footerPanel;
    private javax.swing.JLabel houseTypeLbl;
    private javax.swing.JButton jButton20;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel lotSelectorPanel;
    private javax.swing.JLabel lotSizeLbl;
    private javax.swing.JLabel reservedLbl;
    private javax.swing.JLabel selectionLabel;
    private javax.swing.JLabel soldLbl;
    private javax.swing.JLabel totalLbl;
    // End of variables declaration//GEN-END:variables
}
