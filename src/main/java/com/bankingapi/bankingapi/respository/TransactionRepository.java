package com.bankingapi.bankingapi.respository;


import com.bankingapi.bankingapi.model.Transaction;

import java.util.List;

public interface TransactionRepository {
    void save(Transaction transaction);

    List<Transaction>findAll();


}