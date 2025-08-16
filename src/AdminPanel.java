import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class AdminPanel extends JFrame implements ActionListener {
    
    public AdminPanel() {
        super("Admin Panel - Electricity Billing System");
        
        setSize(1500, 800);
        setLocation(100, 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        
        // Background image
        ImageIcon ic = new ImageIcon(ClassLoader.getSystemResource("images/main.jpg"));
        Image i3 = ic.getImage().getScaledInstance(1420, 720, Image.SCALE_DEFAULT);
        ImageIcon icc3 = new ImageIcon(i3);
        JLabel l1 = new JLabel(icc3);
        add(l1);
        
        // Menu Bar
        JMenuBar mb = new JMenuBar();
        
        // Customer Management Menu
        JMenu customerMenu = new JMenu("Customer Management");
        customerMenu.setForeground(Color.BLUE);
        
        JMenuItem addCustomer = new JMenuItem("Add New Customer");
        addCustomer.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon1 = new ImageIcon(ClassLoader.getSystemResource("images/icon1.jpg"));
        Image image1 = icon1.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        addCustomer.setIcon(new ImageIcon(image1));
        addCustomer.addActionListener(this);
        
        JMenuItem viewCustomers = new JMenuItem("View Customer Details");
        viewCustomers.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon2 = new ImageIcon(ClassLoader.getSystemResource("images/icon2.png"));
        Image image2 = icon2.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        viewCustomers.setIcon(new ImageIcon(image2));
        viewCustomers.addActionListener(this);
        
        customerMenu.add(addCustomer);
        customerMenu.add(viewCustomers);
        
        // Billing Management Menu
        JMenu billingMenu = new JMenu("Billing Management");
        billingMenu.setForeground(Color.RED);
        
        JMenuItem calculateBill = new JMenuItem("Calculate Bill");
        calculateBill.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon3 = new ImageIcon(ClassLoader.getSystemResource("images/icon5.png"));
        Image image3 = icon3.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        calculateBill.setIcon(new ImageIcon(image3));
        calculateBill.addActionListener(this);
        
        JMenuItem generateBill = new JMenuItem("Generate Bill");
        generateBill.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon4 = new ImageIcon(ClassLoader.getSystemResource("images/icon7.png"));
        Image image4 = icon4.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        generateBill.setIcon(new ImageIcon(image4));
        generateBill.addActionListener(this);
        
        JMenuItem viewLastBill = new JMenuItem("View Last Bill");
        viewLastBill.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon5 = new ImageIcon(ClassLoader.getSystemResource("images/icon6.png"));
        Image image5 = icon5.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        viewLastBill.setIcon(new ImageIcon(image5));
        viewLastBill.addActionListener(this);
        
        billingMenu.add(calculateBill);
        billingMenu.add(generateBill);
        billingMenu.add(viewLastBill);
        
        // Payment Management Menu
        JMenu paymentMenu = new JMenu("Payment Management");
        paymentMenu.setForeground(Color.GREEN);
        
        JMenuItem paymentHistory = new JMenuItem("Payment History");
        paymentHistory.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon6 = new ImageIcon(ClassLoader.getSystemResource("images/icon4.png"));
        Image image6 = icon6.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        paymentHistory.setIcon(new ImageIcon(image6));
        paymentHistory.addActionListener(this);
        
        paymentMenu.add(paymentHistory);
        
        // Utilities Menu
        JMenu utilityMenu = new JMenu("Utilities");
        utilityMenu.setForeground(Color.MAGENTA);
        
        JMenuItem notepad = new JMenuItem("Notepad");
        notepad.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon7 = new ImageIcon(ClassLoader.getSystemResource("images/icon12.png"));
        Image image7 = icon7.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        notepad.setIcon(new ImageIcon(image7));
        notepad.addActionListener(this);
        
        JMenuItem calculator = new JMenuItem("Calculator");
        calculator.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon8 = new ImageIcon(ClassLoader.getSystemResource("images/icon9.png"));
        Image image8 = icon8.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        calculator.setIcon(new ImageIcon(image8));
        calculator.addActionListener(this);
        
        JMenuItem webBrowser = new JMenuItem("Web Browser");
        webBrowser.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon9 = new ImageIcon(ClassLoader.getSystemResource("images/icon10.png"));
        Image image9 = icon9.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        webBrowser.setIcon(new ImageIcon(image9));
        webBrowser.addActionListener(this);
        
        utilityMenu.add(notepad);
        utilityMenu.add(calculator);
        utilityMenu.add(webBrowser);
        
        // System Menu
        JMenu systemMenu = new JMenu("System");
        systemMenu.setForeground(Color.BLUE);
        
        JMenuItem logout = new JMenuItem("Logout");
        logout.setFont(new Font("monospaced", Font.PLAIN, 12));
        ImageIcon icon10 = new ImageIcon(ClassLoader.getSystemResource("images/icon11.png"));
        Image image10 = icon10.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        logout.setIcon(new ImageIcon(image10));
        logout.addActionListener(this);
        
        JMenuItem exit = new JMenuItem("Exit");
        exit.setFont(new Font("monospaced", Font.PLAIN, 12));
        exit.setIcon(new ImageIcon(image10));
        exit.addActionListener(this);
        
        systemMenu.add(logout);
        systemMenu.add(exit);
        
        // Add menus to menu bar
        mb.add(customerMenu);
        mb.add(billingMenu);
        mb.add(paymentMenu);
        mb.add(utilityMenu);
        mb.add(systemMenu);
        
        setJMenuBar(mb);
        setLayout(new FlowLayout());
    }
    
    public void actionPerformed(ActionEvent ae) {
        String command = ae.getActionCommand();
        
        switch (command) {
            case "Add New Customer":
                new new_customer().setVisible(true);
                break;
            case "View Customer Details":
                new customer_details().setVisible(true);
                break;
            case "Calculate Bill":
                new AdminBillManager().setVisible(true);
                break;
            case "Advanced Bill Calculator":
                new AdvancedBillCalculator().setVisible(true);
                break;
            case "Generate Bill":
                new SimpleBillGenerator("Admin User", "ADMIN").setVisible(true);
                break;
            case "View Last Bill":
                new LastBill().setVisible(true);
                break;
            case "Payment History":
                new pay_bill().setVisible(true);
                break;
            case "Notepad":
                try {
                    Runtime.getRuntime().exec("notepad.exe");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Calculator":
                try {
                    Runtime.getRuntime().exec("calc.exe");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "Web Browser":
                try {
                    Runtime.getRuntime().exec("C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
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
            case "Exit":
                int exitOption = JOptionPane.showConfirmDialog(this, 
                    "Are you sure you want to exit?", 
                    "Exit Confirmation", 
                    JOptionPane.YES_NO_OPTION);
                if (exitOption == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                break;
        }
    }
    
    public static void main(String[] args) {
        new AdminPanel().setVisible(true);
    }
}
