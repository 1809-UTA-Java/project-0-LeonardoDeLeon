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

import com.revature.BankDemo.model.BankUsers;
import com.revature.BankDemo.repository.BankAppDao;

public class BankinApp {
	public static void main(String[] args) {

        BankAppDao bad = new BankAppDao();
        List<BankUsers> buList = bad.getCustomers();

        for (BankUsers b: buList) {
            System.out.println(b.toString());
        }
		
	}
}