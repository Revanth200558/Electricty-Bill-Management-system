import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class RoleSelection extends JFrame implements ActionListener {
    JLabel titleLabel, backgroundLabel;
    JButton adminButton, customerButton;
    JPanel buttonPanel;

    public RoleSelection() {
        super("Electricity Billing System - Select User Type");
        
        setSize(600, 400);
        setLocation(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Title
        titleLabel = new JLabel("Select User Type", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.BLUE);
        add(titleLabel, BorderLayout.NORTH);

        // Background image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("images/elect.jpg"));
        Image backgroundImage = backgroundIcon.getImage().getScaledInstance(300, 200, Image.SCALE_DEFAULT);
        backgroundLabel = new JLabel(new ImageIcon(backgroundImage));
        add(backgroundLabel, BorderLayout.CENTER);

        // Button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
        buttonPanel.setBackground(Color.WHITE);

        // Admin button
        ImageIcon adminIcon = new ImageIcon(ClassLoader.getSystemResource("images/icon1.jpg"));
        Image adminImage = adminIcon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        adminButton = new JButton("ADMIN LOGIN", new ImageIcon(adminImage));
        adminButton.setFont(new Font("Arial", Font.BOLD, 16));
        adminButton.setBackground(Color.BLUE);
        adminButton.setForeground(Color.WHITE);
        adminButton.setPreferredSize(new Dimension(200, 60));
        adminButton.addActionListener(this);

        // Customer button
        ImageIcon customerIcon = new ImageIcon(ClassLoader.getSystemResource("images/icon2.png"));
        Image customerImage = customerIcon.getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT);
        customerButton = new JButton("CUSTOMER LOGIN", new ImageIcon(customerImage));
        customerButton.setFont(new Font("Arial", Font.BOLD, 16));
        customerButton.setBackground(Color.GREEN);
        customerButton.setForeground(Color.WHITE);
        customerButton.setPreferredSize(new Dimension(200, 60));
        customerButton.addActionListener(this);

        buttonPanel.add(adminButton);
        buttonPanel.add(customerButton);
        add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(Color.WHITE);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == adminButton) {
            new AdminLogin().setVisible(true);
            this.setVisible(false);
        } else if (ae.getSource() == customerButton) {
            new CustomerLogin().setVisible(true);
            this.setVisible(false);
        }
    }

    public static void main(String[] args) {
        new RoleSelection();
    }
}
