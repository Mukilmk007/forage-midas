package com.jpmc.midascore;

// Simple DTO matching Balance fields
public class BalanceDTO {
    private int userId;
    private double balance;

    public BalanceDTO(int userId, double balance) {
        this.userId = userId;
        this.balance = balance;
    }

    // Getters (needed for JSON serialization)
    public int getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "BalanceDTO{" +
                "userId=" + userId +
                ", balance=" + balance +
                '}';
    }
}