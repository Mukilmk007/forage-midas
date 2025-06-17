package com.jpmc.midascore.component;

import com.jpmc.midascore.entity.TransactionRecord;
import com.jpmc.midascore.entity.UserRecord;
import com.jpmc.midascore.foundation.Transaction;
import com.jpmc.midascore.foundation.Incentive;
import com.jpmc.midascore.repository.TransactionRepository;
import com.jpmc.midascore.repository.UserRepository;
import com.jpmc.midascore.component.IncentiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
public class TransactionListener {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private IncentiveService incentiveService;

    @KafkaListener(topics = "transactions", groupId = "midas-group")
    @Transactional
    public void listen(Transaction transaction) {
        System.out.println("Received transaction: " + transaction);

        Optional<UserRecord> senderOpt = userRepository.findById(transaction.getSenderId());
        Optional<UserRecord> recipientOpt = userRepository.findById(transaction.getRecipientId());

        if (senderOpt.isPresent() && recipientOpt.isPresent()) {
            UserRecord sender = senderOpt.get();
            UserRecord recipient = recipientOpt.get();

            if (sender.getBalance() >= transaction.getAmount()) {
                // Fetch incentive from Incentive API
                Incentive incentive = incentiveService.fetchIncentive(transaction);
                double incentiveAmount = incentive.getAmount();

                // Update balances
                sender.setBalance(sender.getBalance() - transaction.getAmount());
                recipient.setBalance(recipient.getBalance() + transaction.getAmount() + incentiveAmount);

                // Save updated users
                userRepository.save(sender);
                userRepository.save(recipient);

                // Record transaction with incentive
                TransactionRecord record = new TransactionRecord(sender, recipient, transaction.getAmount(), incentiveAmount);
                transactionRepository.save(record);

                System.out.println("Transaction processed and recorded with incentive.");
            } else {
                System.out.println("Transaction discarded: insufficient funds.");
            }
        } else {
            System.out.println("Transaction discarded: invalid sender or recipient.");
        }
    }
}