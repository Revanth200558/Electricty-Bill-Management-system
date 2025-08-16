import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

public class AdminBillManager extends JFrame implements ActionListener {
    private JComboBox<String> meterCombo;
    private JTextField monthField, yearField, unitsField, amountField, dueDateField;
    private JComboBox<String> statusCombo;
    private JButton addBillButton, clearButton, closeButton;
    private JTable billTable;
    private JScrollPane scrollPane;
    
    public AdminBillManager() {
        super("Admin Bill Manager - Add/View Bills");
        
        setSize(800, 600);
        setLocation(200, 100);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Initialize data
        new conn();
        
        // Create main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("BILL MANAGEMENT SYSTEM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Bill"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Meter Number
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Meter Number:"), gbc);
        meterCombo = new JComboBox<>();
        loadMeterNumbers();
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(meterCombo, gbc);
        
        // Month
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Month:"), gbc);
        monthField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(monthField, gbc);
        
        // Year
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Year:"), gbc);
        yearField = new JTextField(15);
        yearField.setText("2024");
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(yearField, gbc);
        
        // Units
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Units Consumed:"), gbc);
        unitsField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 3;
        formPanel.add(unitsField, gbc);
        
        // Amount
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Amount (₹):"), gbc);
        amountField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 4;
        formPanel.add(amountField, gbc);
        
        // Status
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Status:"), gbc);
        statusCombo = new JComboBox<>(new String[]{"Pending", "Paid", "Overdue"});
        gbc.gridx = 1; gbc.gridy = 5;
        formPanel.add(statusCombo, gbc);
        
        // Due Date
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Due Date (YYYY-MM-DD):"), gbc);
        dueDateField = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 6;
        formPanel.add(dueDateField, gbc);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        addBillButton = new JButton("Add Bill");
        addBillButton.setBackground(Color.GREEN);
        addBillButton.setForeground(Color.WHITE);
        addBillButton.addActionListener(this);
        
        clearButton = new JButton("Clear");
        clearButton.setBackground(Color.ORANGE);
        clearButton.setForeground(Color.WHITE);
        clearButton.addActionListener(this);
        
        closeButton = new JButton("Close");
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(this);
        
        buttonPanel.add(addBillButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(closeButton);
        
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        formPanel.add(buttonPanel, gbc);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        
        // Table panel for viewing existing bills
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Existing Bills"));
        
        String[] columnNames = {"Meter", "Month", "Year", "Units", "Amount", "Status", "Due Date"};
        billTable = new JTable(new Object[0][0], columnNames);
        scrollPane = new JScrollPane(billTable);
        scrollPane.setPreferredSize(new Dimension(750, 200));
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        
        // Load existing bills
        loadBillTable();
        
        setVisible(true);
    }
    
    private void loadMeterNumbers() {
        meterCombo.removeAllItems();
        List<String> meterNumbers = conn.getAllMeterNumbers();
        for (String meter : meterNumbers) {
            meterCombo.addItem(meter);
        }
    }
    
    private void loadBillTable() {
        String[] columnNames = {"Meter", "Month", "Year", "Units", "Amount", "Status", "Due Date"};
        java.util.List<Object[]> rows = new ArrayList<>();
        
        List<String> allMeters = conn.getAllMeterNumbers();
        for (String meter : allMeters) {
            List<Map<String, String>> bills = conn.getBillData(meter);
            if (bills != null) {
                for (Map<String, String> bill : bills) {
                    Object[] row = {
                        bill.get("meter_number"),
                        bill.get("month"),
                        bill.get("year"),
                        bill.get("units"),
                        "₹" + bill.get("amount"),
                        bill.get("status"),
                        bill.get("due_date")
                    };
                    rows.add(row);
                }
            }
        }
        
        Object[][] data = rows.toArray(new Object[0][]);
        billTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addBillButton) {
            addNewBill();
        } else if (ae.getSource() == clearButton) {
            clearFields();
        } else if (ae.getSource() == closeButton) {
            this.dispose();
        }
    }
    
    private void addNewBill() {
        String meterNumber = (String) meterCombo.getSelectedItem();
        String month = monthField.getText().trim();
        String year = yearField.getText().trim();
        String units = unitsField.getText().trim();
        String amount = amountField.getText().trim();
        String status = (String) statusCombo.getSelectedItem();
        String dueDate = dueDateField.getText().trim();
        
        if (meterNumber == null || month.isEmpty() || year.isEmpty() || 
            units.isEmpty() || amount.isEmpty() || dueDate.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill all fields!", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Validate numeric fields
            Integer.parseInt(units);
            Double.parseDouble(amount);
            
            // Add bill to database
            conn.addBill(meterNumber, month, year, units, amount, status, dueDate);
            
            JOptionPane.showMessageDialog(this, 
                "Bill added successfully!", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            
            clearFields();
            loadBillTable(); // Refresh table
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Units and Amount must be valid numbers!", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void clearFields() {
        monthField.setText("");
        yearField.setText("2024");
        unitsField.setText("");
        amountField.setText("");
        dueDateField.setText("");
        statusCombo.setSelectedIndex(0);
    }
    
    public static void main(String[] args) {
        new AdminBillManager();
    }
}
