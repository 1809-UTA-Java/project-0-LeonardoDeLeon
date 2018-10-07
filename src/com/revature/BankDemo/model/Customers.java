package com.revature.BankDemo.model;

import java.util.Hashtable;
import java.io.Serializable;

public class Customers implements Serializable {

    private static final long serialVersionUID = 1L;

    public Hashtable<String, String> contents = new Hashtable<>();

    public Hashtable<String, String> getContents() {
        return this.contents;
    }
    public Customers (String username, String password) {
        this.contents.put(username,password);
    }
 
    public static void main (String [] args) {

    }
    
}

