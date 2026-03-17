package MyLib.Panels;

import MyLib.Classes.Models.*;
import MyLib.Classes.Services.*;
import javax.swing.table.DefaultTableModel;

public final class ClientsPanel extends javax.swing.JPanel {
    public ClientsPanel() {
        initComponents();
        updateClientTable();
        
        this.addAncestorListener(new javax.swing.event.AncestorListener() {
            @Override
            public void ancestorAdded(javax.swing.event.AncestorEvent e) {
                updateClientTable();
            }

            @Override
            public void ancestorRemoved(javax.swing.event.AncestorEvent e) {
            }

            @Override
            public void ancestorMoved(javax.swing.event.AncestorEvent e) {
            }
        });
    }
    
    public void updateClientTable() {
        DefaultTableModel model = (DefaultTableModel) clientTable.getModel();
        model.setRowCount(0);
        
        String fName = AuthService.getCurrentUser().getFirstName();
        String lName = AuthService.getCurrentUser().getLastName();
        String currentAgent;
        
        if (fName == null || fName.isEmpty() || lName == null || lName.isEmpty()) {
            currentAgent = AuthService.getCurrentUser().getEmail();
        } else {
            currentAgent = fName + " " + lName;
        }

        if (currentAgent == null)
            return;
        
        for (int block = 1; block <= 5; block++) {
            for (int lot = 1; lot <= 20; lot++) {
                Property p = PropertyService.getProperty(block, lot);

                if (p != null && p.getAssignedAgent() != null
                        && p.getAssignedAgent().equalsIgnoreCase(currentAgent)) {

                    String clientEmail = "N/A";
                    if ("Reserved".equalsIgnoreCase(p.getStatus())) {
                        clientEmail = p.getReservedBy();
                    } else if ("Sold".equalsIgnoreCase(p.getStatus())) {
                        clientEmail = findBuyerEmailForProperty(p.getPropertyID());
                    }

                    model.addRow(new Object[]{
                        p.getPropertyID(), // Location
                        clientEmail, // Client Email
                        p.getClass().getSimpleName(), // Property Type
                        p.getStatus() // Status
                    });
                }
            }
        }
    }
    
    private String findBuyerEmailForProperty(String propertyID) {
        for (Transaction t : PropertyService.getAllTransactions()) {
            if (t.getProperty().getPropertyID().equals(propertyID)) {
                return t.getBuyerUsername();
            }
        }
        return "N/A";
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        header = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        clientTable = new javax.swing.JTable();

        java.awt.GridBagLayout headerLayout = new java.awt.GridBagLayout();
        headerLayout.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0};
        headerLayout.rowHeights = new int[] {0};
        header.setLayout(headerLayout);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        jLabel1.setText("Clients");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        header.add(jLabel1, gridBagConstraints);

        clientTable.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        clientTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Location", "Client Email", "Property Type", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        clientTable.setRowHeight(30);
        clientTable.getTableHeader().setResizingAllowed(false);
        clientTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(clientTable);
        if (clientTable.getColumnModel().getColumnCount() > 0) {
            clientTable.getColumnModel().getColumn(0).setResizable(false);
            clientTable.getColumnModel().getColumn(1).setResizable(false);
            clientTable.getColumnModel().getColumn(2).setResizable(false);
            clientTable.getColumnModel().getColumn(3).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable clientTable;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
