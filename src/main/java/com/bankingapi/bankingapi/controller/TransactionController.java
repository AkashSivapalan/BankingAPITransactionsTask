package com.bankingapi.bankingapi.controller;

import com.bankingapi.bankingapi.model.Transaction;
import com.bankingapi.bankingapi.model.User;
import com.bankingapi.bankingapi.service.TransactionService;
import com.bankingapi.bankingapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    @GetMapping("/transactions/{accountId}")
    public ResponseEntity<?> getAllTransactions(@PathVariable String accountId) {
        return transactionService.getAllTransactions(accountId);
    }

}
