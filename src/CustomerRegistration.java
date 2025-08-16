import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class CustomerRegistration extends JFrame implements ActionListener {
    JLabel titleLabel, nameLabel, meterLabel, addressLabel, stateLabel, cityLabel, emailLabel, phoneLabel, passwordLabel, confirmPasswordLabel;
    JTextField nameField, meterField, addressField, stateField, cityField, emailField, phoneField;
    JPasswordField passwordField, confirmPasswordField;
    JButton registerButton, cancelButton, backButton;
    JPanel formPanel, buttonPanel;

    public CustomerRegistration() {
        super("Customer Registration - Electricity Billing System");
        
        setSize(600, 700);
        setLocation(300, 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title
        titleLabel = new JLabel("CUSTOMER REGISTRATION", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Form panel
        formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLUE, 2), 
            "Customer Information", 
            0, 0, 
            new Font("Arial", Font.BOLD, 14), 
            Color.BLUE
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Name
        nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        nameField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(nameField, gbc);

        // Meter Number
        meterLabel = new JLabel("Meter Number:");
        meterLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(meterLabel, gbc);

        meterField = new JTextField(20);
        meterField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(meterField, gbc);

        // Address
        addressLabel = new JLabel("Address:");
        addressLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(addressLabel, gbc);

        addressField = new JTextField(20);
        addressField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(addressField, gbc);

        // State
        stateLabel = new JLabel("State:");
        stateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(stateLabel, gbc);

        stateField = new JTextField(20);
        stateField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 3;
        formPanel.add(stateField, gbc);

        // City
        cityLabel = new JLabel("City:");
        cityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(cityLabel, gbc);

        cityField = new JTextField(20);
        cityField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 4;
        formPanel.add(cityField, gbc);

        // Email
        emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        emailField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 5;
        formPanel.add(emailField, gbc);

        // Phone
        phoneLabel = new JLabel("Phone Number:");
        phoneLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(phoneLabel, gbc);

        phoneField = new JTextField(20);
        phoneField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 6;
        formPanel.add(phoneField, gbc);

        // Password
        passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 7;
        formPanel.add(passwordField, gbc);

        // Confirm Password
        confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 8;
        formPanel.add(confirmPasswordLabel, gbc);

        confirmPasswordField = new JPasswordField(20);
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 8;
        formPanel.add(confirmPasswordField, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Button panel
        buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.WHITE);

        registerButton = new JButton("REGISTER");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(Color.GREEN);
        registerButton.setForeground(Color.WHITE);
        registerButton.addActionListener(this);

        cancelButton = new JButton("CANCEL");
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelButton.setBackground(Color.RED);
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);

        backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(Color.GRAY);
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(Color.WHITE);
        nameField.requestFocus();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == registerButton) {
            registerCustomer();
        } else if (ae.getSource() == cancelButton) {
            clearFields();
        } else if (ae.getSource() == backButton) {
            new CustomerLogin().setVisible(true);
            this.setVisible(false);
        }
    }

    private void registerCustomer() {
        String name = nameField.getText().trim();
        String meterNumber = meterField.getText().trim();
        String address = addressField.getText().trim();
        String state = stateField.getText().trim();
        String city = cityField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validation
        if (name.isEmpty() || meterNumber.isEmpty() || address.isEmpty() || 
            state.isEmpty() || city.isEmpty() || email.isEmpty() || 
            phone.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please fill all fields!", 
                "Input Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, 
                "Passwords do not match!", 
                "Password Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, 
                "Password must be at least 6 characters long!", 
                "Password Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Initialize connection to load mock data
            new conn();
            
            // Use direct method to add customer
            conn.addCustomer(meterNumber, password, name, address, state, city, email, phone);
            
            JOptionPane.showMessageDialog(this, 
                "Registration successful!\nYou can now login with meter number: " + meterNumber,
                "Registration Complete",
                JOptionPane.INFORMATION_MESSAGE);

            new CustomerLogin().setVisible(true);
            this.setVisible(false);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Registration failed!\nPlease try again.", 
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void clearFields() {
        nameField.setText("");
        meterField.setText("");
        addressField.setText("");
        stateField.setText("");
        cityField.setText("");
        emailField.setText("");
        phoneField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        nameField.requestFocus();
    }

    public static void main(String[] args) {
        new CustomerRegistration().setVisible(true);
    }
}
