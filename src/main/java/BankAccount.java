package main.java;

import main.java.com.revature.model.Account;

public class BankAccount implements Account {
	
	private int current;
	
	public boolean deposit(double amt) {
		double balance = 0;
		balance += amt;

		
        return ((balance == current + amt) ? true : false); 
	}
	
	public void withdraw() {
		System.out.println("withdrawing");
	}

	public void transfer() {
		System.out.println("transferring");
	}
}
