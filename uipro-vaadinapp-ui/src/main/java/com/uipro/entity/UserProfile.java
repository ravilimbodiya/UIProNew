package com.uipro.entity;

public class UserProfile {
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNo;
	private String dob;

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public UserProfile() {
		firstName = " ";
		lastName = " ";
		dob = " ";
		email = " ";
		phoneNo = " ";
	}

	public UserProfile(String fname, String lname, String email,
			String phoneNo, String dob) {
		this.dob = dob;
		this.email = email;
		this.phoneNo = phoneNo;
		this.firstName = fname;
		this.lastName = lname;
	}
	
	
	@Override
	public String toString() {
		System.out.println(firstName+" "+ lastName+ " "+ email+ " "+ phoneNo+ " " + dob);
		return "";
	}

}
