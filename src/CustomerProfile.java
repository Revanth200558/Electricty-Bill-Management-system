import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class CustomerProfile extends JFrame implements ActionListener {
    JLabel titleLabel;
    JTextArea profileArea;
    JButton closeButton, updateButton;
    String meterNumber;

    public CustomerProfile(String meterNumber) {
        super("My Profile - Meter: " + meterNumber);
        this.meterNumber = meterNumber;
        
        setSize(500, 400);
        setLocation(400, 200);
        setLayout(new BorderLayout());

        // Title
        titleLabel = new JLabel("MY PROFILE", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Profile display area
        profileArea = new JTextArea(15, 40);
        profileArea.setFont(new Font("Arial", Font.PLAIN, 14));
        profileArea.setEditable(false);
        profileArea.setBackground(Color.WHITE);
        JScrollPane scrollPane = new JScrollPane(profileArea);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        
        updateButton = new JButton("Update Profile");
        updateButton.setBackground(Color.BLUE);
        updateButton.setForeground(Color.WHITE);
        updateButton.addActionListener(this);
        
        closeButton = new JButton("Close");
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(this);
        
        buttonPanel.add(updateButton);
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().setBackground(Color.WHITE);
        
        // Load profile data
        loadProfile();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == updateButton) {
            JOptionPane.showMessageDialog(this, 
                "Profile update feature coming soon!\nPlease contact admin for changes.", 
                "Feature Not Available", 
                JOptionPane.INFORMATION_MESSAGE);
        } else if (ae.getSource() == closeButton) {
            this.setVisible(false);
        }
    }

    private void loadProfile() {
        try {
            conn c = new conn();
            ResultSet rs = c.s.executeQuery("SELECT * FROM emp WHERE meter_number='" + meterNumber + "'");
            
            if (rs.next()) {
                profileArea.setText("CUSTOMER PROFILE DETAILS\n");
                profileArea.append("========================================\n\n");
                profileArea.append("Name:           " + rs.getString("name") + "\n\n");
                profileArea.append("Meter Number:   " + rs.getString("meter_number") + "\n\n");
                profileArea.append("Address:        " + rs.getString("address") + "\n\n");
                profileArea.append("State:          " + rs.getString("state") + "\n\n");
                profileArea.append("City:           " + rs.getString("city") + "\n\n");
                profileArea.append("Email:          " + rs.getString("email") + "\n\n");
                profileArea.append("Phone Number:   " + rs.getString("phone") + "\n\n");
                profileArea.append("========================================\n");
                profileArea.append("Account Status: Active\n");
                profileArea.append("Registration:   Completed\n");
            } else {
                profileArea.setText("Profile not found!\nPlease contact customer service.");
            }

        } catch (Exception e) {
            profileArea.setText("Error loading profile.\nPlease try again later.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CustomerProfile("1001").setVisible(true);
    }
}
