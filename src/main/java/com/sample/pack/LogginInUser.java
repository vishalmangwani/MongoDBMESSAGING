package com.sample.pack;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="InactiveUsers")
public class LogginInUser {
	private String username;
	private String password;
	private boolean isLoggedIn;
	private String Sess;
	public LogginInUser(String username, String password, boolean isLoggedIn, String sess) {
		this.username = username;
		this.password = password;
		this.isLoggedIn = isLoggedIn;
		Sess = sess;
	}
	public LogginInUser() {
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isLoggedIn() {
		return isLoggedIn;
	}
	public void setLoggedIn(boolean isLoggedIn) {
		this.isLoggedIn = isLoggedIn;
	}
	public String getSess() {
		return Sess;
	}
	public void setSess(String sess) {
		Sess = sess;
	}
	
}
