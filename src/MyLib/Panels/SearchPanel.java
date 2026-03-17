package MyLib.Panels;

import MyLib.Classes.Models.Property;
import MyLib.Classes.Services.PropertyService;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.*;
import javax.swing.*;
import static javax.swing.Box.createRigidArea;
import static javax.swing.Box.createVerticalGlue;

public class SearchPanel extends javax.swing.JPanel {
    // CONSTRUCTOR
    public SearchPanel() {
        initComponents();
        
        searchPropertyTxt.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                applyFilters();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                applyFilters();
            }

            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                applyFilters();
            }
        });
        
        this.addAncestorListener(new javax.swing.event.AncestorListener() {
            @Override
            public void ancestorAdded(javax.swing.event.AncestorEvent event) {
                applyFilters();
            }

            @Override
            public void ancestorRemoved(javax.swing.event.AncestorEvent event) {
            }

            @Override
            public void ancestorMoved(javax.swing.event.AncestorEvent event) {
            }
        });
        
        statusCb.addActionListener(e -> applyFilters());
        
        maxPriceSlider.addChangeListener(e -> {
            double maxPrice = (double) maxPriceSlider.getValue();
            maxPriceLbl.setText("Max Price: PHP " + java.text.NumberFormat.getInstance().format(maxPrice));
            applyFilters();
        });
        
        bedGroup = new javax.swing.ButtonGroup();
        bathGroup = new javax.swing.ButtonGroup();
        houseTypeGroup = new javax.swing.ButtonGroup();
        
        bedGroup.add(bed1);
        bedGroup.add(bed2);
        bedGroup.add(bed3);
        bedGroup.add(bed4);

        bathGroup.add(bath1);
        bathGroup.add(bath2);
        bathGroup.add(bath3);
        bathGroup.add(bath4);
        
        ActionListener toggleListener = e -> applyFilters();
        AbstractButton[] allToggles = {bed1, bed2, bed3, bed4, bath1, bath2, bath3, bath4};
        for (javax.swing.AbstractButton b : allToggles) {
            b.addActionListener(toggleListener);
        }
        
        ActionListener houseTypeListener = e -> applyFilters();
        sAttachedBtn.addActionListener(houseTypeListener);
        sDetachedBtn.addActionListener(houseTypeListener);
        townhouseBtn.addActionListener(houseTypeListener);
        
        filterSidePanel.setVisible(false);
        hotbar.putClientProperty("FlatLaf.style", "arc: 20");
        mainContent.putClientProperty("FlatLaf.style", "arc: 20");
        filterSidePanel.putClientProperty("FlatLaf.style", "arc: 20");
    
        sortOrderBtn.setText("Ascending");
        sortOrderBtn.addActionListener(e -> {
            if (sortOrderBtn.isSelected()) {
                sortOrderBtn.setText("Descending");
            } else {
                sortOrderBtn.setText("Ascending");
            }
            applyFilters();
        });
        
        generateFullReport();
    }
    
    //HELPERS
    public void generateFullReport() {
        reportContainer.removeAll();
        
        for (int block = 1; block <= 5; block++) {
            for (int lot = 1; lot <= 20; lot++ ) {
                Property p = PropertyService.getProperty(block, lot);
                
                if (p.isListed()) {
                    LotReportTemplate row = new LotReportTemplate(p);
                    row.putClientProperty("FlatLaf.style", "arc: 20");
                    reportContainer.add(row);

                    reportContainer.add(createRigidArea(new Dimension(0, 5)));
                    
                }
            }
        }
        
        reportContainer.add(createVerticalGlue());
        reportContainer.revalidate();
        reportContainer.repaint();
    }

    private void toggleFilterPanel() {
        boolean isVisible = filterSidePanel.isVisible();
        
        filterSidePanel.setVisible(!isVisible);
        
        this.revalidate();
        this.repaint();
    }
    
    public void applyFilters() {
        String query = searchPropertyTxt.getText().toLowerCase();
        double maxPrice = (double) maxPriceSlider.getValue();
        String selectedStatus = statusCb.getSelectedItem().toString();

        int beds = getSelectedValue(bedGroup);
        int baths = getSelectedValue(bathGroup);

        List<Property> filteredList = new ArrayList<>();

        for (int block = 1; block <= 5; block++) {
            for (int lot = 1; lot <= 20; lot++) {
                Property p = PropertyService.getProperty(block, lot);

                if (p == null || !p.isListed()) {
                    continue;
                }

                boolean matchesType = true;
                if (sAttachedBtn.isSelected()) {
                    matchesType = p instanceof MyLib.Classes.Models.SingleAttached;
                } else if (sDetachedBtn.isSelected()) {
                    matchesType = p instanceof MyLib.Classes.Models.SingleDetached;
                } else if (townhouseBtn.isSelected()) {
                    matchesType = p instanceof MyLib.Classes.Models.Townhouse;
                }

                boolean matchesSearch = p.getPropertyID().toLowerCase().contains(query);
                boolean matchesPrice = p.calculatePricePerSqFt() <= maxPrice;
                boolean matchesStatus = selectedStatus.equals("Show All") || p.getStatus().equalsIgnoreCase(selectedStatus);
                boolean matchesBeds = (beds == 0) || (p.getNumBedrooms() >= beds);
                boolean matchesBaths = (baths == 0) || (p.getNumBathrooms() >= baths);

                if (matchesType && matchesSearch && matchesPrice && matchesStatus && matchesBeds && matchesBaths) {
                    filteredList.add(p);
                }
            }
        }

        boolean isDescending = sortOrderBtn.isSelected();
        filteredList.sort((p1, p2) -> {
            double v1 = p1.calculatePricePerSqFt();
            double v2 = p2.calculatePricePerSqFt();
            return isDescending ? Double.compare(v2, v1) : Double.compare(v1, v2);
        });

        reportContainer.removeAll(); // Clear current items

        if (filteredList.isEmpty()) {
            JLabel noResults = new JLabel("No properties found matching your filters.");
            noResults.setForeground(java.awt.Color.GRAY);
            noResults.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
            reportContainer.add(javax.swing.Box.createVerticalGlue());
            reportContainer.add(noResults);
            reportContainer.add(javax.swing.Box.createVerticalGlue());
        } else {
            for (Property p : filteredList) {
                LotReportTemplate row = new LotReportTemplate(p);
                row.putClientProperty("FlatLaf.style", "arc: 20");
                reportContainer.add(row);
                reportContainer.add(javax.swing.Box.createRigidArea(new Dimension(0, 5)));
            }
        }

        reportContainer.revalidate();
        reportContainer.repaint();
    }
    
    private int getSelectedValue(javax.swing.ButtonGroup group) {
        java.util.Enumeration<javax.swing.AbstractButton> en = group.getElements();
        while (en.hasMoreElements()) {
            javax.swing.AbstractButton b = en.nextElement();
            if (b.isSelected()) {
                return Integer.parseInt(b.getText().replace("+", ""));
            }
        }
        return 0;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        bedGroup = new javax.swing.ButtonGroup();
        bathGroup = new javax.swing.ButtonGroup();
        houseTypeGroup = new javax.swing.ButtonGroup();
        hotbar = new javax.swing.JPanel();
        filterBtn = new javax.swing.JButton();
        searchPropertyTxt = new javax.swing.JTextField();
        filterBtn1 = new javax.swing.JButton();
        sAttachedBtn = new javax.swing.JToggleButton();
        sDetachedBtn = new javax.swing.JToggleButton();
        townhouseBtn = new javax.swing.JToggleButton();
        mainContent = new javax.swing.JPanel();
        filterSidePanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        maxPriceLbl = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        statusCb = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        bath1 = new javax.swing.JToggleButton();
        bath2 = new javax.swing.JToggleButton();
        bath3 = new javax.swing.JToggleButton();
        bath4 = new javax.swing.JToggleButton();
        maxPriceSlider = new javax.swing.JSlider();
        clearBtn = new javax.swing.JButton();
        sortOrderBtn = new javax.swing.JToggleButton();
        jPanel4 = new javax.swing.JPanel();
        bed1 = new javax.swing.JToggleButton();
        bed2 = new javax.swing.JToggleButton();
        bed3 = new javax.swing.JToggleButton();
        bed4 = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        reportContainer = new javax.swing.JPanel();

        hotbar.setBackground(new java.awt.Color(255, 255, 255));
        java.awt.GridBagLayout jPanel1Layout = new java.awt.GridBagLayout();
        jPanel1Layout.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0, 10, 0};
        jPanel1Layout.rowHeights = new int[] {0, 10, 0, 10, 0};
        hotbar.setLayout(jPanel1Layout);

        filterBtn.setBackground(new java.awt.Color(36, 5, 2));
        filterBtn.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        filterBtn.setForeground(new java.awt.Color(255, 255, 255));
        filterBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Filter.png"))); // NOI18N
        filterBtn.setText("Filter");
        filterBtn.setToolTipText("");
        filterBtn.setIconTextGap(10);
        filterBtn.addActionListener(this::filterBtnActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3;
        hotbar.add(filterBtn, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 140;
        gridBagConstraints.ipady = 10;
        hotbar.add(searchPropertyTxt, gridBagConstraints);

        filterBtn1.setBackground(new java.awt.Color(36, 5, 2));
        filterBtn1.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        filterBtn1.setForeground(new java.awt.Color(255, 255, 255));
        filterBtn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Filter.png"))); // NOI18N
        filterBtn1.setText("Filter");
        filterBtn1.setToolTipText("");
        filterBtn1.setIconTextGap(10);
        filterBtn1.addActionListener(this::filterBtn1ActionPerformed);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3;
        hotbar.add(filterBtn1, gridBagConstraints);

        houseTypeGroup.add(sAttachedBtn);
        sAttachedBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Single-Attached.png"))); // NOI18N
        sAttachedBtn.setText("Single-Attached");
        sAttachedBtn.setBorder(null);
        sAttachedBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sAttachedBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        hotbar.add(sAttachedBtn, gridBagConstraints);

        houseTypeGroup.add(sDetachedBtn);
        sDetachedBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Single-Detached.png"))); // NOI18N
        sDetachedBtn.setText("Single-Detached");
        sDetachedBtn.setBorder(null);
        sDetachedBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        sDetachedBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        hotbar.add(sDetachedBtn, gridBagConstraints);

        houseTypeGroup.add(townhouseBtn);
        townhouseBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/Townhouse.png"))); // NOI18N
        townhouseBtn.setText("Townhouse");
        townhouseBtn.setBorder(null);
        townhouseBtn.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        townhouseBtn.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 1.0;
        hotbar.add(townhouseBtn, gridBagConstraints);

        mainContent.setBackground(new java.awt.Color(255, 255, 255));
        mainContent.setOpaque(false);
        mainContent.setLayout(new java.awt.BorderLayout(0, 20));

        filterSidePanel.setBackground(new java.awt.Color(36, 5, 2));
        filterSidePanel.setPreferredSize(new java.awt.Dimension(240, 442));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Property Filter");

        maxPriceLbl.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        maxPriceLbl.setForeground(new java.awt.Color(255, 255, 255));
        maxPriceLbl.setText("Max Price: PHP 10,000,000");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Bedrooms:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Bathrooms:");

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Status:");

        statusCb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Show All", "Available", "Reserved", "Sold" }));

        jPanel2.setOpaque(false);
        java.awt.GridBagLayout jPanel2Layout = new java.awt.GridBagLayout();
        jPanel2Layout.columnWidths = new int[] {0, 10, 0, 10, 0, 10, 0};
        jPanel2Layout.rowHeights = new int[] {0};
        jPanel2.setLayout(jPanel2Layout);

        bathGroup.add(bath1);
        bath1.setText("1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(bath1, gridBagConstraints);

        bathGroup.add(bath2);
        bath2.setText("2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(bath2, gridBagConstraints);

        bathGroup.add(bath3);
        bath3.setText("3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(bath3, gridBagConstraints);

        bathGroup.add(bath4);
        bath4.setText("4+");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(bath4, gridBagConstraints);

        maxPriceSlider.setForeground(new java.awt.Color(255, 200, 102));
        maxPriceSlider.setMaximum(10000000);
        maxPriceSlider.setValue(10000000);
        maxPriceSlider.addChangeListener(this::maxPriceSliderStateChanged);

        clearBtn.setBackground(new java.awt.Color(255, 200, 102));
        clearBtn.setFont(new java.awt.Font("Segoe UI", 1, 15)); // NOI18N
        clearBtn.setForeground(new java.awt.Color(255, 255, 255));
        clearBtn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/MyLib/Icons/ClearAll.png"))); // NOI18N
        clearBtn.setText("CLEAR");
        clearBtn.setContentAreaFilled(false);
        clearBtn.addActionListener(this::clearBtnActionPerformed);

        sortOrderBtn.setText("Ascending");

        jPanel4.setOpaque(false);
        jPanel4.setLayout(new java.awt.GridBagLayout());

        bedGroup.add(bed1);
        bed1.setText("1");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(bed1, gridBagConstraints);

        bedGroup.add(bed2);
        bed2.setText("2");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(bed2, gridBagConstraints);

        bedGroup.add(bed3);
        bed3.setText("3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(bed3, gridBagConstraints);

        bedGroup.add(bed4);
        bed4.setText("4+");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.weightx = 1.0;
        jPanel4.add(bed4, gridBagConstraints);

        javax.swing.GroupLayout filterSidePanelLayout = new javax.swing.GroupLayout(filterSidePanel);
        filterSidePanel.setLayout(filterSidePanelLayout);
        filterSidePanelLayout.setHorizontalGroup(
            filterSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(maxPriceSlider, javax.swing.GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
            .addGroup(filterSidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(filterSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sortOrderBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(maxPriceLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusCb, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(clearBtn, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        filterSidePanelLayout.setVerticalGroup(
            filterSidePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(filterSidePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(maxPriceLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(maxPriceSlider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusCb, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sortOrderBtn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addComponent(clearBtn)
                .addContainerGap())
        );

        mainContent.add(filterSidePanel, java.awt.BorderLayout.EAST);

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        reportContainer.setLayout(new javax.swing.BoxLayout(reportContainer, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane1.setViewportView(reportContainer);

        mainContent.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(mainContent, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(hotbar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(hotbar, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainContent, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void clearBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearBtnActionPerformed
        searchPropertyTxt.setText("");
        maxPriceSlider.setValue(maxPriceSlider.getMaximum());
        statusCb.setSelectedIndex(0);
        
        bedGroup.clearSelection();
        bathGroup.clearSelection();
        houseTypeGroup.clearSelection();
        
        javax.swing.AbstractButton[] allToggles = {bed1, bed2, bed3, bed4, bath1, bath2, bath3, bath4};
        for (javax.swing.AbstractButton b : allToggles) {
            b.setBackground(java.awt.Color.WHITE);
        }
        
        generateFullReport();
    }//GEN-LAST:event_clearBtnActionPerformed

    private void maxPriceSliderStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_maxPriceSliderStateChanged
        double maxPrice = (double) maxPriceSlider.getValue();
        maxPriceLbl.setText("Max Price: PHP " + java.text.NumberFormat.getInstance().format(maxPrice));
    }//GEN-LAST:event_maxPriceSliderStateChanged

    private void filterBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterBtn1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filterBtn1ActionPerformed

    private void filterBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filterBtnActionPerformed
        toggleFilterPanel();
    }//GEN-LAST:event_filterBtnActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton bath1;
    private javax.swing.JToggleButton bath2;
    private javax.swing.JToggleButton bath3;
    private javax.swing.JToggleButton bath4;
    private javax.swing.ButtonGroup bathGroup;
    private javax.swing.JToggleButton bed1;
    private javax.swing.JToggleButton bed2;
    private javax.swing.JToggleButton bed3;
    private javax.swing.JToggleButton bed4;
    private javax.swing.ButtonGroup bedGroup;
    private javax.swing.JButton clearBtn;
    private javax.swing.JButton filterBtn;
    private javax.swing.JButton filterBtn1;
    private javax.swing.JPanel filterSidePanel;
    private javax.swing.JPanel hotbar;
    private javax.swing.ButtonGroup houseTypeGroup;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainContent;
    private javax.swing.JLabel maxPriceLbl;
    private javax.swing.JSlider maxPriceSlider;
    private javax.swing.JPanel reportContainer;
    private javax.swing.JToggleButton sAttachedBtn;
    private javax.swing.JToggleButton sDetachedBtn;
    private javax.swing.JTextField searchPropertyTxt;
    private javax.swing.JToggleButton sortOrderBtn;
    private javax.swing.JComboBox<String> statusCb;
    private javax.swing.JToggleButton townhouseBtn;
    // End of variables declaration//GEN-END:variables
}
