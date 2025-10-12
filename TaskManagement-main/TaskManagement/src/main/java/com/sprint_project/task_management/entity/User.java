package com.sprint_project.task_management.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


@Entity

@Table(name = "User")

public class User {
 
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer userId;
 
    @NotBlank(message = "Username is required")

    @Size(max = 255)

    @Column(nullable = false, unique = true)

    private String username;
 
    @NotBlank(message = "Password is required")

    @Size(max = 255)

    @Column(nullable = false)

    private String password;
 
    @NotBlank(message = "Email is required")

    @Email(message = "Email should be valid")

    @Size(max = 255)

    @Column(nullable = false)

    private String email;
 
    @NotBlank(message = "Full name is required")

    @Size(max = 255)

    @Column(nullable = false)

    private String fullName;
 
    // Getters and Setters
 
    public Integer getUserId() {

        return userId;

    }
 
    public void setUserId(Integer userId) {

        this.userId = userId;

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
 
    public String getEmail() {

        return email;

    }
 
    public void setEmail(String email) {

        this.email = email;

    }
 
    public String getFullName() {

        return fullName;

    }
 
    public void setFullName(String fullName) {

        this.fullName = fullName;

    }

}
 