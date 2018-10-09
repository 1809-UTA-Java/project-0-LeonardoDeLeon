package com.revature.BankDemo.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;

public class DbConnUtil {
    public static Connection getDbConnect() throws SQLException, IOException {
        String url = "jdbc:oracle:thin:@192.168.56.105:1521:xe";
		String username = "bank";
		String password = "password13";
		
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		return DriverManager.getConnection(url, username, password);
/*
		//in the connection.properties folder you add:

		//url=jdbc:oracle:thin:@192.168.56.105:1521:xe
		//username=bank
		//password=password13

		//then here in java you do this

		Properties prop = new Properties();
		InputStream in = new FileInputStream ("connection.properties");
		prop.load(in);


		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		return DriverManager.getConnection(
			prop.getProperty("url"),
		 	prop.getProperty("username"),
			prop.getProperty("password");
*/
    }
}