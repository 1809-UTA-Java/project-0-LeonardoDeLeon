package com.revature.BankDemo.model;

public class BankUsers {
	private int uid;
	private String username;
	private String password;
	private int userType;

	public BankUsers(int uid, String username, String password, int userType) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
		this.userType = userType;
	}

	public BankUsers() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return uid;
	}

	public void setId(int uid) {
		this.uid = uid;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getUserType() {
		return userType;
	}

	public void setLegs(int userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "BankUsers [uid=" + uid + ", username=" + username + ", password=" + password + ", userType=" + userType + "]";
	}

}