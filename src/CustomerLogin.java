import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class CustomerLogin extends JFrame implements ActionListener {
    JLabel titleLabel, meterLabel, passwordLabel, backgroundLabel;
    JTextField meterField;
    JPasswordField passwordField;
    JButton loginButton, cancelButton, backButton, registerButton;
    JPanel formPanel, buttonPanel;

    public CustomerLogin() {
        super("Customer Login - Electricity Billing System");
        
        setSize(700, 500);
        setLocation(350, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title
        titleLabel = new JLabel("CUSTOMER LOGIN", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.GREEN);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Main panel with background
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Background image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("images/pop.jpg"));
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(300, 350, Image.SCALE_DEFAULT);
        backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        mainPanel.add(backgroundLabel, BorderLayout.WEST);

        // Form panel
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.GREEN, 2), 
            "Customer Credentials", 
            0, 0, 
            new Font("Arial", Font.BOLD, 14), 
            Color.GREEN
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Meter Number
        meterLabel = new JLabel("Meter Number:");
        meterLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(meterLabel, gbc);

        meterField = new JTextField(15);
        meterField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(meterField, gbc);

        // Password
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(passwordField, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);

        // Button panel
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        // Login button
        ImageIcon loginIcon = new ImageIcon(ClassLoader.getSystemResource("images/login.jpg"));
        Image loginImage = loginIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        loginButton = new JButton("LOGIN", new ImageIcon(loginImage));
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(Color.GREEN);
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this);

        // Register button
        registerButton = new JButton("REGISTER");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(Color.ORANGE);
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(this);

        // Cancel button
        ImageIcon cancelIcon = new ImageIcon(ClassLoader.getSystemResource("images/cancel.png"));
        Image cancelImage = cancelIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        cancelButton = new JButton("CANCEL", new ImageIcon(cancelImage));
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);

        // Back button
        backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(Color.WHITE);
        
        // Set focus to meter field
        meterField.requestFocus();
        
        // Add Enter key support
        getRootPane().setDefaultButton(loginButton);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == loginButton) {
            authenticateCustomer();
        } else if (ae.getSource() == registerButton) {
            new CustomerRegistration().setVisible(true);
            this.setVisible(false);
        } else if (ae.getSource() == cancelButton) {
            System.exit(0);
        } else if (ae.getSource() == backButton) {
            new RoleSelection();
            this.setVisible(false);
        }
    }

    private void authenticateCustomer() {
        String meterNumber = meterField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (meterNumber.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter both meter number and password!", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Initialize connection to load mock data
            new conn();
            
            // Use direct validation method
            if (conn.validateCustomerLogin(meterNumber, password)) {
                this.setVisible(false);
                new CustomerPanel(meterNumber);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Invalid customer credentials!\nPlease check your meter number and password.", 
                    "Authentication Failed", 
                    JOptionPane.ERROR_MESSAGE);
                passwordField.setText("");
                meterField.requestFocus();
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Database connection error!\nPlease contact system administrator.", 
                "System Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CustomerLogin().setVisible(true);
    }
}
