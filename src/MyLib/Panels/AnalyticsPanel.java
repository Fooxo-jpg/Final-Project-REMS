package MyLib.Panels;

import MyLib.Classes.Models.*;
import MyLib.Classes.Services.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import javax.swing.table.DefaultTableModel;

public class AnalyticsPanel extends javax.swing.JPanel {
    private final DecimalFormat df = new DecimalFormat("#,##0.00");
    private final SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy");

    public AnalyticsPanel() {
        initComponents();
        
        // ROUNDED CORNERS
        ProfitPanel.putClientProperty("FlatLaf.style", "arc: 20");
        SalesPanel.putClientProperty("FlatLaf.style", "arc: 20");
        PropertySoldPanel.putClientProperty("FlatLaf.style", "arc: 20");
        ClientsPanel.putClientProperty("FlatLaf.style", "arc: 20");
        topContribPanel.putClientProperty("FlatLaf.style", "arc: 20");
    
        refreshAnalytics();
        
        this.addAncestorListener(new javax.swing.event.AncestorListener() {
            @Override
            public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                refreshAnalytics();
            }

            @Override
            public void ancestorRemoved(javax.swing.event.AncestorEvent e) {
            }

            @Override
            public void ancestorMoved(javax.swing.event.AncestorEvent e) {
            }
        });
    }
    
    // HELPERS
    public void refreshAnalytics() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        // Map to store Agent Name -> Total Profit
        Map<String, Double> agentProfits = new HashMap<>();

        // Find EVERY agent that exists in the subdivision map
        for (int block = 1; block <= 5; block++) {
            for (int lot = 1; lot <= 20; lot++) {
                Property p = PropertyService.getProperty(block, lot);
                if (p != null) {
                    String agent = p.getAssignedAgent();
                    // Initialize them with 0 profit if they aren't "No Agent Assigned"
                    if (agent != null && !agent.equals("No Agent Assigned")) {
                        agentProfits.putIfAbsent(agent, 0.0);
                    }
                }
            }
        }

        // Calculate actual profits from successful transactions
        for (Transaction t : PropertyService.getAllTransactions()) {
            String status = t.getStatus();
            if (status.equalsIgnoreCase("Sold") || status.equalsIgnoreCase("Loan Approved") || status.equalsIgnoreCase("Finalized")) {
                String agentName = t.getProperty().getAssignedAgent();

                if (agentName != null && agentProfits.containsKey(agentName)) {
                    double propertyPrice = t.getProperty().calculatePricePerSqFt();
                    agentProfits.put(agentName, agentProfits.get(agentName) + propertyPrice);
                }
            }
        }

        // Sort by Profit (Highest to Lowest)
        List<Map.Entry<String, Double>> sortedAgents = agentProfits.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .collect(Collectors.toList());

        // Populate Table
        for (Map.Entry<String, Double> entry : sortedAgents) {
            model.addRow(new Object[]{
                entry.getKey(),
                "PHP " + df.format(entry.getValue())
            });
        }

        // Update summary cards
        updateSummaryStats(agentProfits);
    }

    private void updateSummaryStats(Map<String, Double> profits) {
        double totalSales = profits.values().stream().mapToDouble(Double::doubleValue).sum();
        long soldCount = PropertyService.getAllTransactions().stream()
                .filter(t -> t.getStatus().contains("Sold") || t.getStatus().contains("Approved")).count();

        totSalesLbl.setText("PHP " + df.format(totalSales)); // Total Sales
        totProfitLbl.setText("PHP " + df.format(totalSales * 0.05)); // Total Profit (Assuming 5% commission)
        propSoldLbl.setText(String.valueOf(soldCount)); // Property Sold
        ClientsLbl.setText(String.valueOf(PropertyService.getAllTransactions().size())); // Total Clients

        dateLbl.setText("DATE: " + new java.text.SimpleDateFormat("MMMM dd, yyyy").format(new Date()));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        ClientsPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        ClientsLbl = new javax.swing.JLabel();
        topContribPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        dateLbl = new javax.swing.JLabel();
        PropertySoldPanel = new javax.swing.JPanel();
        propSoldLbl = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        SalesPanel = new javax.swing.JPanel();
        totSalesLbl = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        ProfitPanel = new javax.swing.JPanel();
        TotalProfit = new javax.swing.JLabel();
        totProfitLbl = new javax.swing.JLabel();

        java.awt.GridBagLayout layout1 = new java.awt.GridBagLayout();
        layout1.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        layout1.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0};
        setLayout(layout1);

        ClientsPanel.setBackground(new java.awt.Color(36, 5, 2));
        java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
        jPanel1Layout.columnWidths = new int[] {0, 10, 0, 10, 0};
        jPanel1Layout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0};
        ClientsPanel.setLayout(jPanel1Layout);

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 200, 102));
        jLabel6.setText("Total Clients:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        ClientsPanel.add(jLabel6, gridBagConstraints);

        ClientsLbl.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        ClientsLbl.setForeground(new java.awt.Color(255, 255, 255));
        ClientsLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ClientsLbl.setText("0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        ClientsPanel.add(ClientsLbl, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 81;
        gridBagConstraints.ipady = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(ClientsPanel, gridBagConstraints);

        topContribPanel.setBackground(new java.awt.Color(36, 5, 2));
        java.awt.GridBagLayout topContribPanelLayout = new java.awt.GridBagLayout();
        topContribPanelLayout.columnWidths = new int[] {0, 10, 0, 10, 0};
        topContribPanelLayout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0};
        topContribPanel.setLayout(topContribPanelLayout);

        jLabel4.setBackground(new java.awt.Color(36, 5, 2));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 200, 102));
        jLabel4.setText("TOP CONTRIBUTORS");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.weightx = 1.0;
        topContribPanel.add(jLabel4, gridBagConstraints);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Agent", "Profit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setFocusable(false);
        jTable1.setRowHeight(50);
        jScrollPane1.setViewportView(jTable1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        topContribPanel.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 417;
        gridBagConstraints.ipady = 295;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(topContribPanel, gridBagConstraints);

        jPanel7.setBackground(new java.awt.Color(255, 200, 102));

        jButton1.setText("Export PDF");

        jButton3.setText("Export CSV");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(310, Short.MAX_VALUE))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 87;
        gridBagConstraints.ipady = 233;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jPanel7, gridBagConstraints);

        java.awt.GridBagLayout jPanel8Layout = new java.awt.GridBagLayout();
        jPanel8Layout.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel8Layout.rowHeights = new int[] {0, 10, 0, 10, 0};
        jPanel8.setLayout(jPanel8Layout);

        dateLbl.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        dateLbl.setText("DATE: MONTH-DAY-YEAR");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        jPanel8.add(dateLbl, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 9;
        gridBagConstraints.ipady = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jPanel8, gridBagConstraints);

        PropertySoldPanel.setBackground(new java.awt.Color(36, 5, 2));
        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWidths = new int[] {0, 10, 0, 10, 0};
        jPanel3Layout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0};
        PropertySoldPanel.setLayout(jPanel3Layout);

        propSoldLbl.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        propSoldLbl.setForeground(new java.awt.Color(255, 255, 255));
        propSoldLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        propSoldLbl.setText("0");
        propSoldLbl.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        PropertySoldPanel.add(propSoldLbl, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 200, 102));
        jLabel9.setText("Property Sold:");
        jLabel9.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        PropertySoldPanel.add(jLabel9, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 81;
        gridBagConstraints.ipady = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(PropertySoldPanel, gridBagConstraints);

        SalesPanel.setBackground(new java.awt.Color(36, 5, 2));
        java.awt.GridBagLayout jPanel9Layout = new java.awt.GridBagLayout();
        jPanel9Layout.columnWidths = new int[] {0, 10, 0, 10, 0};
        jPanel9Layout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0};
        SalesPanel.setLayout(jPanel9Layout);

        totSalesLbl.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        totSalesLbl.setForeground(new java.awt.Color(255, 255, 255));
        totSalesLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totSalesLbl.setText("PHP 0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        SalesPanel.add(totSalesLbl, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 200, 102));
        jLabel3.setText("Total Sales:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        SalesPanel.add(jLabel3, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 81;
        gridBagConstraints.ipady = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(SalesPanel, gridBagConstraints);

        ProfitPanel.setBackground(new java.awt.Color(36, 5, 2));
        java.awt.GridBagLayout jPanel10Layout = new java.awt.GridBagLayout();
        jPanel10Layout.columnWidths = new int[] {0, 10, 0, 10, 0};
        jPanel10Layout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0};
        ProfitPanel.setLayout(jPanel10Layout);

        TotalProfit.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        TotalProfit.setForeground(new java.awt.Color(255, 200, 102));
        TotalProfit.setText("Total Profit:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        ProfitPanel.add(TotalProfit, gridBagConstraints);

        totProfitLbl.setFont(new java.awt.Font("Segoe UI", 0, 20)); // NOI18N
        totProfitLbl.setForeground(new java.awt.Color(255, 255, 255));
        totProfitLbl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        totProfitLbl.setText("PHP 0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        ProfitPanel.add(totProfitLbl, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 81;
        gridBagConstraints.ipady = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(ProfitPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ClientsLbl;
    private javax.swing.JPanel ClientsPanel;
    private javax.swing.JPanel ProfitPanel;
    private javax.swing.JPanel PropertySoldPanel;
    private javax.swing.JPanel SalesPanel;
    private javax.swing.JLabel TotalProfit;
    private javax.swing.JLabel dateLbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel propSoldLbl;
    private javax.swing.JPanel topContribPanel;
    private javax.swing.JLabel totProfitLbl;
    private javax.swing.JLabel totSalesLbl;
    // End of variables declaration//GEN-END:variables
}
