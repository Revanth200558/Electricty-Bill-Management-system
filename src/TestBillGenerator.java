import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TestBillGenerator extends JFrame {
    
    public TestBillGenerator() {
        super("Test Bill Generator");
        
        setSize(400, 300);
        setLocation(100, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        
        JButton testConnButton = new JButton("Test conn.java");
        JButton testSimpleButton = new JButton("Test SimpleBillGenerator");
        
        testConnButton.addActionListener(e -> testConnClass());
        testSimpleButton.addActionListener(e -> testSimpleBillGenerator());
        
        add(testConnButton);
        add(testSimpleButton);
        
        setVisible(true);
    }
    
    private void testConnClass() {
        try {
            new conn();
            System.out.println("conn.java initialized successfully");
            
            // Test getAllMeterNumbers
            java.util.List<String> meters = conn.getAllMeterNumbers();
            System.out.println("Available meter numbers: " + meters);
            
            // Test getCustomerData
            if (!meters.isEmpty()) {
                String firstMeter = meters.get(0);
                java.util.Map<String, String> customerData = conn.getCustomerData(firstMeter);
                System.out.println("Customer data for " + firstMeter + ": " + customerData);
            }
            
            // Test calculateBillAmount
            double amount = conn.calculateBillAmount(150, "domestic");
            System.out.println("Bill amount for 150 units domestic: ₹" + amount);
            
            JOptionPane.showMessageDialog(this, "conn.java test completed successfully!\nCheck console for details.");
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error testing conn.java: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void testSimpleBillGenerator() {
        try {
            new SimpleBillGenerator("Test Customer", "TEST001").setVisible(true);
            JOptionPane.showMessageDialog(this, "SimpleBillGenerator opened successfully!");
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error opening SimpleBillGenerator: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TestBillGenerator();
        });
    }
}
