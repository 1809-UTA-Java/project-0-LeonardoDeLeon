package com.revature.BankDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Scanner;

import com.revature.BankDemo.model.BankAccounts;
import com.revature.BankDemo.model.BankUsers;
import com.revature.BankDemo.repository.BankAppDao;

public class BankinApp {
	public static void main(String[] args) {

        int userId;
        BankAppDao bad = new BankAppDao();
        List<BankUsers> buList = bad.getBankUsersSp();
        
        // List<BankAccounts> baList = bad.getBankAccounts();

        // for (BankAccounts ba: baList) {
        //     System.out.println(ba.toString());
        // }
    
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------------------------------------");
        System.out.println("Welcome to the Inter-Galactic Bank of the Universe");
        System.out.println("It's the only bank your will ever need");
        System.out.println("--------------------------------------------------");
        System.out.println();
        System.out.println("Press 1 for Customer");
        System.out.println("Press 2 for Employee");
        System.out.println("Press 3 for Bank Admin");
        
        String userInput = sc.nextLine();

        boolean checkingUser = true; 

        while (checkingUser) {
            if (userInput.equals("1")) {
                System.out.println("Welcome Customer...");

                // Customer screen view
                System.out.println("Select 1 to log in or 2 to register: ");
                userInput = sc.nextLine();
                
                boolean isloggingIn = true;
                while (isloggingIn) {

                    if (userInput.equals("1")){ // user attempts to log in
                        System.out.println("Enter username: ");
                        userInput = sc.nextLine();

                        boolean isUsername = false;
                        // check the list from the database for username
                        for (BankUsers bu: buList) {
                            if (userInput.equals(bu.getUserName())) {
                                isUsername = true;
                                System.out.println(userInput+" = "+bu.getUserName());
                                break; // proceed to password check
                            }         
                        }

                        boolean isPassword = false; // check password

                        if (isUsername) {
                            System.out.println("Please enter password: ");
                            userInput = sc.nextLine();
                            for (BankUsers bu: buList) {
                                if (userInput.equals(bu.getPassword())) {
                                    isPassword = true; 
                                    userId = bu.getId();
                                    System.out.println("userId = "+userId);                                   
                                    System.out.println(userInput+" = "+bu.getPassword());
                                    break; // password verified proceed to the next step
                                }         
                            }
                            
                            if (isPassword) {
                                isloggingIn = false; // make sure to end the inner while loop
                                checkingUser = false; // make sure to end the outer loop
                                System.out.println("Hooray, you're a customer....");
                                System.out.println();
                                // add more stuff here
                                System.out.println("What would you like to do: ");
                                System.out.println();
                                System.out.println("  1. Check your account");
                                System.out.println("  2. Open an account");
                                System.out.println("Press 1 or 2 to proceed:");
                                userInput = sc.nextLine();

                                boolean isCheckingAccount = true;

                                while (isCheckingAccount) {
                                    if (userInput.equals("1")) {

                                        System.out.println("Welcome back: ");
                                        isCheckingAccount = false;
                                    } else if (userInput.equals("2")) {
                                        System.out.println("Nice... opening an account...");
                                        isCheckingAccount = false;
                                    } else {
                                        System.out.println("Please press 1 or 2 only: ");
                                    }
                                }

                            } else {
                                // reset the flag to allow user to retry
                                isPassword = false;
                                System.out.println("Please re-enter a password: ");
                                userInput = sc.nextLine();                                
                            }
                        } else {
                            // reset the flag to allow user to retry
                            isUsername = false;
                            System.out.println("Username not found.  Please try again: ");
                            System.out.println("If you are new please enter 2 to register: ");
                            userInput = sc.nextLine();
                        }
                                                                   
                    } else if (userInput.equals("2")) { // user registers a new account
                        System.out.println("Wise choice... Please create a username: ");
                        String usernameInput = sc.nextLine();
                        boolean isUsernameExist =false;
                        for (BankUsers bu: buList) {
                            if (usernameInput.equals(bu.getUserName())) {
                                isUsernameExist = true;
                               
                                break; // proceed to password check
                            }         
                        }
                        while (isUsernameExist) {
                            System.out.println("Looks like that username is taken, please choose another one: ");
                            usernameInput = sc.nextLine();
                            boolean isStillMatching = false;
                            for (BankUsers bu: buList) {
                                if (usernameInput.equals(bu.getUserName())) {
                                    isStillMatching = true;
                                    
                                    break; // proceed 
                                }                                                                     
                            }
                            if (isStillMatching) {
                                System.out.println("Still taken, please choose another one: ");
                                usernameInput = sc.nextLine();
                            } else {
                                isUsernameExist = false;
                            }
                        }

                        System.out.println("Now create a password: ");
                        String passwordInput = sc.nextLine();

                        bad.registerNewUser(usernameInput,passwordInput);
                        System.out.println("You have registered successfully");
                        isloggingIn = false;
                        checkingUser = false;
                    } else { // user did not enter 1 or 2
                        System.out.println("Please enter 1 or 2: ");
                        userInput = sc.nextLine();
                    }
            
                }
                        

                
            } else if (userInput.equals("2")) {
                System.out.println("Get to work Employee...");
                checkingUser = false;
            } else if (userInput.equals("3")) {
                System.out.println("Your coffee and breakfast is on its way...");
                checkingUser = false;
            } else {
                System.out.println("Are you testing my patience... type in 1, 2 or 3 ONLY!");
                userInput = sc.nextLine();
            }
        }


        sc.close();

	}
}