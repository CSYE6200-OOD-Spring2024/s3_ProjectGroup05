package com.example.Course.Registration.payload.response;

import java.util.List;

public class JwtResponse extends AbstractResponse {
	private String token;
	private String type = "Bearer";
	private String id;
	private String name;
	private String email;
	private String roles;

	public JwtResponse(String accessToken, String id, String name, String email, String roles) {
		super();
		this.token = accessToken;
		this.id = id;
		this.name = name;
		this.email = email;
		this.roles = roles;
	}

	public JwtResponse(String message) {
		super();
		String[] parts = message.split(",");
		this.token = parts[0];
		this.id = parts[1];
		this.name = parts[2];
		this.email = parts[3];
		this.roles = parts[4];
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	 public String getEmail() {
	 	return email;
	 }

	 public void setEmail(String email) {
	 	this.email = email;
	 }

	public String getName() {
		return name;
	}

	public void setUsername(String username) {
		this.name = username;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}
}
