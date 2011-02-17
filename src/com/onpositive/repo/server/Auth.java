package com.onpositive.repo.server;

public class Auth {

	String username;
	String password;
	
	public Auth(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	
}
