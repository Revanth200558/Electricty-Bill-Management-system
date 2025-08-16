import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Map;

public class CustomerPanel extends JFrame implements ActionListener {
    private String customerMeterNumber;
    
    public CustomerPanel(String meterNumber) {
        super("Customer Portal - Electricity Billing System");
        this.customerMeterNumber = meterNumber;
        
        setSize(1200, 700);
        setLocation(150, 75);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        // Background image
        ImageIcon ic = new ImageIcon(ClassLoader.getSystemResource("images/main.jpg"));
        Image i3 = ic.getImage().getScaledInstance(1150, 620, Image.SCALE_DEFAULT);
        ImageIcon icc3 = new ImageIcon(i3);
        JLabel l1 = new JLabel(icc3);
        add(l1);
        
        // Menu Bar
        JMenuBar mb = new JMenuBar();
        
        // Bill Management Menu
        JMenu billMenu = new JMenu("My Bills");
        billMenu.setForeground(Color.BLUE);
        
        JMenuItem viewBill = new JMenuItem("Generate Bill");
        viewBill.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon1 = new ImageIcon(ClassLoader.getSystemResource("images/icon7.png"));
        Image image1 = icon1.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        viewBill.setIcon(new ImageIcon(image1));
        viewBill.addActionListener(this);
        
        JMenuItem billHistory = new JMenuItem("Bill History");
        billHistory.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon2 = new ImageIcon(ClassLoader.getSystemResource("images/icon6.png"));
        Image image2 = icon2.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        billHistory.setIcon(new ImageIcon(image2));
        billHistory.addActionListener(this);
        
        billMenu.add(viewBill);
        billMenu.add(billHistory);
        
        // Payment Menu
        JMenu paymentMenu = new JMenu("Payments");
        paymentMenu.setForeground(Color.GREEN);
        
        JMenuItem payBill = new JMenuItem("Pay Bill");
        payBill.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon3 = new ImageIcon(ClassLoader.getSystemResource("images/icon4.png"));
        Image image3 = icon3.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        payBill.setIcon(new ImageIcon(image3));
        payBill.addActionListener(this);
        
        paymentMenu.add(payBill);
        
        // Profile Menu
        JMenu profileMenu = new JMenu("My Profile");
        profileMenu.setForeground(Color.ORANGE);
        
        JMenuItem viewProfile = new JMenuItem("View Profile");
        viewProfile.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon4 = new ImageIcon(ClassLoader.getSystemResource("images/icon2.png"));
        Image image4 = icon4.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        viewProfile.setIcon(new ImageIcon(image4));
        viewProfile.addActionListener(this);
        
        profileMenu.add(viewProfile);
        
        // Utilities Menu (Limited)
        JMenu utilityMenu = new JMenu("Utilities");
        utilityMenu.setForeground(Color.MAGENTA);
        
        JMenuItem calculator = new JMenuItem("Calculator");
        calculator.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon5 = new ImageIcon(ClassLoader.getSystemResource("images/icon9.png"));
        Image image5 = icon5.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        calculator.setIcon(new ImageIcon(image5));
        calculator.addActionListener(this);
        
        utilityMenu.add(calculator);
        
        // System Menu
        JMenu systemMenu = new JMenu("Account");
        systemMenu.setForeground(Color.RED);
        
        JMenuItem logout = new JMenuItem("Logout");
        logout.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon6 = new ImageIcon(ClassLoader.getSystemResource("images/icon11.png"));
        Image image6 = icon6.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        logout.setIcon(new ImageIcon(image6));
        logout.addActionListener(this);
        
        systemMenu.add(logout);
        
        // Add menus to menu bar
        mb.add(billMenu);
        mb.add(paymentMenu);
        mb.add(profileMenu);
        mb.add(utilityMenu);
        mb.add(systemMenu);
        
        setJMenuBar(mb);
        
        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome Customer! Meter Number: " + customerMeterNumber, JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeLabel.setForeground(Color.BLUE);
        welcomeLabel.setOpaque(true);
        welcomeLabel.setBackground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        setLayout(new BorderLayout());
        add(welcomeLabel, BorderLayout.NORTH);
        add(l1, BorderLayout.CENTER);
        
    }
    
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        
        switch (command) {
            case "Generate Bill":
                // Get customer name from database or use default
                String customerName = "Customer";
                try {
                    Map<String, String> customerData = conn.getCustomerData(customerMeterNumber);
                    if (customerData != null && customerData.get("name") != null) {
                        customerName = customerData.get("name");
                    }
                } catch (Exception e) {
                    System.err.println("Error getting customer data: " + e.getMessage());
                }
                new SimpleBillGenerator(customerName, customerMeterNumber).setVisible(true);
                break;
            case "Bill History":
                new LastBill().setVisible(true);
                break;
            case "Pay Bill":
                new pay_bill().setVisible(true);
                break;
            case "View Profile":
                new CustomerProfile(customerMeterNumber).setVisible(true);
                break;
            case "Calculator":
                try {
                    Runtime.getRuntime().exec("calc.exe");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Logout":
                int option = JOptionPane.showConfirmDialog(this, 
                    "Are you sure you want to logout?", 
                    "Logout Confirmation", 
                    JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    new RoleSelection();
                    this.setVisible(false);
                }
                break;
        }
    }
    
    public static void main(String[] args) {
        new CustomerPanel("1001").setVisible(true);
    }
}
