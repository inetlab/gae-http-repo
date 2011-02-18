package com.onpositive.repo.server;

import com.google.appengine.repackaged.com.google.common.util.Base64;

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
	
	public String getBase64() {
		String userAndPassword = "" + getUsername() + ":" + getPassword();
		String base64 = Base64.encode(userAndPassword.getBytes());
		// workaround base64 encoder bug: http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4615330
		if(base64.endsWith("\n")) base64 = base64.substring(0, base64.length()-2);
		return base64;
	}
}
