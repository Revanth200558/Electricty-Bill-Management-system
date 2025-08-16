import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.*;
import java.util.*;
import java.util.List;
import java.text.DecimalFormat;

public class CustomerDashboard extends JFrame implements ActionListener {
    private String customerMeterNumber;
    private JLabel welcomeLabel, totalBillsLabel, pendingBillsLabel, totalAmountLabel;
    private JTable billHistoryTable, paymentHistoryTable;
    private DefaultTableModel billTableModel, paymentTableModel;
    private JTextArea notificationsArea;
    private JButton refreshButton, payBillButton, viewBillButton, closeButton;
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    
    // Modern color scheme
    private Color primaryColor = new Color(41, 128, 185);
    private Color successColor = new Color(39, 174, 96);
    private Color warningColor = new Color(241, 196, 15);
    private Color dangerColor = new Color(231, 76, 60);
    private Color lightGray = new Color(236, 240, 241);
    
    public CustomerDashboard(String meterNumber) {
        super("Customer Dashboard - Professional Edition");
        this.customerMeterNumber = meterNumber;
        
        setSize(1200, 800);
        setLocation(100, 50);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        createUI();
        loadDashboardData();
        
        setVisible(true);
    }
    
    private void createUI() {
        // Header Panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Main Content Panel
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(lightGray);
        
        // Top Panel - Statistics Cards
        JPanel statsPanel = createStatsPanel();
        
        // Center Panel - Tables
        JPanel tablesPanel = createTablesPanel();
        
        // Right Panel - Notifications
        JPanel notificationsPanel = createNotificationsPanel();
        
        mainPanel.add(statsPanel, BorderLayout.NORTH);
        mainPanel.add(tablesPanel, BorderLayout.CENTER);
        mainPanel.add(notificationsPanel, BorderLayout.EAST);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Bottom Panel - Action Buttons
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(primaryColor);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        
        Map<String, String> customerData = conn.getCustomerData(customerMeterNumber);
        String customerName = customerData != null ? customerData.get("name") : "Unknown Customer";
        
        welcomeLabel = new JLabel("👋 Welcome, " + customerName, JLabel.LEFT);
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.WHITE);
        
        JLabel meterLabel = new JLabel("Meter Number: " + customerMeterNumber, JLabel.RIGHT);
        meterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        meterLabel.setForeground(new Color(189, 195, 199));
        
        JLabel dateLabel = new JLabel("Dashboard Date: " + java.time.LocalDate.now(), JLabel.RIGHT);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateLabel.setForeground(new Color(189, 195, 199));
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(primaryColor);
        rightPanel.add(meterLabel, BorderLayout.NORTH);
        rightPanel.add(dateLabel, BorderLayout.SOUTH);
        
        panel.add(welcomeLabel, BorderLayout.WEST);
        panel.add(rightPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createStatsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 4, 15, 0));
        panel.setBackground(lightGray);
        panel.setPreferredSize(new Dimension(1000, 120));
        
        // Create stat cards
        totalBillsLabel = new JLabel("0");
        JPanel totalBillsCard = createStatCard("📋 Total Bills", totalBillsLabel, primaryColor);
        
        pendingBillsLabel = new JLabel("0");
        JPanel pendingBillsCard = createStatCard("⏳ Pending Bills", pendingBillsLabel, warningColor);
        
        totalAmountLabel = new JLabel("₹0.00");
        JPanel totalAmountCard = createStatCard("💰 Outstanding Amount", totalAmountLabel, dangerColor);
        
        JLabel lastPaymentLabel = new JLabel("N/A");
        JPanel lastPaymentCard = createStatCard("💳 Last Payment", lastPaymentLabel, successColor);
        
        panel.add(totalBillsCard);
        panel.add(pendingBillsCard);
        panel.add(totalAmountCard);
        panel.add(lastPaymentCard);
        
        return panel;
    }
    
    private JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        titleLabel.setForeground(color);
        
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        valueLabel.setForeground(new Color(44, 62, 80));
        valueLabel.setHorizontalAlignment(JLabel.CENTER);
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createTablesPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBackground(lightGray);
        
        // Bill History Table
        JPanel billHistoryPanel = createBillHistoryPanel();
        
        // Payment History Table
        JPanel paymentHistoryPanel = createPaymentHistoryPanel();
        
        panel.add(billHistoryPanel);
        panel.add(paymentHistoryPanel);
        
        return panel;
    }
    
    private JPanel createBillHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(primaryColor, 2),
            "📋 RECENT BILL HISTORY",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            primaryColor
        ));
        
        String[] billColumns = {"Month", "Year", "Units", "Amount", "Status", "Due Date"};
        billTableModel = new DefaultTableModel(billColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        billHistoryTable = new JTable(billTableModel);
        billHistoryTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        billHistoryTable.setRowHeight(25);
        billHistoryTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        // Style table header
        JTableHeader billHeader = billHistoryTable.getTableHeader();
        billHeader.setBackground(primaryColor);
        billHeader.setForeground(Color.WHITE);
        billHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        
        // Custom cell renderer for status column
        billHistoryTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    String status = (String) value;
                    if ("Paid".equals(status)) {
                        c.setBackground(new Color(212, 237, 218));
                        c.setForeground(new Color(21, 87, 36));
                    } else if ("Pending".equals(status)) {
                        c.setBackground(new Color(255, 243, 205));
                        c.setForeground(new Color(133, 100, 4));
                    } else if ("Overdue".equals(status)) {
                        c.setBackground(new Color(248, 215, 218));
                        c.setForeground(new Color(114, 28, 36));
                    } else {
                        c.setBackground(Color.WHITE);
                        c.setForeground(Color.BLACK);
                    }
                }
                return c;
            }
        });
        
        JScrollPane billScrollPane = new JScrollPane(billHistoryTable);
        panel.add(billScrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createPaymentHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(successColor, 2),
            "💳 PAYMENT HISTORY",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            successColor
        ));
        
        String[] paymentColumns = {"Date", "Amount", "Method", "Transaction ID", "Status"};
        paymentTableModel = new DefaultTableModel(paymentColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        paymentHistoryTable = new JTable(paymentTableModel);
        paymentHistoryTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        paymentHistoryTable.setRowHeight(25);
        
        // Style table header
        JTableHeader paymentHeader = paymentHistoryTable.getTableHeader();
        paymentHeader.setBackground(successColor);
        paymentHeader.setForeground(Color.WHITE);
        paymentHeader.setFont(new Font("Segoe UI", Font.BOLD, 11));
        
        JScrollPane paymentScrollPane = new JScrollPane(paymentHistoryTable);
        panel.add(paymentScrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createNotificationsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, 400));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(warningColor, 2),
            "🔔 NOTIFICATIONS",
            TitledBorder.LEFT,
            TitledBorder.TOP,
            new Font("Segoe UI", Font.BOLD, 14),
            warningColor
        ));
        
        notificationsArea = new JTextArea();
        notificationsArea.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        notificationsArea.setEditable(false);
        notificationsArea.setBackground(new Color(248, 249, 250));
        notificationsArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        notificationsArea.setLineWrap(true);
        notificationsArea.setWrapStyleWord(true);
        
        JScrollPane notificationScrollPane = new JScrollPane(notificationsArea);
        panel.add(notificationScrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        panel.setBackground(lightGray);
        
        refreshButton = createStyledButton("🔄 Refresh Dashboard", primaryColor);
        payBillButton = createStyledButton("💳 Pay Selected Bill", successColor);
        viewBillButton = createStyledButton("📋 View All Bills", warningColor);
        closeButton = createStyledButton("❌ Close Dashboard", dangerColor);
        
        refreshButton.addActionListener(this);
        payBillButton.addActionListener(this);
        viewBillButton.addActionListener(this);
        closeButton.addActionListener(this);
        
        panel.add(refreshButton);
        panel.add(payBillButton);
        panel.add(viewBillButton);
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
    
    private void loadDashboardData() {
        // Load bill statistics
        List<Map<String, String>> customerBills = conn.getBillsForCustomer(customerMeterNumber);
        
        int totalBills = customerBills != null ? customerBills.size() : 0;
        int pendingBills = 0;
        double totalOutstanding = 0.0;
        
        totalBillsLabel.setText(String.valueOf(totalBills));
        
        // Load bill history table
        billTableModel.setRowCount(0);
        if (customerBills != null) {
            for (Map<String, String> bill : customerBills) {
                String status = bill.get("status");
                if ("Pending".equals(status) || "Overdue".equals(status)) {
                    pendingBills++;
                    try {
                        totalOutstanding += Double.parseDouble(bill.get("amount"));
                    } catch (NumberFormatException e) {
                        // Ignore invalid amounts
                    }
                }
                
                Object[] rowData = {
                    bill.get("month"),
                    bill.get("year"),
                    bill.get("units") + " kWh",
                    "₹" + df.format(Double.parseDouble(bill.get("amount"))),
                    status,
                    bill.get("due_date")
                };
                billTableModel.addRow(rowData);
            }
        }
        
        pendingBillsLabel.setText(String.valueOf(pendingBills));
        totalAmountLabel.setText("₹" + df.format(totalOutstanding));
        
        // Load payment history
        loadPaymentHistory();
        
        // Load notifications
        loadNotifications();
    }
    
    private void loadPaymentHistory() {
        paymentTableModel.setRowCount(0);
        List<Map<String, String>> payments = conn.getPaymentHistory(customerMeterNumber);
        
        if (payments != null) {
            for (Map<String, String> payment : payments) {
                Object[] rowData = {
                    payment.get("date"),
                    "₹" + df.format(Double.parseDouble(payment.get("amount"))),
                    payment.get("method"),
                    payment.get("transaction_id"),
                    payment.get("status")
                };
                paymentTableModel.addRow(rowData);
            }
        }
    }
    
    private void loadNotifications() {
        StringBuilder notifications = new StringBuilder();
        notifications.append("📢 RECENT NOTIFICATIONS\n");
        notifications.append("═══════════════════════════════════\n\n");
        
        // Sample notifications - in a real system, these would come from the database
        notifications.append("🔔 Your February 2024 bill is now available\n");
        notifications.append("   Amount: ₹1,284.00 | Due: 2024-03-15\n\n");
        
        notifications.append("💡 Energy Saving Tip:\n");
        notifications.append("   Use LED bulbs to reduce electricity consumption by up to 80%\n\n");
        
        notifications.append("⚡ Rate Update:\n");
        notifications.append("   New electricity rates effective from March 2024\n\n");
        
        notifications.append("🎯 Usage Alert:\n");
        notifications.append("   Your current month usage is 15% higher than last month\n\n");
        
        notifications.append("💳 Payment Reminder:\n");
        notifications.append("   1 bill is due within 7 days. Pay now to avoid late charges.\n\n");
        
        notificationsArea.setText(notifications.toString());
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == refreshButton) {
            loadDashboardData();
            JOptionPane.showMessageDialog(this, "Dashboard refreshed successfully!", "Refresh Complete", JOptionPane.INFORMATION_MESSAGE);
        } else if (ae.getSource() == payBillButton) {
            int selectedRow = billHistoryTable.getSelectedRow();
            if (selectedRow >= 0) {
                String status = (String) billTableModel.getValueAt(selectedRow, 4);
                if ("Pending".equals(status) || "Overdue".equals(status)) {
                    paySelectedBill(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(this, "Selected bill is already paid!", "Payment Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Please select a bill to pay!", "Selection Required", JOptionPane.WARNING_MESSAGE);
            }
        } else if (ae.getSource() == viewBillButton) {
            new CustomerBillViewer(customerMeterNumber).setVisible(true);
        } else if (ae.getSource() == closeButton) {
            this.dispose();
        }
    }
    
    private void paySelectedBill(int selectedRow) {
        String month = (String) billTableModel.getValueAt(selectedRow, 0);
        String year = (String) billTableModel.getValueAt(selectedRow, 1);
        String amountStr = (String) billTableModel.getValueAt(selectedRow, 3);
        
        // Extract numeric amount
        String numericAmount = amountStr.replace("₹", "").replace(",", "").trim();
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Pay bill for " + month + " " + year + "?\n" +
            "Amount: " + amountStr,
            "Confirm Payment",
            JOptionPane.YES_NO_OPTION);
            
        if (confirm == JOptionPane.YES_OPTION) {
            // Update bill status
            conn.updateBillStatus(customerMeterNumber, month, year, "Paid");
            
            // Add payment record
            String transactionId = "TXN" + System.currentTimeMillis();
            conn.addPayment(customerMeterNumber, numericAmount, "Online", transactionId);
            
            // Refresh dashboard
            loadDashboardData();
            
            JOptionPane.showMessageDialog(this,
                "Payment successful!\n" +
                "Transaction ID: " + transactionId + "\n" +
                "Amount: " + amountStr,
                "Payment Successful",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new conn(); // Initialize database
            new CustomerDashboard("1001");
        });
    }
}
