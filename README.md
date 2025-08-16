# Electricity Billing Management System
This is a GUI made using Java Swing.
It lets User perform multiple operations like:-


1- User can Create his Personal login for security purposes.

2- User can Add customers and Calculate their Electricity Bill.

3- User can Pay Electricity Bills.

4- User can Generate Bill.

## About Project:
This Java application was created using Intelli J .
Additional library was added for the support of JDBC (Required to setup the connection between the Database and Java Application).
It contains 9 different classes which works together to create a better user experience .

->Splash Screen class

->Login Screen class

->Main System class

->Add Customer class

->Pay Bill class

->Generate Bill class

->Show Details class

->Last Bill class

->Connection Setup class(JDBC - MySQL)

## Database (MySQL)
Database for this Electricity Billing System contains 4 Tables


->Login Table (UserName,Password)

->Bill Table(MeterNumber,Units,Month,Amount)

->Emp Table(Name, MeterNumber, Address, State, City, Email, Phone)

->Tax Table(MeterLocation,MeterType,PhaseCode,BillType,Days,MeterRent,MCB_Rent,ServiceRent,GST)


Java communicates with MySQL tables using JDBC which stands for Java Database Connectivity.

# How to Run

## Clone the Repository
git clone https://github.com/<your-username>/Electricity-Bill-Management-system.git
cd Electricity-Bill-Management-system

## Setup Database (MySQL)

# Install MySQL server.

Open MySQL shell or Workbench.

# Create a new database:

CREATE DATABASE electricity;
USE electricity;


# Create required tables:

CREATE TABLE login (username VARCHAR(50), password VARCHAR(50));
CREATE TABLE emp (name VARCHAR(50), meternumber VARCHAR(20), address VARCHAR(100), state VARCHAR(50), city VARCHAR(50), email VARCHAR(50), phone VARCHAR(20));
CREATE TABLE bill (meternumber VARCHAR(20), units INT, month VARCHAR(20), amount DOUBLE);
CREATE TABLE tax (meterlocation VARCHAR(50), metertype VARCHAR(50), phasecode VARCHAR(20), billtype VARCHAR(20), days INT, meterrent DOUBLE, mcb_rent DOUBLE, servicerent DOUBLE, gst DOUBLE);

## Add MySQL Connector

Download MySQL Connector/J.

Place the .jar file in your project lib/ folder.

Add it to your project classpath in IntelliJ (File > Project Structure > Modules > Dependencies).

## Configure Database Connection

In your Connection Setup class, update credentials:

String url = "jdbc:mysql://localhost:3306/electricity";
String user = "root";
String password = "your_password";

## Run the Application

Open project in IntelliJ IDEA.

Run the Main class (or the class containing public static void main).

Login and start using the system 

## Author

Developed & Maintained by **Revanth Reddy**

