package MyLib.Panels;

import MyLib.Classes.Models.*;
import MyLib.Classes.Services.*;
import MyLib.Dialogs.TransactionReceipt;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.*;

public class FinancialPanel extends javax.swing.JPanel {
    private final Property property;
    private final DecimalFormat df = new DecimalFormat("#,##0.00");
    
    public FinancialPanel(Property p) {
        this.property = p;
        initComponents();
        
        setupInfoTable();
        AmortizationCb.setSelectedIndex(0);
    }
    
    // HELPERS
    private void setupInfoTable() {
        DefaultTableModel model = (DefaultTableModel) infoTable.getModel();
        model.setRowCount(0);

        // --- STANDARD DATA ---
        model.addRow(new Object[]{"Location", property.getPropertyID()});
        model.addRow(new Object[]{"Model Type", property.getClass().getSimpleName()});
        model.addRow(new Object[]{"Lot Area", property.getLotArea() + " sqm"});
        model.addRow(new Object[]{"Floor Area", property.getFloorArea() + " sqm"});
        model.addRow(new Object[]{"Bedrooms", property.getNumBedrooms()});
        model.addRow(new Object[]{"Bathrooms", property.getNumBathrooms()});

        // --- SPECIAL SUBCLASS DATA ---
        if (property instanceof SingleDetached sd) {
            model.addRow(new Object[]{"Garden Area", sd.getGardenArea() + " sqm"});
            model.addRow(new Object[]{"Bank Distance", sd.getBankDistance() + " km"});
        } else if (property instanceof SingleAttached sa) {
            model.addRow(new Object[]{"Shared Wall Side", sa.getSharedWallSide()});
            model.addRow(new Object[]{"Firewall Status", sa.HasFireWall() ? "Installed" : "None"});
        } else if (property instanceof Townhouse th) {
            model.addRow(new Object[]{"Unit Position", th.getUnitPosition()});
            model.addRow(new Object[]{"Total Floors", th.getNumFloors()});
        }

        model.addRow(new Object[]{"Status", property.getStatus()});
    }
    
    private void updateCalculations() {
        String financingMethod = AmortizationCb.getSelectedItem().toString();
        String dpMethod = DPMethodCb.getSelectedItem().toString();

        DefaultTableModel compModel = (DefaultTableModel) compTable.getModel();
        compModel.setRowCount(0);

        if (financingMethod.equals("Select Method")) {
            return;
        }

        double nsp = FinancialCalculator.calculateNSP(property);
        double tcp = FinancialCalculator.calculateTCP(property);

        double interestRate = 0;
        double dpPercent = 0;
        int maxYears = 20;

        switch (financingMethod) {
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

        compModel.addRow(new Object[]{"--- PROPERTY PRICING ---", ""});
        compModel.addRow(new Object[]{"Net Selling Price", "PHP " + df.format(nsp)});
        compModel.addRow(new Object[]{"Total Contract Price", "PHP " + df.format(tcp)});
        compModel.addRow(new Object[]{"Reservation Fee (Fixed)", "PHP " + df.format(FinancialCalculator.RESERVATION_FEE)});

        compModel.addRow(new Object[]{"--- DOWNPAYMENT PLAN ---", dpMethod});
        compModel.addRow(new Object[]{"Total DP (" + (int) (dpPercent * 100) + "%)", "PHP " + df.format(totalDP)});
        if (dpMethod.equals("Spot Cash")) {
            compModel.addRow(new Object[]{"DP Payment", "Full Payment / Check"});
        } else {
            compModel.addRow(new Object[]{"Monthly DP (18 mos)", "PHP " + df.format(totalDP / 18)});
        }

        compModel.addRow(new Object[]{"--- AMORTIZATION OPTIONS ---", financingMethod});
        compModel.addRow(new Object[]{"Loanable Amount", "PHP " + df.format(loanAmount)});
        compModel.addRow(new Object[]{"Interest Rate", (interestRate * 100) + "%"});

        int[] terms = {20, 15, 10, 5};
        for (int y : terms) {
            if (y > maxYears) {
                continue;
            }
            double monthly = calculateCustomAmortization(loanAmount, interestRate, y);
            compModel.addRow(new Object[]{y + " Years Term", "PHP " + df.format(monthly)});
        }
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        infoTable = new javax.swing.JTable();
        AmortizationCb = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        compTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        buyBtn = new javax.swing.JButton();
        reserveBtn = new javax.swing.JButton();
        dpMethodCb = new javax.swing.JComboBox<>();
        DPMethodCb = new javax.swing.JComboBox<>();

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
        jPanel1Layout.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel1Layout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        infoTable.setRowHeight(35);
        infoTable.setRowSelectionAllowed(false);
        jScrollPane1.setViewportView(infoTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jScrollPane1, gridBagConstraints);

        AmortizationCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select Method", "Bank - BDO", "Bank - RCBC", "In-House Financing" }));
        AmortizationCb.addActionListener(this::AmortizationCbActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        jPanel1.add(AmortizationCb, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel2.setText("Financial Method");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
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
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        compTable.setRowHeight(35);
        compTable.setShowHorizontalLines(true);
        jScrollPane2.setViewportView(compTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(jScrollPane2, gridBagConstraints);

        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWidths = new int[] {0, 10, 0};
        jPanel3Layout.rowHeights = new int[] {0};
        jPanel3.setLayout(jPanel3Layout);

        buyBtn.setText("Buy");
        buyBtn.addActionListener(this::buyBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(buyBtn, gridBagConstraints);

        reserveBtn.setText("Reserve");
        reserveBtn.addActionListener(this::reserveBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.weightx = 1.0;
        jPanel3.add(reserveBtn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel3, gridBagConstraints);

        dpMethodCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DP Installment (18 mos)", "Spot Downpayment" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 15;
        jPanel1.add(dpMethodCb, gridBagConstraints);

        DPMethodCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Cash", "Check" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 15;
        jPanel1.add(DPMethodCb, gridBagConstraints);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 529, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void AmortizationCbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AmortizationCbActionPerformed
        updateCalculations();
    }//GEN-LAST:event_AmortizationCbActionPerformed

    private void buyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buyBtnActionPerformed
        String financingMethod = AmortizationCb.getSelectedItem().toString();
        String dpPlan = dpMethodCb.getSelectedItem().toString();
        String payInstrument = DPMethodCb.getSelectedItem().toString();

        if (financingMethod.equals("Select Method")) {
            JOptionPane.showMessageDialog(this, "Please select a financing method.");
            return;
        }

        String[] terms = financingMethod.equals("In-House Financing") ? new String[]{"5 Years", "10 Years"} : new String[]{"5 Years", "10 Years", "15 Years", "20 Years"};
        String selectedYear = (String) JOptionPane.showInputDialog(this, "Choose Amortization Term:", "Plan Selection", JOptionPane.QUESTION_MESSAGE, null, terms, terms[0]);
        if (selectedYear == null) return;

        double tcp = FinancialCalculator.calculateTCP(property);
        double dpPercent = switch (financingMethod) {
            case "Bank - BDO" ->
                FinancialCalculator.BDO_DP_PERCENT;
            case "Bank - RCBC" ->
                FinancialCalculator.RCBC_DP_PERCENT;
            case "In-House Financing" ->
                FinancialCalculator.INHOUSE_DP_PERCENT;
            default ->
                0.15;
        };

        double interestRate = switch (financingMethod) {
            case "Bank - BDO" ->
                FinancialCalculator.BDO_INTEREST;
            case "Bank - RCBC" ->
                FinancialCalculator.RCBC_INTEREST;
            case "In-House Financing" ->
                FinancialCalculator.INHOUSE_INTEREST;
            default ->
                0.07;
        };

        double totalDP = tcp * dpPercent;
        double loanAmount = tcp - totalDP;
        double initialPayment;
        double reservationDeduction = property.getStatus().equalsIgnoreCase("Reserved") ? FinancialCalculator.RESERVATION_FEE : 0;

        if (dpPlan.equals("Spot Downpayment")) {
            initialPayment = totalDP - reservationDeduction;
        } else {
            initialPayment = (totalDP / 18); // Monthly DP installment
        }

        int years = Integer.parseInt(selectedYear.split(" ")[0]);
        double monthlyAmort = calculateCustomAmortization(loanAmount, interestRate, years);

        // IN-HOUSE LOAN INPUTS (Income & Account Check) ---
        double income = 0;
        if (financingMethod.equalsIgnoreCase("In-House Financing")) {
            String incomeInput = JOptionPane.showInputDialog(this, "In-House Financing requires Income Verification.\nPlease enter your Annual Income:");
            if (incomeInput == null || incomeInput.trim().isEmpty()) {
                return;
            }
            try {
                income = Double.parseDouble(incomeInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid income amount.");
                return;
            }
        }
        
        Payment paymentDetail;
        if (payInstrument.equalsIgnoreCase("Check")) {
            Check check = new Check();

            // Ask for Bank Name
            String bank = JOptionPane.showInputDialog(this, "Enter Issuing Bank Name:");
            if (bank == null || bank.trim().isEmpty()) {
                return;
            }

            // Ask for Account Number
            String accInput = JOptionPane.showInputDialog(this, "Enter Check Account Number:");
            if (accInput == null || accInput.trim().isEmpty()) {
                return;
            }

            try {
                long accNo = Long.parseLong(accInput.trim());
                check.setAccNo((int) accNo);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid Account Number. Please enter digits only.");
                return;
            }

            check.setBankName(bank);
            check.setCheckNo(IDGenerator.generateCheckNumber());
            check.setReferenceNumber(IDGenerator.generateReferenceNumber()); // Set the reference for the payment

            paymentDetail = check;
        } else {
            Cash cash = new Cash();
            cash.setReferenceNumber(IDGenerator.generateReferenceNumber());
            paymentDetail = cash;
        }

        String confirmMsg = String.format("""
                                      FINAL PURCHASE CONFIRMATION
                                      Property: %s
                                      
                                      Total DP: PHP %s
                                      %s
                                      MONTHLY AMORTIZATION: PHP %s
                                      ------------------------------
                                      INITIAL PAYMENT DUE: PHP %s
                                      """,
                property.getPropertyID(), df.format(totalDP),
                (reservationDeduction > 0 ? "Less Reservation: -PHP " + df.format(reservationDeduction) : ""),
                df.format(monthlyAmort), df.format(initialPayment));

        if (JOptionPane.showConfirmDialog(this, confirmMsg, "Proceed?", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            paymentDetail.processPayment();
            
            Transaction trx = new Transaction(
                    AuthService.getCurrentUser().getEmail(),
                    property,
                    financingMethod,
                    initialPayment,
                    paymentDetail,
                    selectedYear,
                    monthlyAmort,
                    income
            );
            
            trx.setTransactionID(IDGenerator.generateTransactionID());
            
            PropertyService.finalizeSale(trx);

            java.awt.Frame parent = (java.awt.Frame) SwingUtilities.getWindowAncestor(this);
            new TransactionReceipt(parent, true, trx, paymentDetail).setVisible(true);

            if (parent instanceof MyApp.BuyerDashboard dashboard) {
                dashboard.showCard("overview_view");
                dashboard.refreshCurrentPanel();
            }
        }
    }//GEN-LAST:event_buyBtnActionPerformed

    private void reserveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reserveBtnActionPerformed
        if (!property.getStatus().equalsIgnoreCase("Available")) {
            JOptionPane.showMessageDialog(this, "Property is no longer available for reservation.");
            return;
        }

        String[] options = {"Cash", "Check"};
        int choice = JOptionPane.showOptionDialog(this,
                "How would you like to pay the PHP " + df.format(FinancialCalculator.RESERVATION_FEE) + " Reservation Fee?",
                "Reservation Payment", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == -1) {
            return;
        }
        
        property.reserveProperty(AuthService.getCurrentUser().getEmail());
        JOptionPane.showMessageDialog(this, "Property " + property.getPropertyID() + " is now RESERVED for you.\nReservation expires in 30 days.");

        java.awt.Window ancestor = SwingUtilities.getWindowAncestor(this);
        if (ancestor instanceof MyApp.BuyerDashboard dashboard) {
            dashboard.showCard("overview_view");
            dashboard.refreshCurrentPanel();
        }
    }//GEN-LAST:event_reserveBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> AmortizationCb;
    private javax.swing.JComboBox<String> DPMethodCb;
    private javax.swing.JButton buyBtn;
    private javax.swing.JTable compTable;
    private javax.swing.JComboBox<String> dpMethodCb;
    private javax.swing.JTable infoTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton reserveBtn;
    // End of variables declaration//GEN-END:variables
}
