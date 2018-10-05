package com.revature;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;

//public class BankApp implements DaoOfBiable {
public class BankApp {    

    private static Hashtable<String,String> customer;
    private static Hashtable<String,String> employee;
    private static Hashtable<String,String> bAdmin;

    private static boolean isUsername = false;
    private static boolean isPassword = false;

    public static void main(String[] args) {

        File [] bankFiles = findAllFilesIn("bunk"); // get all bank related files

        String nameOfSerCFile = "serCustomer"; // serialize customer file
        String nameOfSerEFile = "serEmployee"; // serialize employee file
        String nameOfSerBFile = "serBankAdmin"; // serialize bank admin file
        
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
                        String cusUsername = sc.nextLine();
                        System.out.println("Please enter password: ");
                        String cusPassword = sc.nextLine();                                                
                        
                        boolean serCFileExist = false;

                        // search customer file 'serC' exist in 'bunk' folder
                        for (int i = 0; i < bankFiles.length; i++) {
                            // if user choose a file 			
                            if (bankFiles[i].toString().contains("serC")) {
                                serCFileExist = true;
                                nameOfSerCFile = bankFiles[i].toString(); 
                            }
                        }
                        if (serCFileExist) {
                            customer = openAndReadSerFile(nameOfSerCFile); // get customer's record

                            displayCustomerContents(customer); // displaying for dev purpose only
            
                            // match username entered by the user
                            Enumeration<String> username = customer.keys();             
                            for (;username.hasMoreElements();) {
                                if (cusUsername.equals(username.nextElement())) {
                                    isUsername = true;
                                    break;
                                } 
                            }

                            // match password entered by the user
                            Enumeration<String> password = customer.elements();                          
                            for (;password.hasMoreElements();) {
                                if (cusPassword.equals(password.nextElement())) {
                                    isPassword = true;
                                    break;
                                }                                 
                            }
                            boolean validatingCustomersInfo = true;
                            while (validatingCustomersInfo) {
                                if (isUsername && isPassword) {
                                    System.out.println("Hooray, you're a customer....");
                                    validatingCustomersInfo = false;
                                    isloggingIn = false;
                                } else {
                                    if (!isUsername) {
                                        System.out.println("Please re-enter username: ");
                                        cusUsername = sc.nextLine();           
                                        for (;username.hasMoreElements();) {
                                            if (cusUsername.equals(username.nextElement())) {
                                                isUsername = true;
                                                break;
                                            } 
                                        }                                        
                                    } 
                                    if (!isPassword) {
                                        System.out.println("Please re-enter password: ");
                                        cusPassword = sc.nextLine();  
                                        System.out.println("password.hasMoreElement(): "+password.hasMoreElements());
                                        System.out.println("password.hasMoreElement(): "+password.hasMoreElements());
                                        for (;password.hasMoreElements();) {
                                            if (cusPassword.equals(password.nextElement())) {
                                                System.out.println("Password is confirmed...");
                                                isPassword = true;
                                                break;
                                            }                                 
                                        }                                                                         
                                    }
                                }
                            }
                           
                        } else {
                            System.out.println("Username not found.  Please try again: ");
                            System.out.println("If you are new please enter 2 to register: ");
                        }
                                                                   
                    } else if (userInput.equals("2")) { // user registers a new account
                        System.out.println("Wise choice... Please create a username: ");
                        String usernameRegistry = sc.nextLine();
                        System.out.println("Now create a password: ");
                        String passwordRegistry = sc.nextLine();
                        Customers cus = new Customers(usernameRegistry, passwordRegistry);
                        writeObject("bunk/"+nameOfSerCFile,cus);
                        System.out.println("You have registered successfully");
                        isloggingIn = false;
                    } else { // user did not enter 1 or 2
                        System.out.println("Please enter 1 or 2: ");
                        userInput = sc.nextLine();
                    }
            
                }
                        

                checkingUser = false;
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

    public static void writeObject(String filename, Object obj) {
        try(ObjectOutputStream oos = 
            new ObjectOutputStream(
                new FileOutputStream(filename))) {
                    oos.writeObject(obj);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
    }    

    static File [] findAllFilesIn(String path) {
		// create a file obj, f, and give it a path to the dir, 'junk'
		File f = new File(path);

		// find and collect all files in 'junk' dir that ends in 'txt' or not
		File[] matchingFiles = f.listFiles(new java.io.FilenameFilter(){
			public boolean accept(File dir, String name) {
				//return name.endsWith("txt");
				return true; // or just return all files it sees
			}
		});
		return matchingFiles;
	}

    static Hashtable<String,String> openAndReadSerFile(String fileName) {
        Hashtable<String, String> contents = null;

        try (ObjectInputStream ois =
            new ObjectInputStream(
                new FileInputStream(fileName)
            )) {
                Customers customer = (Customers) ois.readObject();
                contents = customer.getContents();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                ex.getMessage();
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
        return contents;
    }

    static void displayCustomerContents (Hashtable<String,String> ht) {
        Enumeration<String> username = ht.keys();
        Enumeration<String> password = ht.elements();

        for (;username.hasMoreElements();) {
            System.out.println(username.nextElement());
            System.out.println(password.nextElement());
        } 
    }

}