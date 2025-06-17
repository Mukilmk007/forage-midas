package com.jpmc.midascore.component;

import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.foundation.Incentive;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class IncentiveService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String INCENTIVE_API_URL = "http://localhost:8080/incentive";

    public Incentive fetchIncentive(Transaction transaction) {
        try {
            Incentive response = restTemplate.postForObject(INCENTIVE_API_URL, transaction, Incentive.class);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            // If API fails, return incentive 0 as fallback
            Incentive fallback = new Incentive();
            fallback.setAmount(0.0);
            return fallback;
        }
    }
}