import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.List;

public class CustomerBillViewer extends JFrame implements ActionListener {
    private String customerMeterNumber;
    private JTable billTable;
    private JScrollPane scrollPane;
    private JButton refreshButton, closeButton, payButton;
    private JLabel totalLabel;
    
    public CustomerBillViewer(String meterNumber) {
        super("My Bills - Meter: " + meterNumber);
        this.customerMeterNumber = meterNumber;
        
        setSize(800, 500);
        setLocation(200, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Initialize data
        new conn();
        
        // Title
        JLabel titleLabel = new JLabel("MY ELECTRICITY BILLS", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);
        
        // Customer info panel
        JPanel infoPanel = new JPanel(new FlowLayout());
        Map<String, String> customerData = conn.getCustomerData(meterNumber);
        if (customerData != null) {
            JLabel customerLabel = new JLabel("Customer: " + customerData.get("name") + " | Meter: " + meterNumber);
            customerLabel.setFont(new Font("Arial", Font.BOLD, 14));
            infoPanel.add(customerLabel);
        }
        add(infoPanel, BorderLayout.NORTH);
        
        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Bill History"));
        
        String[] columnNames = {"Month", "Year", "Units", "Amount", "Status", "Due Date"};
        billTable = new JTable();
        scrollPane = new JScrollPane(billTable);
        scrollPane.setPreferredSize(new Dimension(750, 300));
        
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        
        // Summary panel
        JPanel summaryPanel = new JPanel(new FlowLayout());
        totalLabel = new JLabel();
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        summaryPanel.add(totalLabel);
        tablePanel.add(summaryPanel, BorderLayout.SOUTH);
        
        add(tablePanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        refreshButton = new JButton("Refresh");
        refreshButton.setBackground(Color.BLUE);
        refreshButton.setForeground(Color.WHITE);
        refreshButton.addActionListener(this);
        
        payButton = new JButton("Pay Selected Bill");
        payButton.setBackground(Color.GREEN);
        payButton.setForeground(Color.WHITE);
        payButton.addActionListener(this);
        
        closeButton = new JButton("Close");
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(this);
        
        buttonPanel.add(refreshButton);
        buttonPanel.add(payButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);
        
        // Load bills
        loadBillTable();
        
        setVisible(true);
    }
    
    private void loadBillTable() {
        String[] columnNames = {"Month", "Year", "Units", "Amount", "Status", "Due Date"};
        List<Map<String, String>> bills = conn.getBillData(customerMeterNumber);
        
        if (bills == null || bills.isEmpty()) {
            Object[][] emptyData = {{"No bills found", "", "", "", "", ""}};
            billTable.setModel(new javax.swing.table.DefaultTableModel(emptyData, columnNames));
            totalLabel.setText("Total Outstanding: ₹0.00");
            return;
        }
        
        Object[][] data = new Object[bills.size()][6];
        double totalOutstanding = 0.0;
        
        for (int i = 0; i < bills.size(); i++) {
            Map<String, String> bill = bills.get(i);
            data[i][0] = bill.get("month");
            data[i][1] = bill.get("year");
            data[i][2] = bill.get("units") + " kWh";
            data[i][3] = "₹" + bill.get("amount");
            data[i][4] = bill.get("status");
            data[i][5] = bill.get("due_date");
            
            // Calculate outstanding amount
            if ("Pending".equals(bill.get("status")) || "Overdue".equals(bill.get("status"))) {
                try {
                    totalOutstanding += Double.parseDouble(bill.get("amount"));
                } catch (NumberFormatException e) {
                    // Ignore invalid amounts
                }
            }
        }
        
        billTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));
        totalLabel.setText("Total Outstanding: ₹" + String.format("%.2f", totalOutstanding));
        
        // Set row colors based on status
        billTable.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    String status = (String) table.getValueAt(row, 4);
                    if ("Paid".equals(status)) {
                        c.setBackground(new Color(200, 255, 200)); // Light green
                    } else if ("Overdue".equals(status)) {
                        c.setBackground(new Color(255, 200, 200)); // Light red
                    } else if ("Pending".equals(status)) {
                        c.setBackground(new Color(255, 255, 200)); // Light yellow
                    } else {
                        c.setBackground(Color.WHITE);
                    }
                }
                return c;
            }
        });
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == refreshButton) {
            loadBillTable();
            JOptionPane.showMessageDialog(this, "Bills refreshed!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else if (ae.getSource() == payButton) {
            paySelectedBill();
        } else if (ae.getSource() == closeButton) {
            this.dispose();
        }
    }
    
    private void paySelectedBill() {
        int selectedRow = billTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, 
                "Please select a bill to pay!", 
                "No Selection", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        String status = (String) billTable.getValueAt(selectedRow, 4);
        if ("Paid".equals(status)) {
            JOptionPane.showMessageDialog(this, 
                "This bill is already paid!", 
                "Already Paid", 
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        String month = (String) billTable.getValueAt(selectedRow, 0);
        String year = (String) billTable.getValueAt(selectedRow, 1);
        String amount = (String) billTable.getValueAt(selectedRow, 3);
        
        int option = JOptionPane.showConfirmDialog(this, 
            "Pay bill for " + month + " " + year + "\nAmount: " + amount + "\n\nConfirm payment?", 
            "Confirm Payment", 
            JOptionPane.YES_NO_OPTION);
            
        if (option == JOptionPane.YES_OPTION) {
            // Update bill status to paid
            updateBillStatus(month, year, "Paid");
            loadBillTable();
            JOptionPane.showMessageDialog(this, 
                "Payment successful!\nBill for " + month + " " + year + " has been paid.", 
                "Payment Successful", 
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void updateBillStatus(String month, String year, String newStatus) {
        List<Map<String, String>> bills = conn.getBillData(customerMeterNumber);
        if (bills != null) {
            for (Map<String, String> bill : bills) {
                if (month.equals(bill.get("month")) && year.equals(bill.get("year"))) {
                    bill.put("status", newStatus);
                    break;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        new CustomerBillViewer("1001");
    }
}
