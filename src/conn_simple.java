import java.sql.*;

public class conn_simple
{
    Connection c;
    Statement s;
    public conn_simple()
    {
        try
        {
            // Use embedded H2 database for testing without MySQL setup
            Class.forName("org.h2.Driver");
            c = DriverManager.getConnection("jdbc:h2:mem:testdb", "sa", "");
            s = c.createStatement();
            
            // Create tables in memory
            createTables();
            insertSampleData();
        }
        catch(Exception e)
        {
            System.out.println("Database connection error: " + e.getMessage());
            // Fallback to mock connection
            c = null;
            s = null;
        }
    }
    
    private void createTables() throws SQLException {
        // Create admin_login table
        s.execute("CREATE TABLE IF NOT EXISTS admin_login (username VARCHAR(50) PRIMARY KEY, password VARCHAR(100))");
        
        // Create customer_login table  
        s.execute("CREATE TABLE IF NOT EXISTS customer_login (meter_number VARCHAR(20) PRIMARY KEY, password VARCHAR(100))");
        
        // Create emp table
        s.execute("CREATE TABLE IF NOT EXISTS emp (name VARCHAR(100), meter_number VARCHAR(20) PRIMARY KEY, address VARCHAR(200), state VARCHAR(50), city VARCHAR(50), email VARCHAR(100), phone VARCHAR(15))");
        
        // Create bill table
        s.execute("CREATE TABLE IF NOT EXISTS bill (meter_number VARCHAR(20), month VARCHAR(20), units INT, amount DECIMAL(10,2))");
        
        // Create tax table
        s.execute("CREATE TABLE IF NOT EXISTS tax (meter_location VARCHAR(100), meter_type VARCHAR(50), phase_code VARCHAR(20), bill_type VARCHAR(30), days INT, meter_rent DECIMAL(8,2), mcb_rent DECIMAL(8,2), service_rent DECIMAL(8,2), gst DECIMAL(8,2))");
        
        // Create login table for backward compatibility
        s.execute("CREATE TABLE IF NOT EXISTS login (username VARCHAR(50) PRIMARY KEY, password VARCHAR(100))");
    }
    
    private void insertSampleData() throws SQLException {
        // Insert admin data
        s.execute("INSERT INTO admin_login VALUES ('admin', 'admin123')");
        s.execute("INSERT INTO login VALUES ('admin', 'admin123')");
        
        // Insert sample customer data
        s.execute("INSERT INTO emp VALUES ('John Doe', '1001', '123 Main St', 'California', 'Los Angeles', 'john@email.com', '1234567890')");
        s.execute("INSERT INTO emp VALUES ('Jane Smith', '1002', '456 Oak Ave', 'New York', 'NYC', 'jane@email.com', '0987654321')");
        
        // Insert customer login data
        s.execute("INSERT INTO customer_login VALUES ('1001', 'customer123')");
        s.execute("INSERT INTO customer_login VALUES ('1002', 'password456')");
        
        // Insert sample bill data
        s.execute("INSERT INTO bill VALUES ('1001', 'January', 150, 1284.00)");
        s.execute("INSERT INTO bill VALUES ('1002', 'January', 200, 1584.00)");
        
        // Insert tax data
        s.execute("INSERT INTO tax VALUES ('Outside', 'Electric Meter', '011', 'Normal', 30, 50.00, 12.00, 102.00, 20.00)");
    }
}
