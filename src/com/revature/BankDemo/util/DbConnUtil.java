package com.revature.BankDemo.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnUtil {
    public static Connection getDbConnect() throws SQLException, IOException {
        String url = "jdbc:oracle:thin:@192.168.56.105:1521:xe";
		String username = "bank";
		String password = "password13";
		
		DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		return DriverManager.getConnection(url, username, password);
    }
}