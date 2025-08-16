-- Database Setup Script for Electricity Billing System
-- Run this script in MySQL to create the required database and tables

-- Create database
CREATE DATABASE IF NOT EXISTS ebs;
USE ebs;

-- Create admin_login table for administrator authentication
CREATE TABLE IF NOT EXISTS admin_login (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create customer_login table for customer authentication
CREATE TABLE IF NOT EXISTS customer_login (
    meter_number VARCHAR(20) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create existing login table (for backward compatibility)
CREATE TABLE IF NOT EXISTS login (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL
);

-- Create emp table for customer details
CREATE TABLE IF NOT EXISTS emp (
    name VARCHAR(100) NOT NULL,
    meter_number VARCHAR(20) PRIMARY KEY,
    address VARCHAR(200) NOT NULL,
    state VARCHAR(50) NOT NULL,
    city VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    phone VARCHAR(15) NOT NULL
);

-- Create bill table for billing information
CREATE TABLE IF NOT EXISTS bill (
    meter_number VARCHAR(20),
    month VARCHAR(20),
    units INT NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    bill_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (meter_number) REFERENCES emp(meter_number)
);

-- Create tax table for tax and charges information
CREATE TABLE IF NOT EXISTS tax (
    meter_location VARCHAR(100) DEFAULT 'Outside',
    meter_type VARCHAR(50) DEFAULT 'Electric Meter',
    phase_code VARCHAR(20) DEFAULT '011',
    bill_type VARCHAR(30) DEFAULT 'Normal',
    days INT DEFAULT 30,
    meter_rent DECIMAL(8,2) DEFAULT 50.00,
    mcb_rent DECIMAL(8,2) DEFAULT 12.00,
    service_rent DECIMAL(8,2) DEFAULT 102.00,
    gst DECIMAL(8,2) DEFAULT 20.00
);

-- Insert default admin user
INSERT INTO admin_login (username, password) VALUES 
('admin', 'admin123'),
('administrator', 'password123')
ON DUPLICATE KEY UPDATE password=VALUES(password);

-- Insert default login data (for backward compatibility)
INSERT INTO login (username, password) VALUES 
('admin', 'admin123')
ON DUPLICATE KEY UPDATE password=VALUES(password);

-- Insert default tax information
INSERT INTO tax VALUES 
('Outside', 'Electric Meter', '011', 'Normal', 30, 50.00, 12.00, 102.00, 20.00)
ON DUPLICATE KEY UPDATE 
    meter_location=VALUES(meter_location),
    meter_type=VALUES(meter_type),
    phase_code=VALUES(phase_code),
    bill_type=VALUES(bill_type),
    days=VALUES(days),
    meter_rent=VALUES(meter_rent),
    mcb_rent=VALUES(mcb_rent),
    service_rent=VALUES(service_rent),
    gst=VALUES(gst);

-- Insert sample customer data
INSERT INTO emp VALUES 
('John Doe', '1001', '123 Main St, Downtown', 'California', 'Los Angeles', 'john.doe@email.com', '1234567890'),
('Jane Smith', '1002', '456 Oak Ave, Uptown', 'New York', 'New York City', 'jane.smith@email.com', '0987654321'),
('Bob Johnson', '1003', '789 Pine Rd, Suburbs', 'Texas', 'Houston', 'bob.johnson@email.com', '5551234567')
ON DUPLICATE KEY UPDATE 
    name=VALUES(name),
    address=VALUES(address),
    state=VALUES(state),
    city=VALUES(city),
    email=VALUES(email),
    phone=VALUES(phone);

-- Insert sample customer login credentials
INSERT INTO customer_login VALUES 
('1001', 'customer123'),
('1002', 'password456'),
('1003', 'secure789')
ON DUPLICATE KEY UPDATE password=VALUES(password);

-- Insert sample bill data
INSERT INTO bill (meter_number, month, units, amount) VALUES 
('1001', 'January', 150, 1284.00),
('1001', 'February', 175, 1409.00),
('1002', 'January', 200, 1584.00),
('1002', 'February', 180, 1444.00),
('1003', 'January', 120, 1074.00)
ON DUPLICATE KEY UPDATE 
    units=VALUES(units),
    amount=VALUES(amount);

-- Display success message
SELECT 'Database setup completed successfully!' as Status;
SELECT 'Default Admin Login: username=admin, password=admin123' as AdminCredentials;
SELECT 'Sample Customer Login: meter=1001, password=customer123' as CustomerCredentials;
