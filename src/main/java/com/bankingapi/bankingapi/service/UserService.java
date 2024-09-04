package com.bankingapi.bankingapi.service;

import com.bankingapi.bankingapi.model.Transaction;
import com.bankingapi.bankingapi.model.User;
import com.bankingapi.bankingapi.model.UserDTO;
import com.bankingapi.bankingapi.respository.InMemoryTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.bankingapi.bankingapi.respository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepo;
    private final InMemoryTransactionRepository transRepo;

    @Autowired
    public UserService(UserRepository userRepo, InMemoryTransactionRepository transRepo){
        this.userRepo=userRepo;
        this.transRepo=transRepo;
    }

    public ResponseEntity<?> postUser(UserDTO userData) {

        User newUser = new User();

        String generatedAccountId = generateUniqueAccountId();

        newUser.setBalance(userData.getBalance());
        newUser.setName(userData.getName());
        newUser.setAccountId(generatedAccountId);

        User savedUser = this.userRepo.save(newUser);

        return ResponseEntity.status(201).body(savedUser);
    }

    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepo.findAll();
        return ResponseEntity.ok(users);
    }


    private String generateUniqueAccountId() {
        String accountId;
        do {
            accountId = UUID.randomUUID().toString(); // Generate a random UUID
        } while (userRepo.existsByAccountId(accountId)); // Check if the accountId already exists
        return accountId;
    }

    public ResponseEntity<?>transferFunds(Transaction transDTO){
        if (transDTO.getAccountReceiver().equals(transDTO.getAccountSender())){
            return ResponseEntity.badRequest().body("Cannot transfer funds to the same account.");
        }

        double funds = transDTO.getFunds();

        if (funds<=0){
            return ResponseEntity.badRequest().body("Funds must be a positive value.");
        }

        Optional<User> userReceiver = userRepo.findByAccountId(transDTO.getAccountReceiver());
        Optional<User> userSender = userRepo.findByAccountId(transDTO.getAccountSender());

        if(userReceiver.isEmpty() || userSender.isEmpty()){
            return ResponseEntity.badRequest().body("One of the accounts does not exist");
        }

        User receiver =userReceiver.get();
        User sender = userSender.get();

        Transaction newTrans = new Transaction();
        newTrans.setFunds(transDTO.getFunds());
        newTrans.setAccountReceiver(transDTO.getAccountReceiver());
        newTrans.setAccountSender(transDTO.getAccountSender());
        newTrans.setReceiverName(receiver.getName());
        newTrans.setSenderName(sender.getName());



        if (sender.getBalance()<funds){
            return ResponseEntity.badRequest().body("Insufficient Funds");
        }

        double senderChange =sender.getBalance() - funds;
        sender.setBalance(senderChange);
        double receiverChange = receiver.getBalance()+funds;
        receiver.setBalance(receiverChange);


        userRepo.save(receiver);
        userRepo.save(sender);

        transRepo.save(newTrans);

        return ResponseEntity.ok("Transfer Successful");



    }

}
