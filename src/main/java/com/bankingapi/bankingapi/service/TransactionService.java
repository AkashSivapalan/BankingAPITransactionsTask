package com.bankingapi.bankingapi.service;

import com.bankingapi.bankingapi.model.Transaction;
import com.bankingapi.bankingapi.model.TransactionDTO;
import com.bankingapi.bankingapi.model.User;
import com.bankingapi.bankingapi.respository.InMemoryTransactionRepository;
import com.bankingapi.bankingapi.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final UserRepository userRepo;
    private final InMemoryTransactionRepository transRepo;


    @Autowired
    public TransactionService(UserRepository userRepo, InMemoryTransactionRepository transRepo){
        this.userRepo=userRepo;
        this.transRepo=transRepo;
    }


    public ResponseEntity<?> getAllTransactions(String accountId) {

        if (accountId==null || accountId.isEmpty()){
            return ResponseEntity.badRequest().body("Account ID field cannot be empty");
        }

        try{

            Optional<User> user = userRepo.findByAccountId(accountId);

            if(user.isEmpty() ){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account Does Not Exist.");
            }

            List<Transaction> transactions = transRepo.findAll();

            List<TransactionDTO> userTrans = new ArrayList<>();

            for(Transaction trans:transactions){
                if (trans.getAccountReceiver().equals(accountId) || trans.getAccountSender().equals(accountId)){
                    TransactionDTO dto = new TransactionDTO();
                    dto.setFunds(trans.getFunds());
                    dto.setAccountSender(trans.getAccountSender());
                    dto.setAccountReceiver(trans.getAccountSender());
                    userTrans.add(dto);
                }
            }

            if (userTrans.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No transactions were found for this account.");
            }

            return ResponseEntity.ok(userTrans);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while fetching transactions");
        }


    }
}
