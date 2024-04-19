package com.example.Course.Registration.payload.request;

import java.util.Set;

import jakarta.validation.constraints.*;
 
public class SignupRequest extends Request {
    // @NotBlank
    // @Size(min = 3, max = 20)
    // private String username;
 
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    
    private Set<String> roles;

    @NotBlank
    private String degree;

    @NotBlank
    private String branch;

    @NotBlank
    private String name;

    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
  
    // public String getUsername() {
    //     return username;
    // }
 
    // public void setUsername(String username) {
    //     this.username = username;
    // }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRoles() {
      return this.roles;
    }
    
    public void setRole(Set<String> roles) {
      this.roles = roles;
    }

    public String getDegree() {
        return degree;
    }

    public String getBranch() {
        return branch;
    }

    public String getName() {
        return name;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setName(String name) {
        this.name = name;
    }

}
