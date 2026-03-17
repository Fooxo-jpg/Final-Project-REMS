package MyLib.Panels;

import MyLib.Classes.Models.Transaction;
import MyLib.Classes.Services.AuthService;
import MyLib.Classes.Services.PropertyService;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

public class TransactionPanel extends javax.swing.JPanel {
    private final DecimalFormat df = new DecimalFormat("#,##0.00");
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    
    public TransactionPanel() {
        initComponents();
        refreshTable();
        
        transactionTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    openSelectedReceipt();
                }
            }
        });
        
        this.addAncestorListener(new javax.swing.event.AncestorListener() {
            @Override
            public void ancestorAdded(javax.swing.event.AncestorEvent e) { refreshTable(); }
            @Override
            public void ancestorRemoved(javax.swing.event.AncestorEvent e) {}
            @Override
            public void ancestorMoved(javax.swing.event.AncestorEvent e) {}
        });
        
    }
    
    private void openSelectedReceipt() {
        int row = transactionTable.getSelectedRow();
        if (row != -1) {
            String trxID = transactionTable.getValueAt(row, 0).toString();

            Transaction trx = PropertyService.findTransactionByID(trxID);

            if (trx != null) {
                MyLib.Classes.Models.Payment p = trx.getPaymentDetail();

                java.awt.Frame parent = (java.awt.Frame) javax.swing.SwingUtilities.getWindowAncestor(this);
                MyLib.Dialogs.TransactionReceipt receipt = new MyLib.Dialogs.TransactionReceipt(parent, true, trx, p);
                receipt.setVisible(true);
            }
        }
    }
    
    public void refreshTable() {
        DefaultTableModel model = (DefaultTableModel) transactionTable.getModel();
        model.setRowCount(0);

        String currentUser = AuthService.getCurrentUser().getUsername();
        String userRole = AuthService.getCurrentUser().getRole();

        for (Transaction t : PropertyService.getAllTransactions()) {

            if (userRole.equalsIgnoreCase("Admin") || t.getBuyerUsername().equals(currentUser)) {

                String dpMethod = (t.getPaymentDetail() instanceof MyLib.Classes.Models.Check) ? "Check" : "Cash";

                model.addRow(new Object[]{
                    t.getTransactionID(),
                    sdf.format(t.getDate()),
                    t.getProperty().getPropertyID(),
                    t.getProperty().getClass().getSimpleName(),
                    t.getPaymentMethod(),
                    t.getLoanTerm(),
                    "PHP " + df.format(t.getMonthlyAmortization()),
                    dpMethod,
                    "PHP " + df.format(t.getInitialPayment()),
                    "Finalized"
                });
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Transactions = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        transactionTable = new javax.swing.JTable();

        Transactions.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        Transactions.setText("Transactions");

        transactionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Transaction ID", "Date | Time", "Location", "Property Type", "Loan Method", "Loan Terms", "Monthly Amortization", "Downpayment Method", "Initial Payment", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        transactionTable.setRowHeight(30);
        transactionTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(transactionTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                    .addComponent(Transactions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Transactions, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Transactions;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable transactionTable;
    // End of variables declaration//GEN-END:variables
}
