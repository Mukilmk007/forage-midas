package com.jpmc.midascore;

import com.jpmc.midascore.foundation.Transaction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final String topic;
    private final KafkaTemplate<String, Transaction> kafkaTemplate;

    public KafkaProducer(@Value("${general.kafka-topic}") String topic, KafkaTemplate<String, Transaction> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Parses a transaction line in format "senderId, recipientId, amount"
     * and sends it as a Transaction object to Kafka.
     */
    public void send(String transactionLine) {
        try {
            // Split input string by comma and optional space
            String[] transactionData = transactionLine.trim().split("\\s*,\\s*");

            if (transactionData.length != 3) {
                throw new IllegalArgumentException("Invalid transaction line format, expected 3 parts separated by commas");
            }

            long senderId = Long.parseLong(transactionData[0]);
            long recipientId = Long.parseLong(transactionData[1]);
            float amount = Float.parseFloat(transactionData[2]);

            Transaction transaction = new Transaction(senderId, recipientId, amount);

            // Send the transaction to Kafka topic
            kafkaTemplate.send(topic, transaction);

            System.out.println("Sent transaction to topic " + topic + ": " + transaction);

        } catch (Exception e) {
            System.err.println("Failed to send transaction: " + e.getMessage());
            e.printStackTrace();
        }
    }
}