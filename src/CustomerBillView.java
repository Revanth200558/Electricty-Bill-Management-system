import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class CustomerBillView extends JFrame implements ActionListener {
    JLabel titleLabel;
    JTextArea billArea;
    JButton closeButton;
    Choice monthChoice;
    JButton generateButton;
    String meterNumber;

    public CustomerBillView(String meterNumber) {
        super("My Bill - Meter: " + meterNumber);
        this.meterNumber = meterNumber;
        
        setSize(500, 700);
        setLocation(350, 50);
        setLayout(new BorderLayout());

        // Title
        titleLabel = new JLabel("MY ELECTRICITY BILL", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.BLUE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Month selection panel
        JPanel selectionPanel = new JPanel(new FlowLayout());
        selectionPanel.add(new JLabel("Select Month: "));
        
        monthChoice = new Choice();
        monthChoice.add("January");
        monthChoice.add("February");
        monthChoice.add("March");
        monthChoice.add("April");
        monthChoice.add("May");
        monthChoice.add("June");
        monthChoice.add("July");
        monthChoice.add("August");
        monthChoice.add("September");
        monthChoice.add("October");
        monthChoice.add("November");
        monthChoice.add("December");
        
        generateButton = new JButton("Generate Bill");
        generateButton.setBackground(Color.BLUE);
        generateButton.setForeground(Color.WHITE);
        generateButton.addActionListener(this);
        
        selectionPanel.add(monthChoice);
        selectionPanel.add(generateButton);
        add(selectionPanel, BorderLayout.NORTH);

        // Bill display area
        billArea = new JTextArea(35, 40);
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        billArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(billArea);
        add(scrollPane, BorderLayout.CENTER);

        // Close button
        closeButton = new JButton("Close");
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        closeButton.addActionListener(this);
        add(closeButton, BorderLayout.SOUTH);

        getContentPane().setBackground(Color.WHITE);
        
        // Auto-generate current month bill
        generateBill();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == generateButton) {
            generateBill();
        } else if (ae.getSource() == closeButton) {
            this.setVisible(false);
        }
    }

    private void generateBill() {
        try {
            conn c = new conn();
            String month = monthChoice.getSelectedItem();
            
            billArea.setText("\t\tReliance Power Limited\n");
            billArea.append("ELECTRICITY BILL FOR THE MONTH OF " + month + ", 2024\n");
            billArea.append("============================================================\n\n");

            // Customer details
            ResultSet rs = c.s.executeQuery("SELECT * FROM emp WHERE meter_number='" + meterNumber + "'");
            if (rs.next()) {
                billArea.append("Customer Name: " + rs.getString("name") + "\n");
                billArea.append("Meter Number:  " + rs.getString("meter_number") + "\n");
                billArea.append("Address:       " + rs.getString("address") + "\n");
                billArea.append("State:         " + rs.getString("state") + "\n");
                billArea.append("City:          " + rs.getString("city") + "\n");
                billArea.append("Email:         " + rs.getString("email") + "\n");
                billArea.append("Phone:         " + rs.getString("phone") + "\n");
                billArea.append("------------------------------------------------------------\n\n");
            }

            // Tax details
            rs = c.s.executeQuery("SELECT * FROM tax");
            if (rs.next()) {
                billArea.append("Meter Location: " + rs.getString("meter_location") + "\n");
                billArea.append("Meter Type:     " + rs.getString("meter_type") + "\n");
                billArea.append("Phase Code:     " + rs.getString("phase_code") + "\n");
                billArea.append("Bill Type:      " + rs.getString("bill_type") + "\n");
                billArea.append("Days:           " + rs.getString("days") + "\n\n");
                billArea.append("------------------------------------------------------------\n");
                billArea.append("CHARGES BREAKDOWN:\n");
                billArea.append("------------------------------------------------------------\n");
                billArea.append("Meter Rent:     ₹" + rs.getString("meter_rent") + "\n");
                billArea.append("MCB Rent:       ₹" + rs.getString("mcb_rent") + "\n");
                billArea.append("Service Tax:    ₹" + rs.getString("service_rent") + "\n");
                billArea.append("GST@9%:         ₹" + rs.getString("gst") + "\n\n");
            }

            // Bill details
            rs = c.s.executeQuery("SELECT * FROM bill WHERE meter_number='" + meterNumber + "'");
            if (rs.next()) {
                billArea.append("Current Month:  " + rs.getString("month") + "\n");
                billArea.append("Units Consumed: " + rs.getString("units") + "\n");
                billArea.append("Total Charges:  ₹" + rs.getString("amount") + "\n");
                billArea.append("============================================================\n");
                billArea.append("TOTAL PAYABLE:  ₹" + rs.getString("amount") + "\n");
                billArea.append("============================================================\n\n");
                billArea.append("Thank you for using our services!\n");
                billArea.append("Pay online or visit our nearest office.\n");
            } else {
                billArea.append("No bill found for the selected month.\n");
                billArea.append("Please contact customer service.\n");
            }

        } catch (Exception e) {
            billArea.setText("Error loading bill details.\nPlease try again later.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new CustomerBillView("1001").setVisible(true);
    }
}
