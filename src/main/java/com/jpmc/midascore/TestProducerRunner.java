package com.jpmc.midascore;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.jpmc.midascore.foundation.Transaction;

@Component
public class TestProducerRunner implements CommandLineRunner {

    private final TransactionProducer transactionProducer;

    public TestProducerRunner(TransactionProducer transactionProducer) {
        this.transactionProducer = transactionProducer;
    }

    @Override
    public void run(String... args) throws Exception {
        // Send a test transaction on app start
        Transaction transaction = new Transaction(101, 202, 300.50f);
        transactionProducer.sendTransaction(transaction);
    }
}