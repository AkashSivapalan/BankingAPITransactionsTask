package com.bankingapi.bankingapi.service;

import com.bankingapi.bankingapi.model.Transaction;
import com.bankingapi.bankingapi.model.User;
import com.bankingapi.bankingapi.respository.InMemoryTransactionRepository;
import com.bankingapi.bankingapi.respository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
        Optional<User> user = userRepo.findByAccountId(accountId);

        if(user.isEmpty() ){
            return ResponseEntity.badRequest().body("Account Does Not Exist");
        }

        List<Transaction> transactions = transRepo.findAll();

        List<Transaction> userTrans = new ArrayList<>();

        for(Transaction trans:transactions){
            if (trans.getAccountReceiver().equals(accountId) || trans.getAccountSender().equals(accountId)){
                userTrans.add(trans);
            }
        }

        if (userTrans.isEmpty()){
            return ResponseEntity.badRequest().body("Account has no transaction history.");
        }

        return ResponseEntity.ok(userTrans);

    }
}
