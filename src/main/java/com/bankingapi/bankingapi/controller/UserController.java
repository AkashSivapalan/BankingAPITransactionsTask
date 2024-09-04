package com.bankingapi.bankingapi.controller;


import com.bankingapi.bankingapi.model.Transaction;
import com.bankingapi.bankingapi.model.TransactionDTO;
import com.bankingapi.bankingapi.model.User;
import com.bankingapi.bankingapi.model.UserDTO;
import com.bankingapi.bankingapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {


    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> postPreference(@RequestBody UserDTO newUser) {
        return userService.postUser(newUser);
    }


    @GetMapping("/user/{accountId}")
    public ResponseEntity<?> getUser(@PathVariable String accountId) {
        return userService.getUser(accountId);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferFunds(@RequestBody TransactionDTO newTrans) {
        return userService.transferFunds(newTrans);
    }
}
