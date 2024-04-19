package com.example.Course.Registration.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "UsersJava")
public class User {

    @Id
    private String id;

    // @NotBlank
    // private String username;
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String branch;
    private String degree;
    @DBRef
    private Set<Role> roles = new HashSet<>();
    @DBRef
    private Set<Courses> courses = new HashSet<>();

    public User(String name, String email, String password, String branch, String degree) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.branch = branch;
        this.degree = degree;
        this.courses = new HashSet<>();
        // this.username = email;
        // this.roles = role;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCourses(Set<Courses> courses) {
        this.courses = courses;
    }

    public void setRole(Set<Role> role) {
        this.roles = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getBranch() {
        return branch;
    }

    public String getDegree() {
        return degree;
    }

    public String getEmail() {
        return email;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Set<Courses> getCourses() {
        return courses;
    }

    // public String getUsername(){ return username; }

    // public void setUsername(String username){ this.username = username; }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

}
