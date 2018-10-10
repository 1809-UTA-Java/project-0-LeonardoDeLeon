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
import java.text.NumberFormat;

import com.revature.BankDemo.model.BankAccounts;
import com.revature.BankDemo.model.BankUsers;
import com.revature.BankDemo.repository.BankAppDao;

public class BankinApp {
	public static void main(String[] args) {

        int userId = 0;
        String username = "";
        String password = "";
        int balance = 0;
        int accountId = 0;
        int existingBalance = 0;
        int userTypeId = 0;

        BankAppDao bad = new BankAppDao();
        List<BankUsers> buList = bad.getBankUsersSp();
        List<BankAccounts> baList = bad.getBankAccounts();
   
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        
        Scanner sc = new Scanner(System.in);
        System.out.println("--------------------------------------------------");
        System.out.println("Welcome to Galaxy Bank");
        System.out.println("The only bank you will ever need");
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
                                username = bu.getUserName();
                                userId = bu.getId();                                

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
                                    //userId = bu.getId();
                                    //username = bu.getUserName();

                                    break; // password verified proceed to the next step
                                }         
                            }
                            
                            if (isPassword) {
                                isloggingIn = false; // end the inner while loop
                                checkingUser = false; // end the outer loop
                              
                                boolean isNewUser = true;
                                for (BankAccounts ba: baList) {
                                    if (userId == ba.getUserId()) {
                                        System.out.println("It's not new");
                                        isNewUser = false;
                                        break;
                                    }
                                }

                                if (!isNewUser) {
                                    System.out.println("What would you like to do "+username+":");
                                    System.out.println();
                                    // retrieve customer's account
                                    System.out.println("  1. View your account");
                                    System.out.println("  2. Create a joint account");
                                    System.out.println();
                                    System.out.println("Press 1 or 2 to proceed:");
                                    userInput = sc.nextLine();
    
                                    boolean isViewingAccount = true;
    
                                    while (isViewingAccount) {
                                        if (userInput.equals("1")) {
    
                                            System.out.println();
                                            System.out.println("Here is the current detail of your account:");
                                            System.out.println();
    
                                            for (BankAccounts ba: baList) {
                                                if (userId == ba.getUserId()) {
                                                    
                                                    balance = ba.getAmount();
                                                    accountId = ba.getAccountId();
                                                }
                                            }
                                            System.out.println("  Account Id: "+accountId);
                                            System.out.println("  Your current balance is "+ fmt.format(balance));
    
                                            System.out.println();
                                            System.out.println("What would you like to do?");
                                            System.out.println();
                                            System.out.println("  1. Deposit money");
                                            System.out.println("  2. Withdraw money");
                                            System.out.println("  3. Transfer money to another account");
                                            System.out.println("  4. or Exit out of Galaxy Bank");
                                            System.out.println();
                                            System.out.println("Press 1, 2, 3 or 4 to proceed: ");
                                            userInput = sc.next();
    
                                            boolean isUserTransaction = true;
                                            while (isUserTransaction) {
                                                if (userInput.equals("1")) {
                                                    System.out.println();
                                                    System.out.println("Please enter the amount to deposit: ");
                                                    userInput = sc.next();
                                                    try {
                                                        bad.updateAccountBalance(accountId,balance+Integer.parseInt(userInput));
                                                        bad.addTransactionRecord(accountId,Integer.parseInt(userInput));
                                                        System.out.println(fmt.format(Integer.parseInt(userInput))+" has been added to your account.");
                                                        isUserTransaction = false;
                                                    } catch (NumberFormatException e) {
                                                        System.out.println("Please enter numbers ONLY: ");
                                                        userInput = sc.nextLine();
                                                    } 
                                                    
                                                } else if (userInput.equals("2")) {
                                                    System.out.println();
                                                    System.out.println("Please enter the amount to withdraw: ");
                                                    userInput = sc.nextLine();
                                                    
                                                    boolean isWithdrawing = true;
                                                    int withdrawalAmount;
                                                    while (isWithdrawing) {
                                                        try {
                                                            withdrawalAmount = Integer.parseInt(userInput);
                                                            if (withdrawalAmount > 0) {
                                                               
                                                                if (balance-withdrawalAmount > 0) {
                                                                    bad.updateAccountBalance(accountId,balance-withdrawalAmount);
                                                                    bad.addTransactionRecord(accountId,withdrawalAmount);
                                                                    System.out.println(fmt.format(withdrawalAmount)+" has been withdrawn from your account.");
                                                                    isUserTransaction = false;
                                                                    isWithdrawing = false;
                                                                } else {
                                                                    System.out.println("Please enter amount less than the balance");
                                                                    userInput = sc.nextLine();
                                                                }
                                                            } else {
                                                                System.out.println("Please enter amount greater than 0:");
                                                                userInput = sc.nextLine();
                                                            }
                                                        } catch (NumberFormatException e) {
                                                            System.out.println("Please enter numbers ONLY: ");
                                                            userInput = sc.nextLine();
                                                        }                                               
                                                    }
                                                } else if (userInput.equals("3")) {
                                                    String userInputToTransfer;
                                                    String userInputToTransferAccountId;
                                                    System.out.println();
                                                    System.out.println("Please enter the amount to transfer: ");
                                                    userInputToTransfer = sc.nextLine();
                                                    
                                                    boolean isTransferring = true;
                                                    int transferringAmount;
                                                    int transferringAccountId;
                                                    while (isTransferring) {
                                                        try {
                                                            transferringAmount = Integer.parseInt(userInputToTransfer.trim());
                                                            
                                                            if (transferringAmount > 0) {
    
                                                                System.out.println("Now enter the account id to transfer to: ");
                                                                userInputToTransferAccountId = sc.nextLine();
                                                                transferringAccountId = Integer.parseInt(userInputToTransferAccountId.trim());
                                                                for (BankAccounts ba: baList) {
                                                                    if (transferringAccountId == ba.getAccountId()) {                                                                    
                                                                        existingBalance = ba.getAmount();                                                 
                                                                    }
                                                                }
                                                                if (balance-transferringAmount > 0) {
                                                                    bad.updateAccountBalance(accountId,balance-transferringAmount);
                                                                    bad.addTransactionRecord(accountId,transferringAmount);
                                                                    bad.updateAccountBalance(transferringAccountId,existingBalance+transferringAmount);
                                                                    bad.addTransactionRecord(transferringAccountId,transferringAmount);                                                                
                                                                    System.out.println(fmt.format(transferringAmount)+" has been transferred from your account "+accountId+" to account "+transferringAccountId);
                                                                    isUserTransaction = false;
                                                                    isTransferring = false;
                                                                } else {
                                                                    System.out.println("Please enter amount less than the balance");
                                                                    userInputToTransfer = sc.nextLine();
                                                                }
                                                            } else {
                                                                System.out.println("Please enter amount greater than 0:");
                                                                userInputToTransfer = sc.nextLine();
                                                            }
                                                        } catch (NumberFormatException e) {
                                                            System.out.println("Please enter numbers ONLY: ");
                                                            userInputToTransfer = sc.nextLine();
                                                        }
                                                        
                                                    }
                                                    isUserTransaction = false;
                                                } else if (userInput.equals("4")) {
                                                    System.out.println("Thank you for visiting Galaxy Bank. Bye now!");
                                                    isUserTransaction = false;
                                                } else {
                                                    System.out.println("Please enter 1, 2 ,3 or 4");
                                                    userInput = sc.nextLine();
                                                }
                                            }
                                            
                                            isViewingAccount = false;
                                        } else if (userInput.equals("2")) {
                                            System.out.println();
                                            System.out.println("Joint Account");
                                            isViewingAccount = false;
                                        } else {
                                            System.out.println("Please press 1 or 2 only: ");
                                            userInput = sc.nextLine();
                                        }
                                    }
    
                                } else {
                                    System.out.println("New User do this...");
                                    // add more stuff here
                                    System.out.println("Welcome back "+username+":");
                                    System.out.println("Would you like to open a new account:");
                                    System.out.println();
                                    System.out.println("To open a new account, a minimum deposit of $1 is required.");
                                    System.out.println("To make it easier, please select from the following: ");
                                    System.out.println();
                                    System.out.println("  1. Press 1 for $1");
                                    System.out.println("  2. Press 2 for $100");
                                    System.out.println("  3. Press 3 for $1000");
                                    System.out.println();
                                    System.out.println("Please select the amount to deposit: ");
                                    userInput = sc.nextLine();

                                    boolean inputtingDeposit = true;
                                    while (inputtingDeposit) {
                                        boolean isCreatingUserAccount = false;
                                        if (userInput.equals("1")) {
                                            bad.submitApplication(userId, 1);
                                            isCreatingUserAccount = true;
                                            inputtingDeposit = false;
                                        } else if(userInput.equals("2")) {
                                            bad.submitApplication(userId, 100);
                                            isCreatingUserAccount = true;
                                            inputtingDeposit = false;
                                        } else if(userInput.equals("3")) {
                                            bad.submitApplication(userId, 1000);
                                            isCreatingUserAccount = true;
                                            inputtingDeposit = false;
                                        } else {
                                            System.out.println("You did not enter 1, 2 or 3. Please try again");                                                
                                            userInput = sc.nextLine();
                                        }
                                        if (isCreatingUserAccount) {
                                            List<BankAccounts> newBaList = bad.getBankAccounts();  
                                            for (BankAccounts ba: newBaList) {
                                                if (userId == ba.getUserId()) {
                                                    balance = ba.getAmount();
                                                    accountId = ba.getAccountId();
                                                    break;
                                                }
                                            }
                                            
                                            bad.addTransactionRecord(accountId, balance);
                                            bad.createUserAccount(userId, accountId);
                                        }
                                    }
                                    System.out.println("Thank you for submitting an application.");
                                    System.out.println("Your application is being processed.");
                                    System.out.println("It may take up to 3 to 5 business days.");
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
                        System.out.println("Please create a username: ");
                        String usernameInput = sc.nextLine();
                        boolean isUsernameExist =false;
                        for (BankUsers bu: buList) {
                            if (usernameInput.equals(bu.getUserName())) {
                                isUsernameExist = true;
                                break; // proceed to password check
                            }         
                        }
                        
                        int cnt = 0; // start counter to stop creating username tries in 3 attempts
                        boolean isStillMatching = false;                           
                        while (isUsernameExist) {
                            if (cnt == 0) {
                                System.out.println("Looks like that username is taken, please choose another one: ");
                                usernameInput = sc.nextLine();
                            }
                            cnt++; 
                            
                            // check username against existing one
                            for (BankUsers bu: buList) {
                                if (usernameInput.equals(bu.getUserName())) {
                                    isStillMatching = true;
                                    break; // proceed to prompting user for a unique username 
                                }                                                                     
                            }
                                                        
                            if (isStillMatching) { // re-prompt user for username
                                if (cnt == 1){
                                    System.out.println("Still taken, please choose another one: ");
                                    usernameInput = sc.nextLine();
                                }
  
                                if (cnt == 2){
                                    System.out.println("That's also taken, try again: ");
                                    usernameInput = sc.nextLine();
                                }

                                if (cnt == 3) { 
                                    System.out.println("Looks like you are not having any luck");
                                    System.out.println("Try again next time!");
                                    isUsernameExist = false; // exit out after 3  more tries
                                } 
                                isStillMatching = false; // reset for the next check                                                                
                                
                            } else { // looks like username finally made the cut
                                isUsernameExist = false;
                            }
                        }

                        // have user create a password to complete registration
                        if (!isStillMatching) { 
                            System.out.println("Now create a password: ");
                            String passwordInput = sc.nextLine();
                            
                            // call stored procedure that triggers a sequence 
                            bad.registerNewUser(usernameInput,passwordInput);
                            System.out.println("You have registered successfully");
                        }

                        isloggingIn = false; // exit out of the logging stage
                        checkingUser = false; // exit out of Customer screen
                    } else { // user did not enter 1 or 2
                        System.out.println("Please enter 1 or 2: ");
                        userInput = sc.nextLine();
                    }
            
                }
                        

                
            } else if (userInput.equals("2")) {
                System.out.println("Galaxy Bank Employee System.  Please enter username to begin: ");
                String employeeUsername = sc.nextLine();
                System.out.println("Next, enter your password: ");
                String employeePassword = sc.nextLine();

                buList = bad.getBankUsersSp();
                baList = bad.getBankAccounts();

                // verify the employee
                for (BankUsers bu: buList) {
                    if (employeeUsername.equals(bu.getUserName())) {
                        userTypeId = bu.getUserType();
                        username = bu.getUserName();
                        break; 
                    }         
                }

                if (userTypeId == 2) { // employee user_type_id is 2

                    System.out.println("Welcome back " + username);
                    System.out.println();
                    System.out.println("Please select your task: ");
                    System.out.println();
                    System.out.println("  1. Deposit/Withdrawal/Transfer");
                    System.out.println("  2. Approve/Deny Application");
                    System.out.println("  3. Or Exit out of the System");
                    System.out.println();
                    System.out.println("Please select 1, 2, or 3 to begin: ");
                    userInput = sc.nextLine();

                    boolean isEmployeeTasking = true;

                    while (isEmployeeTasking) {

                        if (userInput.equals("1")) {

                            System.out.println("Prompt user for username: ");
                            String usernameInput = sc.nextLine();

                            System.out.println("Prompt user for their password: ");
                            String passwordInput = sc.nextLine();

                            for (BankUsers bu: buList) {
                                if (usernameInput.equals(bu.getUserName())) {
                                    userId = bu.getId();
                                    username = bu.getUserName();
                                    password = bu.getPassword();
                                    break; // proceed
                                }                                                                     
                            }

                            for (BankAccounts ba: baList) {
                                if (userId == ba.getUserId()) {
                                    balance = ba.getAmount();
                                    accountId = ba.getAccountId();
                                    break;
                                }
                            }
                            System.out.println();
                            System.out.println("   Username: "+ username +" --- User Id: "+ userId +" --- Password: "+password);
                            System.out.println();
                            System.out.println("   Account Id: "+ accountId +" --- Account Balance: "+ fmt.format(balance));
                            System.out.println();

                            System.out.println("Ask user/customer what they want to do next: ");
                            System.out.println();
                            System.out.println("   1. Deposit");
                            System.out.println("   2. Withdrawal");
                            System.out.println("   3. Transfer");
                            System.out.println("   4. Or if they're done, exit");
                            System.out.println();
                            System.out.println("What would it be: ");
                            userInput = sc.nextLine();

                            boolean transacting = true; 
                            while (transacting) {
                                if (userInput.equals("1")) {
                                    // deposit
                                    System.out.println();
                                    System.out.println("Please enter the amount to deposit: ");
                                    userInput = sc.next();
                                    try {
                                        bad.updateAccountBalance(accountId,balance+Integer.parseInt(userInput));
                                        bad.addTransactionRecord(accountId,Integer.parseInt(userInput));
                                        System.out.println(fmt.format(Integer.parseInt(userInput))+" has been added to your account.");                                
                                        isEmployeeTasking = false;
                                        transacting = false;
                                    } catch (NumberFormatException e) {
                                        System.out.println("Please enter numbers ONLY: ");
                                        userInput = sc.nextLine();
                                    } 
                                } else if (userInput.equals("2")) {
                                    //withdrawal
                                    System.out.println();
                                    System.out.println("Please enter the amount to withdraw: ");
                                    userInput = sc.nextLine();
                                    
                                    boolean isWithdrawing = true;
                                    int withdrawalAmount;
                                    while (isWithdrawing) {
                                        try {
                                            withdrawalAmount = Integer.parseInt(userInput);
                                            if (withdrawalAmount > 0) {
                                                
                                                if (balance-withdrawalAmount > 0) {
                                                    bad.updateAccountBalance(accountId,balance-withdrawalAmount);
                                                    bad.addTransactionRecord(accountId,withdrawalAmount);
                                                    System.out.println(fmt.format(withdrawalAmount)+" has been withdrawn from your account.");
                                                    transacting = false;
                                                    isWithdrawing = false;
                                                    isEmployeeTasking = false;
                                                } else {
                                                    System.out.println("Please enter amount less than the balance");
                                                    userInput = sc.nextLine();
                                                }
                                            } else {
                                                System.out.println("Please enter amount greater than 0:");
                                                userInput = sc.nextLine();
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println("Please enter numbers ONLY: ");
                                            userInput = sc.nextLine();
                                        }                                    
                                    }
                                    
                                } else if (userInput.equals("3")) {
                                    // transfer
                                    System.out.println("Transferring");
                                    String userInputToTransfer;
                                    String userInputToTransferAccountId;
                                    System.out.println();
                                    System.out.println("Please enter the amount to transfer: ");
                                    userInputToTransfer = sc.nextLine();
                                    
                                    boolean isTransferring = true;
                                    int transferringAmount;
                                    int transferringAccountId;
                                    while (isTransferring) {
                                        try {
                                            transferringAmount = Integer.parseInt(userInputToTransfer.trim());
                                            
                                            if (transferringAmount > 0) {

                                                System.out.println("Now enter the account id to transfer to: ");
                                                userInputToTransferAccountId = sc.nextLine();
                                                transferringAccountId = Integer.parseInt(userInputToTransferAccountId.trim());
                                                for (BankAccounts ba: baList) {
                                                    if (transferringAccountId == ba.getAccountId()) {                                                                    
                                                        existingBalance = ba.getAmount();                                                 
                                                    }
                                                }
                                                if (balance-transferringAmount > 0) {
                                                    bad.updateAccountBalance(accountId,balance-transferringAmount);
                                                    bad.addTransactionRecord(accountId,transferringAmount);
                                                    bad.updateAccountBalance(transferringAccountId,existingBalance+transferringAmount);
                                                    bad.addTransactionRecord(transferringAccountId,transferringAmount);                                                                
                                                    System.out.println(fmt.format(transferringAmount)+" has been transferred from your account "+accountId+" to account "+transferringAccountId);
                                                    
                                                    isTransferring = false;
                                                } else {
                                                    System.out.println("Please enter amount less than the balance");
                                                    userInputToTransfer = sc.nextLine();
                                                }
                                            } else {
                                                System.out.println("Please enter amount greater than 0:");
                                                userInputToTransfer = sc.nextLine();
                                            }
                                        } catch (NumberFormatException e) {
                                            System.out.println("Please enter numbers ONLY: ");
                                            userInputToTransfer = sc.nextLine();
                                        }
                                        
                                    }
                                    

                                    transacting = false;
                                    isEmployeeTasking = false;
                                } else if (userInput.equals("4")) {
                                    System.out.println("Exiting out of the System");
                                    isEmployeeTasking = false;
                                    transacting = false;
                                } else {
                                    System.out.println("Please enter 1, 2, 3 or 4 ONLY!");
                                    userInput = sc.nextLine();
                                }
                            }
                        } else if(userInput.equals("2")) {
                            // Approve or denying
                            isEmployeeTasking = false;
                        } else if(userInput.equals("3")) {
                            System.out.println("Bye");
                            isEmployeeTasking = false;
                        } else {
                            System.out.println("Please enter 1, 2 or 3 ONLY!");
                            userInput = sc.nextLine();
                        }
                    }
                
                } else {
                    System.out.println("Sorry but the system could not verify you as an employee");
                    System.out.println("Please go see the manager");
                }

                checkingUser = false;
            } else if (userInput.equals("3")) {
                System.out.println("Your coffee and breakfast is on its way...");
                checkingUser = false;
            } else {
                System.out.println("Please select 1, 2 or 3");
                userInput = sc.nextLine();
            }
        }

        sc.close();
	}
}