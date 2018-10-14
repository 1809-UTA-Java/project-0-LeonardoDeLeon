package main.java;

import main.java.com.revature.model.Account;

public class BankAccount implements Account {
	
	private int current;
	
	public boolean deposit(double amt) {
		double balance = current;
		balance += amt;

		
        return ((balance == current + amt) ? true : false); 
	}
	
	public boolean withdraw(double amt) {
		double balance = current;
		if ((balance -= amt) >= 1) {
			return (balance == current - amt);
		} else {
			return false;
		}		
	}

	public boolean transfer(double amt) {
		
		return true;
	}
}
