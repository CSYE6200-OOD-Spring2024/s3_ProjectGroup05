package com.example.Course.Registration.payload.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest extends Request {
	@NotBlank
	private String email;

	@NotBlank
	private String password;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return email;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
