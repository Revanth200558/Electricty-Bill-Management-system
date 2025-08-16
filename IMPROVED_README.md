# Electricity Billing System - Improved Version

## Overview
This is an enhanced Java Swing-based Electricity Billing System with **separate login systems for administrators and customers**. The system provides role-based access control, improved security, and a better user experience.

## New Features Added

### 🔐 **Role-Based Authentication**
- **Admin Login**: Full system access for administrators
- **Customer Login**: Limited access for customers to view bills and profile
- **Customer Registration**: Self-service registration for new customers
- **Role Selection**: Initial screen to choose user type

### 🛡️ **Security Improvements**
- Prepared statements to prevent SQL injection
- Separate authentication tables for admins and customers
- Password validation and confirmation
- Input validation and error handling

### 👥 **User Experience Enhancements**
- Modern GUI with improved layouts
- Separate interfaces for different user roles
- Better navigation and user feedback
- Professional styling with icons and colors

## System Architecture

### **New Classes Added**
1. `RoleSelection.java` - User type selection screen
2. `AdminLogin.java` - Administrator authentication
3. `CustomerLogin.java` - Customer authentication
4. `CustomerRegistration.java` - Customer self-registration
5. `AdminPanel.java` - Full administrative interface
6. `CustomerPanel.java` - Limited customer interface
7. `CustomerBillView.java` - Customer bill viewing
8. `CustomerProfile.java` - Customer profile management

### **Database Schema Updates**
- `admin_login` - Administrator credentials
- `customer_login` - Customer credentials
- Enhanced security with proper table relationships

## Installation & Setup

### Prerequisites
- Java JDK 8 or higher
- MySQL Server 5.7 or higher
- MySQL JDBC Driver (mysql-connector-java)

### Database Setup
1. **Install MySQL** and start the service
2. **Run the database setup script**:
   ```sql
   mysql -u root -p < database_setup.sql
   ```
3. **Verify database creation**:
   ```sql
   USE ebs;
   SHOW TABLES;
   ```

### Project Setup
1. **Clone/Download** the project
2. **Open in IntelliJ IDEA** or your preferred IDE
3. **Add MySQL JDBC Driver** to classpath:
   - Download `mysql-connector-java-8.0.x.jar`
   - Add to project libraries
4. **Update database connection** in `conn.java` if needed:
   ```java
   c=DriverManager.getConnection("jdbc:mysql://localhost/ebs","root","your_password");
   ```

## Running the Application

### Method 1: From IDE
1. Open `splash.java`
2. Run the main method
3. Application will show splash screen → Role selection → Login

### Method 2: From Command Line
```bash
cd src
javac -cp ".:mysql-connector-java-8.0.x.jar" *.java
java -cp ".:mysql-connector-java-8.0.x.jar" splash
```

## Default Login Credentials

### Administrator Access
- **Username**: `admin`
- **Password**: `admin123`

### Sample Customer Access
- **Meter Number**: `1001`
- **Password**: `customer123`

## User Roles & Permissions

### 🔧 **Administrator Features**
- ✅ Add new customers
- ✅ View all customer details
- ✅ Calculate bills for any customer
- ✅ Generate bills
- ✅ View payment history
- ✅ Access system utilities
- ✅ Full system management

### 👤 **Customer Features**
- ✅ View personal bills
- ✅ View bill history
- ✅ Pay bills
- ✅ View/update profile
- ✅ Limited utility access
- ❌ Cannot access other customers' data
- ❌ Cannot perform administrative tasks

## Application Flow

```
Splash Screen (7 seconds)
    ↓
Role Selection
    ├── Admin Login → Admin Panel
    └── Customer Login → Customer Panel
            ↑
    Customer Registration
```

## Key Improvements Made

### **Security Enhancements**
- ✅ SQL injection prevention with prepared statements
- ✅ Role-based access control
- ✅ Password validation and confirmation
- ✅ Secure session management

### **User Experience**
- ✅ Intuitive role selection
- ✅ Separate interfaces for different user types
- ✅ Better error handling and user feedback
- ✅ Professional UI design with proper styling

### **Code Quality**
- ✅ Modular design with separate classes
- ✅ Consistent naming conventions
- ✅ Better exception handling
- ✅ Improved code organization

## Troubleshooting

### Common Issues

**Database Connection Error**
```
Solution: Check MySQL service is running and credentials in conn.java are correct
```

**ClassNotFoundException for MySQL Driver**
```
Solution: Add mysql-connector-java.jar to classpath
```

**Login Failed**
```
Solution: Verify credentials using default login details provided above
```

**Images Not Loading**
```
Solution: Ensure images/ folder is in src/ directory with all required image files
```

## Future Enhancements

### Planned Features
- 🔄 Password reset functionality
- 📧 Email notifications for bills
- 📊 Advanced reporting and analytics
- 💳 Online payment integration
- 📱 Mobile-responsive interface
- 🔒 Two-factor authentication

### Technical Improvements
- 🏗️ Migration to Spring Boot framework
- 🗄️ Connection pooling for better performance
- 🧪 Unit testing implementation
- 📝 Comprehensive logging system
- 🔧 Configuration management

## Support

For technical support or questions:
1. Check the troubleshooting section above
2. Verify database setup using the provided SQL script
3. Ensure all dependencies are properly configured
4. Test with default credentials first

## License
This project is for educational purposes. Feel free to modify and enhance as needed.

---
**Version**: 2.0 (Improved with Role-Based Authentication)  
**Last Updated**: August 2024
