package com.bankingapi.bankingapi.service;

import com.bankingapi.bankingapi.model.Transaction;
import com.bankingapi.bankingapi.model.TransactionDTO;
import com.bankingapi.bankingapi.model.User;
import com.bankingapi.bankingapi.model.UserDTO;
import com.bankingapi.bankingapi.respository.InMemoryTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.bankingapi.bankingapi.respository.UserRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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

        try{

            if(userData.getBalance()<0){
                return ResponseEntity.badRequest().body("Initial account balance cannot be negative.");
            }

            BigDecimal balanceDec = new BigDecimal(userData.getBalance()).setScale(2, RoundingMode.HALF_UP);
            double balance = balanceDec.doubleValue();

            User newUser = new User();

            String generatedAccountId = generateUniqueAccountId();

            newUser.setBalance(balance);
            newUser.setName(userData.getName());
            newUser.setAccountId(generatedAccountId);

            User savedUser = this.userRepo.save(newUser);

            UserDTO dto = new UserDTO();

            dto.setName(savedUser.getName());
            dto.setAccountId(savedUser.getAccountId());
            dto.setBalance(savedUser.getBalance());

            return ResponseEntity.status(201).body(dto);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating new account.");
        }

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

    public ResponseEntity<?>transferFunds(TransactionDTO transDTO){

        if (transDTO.getAccountReceiver()==null || transDTO.getAccountReceiver().isEmpty() || transDTO.getAccountSender()==null || transDTO.getAccountSender().isEmpty()){
            return ResponseEntity.badRequest().body("The account id fields cannot be empty");
        }

        try{
            if (transDTO.getAccountReceiver().equals(transDTO.getAccountSender())){
                return ResponseEntity.badRequest().body("Cannot transfer funds to the same account.");
            }

            BigDecimal fundsDec = new BigDecimal(transDTO.getFunds()).setScale(2, RoundingMode.HALF_UP);
            double funds =fundsDec.doubleValue();

            if (funds<=0){
                return ResponseEntity.badRequest().body("Funds must be a positive value.");
            }

            Optional<User> userReceiver = userRepo.findByAccountId(transDTO.getAccountReceiver());
            Optional<User> userSender = userRepo.findByAccountId(transDTO.getAccountSender());

            if(userReceiver.isEmpty() || userSender.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("One of the accounts does not exist");
            }

            User receiver =userReceiver.get();
            User sender = userSender.get();

            if (sender.getBalance()<funds){
                return ResponseEntity.badRequest().body("Insufficient Funds");
            }

            Transaction newTrans = new Transaction();
            newTrans.setFunds(funds);
            newTrans.setAccountReceiver(transDTO.getAccountReceiver());
            newTrans.setAccountSender(transDTO.getAccountSender());
            newTrans.setReceiverName(receiver.getName());
            newTrans.setSenderName(sender.getName());

            double senderChange =sender.getBalance() - funds;
            sender.setBalance(senderChange);
            double receiverChange = receiver.getBalance()+funds;
            receiver.setBalance(receiverChange);

            userRepo.save(receiver);
            userRepo.save(sender);

            transRepo.save(newTrans);

            TransactionDTO dto = new TransactionDTO();
            dto.setFunds(newTrans.getFunds());
            dto.setAccountReceiver(newTrans.getAccountReceiver());
            dto.setAccountSender(newTrans.getAccountSender());

            return ResponseEntity.ok(dto);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing transaction.");
        }




    }

}
