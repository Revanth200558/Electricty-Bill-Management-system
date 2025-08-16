import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.text.DecimalFormat;

public class ElectricityRateManager extends JFrame implements ActionListener {
    private JTable rateTable;
    private DefaultTableModel tableModel;
    private JTextField rateField;
    private JComboBox<String> rateTypeCombo;
    private JButton updateButton, resetButton, closeButton, saveButton;
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    
    // Modern color scheme
    private Color primaryColor = new Color(41, 128, 185);
    private Color successColor = new Color(39, 174, 96);
    private Color warningColor = new Color(241, 196, 15);
    private Color dangerColor = new Color(231, 76, 60);
    private Color lightGray = new Color(236, 240, 241);
    
    public ElectricityRateManager() {
        super("⚙️ Electricity Rate Management System");
        
        setSize(800, 600);
        setLocation(200, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        createUI();
        loadRateData();
        
        setVisible(true);
    }
    
    private void createUI() {
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("⚡ ELECTRICITY RATE MANAGEMENT", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Configure electricity rates for different connection types and usage slabs", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(189, 195, 199));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(lightGray);
        
        // Rate Table Panel
        JPanel tablePanel = createTablePanel();
        
        // Rate Update Panel
        JPanel updatePanel = createUpdatePanel();
        
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(updatePanel, BorderLayout.EAST);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Button Panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel tableTitle = new JLabel("📊 CURRENT ELECTRICITY RATES");
        tableTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableTitle.setForeground(primaryColor);
        panel.add(tableTitle, BorderLayout.NORTH);
        
        // Create table
        String[] columnNames = {"Rate Category", "Description", "Rate (₹/kWh)", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        rateTable = new JTable(tableModel);
        rateTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rateTable.setRowHeight(25);
        rateTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        rateTable.setGridColor(new Color(189, 195, 199));
        
        // Style table header
        JTableHeader header = rateTable.getTableHeader();
        header.setBackground(primaryColor);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        // Set column widths
        rateTable.getColumnModel().getColumn(0).setPreferredWidth(150);
        rateTable.getColumnModel().getColumn(1).setPreferredWidth(250);
        rateTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        rateTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        
        // Add selection listener
        rateTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = rateTable.getSelectedRow();
                if (selectedRow >= 0) {
                    String rateCategory = (String) tableModel.getValueAt(selectedRow, 0);
                    String rateValue = (String) tableModel.getValueAt(selectedRow, 2);
                    
                    // Set combo box selection
                    rateTypeCombo.setSelectedItem(rateCategory);
                    
                    // Extract numeric value from rate string
                    try {
                        String numericValue = rateValue.replace("₹", "").trim();
                        rateField.setText(numericValue);
                    } catch (Exception ex) {
                        rateField.setText("");
                    }
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(rateTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createUpdatePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(280, 400));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel updateTitle = new JLabel("✏️ UPDATE RATES");
        updateTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        updateTitle.setForeground(primaryColor);
        panel.add(updateTitle, BorderLayout.NORTH);
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Rate Type Selection
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel typeLabel = new JLabel("Select Rate Category:");
        typeLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        formPanel.add(typeLabel, gbc);
        
        gbc.gridy = 1;
        rateTypeCombo = new JComboBox<>(new String[]{
            "Domestic (0-100 units)",
            "Domestic (101-200 units)",
            "Domestic (201-300 units)",
            "Domestic (Above 300 units)",
            "Commercial",
            "Industrial"
        });
        rateTypeCombo.setPreferredSize(new Dimension(220, 30));
        rateTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        formPanel.add(rateTypeCombo, gbc);
        
        // Rate Value Input
        gbc.gridy = 2;
        JLabel rateLabel = new JLabel("New Rate (₹ per kWh):");
        rateLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        formPanel.add(rateLabel, gbc);
        
        gbc.gridy = 3;
        rateField = new JTextField(15);
        rateField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rateField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        rateField.setPreferredSize(new Dimension(220, 30));
        formPanel.add(rateField, gbc);
        
        // Instructions
        gbc.gridy = 4;
        JTextArea instructions = new JTextArea(
            "Instructions:\n" +
            "• Select a rate category from the dropdown\n" +
            "• Enter the new rate per kWh\n" +
            "• Click 'Update Rate' to apply changes\n" +
            "• Use 'Reset to Default' to restore original rates"
        );
        instructions.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        instructions.setBackground(new Color(248, 249, 250));
        instructions.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        instructions.setEditable(false);
        instructions.setLineWrap(true);
        instructions.setWrapStyleWord(true);
        instructions.setPreferredSize(new Dimension(220, 100));
        formPanel.add(instructions, gbc);
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(lightGray);
        
        updateButton = createStyledButton("✏️ Update Rate", successColor);
        resetButton = createStyledButton("🔄 Reset to Default", warningColor);
        saveButton = createStyledButton("💾 Save Changes", primaryColor);
        closeButton = createStyledButton("❌ Close", dangerColor);
        
        updateButton.addActionListener(this);
        resetButton.addActionListener(this);
        saveButton.addActionListener(this);
        closeButton.addActionListener(this);
        
        panel.add(updateButton);
        panel.add(resetButton);
        panel.add(saveButton);
        panel.add(closeButton);
        
        return panel;
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            Color originalColor = button.getBackground();
            public void mouseEntered(MouseEvent e) {
                button.setBackground(originalColor.darker());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
    }
    
    private void loadRateData() {
        tableModel.setRowCount(0); // Clear existing data
        
        Map<String, Double> rates = conn.getAllRates();
        
        // Add domestic rates
        addRateRow("Domestic (0-100 units)", "First 100 units for domestic connections", 
                   rates.get("domestic_0_100"), "Active");
        addRateRow("Domestic (101-200 units)", "Next 100 units (101-200) for domestic connections", 
                   rates.get("domestic_101_200"), "Active");
        addRateRow("Domestic (201-300 units)", "Next 100 units (201-300) for domestic connections", 
                   rates.get("domestic_201_300"), "Active");
        addRateRow("Domestic (Above 300 units)", "Units above 300 for domestic connections", 
                   rates.get("domestic_above_300"), "Active");
        
        // Add commercial and industrial rates
        addRateRow("Commercial", "All units for commercial connections", 
                   rates.get("commercial"), "Active");
        addRateRow("Industrial", "All units for industrial connections", 
                   rates.get("industrial"), "Active");
    }
    
    private void addRateRow(String category, String description, Double rate, String status) {
        Object[] rowData = {
            category,
            description,
            "₹" + df.format(rate),
            status
        };
        tableModel.addRow(rowData);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == updateButton) {
            updateRate();
        } else if (ae.getSource() == resetButton) {
            resetToDefaults();
        } else if (ae.getSource() == saveButton) {
            saveChanges();
        } else if (ae.getSource() == closeButton) {
            this.dispose();
        }
    }
    
    private void updateRate() {
        try {
            String selectedCategory = (String) rateTypeCombo.getSelectedItem();
            String rateText = rateField.getText().trim();
            
            if (rateText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a rate value!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            double newRate = Double.parseDouble(rateText);
            
            if (newRate <= 0) {
                JOptionPane.showMessageDialog(this, "Rate must be greater than 0!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Map display names to database keys
            String rateKey = mapCategoryToKey(selectedCategory);
            
            if (rateKey != null) {
                conn.updateElectricityRate(rateKey, newRate);
                loadRateData(); // Refresh table
                
                JOptionPane.showMessageDialog(this, 
                    "Rate updated successfully!\n" +
                    "Category: " + selectedCategory + "\n" +
                    "New Rate: ₹" + df.format(newRate) + " per kWh", 
                    "Rate Updated", 
                    JOptionPane.INFORMATION_MESSAGE);
                
                rateField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Invalid rate category selected!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric rate!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String mapCategoryToKey(String category) {
        switch (category) {
            case "Domestic (0-100 units)": return "domestic_0_100";
            case "Domestic (101-200 units)": return "domestic_101_200";
            case "Domestic (201-300 units)": return "domestic_201_300";
            case "Domestic (Above 300 units)": return "domestic_above_300";
            case "Commercial": return "commercial";
            case "Industrial": return "industrial";
            default: return null;
        }
    }
    
    private void resetToDefaults() {
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to reset all rates to default values?\n" +
            "This action cannot be undone!",
            "Confirm Reset",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
            
        if (confirm == JOptionPane.YES_OPTION) {
            // Reset to default rates
            conn.updateElectricityRate("domestic_0_100", 3.50);
            conn.updateElectricityRate("domestic_101_200", 4.00);
            conn.updateElectricityRate("domestic_201_300", 5.50);
            conn.updateElectricityRate("domestic_above_300", 6.50);
            conn.updateElectricityRate("commercial", 8.00);
            conn.updateElectricityRate("industrial", 7.50);
            
            loadRateData(); // Refresh table
            rateField.setText("");
            
            JOptionPane.showMessageDialog(this, 
                "All rates have been reset to default values!", 
                "Reset Complete", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void saveChanges() {
        JOptionPane.showMessageDialog(this, 
            "Rate changes have been saved to the system!\n" +
            "New rates will be applied to future bill calculations.", 
            "Changes Saved", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new conn(); // Initialize database
            new ElectricityRateManager();
        });
    }
}
