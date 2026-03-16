package MyLib.Panels;

import MyLib.Classes.Models.*;
import MyLib.Classes.Services.*;
import java.text.DecimalFormat;
import javax.swing.table.*;

public class FinancialPanel extends javax.swing.JPanel {
    private Property property;
    private final DecimalFormat df = new DecimalFormat("#,##0.00");
    
    public FinancialPanel(Property p) {
        this.property = p;
        initComponents();
        setupInfoTable();
        methodCb.setSelectedIndex(0);
    }
    
    // HELPERS
    private void setupInfoTable() {
        DefaultTableModel model = (DefaultTableModel) infoTable.getModel();
        model.setRowCount(0);
        model.addRow(new Object[]{"Location", property.getPropertyID()});
        model.addRow(new Object[]{"Lot Area", property.getLotArea() + " sqm"});
        model.addRow(new Object[]{"Floor Area", property.getFloorArea() + " sqm"});
        model.addRow(new Object[]{"Model Type", property.getClass().getSimpleName()});
        model.addRow(new Object[]{"Status", property.getStatus()});
    }
    
    private void updateCalculations() {
        String method = methodCb.getSelectedItem().toString();
        DefaultTableModel compModel = (DefaultTableModel) compTable.getModel();
        DefaultTableModel loanModel = (DefaultTableModel) loanTable.getModel();

        compModel.setRowCount(0);
        loanModel.setRowCount(0);

        if (method.equals("Select Method")) {
            return;
        }

        double rawBasePrice = property.calculatePricePerSqFt();
        double markupAmount = rawBasePrice * FinancialCalculator.PROFIT_MARKUP;
        double nsp = FinancialCalculator.calculateNSP(property);
        double vatAmount = nsp * FinancialCalculator.VAT_RATE;
        double lmfAmount = nsp * FinancialCalculator.LMF_RATE;
        double tcp = FinancialCalculator.calculateTCP(property);

        compModel.addRow(new Object[]{"Base Property Cost", "PHP " + df.format(rawBasePrice)});
        compModel.addRow(new Object[]{"Developer Markup (15%)", "PHP " + df.format(markupAmount)});
        compModel.addRow(new Object[]{"NET SELLING PRICE (NSP)", "PHP " + df.format(nsp)});
        compModel.addRow(new Object[]{"----------------------", "----------------------"});
        compModel.addRow(new Object[]{"VAT (12%)", "PHP " + df.format(vatAmount)});
        compModel.addRow(new Object[]{"Legal & Misc Fees (10%)", "PHP " + df.format(lmfAmount)});
        compModel.addRow(new Object[]{"TOTAL CONTRACT PRICE", "PHP " + df.format(tcp)});
        compModel.addRow(new Object[]{"----------------------", "----------------------"});

        if (method.equals("Spot Cash")) {
            compModel.addRow(new Object[]{"Payment Method", "Spot Cash"});
            setupSpotCash(tcp, loanModel);
            return;
        }

        double interestRate = 0;
        double dpPercent = 0;
        int maxYears = 20;

        switch (method) {
            case "Bank - BDO" -> {
                interestRate = FinancialCalculator.BDO_INTEREST;
                dpPercent = FinancialCalculator.BDO_DP_PERCENT;
            }
            case "Bank - RCBC" -> {
                interestRate = FinancialCalculator.RCBC_INTEREST;
                dpPercent = FinancialCalculator.RCBC_DP_PERCENT;
            }
            case "In-House Financing" -> {
                interestRate = FinancialCalculator.INHOUSE_INTEREST;
                dpPercent = FinancialCalculator.INHOUSE_DP_PERCENT;
                maxYears = FinancialCalculator.INHOUSE_MAX_YEARS;
            }
        }

        double totalDP = tcp * dpPercent;
        double loanAmount = tcp - totalDP;

        compModel.addRow(new Object[]{"Downpayment (" + (int) (dpPercent * 100) + "%)", "PHP " + df.format(totalDP)});
        compModel.addRow(new Object[]{"Loanable Amount", "PHP " + df.format(loanAmount)});
        compModel.addRow(new Object[]{"Annual Interest Rate", (interestRate * 100) + "%"});

        int[] years = {20, 15, 10, 5};
        for (int y : years) {
            if (y > maxYears) {
                continue;
            }
            double monthly = calculateCustomAmortization(loanAmount, interestRate, y);
            loanModel.addRow(new Object[]{y + " Years", "PHP " + df.format(monthly)});
        }
    }

    private void setupSpotCash(double tcp, DefaultTableModel loanModel) {
        double discountRate = 0.10; // 10% Discount
        double discountAmount = tcp * discountRate;
        double finalPrice = tcp - discountAmount;

        loanModel.addRow(new Object[]{"Total Contract Price", "PHP " + df.format(tcp)});
        loanModel.addRow(new Object[]{"Cash Discount (10%)", "- PHP " + df.format(discountAmount)});
        loanModel.addRow(new Object[]{"----------------------", "----------------------"});
        loanModel.addRow(new Object[]{"NET AMOUNT DUE", "PHP " + df.format(finalPrice)});
    }

    private double calculateCustomAmortization(double principal, double annualRate, int years) {
        if (principal <= 0) {
            return 0;
        }
        double monthlyRate = annualRate / 12;
        int months = years * 12;
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, months))
                / (Math.pow(1 + monthlyRate, months) - 1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        infoTable = new javax.swing.JTable();
        methodCb = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        compTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        loanTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel1.setText("PAYMENT");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
        jPanel1Layout.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel1Layout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel1.setLayout(jPanel1Layout);

        infoTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Location", null},
                {"Lot Area", null},
                {"Floor Area", null},
                {"Model Type", null},
                {"Status", null}
            },
            new String [] {
                "Property Information", "Details"
            }
        ));
        infoTable.setRowHeight(35);
        infoTable.setRowSelectionAllowed(false);
        jScrollPane1.setViewportView(infoTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jScrollPane1, gridBagConstraints);

        methodCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Method", "Spot Cash", "Bank - BDO", "Bank - RCBC", "In-House Financing" }));
        methodCb.addActionListener(this::methodCbActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(methodCb, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setText("Financial Method");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(jLabel2, gridBagConstraints);

        compTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Description", "Amount"
            }
        ));
        compTable.setRowHeight(35);
        compTable.setShowHorizontalLines(true);
        jScrollPane2.setViewportView(compTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jScrollPane2, gridBagConstraints);

        loanTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Terms", "Monthly Payment"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        loanTable.setRowHeight(35);
        loanTable.setShowHorizontalLines(true);
        jScrollPane3.setViewportView(loanTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jScrollPane3, gridBagConstraints);

        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWidths = new int[] {0, 10, 0};
        jPanel3Layout.rowHeights = new int[] {0};
        jPanel3.setLayout(jPanel3Layout);

        jButton1.setText("Buy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(jButton1, gridBagConstraints);

        jButton2.setText("Reserve");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(jButton2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel3, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void methodCbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_methodCbActionPerformed
        updateCalculations();
    }//GEN-LAST:event_methodCbActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable compTable;
    private javax.swing.JTable infoTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable loanTable;
    private javax.swing.JComboBox<String> methodCb;
    // End of variables declaration//GEN-END:variables
}
