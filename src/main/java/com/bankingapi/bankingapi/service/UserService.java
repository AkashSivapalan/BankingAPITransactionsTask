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

            //Round balance to 2 decimals to represent the cents. If the decimal is more than 2 decimals, assumed I can round it.
            BigDecimal balanceDec = new BigDecimal(userData.getBalance()).setScale(2, RoundingMode.HALF_UP);
            double balance = balanceDec.doubleValue();

            User newUser = new User();

            //Generate an accountId to identify accounts. Wasn't sure if requesting the user's email would be more appropriate to identify users similar to e-transfers.
            // Decided on accountId since it was mentioned in the doc.
            String generatedAccountId = generateUniqueAccountId();

            newUser.setBalance(balance);
            newUser.setName(userData.getName());
            newUser.setAccountId(generatedAccountId);

            User savedUser = this.userRepo.save(newUser);

            UserDTO dto = new UserDTO();

            //Used dto object for user to demonstrate it. Since the user is simple, there isn't any difference between the two.
            dto.setName(savedUser.getName());
            dto.setAccountId(savedUser.getAccountId());
            dto.setBalance(savedUser.getBalance());

            return ResponseEntity.status(201).body(dto);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating new account.");
        }

    }


    //Create a unique id and checks if it exists
    private String generateUniqueAccountId() {
        String accountId;
        do {
            accountId = UUID.randomUUID().toString();
        } while (userRepo.existsByAccountId(accountId));
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

            //Similar to balance, if funds has a long decimal, it is just rounded
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

            double senderChange =sender.getBalance() - funds;
            sender.setBalance(senderChange);
            double receiverChange = receiver.getBalance()+funds;
            receiver.setBalance(receiverChange);

            userRepo.save(receiver);
            userRepo.save(sender);

            transRepo.save(newTrans);

            //Transaction DTO object. Return this instead of the actual transaction object
            TransactionDTO dto = new TransactionDTO();
            dto.setFunds(newTrans.getFunds());
            dto.setAccountReceiver(newTrans.getAccountReceiver());
            dto.setAccountSender(newTrans.getAccountSender());

            return ResponseEntity.ok(dto);

        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing transaction.");
        }


    }

    //Get call added outside of project parameters so i can check the user's balance after transactions.
    public ResponseEntity<?> getUser(String accountId) {
        try{
            Optional<User> user = userRepo.findByAccountId(accountId);

            if (user.isPresent()){
                User foundUser = user.get();
                UserDTO dto = new UserDTO();
                dto.setBalance(foundUser.getBalance());
                dto.setName(foundUser.getName());
                dto.setAccountId(foundUser.getAccountId());
                return ResponseEntity.ok(dto);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing transaction.");
        }

        return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account Does Not Exist");
    }
}
