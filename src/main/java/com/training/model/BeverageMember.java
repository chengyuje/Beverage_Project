package com.training.model;

public class BeverageMember {

	private String customerName;
	private String userName;
	private String password;

	public BeverageMember() {}

	public BeverageMember(String customerName, String userName, String password) {
		this.customerName = customerName;
		this.userName = userName;
		this.password = password;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "BeverageMember [customerName=" + customerName + ", userName=" + userName + ", password=" + password
				+ "]";
	}
	
	
}
