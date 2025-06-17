package com.jpmc.midascore;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserBalanceService {

    private final Map<Integer, Double> userBalances = new ConcurrentHashMap<>();

    public void updateBalance(int userId, double amount) {
        userBalances.merge(userId, amount, Double::sum);
    }

    public double getBalanceForUser(int userId) {
        return userBalances.getOrDefault(userId, 0.0);
    }

    public Map<Integer, Double> getAllBalances() {
        return userBalances;
    }
}