package com.revature.BankDemo.model;

public class BankAccounts {
	private int accountId; 
	private int accountTypeId; 
	private int accountStatusId; 
    private int userId; 
    private int amount; 

	public BankAccounts(int accountId, int accountTypeId, int accountStatusId, int userId, int amount) {
		super();
		this.accountId = accountId;
		this.accountTypeId = accountTypeId;
		this.accountStatusId = accountStatusId;
        this.userId = userId;
        this.amount = amount;
	}

	public BankAccounts() {
		super();
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public int getAccountTypeId() {
		return accountTypeId;
	}

	public void setAccountTypeId(int accountTypeId) {
		this.accountTypeId = accountTypeId;
	}

	public int getAccountStatusId() {
		return accountStatusId;
	}

	public void setAccountStatusId(int accountStatusId) {
		this.accountStatusId = accountStatusId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

	@Override
	public String toString() {
		return "BankAccounts [accountId=" + accountId + ", accountTypeId=" + accountTypeId + ", accountStatusId=" + accountStatusId + ", userId=" + userId + ", amount=" + amount + "]";
	}

}