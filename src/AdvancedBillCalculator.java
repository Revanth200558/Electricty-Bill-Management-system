import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.util.*;
import java.util.List;
import java.text.DecimalFormat;

public class AdvancedBillCalculator extends JFrame implements ActionListener {
    private JComboBox<String> meterCombo, connectionTypeCombo, monthCombo, yearCombo;
    private JTextField unitsField, previousReadingField, currentReadingField;
    private JTextArea calculationArea, billDetailsArea;
    private JButton calculateButton, generateBillButton, clearButton, closeButton, rateManagerButton;
    private JLabel totalAmountLabel, dueDateLabel;
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    
    // Modern color scheme
    private Color primaryColor = new Color(41, 128, 185);
    private Color secondaryColor = new Color(52, 152, 219);
    private Color successColor = new Color(39, 174, 96);
    private Color warningColor = new Color(241, 196, 15);
    private Color dangerColor = new Color(231, 76, 60);
    private Color lightGray = new Color(236, 240, 241);
    
    public AdvancedBillCalculator() {
        super("Advanced Bill Calculator & Generator - Professional Edition");
        
        setSize(1000, 700);
        setLocation(150, 50);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Initialize data
        new conn();
        
        // Create modern UI
        createModernUI();
        
        setVisible(true);
    }
    
    private void createModernUI() {
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(primaryColor);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("⚡ ADVANCED ELECTRICITY BILL CALCULATOR", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JLabel subtitleLabel = new JLabel("Professional Bill Generation & Rate Management System", JLabel.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(189, 195, 199));
        
        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(lightGray);
        
        // Left Panel - Input Form
        JPanel leftPanel = createInputPanel();
        
        // Right Panel - Calculation & Results
        JPanel rightPanel = createResultsPanel();
        
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Bottom Panel - Action Buttons
        JPanel bottomPanel = createButtonPanel();
        add(bottomPanel, BorderLayout.SOUTH);
        
        loadMeterNumbers();
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(400, 500));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        // Title
        JLabel formTitle = new JLabel("📋 BILL CALCULATION FORM");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formTitle.setForeground(primaryColor);
        panel.add(formTitle, BorderLayout.NORTH);
        
        // Form Fields
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Customer Selection
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createStyledLabel("Customer Meter:"), gbc);
        meterCombo = new JComboBox<>();
        meterCombo.setPreferredSize(new Dimension(200, 30));
        styleComboBox(meterCombo);
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(meterCombo, gbc);
        
        // Connection Type
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createStyledLabel("Connection Type:"), gbc);
        connectionTypeCombo = new JComboBox<>(new String[]{"Domestic", "Commercial", "Industrial"});
        connectionTypeCombo.setPreferredSize(new Dimension(200, 30));
        styleComboBox(connectionTypeCombo);
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(connectionTypeCombo, gbc);
        
        // Month & Year
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createStyledLabel("Billing Month:"), gbc);
        monthCombo = new JComboBox<>(new String[]{"January", "February", "March", "April", "May", "June", 
                                                 "July", "August", "September", "October", "November", "December"});
        monthCombo.setPreferredSize(new Dimension(200, 30));
        styleComboBox(monthCombo);
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(monthCombo, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(createStyledLabel("Billing Year:"), gbc);
        yearCombo = new JComboBox<>(new String[]{"2024", "2025", "2026"});
        yearCombo.setPreferredSize(new Dimension(200, 30));
        styleComboBox(yearCombo);
        gbc.gridx = 1; gbc.gridy = 3;
        formPanel.add(yearCombo, gbc);
        
        // Meter Readings
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(createStyledLabel("Previous Reading:"), gbc);
        previousReadingField = new JTextField(15);
        styleTextField(previousReadingField);
        gbc.gridx = 1; gbc.gridy = 4;
        formPanel.add(previousReadingField, gbc);
        
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(createStyledLabel("Current Reading:"), gbc);
        currentReadingField = new JTextField(15);
        styleTextField(currentReadingField);
        gbc.gridx = 1; gbc.gridy = 5;
        formPanel.add(currentReadingField, gbc);
        
        // Units Consumed (Auto-calculated)
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(createStyledLabel("Units Consumed:"), gbc);
        unitsField = new JTextField(15);
        unitsField.setEditable(false);
        unitsField.setBackground(lightGray);
        styleTextField(unitsField);
        gbc.gridx = 1; gbc.gridy = 6;
        formPanel.add(unitsField, gbc);
        
        // Auto-calculate units when readings change
        javax.swing.event.DocumentListener readingListener = new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { calculateUnits(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { calculateUnits(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { calculateUnits(); }
        };
        previousReadingField.getDocument().addDocumentListener(readingListener);
        currentReadingField.getDocument().addDocumentListener(readingListener);
        
        panel.add(formPanel, BorderLayout.CENTER);
        return panel;
    }
    
    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(lightGray);
        
        // Calculation Breakdown
        JPanel calcPanel = new JPanel(new BorderLayout());
        calcPanel.setBackground(Color.WHITE);
        calcPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(primaryColor, 2),
            "💰 CALCULATION BREAKDOWN",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            primaryColor
        ));
        
        calculationArea = new JTextArea(12, 30);
        calculationArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        calculationArea.setEditable(false);
        calculationArea.setBackground(new Color(248, 249, 250));
        calculationArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane calcScroll = new JScrollPane(calculationArea);
        calcPanel.add(calcScroll, BorderLayout.CENTER);
        
        // Total Amount Display
        JPanel totalPanel = new JPanel(new FlowLayout());
        totalPanel.setBackground(Color.WHITE);
        totalAmountLabel = new JLabel("Total Amount: ₹0.00");
        totalAmountLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalAmountLabel.setForeground(successColor);
        totalPanel.add(totalAmountLabel);
        
        dueDateLabel = new JLabel("Due Date: Not Set");
        dueDateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dueDateLabel.setForeground(warningColor);
        totalPanel.add(dueDateLabel);
        
        calcPanel.add(totalPanel, BorderLayout.SOUTH);
        
        // Bill Details
        JPanel billPanel = new JPanel(new BorderLayout());
        billPanel.setBackground(Color.WHITE);
        billPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(secondaryColor, 2),
            "📄 GENERATED BILL PREVIEW",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            secondaryColor
        ));
        
        billDetailsArea = new JTextArea(8, 30);
        billDetailsArea.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        billDetailsArea.setEditable(false);
        billDetailsArea.setBackground(new Color(248, 249, 250));
        billDetailsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JScrollPane billScroll = new JScrollPane(billDetailsArea);
        billPanel.add(billScroll, BorderLayout.CENTER);
        
        panel.add(calcPanel, BorderLayout.CENTER);
        panel.add(billPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(lightGray);
        
        calculateButton = createStyledButton("🧮 Calculate Bill", primaryColor);
        generateBillButton = createStyledButton("📋 Generate & Save", successColor);
        rateManagerButton = createStyledButton("⚙️ Manage Rates", warningColor);
        clearButton = createStyledButton("🔄 Clear Form", new Color(149, 165, 166));
        closeButton = createStyledButton("❌ Close", dangerColor);
        
        calculateButton.addActionListener(this);
        generateBillButton.addActionListener(this);
        rateManagerButton.addActionListener(this);
        clearButton.addActionListener(this);
        closeButton.addActionListener(this);
        
        panel.add(calculateButton);
        panel.add(generateBillButton);
        panel.add(rateManagerButton);
        panel.add(clearButton);
        panel.add(closeButton);
        
        return panel;
    }
    
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(new Color(44, 62, 80));
        return label;
    }
    
    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(189, 195, 199), 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        field.setPreferredSize(new Dimension(200, 30));
    }
    
    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        combo.setBorder(BorderFactory.createLineBorder(new Color(189, 195, 199), 1));
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
    
    private void loadMeterNumbers() {
        meterCombo.removeAllItems();
        List<String> meterNumbers = conn.getAllMeterNumbers();
        for (String meter : meterNumbers) {
            Map<String, String> customerData = conn.getCustomerData(meter);
            String displayText = meter + " - " + (customerData != null ? customerData.get("name") : "Unknown");
            meterCombo.addItem(displayText);
        }
    }
    
    private void calculateUnits() {
        try {
            String prevText = previousReadingField.getText().trim();
            String currText = currentReadingField.getText().trim();
            
            if (!prevText.isEmpty() && !currText.isEmpty()) {
                int previous = Integer.parseInt(prevText);
                int current = Integer.parseInt(currText);
                int units = current - previous;
                
                if (units >= 0) {
                    unitsField.setText(String.valueOf(units));
                } else {
                    unitsField.setText("Invalid");
                }
            } else {
                unitsField.setText("");
            }
        } catch (NumberFormatException e) {
            unitsField.setText("Error");
        }
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == calculateButton) {
            calculateBill();
        } else if (ae.getSource() == generateBillButton) {
            generateAndSaveBill();
        } else if (ae.getSource() == rateManagerButton) {
            new ElectricityRateManager().setVisible(true);
        } else if (ae.getSource() == clearButton) {
            clearForm();
        } else if (ae.getSource() == closeButton) {
            this.dispose();
        }
    }
    
    private void calculateBill() {
        try {
            String unitsText = unitsField.getText().trim();
            if (unitsText.isEmpty() || "Invalid".equals(unitsText) || "Error".equals(unitsText)) {
                JOptionPane.showMessageDialog(this, "Please enter valid meter readings!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            int units = Integer.parseInt(unitsText);
            String connectionType = (String) connectionTypeCombo.getSelectedItem();
            
            // Calculate bill amount
            double totalAmount = conn.calculateBillAmount(units, connectionType.toLowerCase());
            
            // Display calculation breakdown
            displayCalculationBreakdown(units, connectionType, totalAmount);
            
            // Update total amount label
            totalAmountLabel.setText("Total Amount: ₹" + df.format(totalAmount));
            
            // Set due date (30 days from now)
            java.time.LocalDate dueDate = java.time.LocalDate.now().plusDays(30);
            dueDateLabel.setText("Due Date: " + dueDate.toString());
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values!", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void displayCalculationBreakdown(int units, String connectionType, double totalAmount) {
        StringBuilder breakdown = new StringBuilder();
        breakdown.append("═══════════════════════════════════════\n");
        breakdown.append("        ELECTRICITY BILL CALCULATION\n");
        breakdown.append("═══════════════════════════════════════\n\n");
        
        breakdown.append("Connection Type: ").append(connectionType).append("\n");
        breakdown.append("Units Consumed: ").append(units).append(" kWh\n\n");
        
        breakdown.append("ENERGY CHARGES:\n");
        breakdown.append("───────────────────────────────────────\n");
        
        double energyCharges = 0.0;
        Map<String, Double> rates = conn.getAllRates();
        
        if ("domestic".equalsIgnoreCase(connectionType)) {
            if (units <= 100) {
                energyCharges = units * rates.get("domestic_0_100");
                breakdown.append(String.format("0-100 units: %d × ₹%.2f = ₹%.2f\n", 
                    units, rates.get("domestic_0_100"), energyCharges));
            } else if (units <= 200) {
                double charge1 = 100 * rates.get("domestic_0_100");
                double charge2 = (units - 100) * rates.get("domestic_101_200");
                energyCharges = charge1 + charge2;
                breakdown.append(String.format("0-100 units: 100 × ₹%.2f = ₹%.2f\n", 
                    rates.get("domestic_0_100"), charge1));
                breakdown.append(String.format("101-%d units: %d × ₹%.2f = ₹%.2f\n", 
                    units, (units-100), rates.get("domestic_101_200"), charge2));
            } else if (units <= 300) {
                double charge1 = 100 * rates.get("domestic_0_100");
                double charge2 = 100 * rates.get("domestic_101_200");
                double charge3 = (units - 200) * rates.get("domestic_201_300");
                energyCharges = charge1 + charge2 + charge3;
                breakdown.append(String.format("0-100 units: 100 × ₹%.2f = ₹%.2f\n", 
                    rates.get("domestic_0_100"), charge1));
                breakdown.append(String.format("101-200 units: 100 × ₹%.2f = ₹%.2f\n", 
                    rates.get("domestic_101_200"), charge2));
                breakdown.append(String.format("201-%d units: %d × ₹%.2f = ₹%.2f\n", 
                    units, (units-200), rates.get("domestic_201_300"), charge3));
            } else {
                double charge1 = 100 * rates.get("domestic_0_100");
                double charge2 = 100 * rates.get("domestic_101_200");
                double charge3 = 100 * rates.get("domestic_201_300");
                double charge4 = (units - 300) * rates.get("domestic_above_300");
                energyCharges = charge1 + charge2 + charge3 + charge4;
                breakdown.append(String.format("0-100 units: 100 × ₹%.2f = ₹%.2f\n", 
                    rates.get("domestic_0_100"), charge1));
                breakdown.append(String.format("101-200 units: 100 × ₹%.2f = ₹%.2f\n", 
                    rates.get("domestic_101_200"), charge2));
                breakdown.append(String.format("201-300 units: 100 × ₹%.2f = ₹%.2f\n", 
                    rates.get("domestic_201_300"), charge3));
                breakdown.append(String.format("Above 300 units: %d × ₹%.2f = ₹%.2f\n", 
                    (units-300), rates.get("domestic_above_300"), charge4));
            }
        } else {
            String rateKey = connectionType.toLowerCase();
            energyCharges = units * rates.get(rateKey);
            breakdown.append(String.format("%d units × ₹%.2f = ₹%.2f\n", 
                units, rates.get(rateKey), energyCharges));
        }
        
        breakdown.append(String.format("\nSubtotal (Energy): ₹%.2f\n\n", energyCharges));
        
        breakdown.append("FIXED CHARGES:\n");
        breakdown.append("───────────────────────────────────────\n");
        breakdown.append("Meter Rent: ₹50.00\n");
        breakdown.append("MCB Rent: ₹12.00\n");
        breakdown.append("Service Rent: ₹102.00\n");
        double fixedCharges = 164.00;
        breakdown.append(String.format("Subtotal (Fixed): ₹%.2f\n\n", fixedCharges));
        
        double subtotal = energyCharges + fixedCharges;
        breakdown.append(String.format("Subtotal: ₹%.2f\n", subtotal));
        
        double gst = subtotal * 0.18;
        breakdown.append(String.format("GST (18%%): ₹%.2f\n", gst));
        
        breakdown.append("═══════════════════════════════════════\n");
        breakdown.append(String.format("TOTAL AMOUNT: ₹%.2f\n", totalAmount));
        breakdown.append("═══════════════════════════════════════\n");
        
        calculationArea.setText(breakdown.toString());
    }
    
    private void generateAndSaveBill() {
        String unitsText = unitsField.getText().trim();
        if (unitsText.isEmpty() || "Invalid".equals(unitsText) || "Error".equals(unitsText)) {
            JOptionPane.showMessageDialog(this, "Please calculate the bill first!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            String meterInfo = (String) meterCombo.getSelectedItem();
            String meterNumber = meterInfo.split(" - ")[0];
            String month = (String) monthCombo.getSelectedItem();
            String year = (String) yearCombo.getSelectedItem();
            int units = Integer.parseInt(unitsText);
            String connectionType = (String) connectionTypeCombo.getSelectedItem();
            
            double totalAmount = conn.calculateBillAmount(units, connectionType.toLowerCase());
            java.time.LocalDate dueDate = java.time.LocalDate.now().plusDays(30);
            
            // Save bill to database
            conn.addBill(meterNumber, month, year, String.valueOf(units), 
                        String.valueOf(totalAmount), "Pending", dueDate.toString());
            
            // Generate bill preview
            generateBillPreview(meterNumber, month, year, units, totalAmount, dueDate);
            
            // Add notification for customer
            conn.addNotification(meterNumber, 
                "New bill generated for " + month + " " + year + " - Amount: ₹" + df.format(totalAmount), 
                "Bill Generated");
            
            JOptionPane.showMessageDialog(this, 
                "Bill generated successfully!\n" +
                "Customer: " + meterNumber + "\n" +
                "Month: " + month + " " + year + "\n" +
                "Amount: ₹" + df.format(totalAmount), 
                "Bill Generated", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error generating bill: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void generateBillPreview(String meterNumber, String month, String year, 
                                   int units, double totalAmount, java.time.LocalDate dueDate) {
        Map<String, String> customerData = conn.getCustomerData(meterNumber);
        
        StringBuilder bill = new StringBuilder();
        bill.append("╔══════════════════════════════════════════════════════════════╗\n");
        bill.append("║                    ELECTRICITY BILL                         ║\n");
        bill.append("║                  Professional Edition                       ║\n");
        bill.append("╠══════════════════════════════════════════════════════════════╣\n");
        bill.append(String.format("║ Bill Date: %-20s Due Date: %-15s ║\n", 
            java.time.LocalDate.now(), dueDate));
        bill.append("╠══════════════════════════════════════════════════════════════╣\n");
        bill.append("║ CUSTOMER DETAILS                                            ║\n");
        bill.append("╠══════════════════════════════════════════════════════════════╣\n");
        
        if (customerData != null) {
            bill.append(String.format("║ Name: %-54s ║\n", customerData.get("name")));
            bill.append(String.format("║ Meter No: %-50s ║\n", meterNumber));
            bill.append(String.format("║ Address: %-52s ║\n", customerData.get("address")));
            bill.append(String.format("║ City: %-55s ║\n", customerData.get("city")));
        }
        
        bill.append("╠══════════════════════════════════════════════════════════════╣\n");
        bill.append("║ BILLING DETAILS                                             ║\n");
        bill.append("╠══════════════════════════════════════════════════════════════╣\n");
        bill.append(String.format("║ Billing Period: %-44s ║\n", month + " " + year));
        bill.append(String.format("║ Units Consumed: %-44s ║\n", units + " kWh"));
        bill.append(String.format("║ Connection Type: %-43s ║\n", connectionTypeCombo.getSelectedItem()));
        bill.append("╠══════════════════════════════════════════════════════════════╣\n");
        bill.append(String.format("║ TOTAL AMOUNT: ₹%-45s ║\n", df.format(totalAmount)));
        bill.append("║ Status: PENDING PAYMENT                                     ║\n");
        bill.append("╚══════════════════════════════════════════════════════════════╝\n");
        bill.append("\n");
        bill.append("Payment Methods: Online Banking, UPI, Credit/Debit Card\n");
        bill.append("Late Payment Charges: 2% per month after due date\n");
        bill.append("For queries: support@electricitybilling.com | 1800-123-4567");
        
        billDetailsArea.setText(bill.toString());
    }
    
    private void clearForm() {
        previousReadingField.setText("");
        currentReadingField.setText("");
        unitsField.setText("");
        calculationArea.setText("");
        billDetailsArea.setText("");
        totalAmountLabel.setText("Total Amount: ₹0.00");
        dueDateLabel.setText("Due Date: Not Set");
        connectionTypeCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(0);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdvancedBillCalculator();
        });
    }
}
