package com.jpmc.midascore;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

    @Autowired
    private UserBalanceService userBalanceService;

    @GetMapping("/balance")
    public BalanceDTO getBalance(@RequestParam("userId") int userId) {
        double balanceAmount = userBalanceService.getBalanceForUser(userId);
        return new BalanceDTO(userId, balanceAmount);
    }
}