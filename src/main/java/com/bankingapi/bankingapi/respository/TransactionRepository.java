package com.bankingapi.bankingapi.respository;


import com.bankingapi.bankingapi.model.Transaction;
import com.bankingapi.bankingapi.model.User;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository {
    Transaction save(Transaction transaction);
    Optional<Transaction> findById(String id);


}