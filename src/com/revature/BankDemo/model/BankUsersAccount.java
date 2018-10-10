package com.revature.BankDemo.model;

import java.sql.Timestamp;

public class BankUsersAccount {
	private int userId;
	private int accountId;
	private Timestamp userAcctCreationTs;

	public BankUsersAccount(int userId, int accountId, Timestamp userAcctCreationTs) {
		super();
		this.userId = userId;
		this.accountId = accountId;
		this.userAcctCreationTs = userAcctCreationTs;		
	}

	public BankUsersAccount() {
		super();
	}

	public int getId() {
		return userId;
	}

	public void setId(int userId) {
		this.userId = userId;
	}

	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public Timestamp getUserAcctCreationTs() {
		return userAcctCreationTs;
	}

	public void setUserAcctCreationTs(Timestamp userAcctCreationTs) {
		this.userAcctCreationTs = userAcctCreationTs;
	}

	@Override
	public String toString() {
		return "BankUsersAccount [userId=" + userId + ", accountId=" + accountId + ", userAcctCreationTs=" + userAcctCreationTs + "]";
	}

}