import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SimpleBillGenerator extends JFrame implements ActionListener {
    private JTextField unitsField, rateField;
    private JTextArea billTicketArea;
    private JButton generateButton, printButton, closeButton;
    private JLabel totalAmountLabel;
    private String customerName;
    private String meterNumber;
    
    private DecimalFormat df = new DecimalFormat("#,##0.00");
    
    // Modern Colors
    private static final Color DARK_BG = new Color(30, 30, 35);
    private static final Color CARD_BG = new Color(45, 45, 50);
    private static final Color ACCENT_BLUE = new Color(64, 158, 255);
    private static final Color ACCENT_GREEN = new Color(76, 217, 100);
    private static final Color TEXT_WHITE = new Color(255, 255, 255);
    private static final Color TEXT_GRAY = new Color(174, 174, 178);
    
    public SimpleBillGenerator(String customerName, String meterNumber) {
        super("⚡ Electricity Bill Generator");
        this.customerName = customerName;
        this.meterNumber = meterNumber;
        
        setupWindow();
        createComponents();
        layoutComponents();
        attachListeners();
        
        setVisible(true);
    }
    
    private void setupWindow() {
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(DARK_BG);
    }
    
    private void createComponents() {
        // Input fields
        unitsField = createStyledTextField();
        rateField = createStyledTextField();
        rateField.setText("5.50"); // Default rate per unit
        
        // Total amount label
        totalAmountLabel = new JLabel("₹0.00");
        totalAmountLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        totalAmountLabel.setForeground(ACCENT_GREEN);
        
        // Bill ticket area
        billTicketArea = new JTextArea();
        billTicketArea.setFont(new Font("Consolas", Font.PLAIN, 12));
        billTicketArea.setBackground(new Color(20, 20, 25));
        billTicketArea.setForeground(TEXT_WHITE);
        billTicketArea.setEditable(false);
        billTicketArea.setMargin(new Insets(20, 20, 20, 20));
        
        // Buttons
        generateButton = createStyledButton("⚡ Generate Bill", ACCENT_BLUE);
        printButton = createStyledButton("🖨️ Print Ticket", ACCENT_GREEN);
        closeButton = createStyledButton("❌ Close", TEXT_GRAY);
        
        displayWelcomeMessage();
    }
    
    private void layoutComponents() {
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ACCENT_BLUE);
        headerPanel.setPreferredSize(new Dimension(800, 60));
        
        JLabel title = new JLabel("⚡ ELECTRICITY BILL GENERATOR", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(TEXT_WHITE);
        headerPanel.add(title, BorderLayout.CENTER);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main panel
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBackground(DARK_BG);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Left panel (inputs)
        JPanel leftPanel = createInputPanel();
        
        // Right panel (ticket display)
        JPanel rightPanel = createTicketPanel();
        
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);
        
        add(mainPanel, BorderLayout.CENTER);
        
        // Bottom panel (buttons)
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        bottomPanel.setBackground(DARK_BG);
        bottomPanel.add(generateButton);
        bottomPanel.add(printButton);
        bottomPanel.add(closeButton);
        
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createInputPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(300, 400));
        panel.setBackground(DARK_BG);
        
        // Customer info card
        JPanel customerCard = createCard("👤 Customer Information", createCustomerInfoPanel());
        panel.add(customerCard);
        panel.add(Box.createVerticalStrut(20));
        
        // Bill details card
        JPanel billCard = createCard("⚡ Bill Details", createBillDetailsPanel());
        panel.add(billCard);
        panel.add(Box.createVerticalStrut(20));
        
        // Total card
        JPanel totalCard = createCard("💰 Total Amount", createTotalPanel());
        panel.add(totalCard);
        
        return panel;
    }
    
    private JPanel createCard(String title, JPanel content) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_BLUE, 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(ACCENT_BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        card.add(titleLabel, BorderLayout.NORTH);
        card.add(content, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createCustomerInfoPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createLabel("Name:"), gbc);
        gbc.gridy = 1;
        JLabel nameLabel = new JLabel(customerName);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        nameLabel.setForeground(TEXT_WHITE);
        panel.add(nameLabel, gbc);
        
        gbc.gridy = 2;
        panel.add(createLabel("Meter No:"), gbc);
        gbc.gridy = 3;
        JLabel meterLabel = new JLabel(meterNumber);
        meterLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        meterLabel.setForeground(TEXT_WHITE);
        panel.add(meterLabel, gbc);
        
        return panel;
    }
    
    private JPanel createBillDetailsPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createLabel("Units Consumed:"), gbc);
        gbc.gridy = 1;
        panel.add(unitsField, gbc);
        
        gbc.gridy = 2;
        panel.add(createLabel("Rate per Unit (₹):"), gbc);
        gbc.gridy = 3;
        panel.add(rateField, gbc);
        
        return panel;
    }
    
    private JPanel createTotalPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        
        panel.add(totalAmountLabel, gbc);
        
        return panel;
    }
    
    private JPanel createTicketPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(DARK_BG);
        
        JLabel title = new JLabel("🎫 Bill Ticket", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(ACCENT_BLUE);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JScrollPane scrollPane = new JScrollPane(billTicketArea);
        scrollPane.setBackground(DARK_BG);
        scrollPane.setBorder(BorderFactory.createLineBorder(ACCENT_BLUE, 1));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    private void attachListeners() {
        javax.swing.event.DocumentListener calcListener = new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { calculateTotal(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { calculateTotal(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { calculateTotal(); }
        };
        
        unitsField.getDocument().addDocumentListener(calcListener);
        rateField.getDocument().addDocumentListener(calcListener);
    }
    
    private void calculateTotal() {
        try {
            String unitsText = unitsField.getText().trim();
            String rateText = rateField.getText().trim();
            
            if (!unitsText.isEmpty() && !rateText.isEmpty()) {
                double units = Double.parseDouble(unitsText);
                double rate = Double.parseDouble(rateText);
                double total = units * rate;
                totalAmountLabel.setText("₹" + df.format(total));
            } else {
                totalAmountLabel.setText("₹0.00");
            }
        } catch (NumberFormatException e) {
            totalAmountLabel.setText("₹0.00");
        }
    }
    
    private void displayWelcomeMessage() {
        billTicketArea.setText(
            "┌─────────────────────────────────────────────┐\n" +
            "│           ELECTRICITY BILL TICKET          │\n" +
            "│         State Electricity Board            │\n" +
            "├─────────────────────────────────────────────┤\n" +
            "│                                             │\n" +
            "│  Welcome " + customerName + "!                     │\n" +
            "│                                             │\n" +
            "│  Instructions:                              │\n" +
            "│  1. Enter units consumed                    │\n" +
            "│  2. Adjust rate per unit if needed          │\n" +
            "│  3. Click 'Generate Bill' for ticket       │\n" +
            "│                                             │\n" +
            "│  Your Meter Number: " + meterNumber + "                │\n" +
            "│  Date: " + LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "                         │\n" +
            "│                                             │\n" +
            "└─────────────────────────────────────────────┘\n"
        );
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch (command) {
            case "⚡ Generate Bill":
                generateBillTicket();
                break;
            case "🖨️ Print Ticket":
                printTicket();
                break;
            case "❌ Close":
                dispose();
                break;
        }
    }
    
    private void generateBillTicket() {
        try {
            String unitsText = unitsField.getText().trim();
            String rateText = rateField.getText().trim();
            
            if (unitsText.isEmpty() || rateText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter units and rate!", "Input Required", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            double units = Double.parseDouble(unitsText);
            double rate = Double.parseDouble(rateText);
            double total = units * rate;
            
            String billNumber = "EB" + System.currentTimeMillis();
            LocalDate billDate = LocalDate.now();
            LocalDate dueDate = billDate.plusDays(30);
            
            StringBuilder ticket = new StringBuilder();
            ticket.append("┌─────────────────────────────────────────────┐\n");
            ticket.append("│           ELECTRICITY BILL TICKET          │\n");
            ticket.append("│         State Electricity Board            │\n");
            ticket.append("├─────────────────────────────────────────────┤\n");
            ticket.append("│                                             │\n");
            ticket.append(String.format("│  Bill No: %-32s │\n", billNumber));
            ticket.append(String.format("│  Date: %-35s │\n", billDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            ticket.append(String.format("│  Due Date: %-31s │\n", dueDate.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));
            ticket.append("│                                             │\n");
            ticket.append("│  CUSTOMER DETAILS:                         │\n");
            ticket.append(String.format("│  Name: %-35s │\n", customerName));
            ticket.append(String.format("│  Meter No: %-31s │\n", meterNumber));
            ticket.append("│                                             │\n");
            ticket.append("│  CONSUMPTION DETAILS:                      │\n");
            ticket.append(String.format("│  Units Consumed: %-25.2f │\n", units));
            ticket.append(String.format("│  Rate per Unit: ₹%-24.2f │\n", rate));
            ticket.append("│                                             │\n");
            ticket.append("│  BILL SUMMARY:                             │\n");
            ticket.append(String.format("│  Energy Charges: ₹%-23.2f │\n", total));
            ticket.append("│  ─────────────────────────────────────────  │\n");
            ticket.append(String.format("│  TOTAL AMOUNT: ₹%-25.2f │\n", total));
            ticket.append("│                                             │\n");
            ticket.append("│  PAYMENT INFO:                             │\n");
            ticket.append("│  • Pay before due date                     │\n");
            ticket.append("│  • Online: www.electricityboard.gov.in     │\n");
            ticket.append("│  • Customer Care: 1800-XXX-XXXX            │\n");
            ticket.append("│                                             │\n");
            ticket.append("│         Thank you for your payment!        │\n");
            ticket.append("└─────────────────────────────────────────────┘\n");
            
            billTicketArea.setText(ticket.toString());
            
            // Save to database if possible
            try {
                conn.addBill(meterNumber, LocalDate.now().getMonth().toString(), 
                           String.valueOf(LocalDate.now().getYear()), 
                           String.valueOf((int)units), String.valueOf(total), 
                           "Generated", dueDate.toString());
            } catch (Exception ex) {
                System.err.println("Database save warning: " + ex.getMessage());
            }
            
            JOptionPane.showMessageDialog(this, 
                "✅ Bill ticket generated successfully!\n\n" +
                "📄 Bill Number: " + billNumber + "\n" +
                "👤 Customer: " + customerName + "\n" +
                "💰 Amount: ₹" + df.format(total), 
                "Bill Generated", 
                JOptionPane.INFORMATION_MESSAGE);
                
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for units and rate!", 
                "Invalid Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error generating bill: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void printTicket() {
        try {
            billTicketArea.print();
            JOptionPane.showMessageDialog(this, "🖨️ Ticket sent to printer successfully!", 
                "Print Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Print error: " + e.getMessage(), 
                "Print Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(TEXT_GRAY);
        return label;
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField(15);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(new Color(55, 55, 60));
        field.setForeground(TEXT_WHITE);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_BLUE, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setCaretColor(TEXT_WHITE);
        return field;
    }
    
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(this);
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(color.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SimpleBillGenerator("Test Customer", "1001");
        });
    }
}
