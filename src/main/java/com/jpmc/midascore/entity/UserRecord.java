package com.jpmc.midascore.entity;

import jakarta.persistence.*;

@Entity
public class UserRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private double balance;

    public UserRecord() {}

    public UserRecord(String username, double balance) {
        this.username = username;
        this.balance = balance;
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
}