package com.training.vo;

import org.apache.struts.action.ActionForm;

public class MemberVO extends ActionForm{
	
	private String customerName;
	private String userName;
	private String password;
	
	public MemberVO() {}
	
	public MemberVO(String customerName, String userName, String password) {
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
	

}
