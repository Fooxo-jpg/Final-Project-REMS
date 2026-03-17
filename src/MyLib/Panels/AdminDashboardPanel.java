package MyLib.Panels;

import MyLib.Classes.Models.Property;
import MyLib.Classes.Models.Transaction;
import MyLib.Classes.Services.PropertyService;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author ymnis
 */
public class AdminDashboardPanel extends javax.swing.JPanel {

    public AdminDashboardPanel() {
        initComponents();
        updateTable();
        
        DefaultTableModel model = (DefaultTableModel) propertyTable.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        propertyTable.setRowSorter(sorter);
        
        searchTxt.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                applyFilters(sorter);
            }

            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                applyFilters(sorter);
            }

            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                applyFilters(sorter);
            }
        });
        
        filterCb.addActionListener(e -> applyFilters(sorter));
        
        propertyTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int row = propertyTable.getSelectedRow();
                if (row != -1) {
                    String loc = propertyTable.getValueAt(row, 0).toString();
                    // Split "B1-L5" to get block and lot
                    String[] parts = loc.replace("B", "").replace("L", "").split("-");
                    Property p = PropertyService.getProperty(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
                    displayPropertyDetails(p);
                }
            }
        });
    }
    
    // HELPER
    private void handleLoanApproval(Transaction trx) {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        
        double monthlyIncome = trx.getAnnualIncome() / 12;
        double amortRatio = (trx.getMonthlyAmortization() / monthlyIncome) * 100;

        String warningMessage = "";
        if (amortRatio > 40) {
            warningMessage = "\n⚠️ WARNING: Amortization is " + df.format(amortRatio)
                    + "% of monthly income! (High Risk)";
        }
        
        String message = String.format("""
            IN-HOUSE LOAN REQUEST
            Buyer: %s | Property: %s
            Monthly Income: PHP %s
            Monthly Amortization: PHP %s
            Ratio: %s%% of income %s
            
            Do you want to APPROVE this loan?""",
                trx.getBuyerUsername(),
                trx.getProperty().getPropertyID(),
                df.format(monthlyIncome),
                df.format(trx.getMonthlyAmortization()),
                df.format(amortRatio),
                warningMessage);

        String[] options = {"Approve", "Decline", "Wait"};
        
        int choice = JOptionPane.showOptionDialog(this, message, "Loan Management",
                JOptionPane.DEFAULT_OPTION, (amortRatio > 40 ? JOptionPane.WARNING_MESSAGE : JOptionPane.QUESTION_MESSAGE),
                null, options, options[0]);

        if (choice == 0) { // APPROVE
            trx.setStatus("Finalized");
            trx.getProperty().setStatus("Sold");
            JOptionPane.showMessageDialog(this, "Loan Approved.");
        } else if (choice == 1) { // DECLINE
            trx.getProperty().setStatus("Available");
            trx.getProperty().setReservedBy(null);
            PropertyService.getAllTransactions().remove(trx);
            JOptionPane.showMessageDialog(this, "Loan Declined. Property reset to Available.");
        }
        updateTable();
    }
    
    private void displayPropertyDetails(Property p) {
        DecimalFormat df = new DecimalFormat("#,##0.00");

        jLabel1.setText(p.getPropertyID());
        propNameLbl.setText(p.getClass().getSimpleName());
        lotAreaLbl.setText(p.getLotArea() + " SQM");
        floorAreaLbl.setText(p.getFloorArea() + " SQM");

        double reservationFee = MyLib.Classes.Services.FinancialCalculator.RESERVATION_FEE;
        double tsp = MyLib.Classes.Services.FinancialCalculator.calculateNSP(p); // NSP is the base Selling Price
        double tcp = MyLib.Classes.Services.FinancialCalculator.calculateTCP(p); // TCP includes Misc Fees

        double dpPercent = 0.15;

        Transaction existingTrx = null;
        for (Transaction t : PropertyService.getAllTransactions()) {
            if (t.getProperty().getPropertyID().equals(p.getPropertyID())) {
                existingTrx = t;
                break;
            }
        }

        double downPayment;
        if (existingTrx != null) {
            downPayment = existingTrx.getInitialPayment();
        } else {
            downPayment = tcp * dpPercent;
        }

        double loanableAmount = tcp - downPayment;

        tspLbl.setText("PHP " + df.format(tsp)); // Total Selling Price (Net)
        reservationLbl.setText("PHP " + df.format(reservationFee));
        dpLbl.setText("PHP " + df.format(downPayment));
        loanableLbl.setText("PHP " + df.format(loanableAmount));
        tcpLbl.setText("PHP " + df.format(tcp)); // Total Contract Price
    }
    
    public void updateTable() {
        DecimalFormat df = new DecimalFormat("#,##0.00");
        String[] columns = {"Location", "House Type", "Assigned Agent", "Client", "Status", "Price"};

        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        for (int block = 1; block <= 5; block++) {
            for (int lot = 1; lot <= 20; lot++) {
                Property p = PropertyService.getProperty(block, lot);
                
                String client = (p.getReservedBy() != null) ? p.getReservedBy() : "None";
                
                Object[] rowData = {
                    p.getPropertyID(),
                    p.getClass().getSimpleName(),
                    p.getAssignedAgent(),
                    client,
                    p.getStatus(),
                    "PHP " + df.format(p.calculatePricePerSqFt())
                };
                model.addRow(rowData);
            }
        }
        propertyTable.setModel(model);
    }
    
    private void applyFilters(TableRowSorter<DefaultTableModel> sorter) {
        String text = searchTxt.getText();
        String status = filterCb.getSelectedItem().toString();

        List<RowFilter<Object, Object>> filters = new ArrayList<>();

        if (text.trim().length() > 0) {
            filters.add(RowFilter.regexFilter("(?i)" + text));
        }

        if (!status.equals("Show All") && !status.equals("Select Status")) {
            filters.add(RowFilter.regexFilter("^" + status + "$", 4));
        }

        if (filters.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filters));
        }
    }

    private void exportToCSV(java.io.File file) {
        try (java.io.FileWriter fw = new java.io.FileWriter(file)) {
            DefaultTableModel model = (DefaultTableModel) propertyTable.getModel();

            for (int i = 0; i < model.getColumnCount(); i++) {
                fw.write(model.getColumnName(i) + (i == model.getColumnCount() - 1 ? "" : ","));
            }
            fw.write("\n");

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    fw.write(model.getValueAt(i, j).toString().replace(",", "") + (j == model.getColumnCount() - 1 ? "" : ","));
                }
                fw.write("\n");
            }
            JOptionPane.showMessageDialog(this, "CSV Report Generated successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error exporting CSV: " + e.getMessage());
        }
    }
    
    private void exportToExcel(java.io.File file) {
        try (java.io.FileWriter fw = new java.io.FileWriter(file)) {
            DefaultTableModel model = (DefaultTableModel) propertyTable.getModel();

            for (int i = 0; i < model.getColumnCount(); i++) {
                fw.write(model.getColumnName(i) + "\t");
            }
            fw.write("\n");

            for (int i = 0; i < model.getRowCount(); i++) {
                for (int j = 0; j < model.getColumnCount(); j++) {
                    fw.write(model.getValueAt(i, j).toString() + "\t");
                }
                fw.write("\n");
            }
            JOptionPane.showMessageDialog(this, "Excel Report Generated successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error exporting Excel: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        propertyTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        Address = new javax.swing.JLabel();
        PropertyName = new javax.swing.JLabel();
        LotArea = new javax.swing.JLabel();
        TotalSellingPrice = new javax.swing.JLabel();
        ReservationFee = new javax.swing.JLabel();
        DownPayment = new javax.swing.JLabel();
        lotAreaLbl = new javax.swing.JLabel();
        propNameLbl = new javax.swing.JLabel();
        tspLbl = new javax.swing.JLabel();
        reservationLbl = new javax.swing.JLabel();
        dpLbl = new javax.swing.JLabel();
        LoanableAmount = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        loanableLbl = new javax.swing.JLabel();
        tcpLbl = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        floorAreaLbl = new javax.swing.JLabel();
        FloorArea = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        reportBtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        searchTxt = new javax.swing.JTextField();
        filterCb = new javax.swing.JComboBox<>();

        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        layout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        setLayout(layout);

        propertyTable.setAutoCreateRowSorter(true);
        propertyTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Location", "House Type", "Assigned Agent", "Client Name", "Status", "Price"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        propertyTable.setFocusable(false);
        propertyTable.setGridColor(new java.awt.Color(248, 235, 210));
        propertyTable.setName(""); // NOI18N
        propertyTable.setRowHeight(30);
        propertyTable.setSelectionBackground(new java.awt.Color(248, 235, 210));
        propertyTable.setSelectionForeground(new java.awt.Color(239, 215, 176));
        propertyTable.setShowGrid(false);
        propertyTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(propertyTable);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);

        jPanel1.setBackground(new java.awt.Color(248, 235, 210));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "DETAILS", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 12))); // NOI18N
        java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
        jPanel1Layout.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel1Layout.rowHeights = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel1.setLayout(jPanel1Layout);

        Address.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        Address.setText("Address:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(Address, gridBagConstraints);

        PropertyName.setText("Property Name:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(PropertyName, gridBagConstraints);

        LotArea.setText("Lot Area:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(LotArea, gridBagConstraints);

        TotalSellingPrice.setText("Total Selling Price:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(TotalSellingPrice, gridBagConstraints);

        ReservationFee.setText("Reservation Fees:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(ReservationFee, gridBagConstraints);

        DownPayment.setText("Down Payment:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(DownPayment, gridBagConstraints);

        lotAreaLbl.setText("0 SQM");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(lotAreaLbl, gridBagConstraints);

        propNameLbl.setText("HOUSE NAME");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(propNameLbl, gridBagConstraints);

        tspLbl.setText("PHP 0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(tspLbl, gridBagConstraints);

        reservationLbl.setText("PHP 0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(reservationLbl, gridBagConstraints);

        dpLbl.setText("PHP 0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(dpLbl, gridBagConstraints);

        LoanableAmount.setText("Loanable Amount:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(LoanableAmount, gridBagConstraints);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel19.setText("Total Contract Price:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(jLabel19, gridBagConstraints);

        loanableLbl.setText("PHP 0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(loanableLbl, gridBagConstraints);

        tcpLbl.setText("PHP 0.00");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(tcpLbl, gridBagConstraints);

        jLabel1.setText("BLOCK 0 LOT 0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        jPanel1.add(jLabel1, gridBagConstraints);

        floorAreaLbl.setText("0 SQM");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(floorAreaLbl, gridBagConstraints);

        FloorArea.setText("Floor Area:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.1;
        gridBagConstraints.weighty = 0.1;
        jPanel1.add(FloorArea, gridBagConstraints);

        jPanel2.setOpaque(false);
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWidths = new int[] {0, 10, 0};
        jPanel2Layout.rowHeights = new int[] {0};
        jPanel2.setLayout(jPanel2Layout);

        jButton1.setBackground(new java.awt.Color(36, 5, 2));
        jButton1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Manage");
        jButton1.setToolTipText("");
        jButton1.addActionListener(this::jButton1ActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(jButton1, gridBagConstraints);

        reportBtn.setText("Generate Report");
        reportBtn.addActionListener(this::reportBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(reportBtn, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 15;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jPanel1, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(36, 5, 2));
        jLabel3.setText("Admin Dashboard");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(36, 5, 2));
        jLabel4.setText("Selected Property Overview");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(jLabel4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.8;
        add(searchTxt, gridBagConstraints);

        filterCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Show All", "Available", "Reserved", "Sold" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        add(filterCb, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        int row = propertyTable.getSelectedRow();
        if (row == -1) {
            return;
        }

        String loc = propertyTable.getValueAt(row, 0).toString();
        String[] parts = loc.replace("B", "").replace("L", "").split("-");
        Property p = PropertyService.getProperty(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));

        Transaction targetTrx = null;
        for (Transaction t : PropertyService.getAllTransactions()) {
            if (t.getProperty().getPropertyID().equals(p.getPropertyID())
                    && t.getStatus().equals("Pending Inhouse Loan")) {
                targetTrx = t;
                break;
            }
        }

        if (targetTrx != null) {
            handleLoanApproval(targetTrx);
        } else {
            javax.swing.JOptionPane.showMessageDialog(this, "This property does not have a pending loan request.");
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void reportBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportBtnActionPerformed
        String[] options = {"Excel (.xls)", "CSV (.csv)"};
        int choice = JOptionPane.showOptionDialog(this,
                "Select report format:", "Generate Report",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
                null, options, options[0]);

        if (choice == -1) {
            return;
        }
        
        javax.swing.JFileChooser fileChooser = new javax.swing.JFileChooser();
        fileChooser.setDialogTitle("Save Report");
        String extension = (choice == 0) ? ".xls" : ".csv";
        fileChooser.setSelectedFile(new java.io.File("Property_Report_" + System.currentTimeMillis() + extension));

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File fileToSave = fileChooser.getSelectedFile();

            if (choice == 0) {
                exportToExcel(fileToSave);
            } else {
                exportToCSV(fileToSave);
            }
        }
    }//GEN-LAST:event_reportBtnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Address;
    private javax.swing.JLabel DownPayment;
    private javax.swing.JLabel FloorArea;
    private javax.swing.JLabel LoanableAmount;
    private javax.swing.JLabel LotArea;
    private javax.swing.JLabel PropertyName;
    private javax.swing.JLabel ReservationFee;
    private javax.swing.JLabel TotalSellingPrice;
    private javax.swing.JLabel dpLbl;
    private javax.swing.JComboBox<String> filterCb;
    private javax.swing.JLabel floorAreaLbl;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel loanableLbl;
    private javax.swing.JLabel lotAreaLbl;
    private javax.swing.JLabel propNameLbl;
    private javax.swing.JTable propertyTable;
    private javax.swing.JButton reportBtn;
    private javax.swing.JLabel reservationLbl;
    private javax.swing.JTextField searchTxt;
    private javax.swing.JLabel tcpLbl;
    private javax.swing.JLabel tspLbl;
    // End of variables declaration//GEN-END:variables
}
