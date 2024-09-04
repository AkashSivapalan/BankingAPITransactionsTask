package com.bankingapi.bankingapi.respository;

import com.bankingapi.bankingapi.model.Transaction;
import org.springframework.stereotype.Repository;

import java.util.*;


@Repository
public class InMemoryTransactionRepository {

    private final List<Transaction> transactionStore = new ArrayList<>();

    public void save(Transaction transaction) {
        transactionStore.add(transaction);
    }

    public List<Transaction> findAll() {
        return new ArrayList<>(transactionStore);
    }
}