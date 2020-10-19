package com.devfam.miag.account.services;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;

public class AuthResponse implements Serializable {
	
	@JsonSetter
    private String userName;
	@JsonSetter
    private String token;

    public AuthResponse(String userName, String token) {
        this.userName = userName;
        this.token = token;
    }
    
    

    public AuthResponse() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getUserName() {
        return userName;
    }

    public AuthResponse setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getToken() {
        return token;
    }

    public AuthResponse setToken(String token) {
        this.token = token;
        return this;
    }
}
