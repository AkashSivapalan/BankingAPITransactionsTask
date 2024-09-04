package com.bankingapi.bankingapi.controller;


import com.bankingapi.bankingapi.model.Transaction;
import com.bankingapi.bankingapi.model.User;
import com.bankingapi.bankingapi.model.UserDTO;
import com.bankingapi.bankingapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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


    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferFunds(@RequestBody Transaction newTrans) {
        return userService.transferFunds(newTrans);
    }
}
