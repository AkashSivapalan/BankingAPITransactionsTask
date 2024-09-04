package com.bankingapi.bankingapi.respository;

import com.bankingapi.bankingapi.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    Optional<User> findByAccountId(String accountId);
    User save(User user);
    boolean existsByAccountId(String accountId);


    List<User> findAll();
}