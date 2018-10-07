package com.revature.BankDemo.repository;


import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.BankDemo.model.BankUsers;
import com.revature.BankDemo.model.Customers;
import com.revature.BankDemo.util.DbConnUtil;

import oracle.jdbc.internal.OracleTypes;

public class BankAppDao {

	public List<BankUsers> getCustomers() {
        PreparedStatement ps = null;
        
		BankUsers bu = null;
		List<BankUsers> bankUsers = new ArrayList<>();
		
		try(Connection conn = DbConnUtil.getDbConnect()) {
			String sql = "SELECT * FROM USERS";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int uid = rs.getInt("u_id");
				String username = rs.getString("username");
				String userPassword = rs.getString("user_password");
				int utId = rs.getInt("ut_id");
				
				bu = new BankUsers(uid, username, userPassword, utId);
				bankUsers.add(bu);
			}
			
			rs.close();
			ps.close();
		} catch (SQLException ex) {
			ex.getMessage();
		} catch (IOException ex) {
			ex.getMessage();
		}
		
		return bankUsers;
	}



    public static void main(String[] args) {

        BankAppDao bad = new BankAppDao();
        List<BankUsers> buList = bad.getCustomers();

        for (BankUsers b: buList) {
            System.out.println(b.toString());
        }

        //Declare Connection and Statement objects
        /*
        Connection myConnection = null;
        Statement myStatement = null;

        try {
            //Register the driver
            DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
            System.out.println("right before myConn");
            //Configure Connection
            myConnection = DriverManager.getConnection(
                "jdbc:oracle:thin:@192.168.56.105:1521:xe", 
                "bank", 
                "password13");
            if (myConnection != null) {
                System.out.print("Conn Successful");
            } else {
                System.out.println("Conn Failed...");
            }
            //Create Statement
            myStatement = myConnection.createStatement();
            System.out.println("b4 execute....");
            //myStatement.execute("INSERT INTO USERS (u_id, username, user_password, ut_id) VALUES (8,'duckiduc', 'duc123', 3)");
            System.out.println("after execute....");
            //Create a ResultSet object for storing data from a SELECT
            ResultSet users = myStatement.executeQuery("SELECT username FROM USERS");

            while(users.next()) {
                //System.out.println(users.getInt("u_id"));
                System.out.println(users.getString("username"));
                //System.out.println(users.getString("user_password"));
                //System.out.println(users.getInt("ut_id"));
                System.out.println();
            }

            myStatement.close();
            myConnection.close();
        } catch (SQLException ex) {
            ex.getMessage();
        } */


    }
}