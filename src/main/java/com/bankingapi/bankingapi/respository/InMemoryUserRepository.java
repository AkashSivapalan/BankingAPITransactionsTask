package com.bankingapi.bankingapi.respository;
import com.bankingapi.bankingapi.model.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> userStore = new HashMap<>();

    @Override
    public Optional<User> findByAccountId(String accountId) {
        return Optional.ofNullable(userStore.get(accountId));
    }

    @Override
    public User save(User user) {
        userStore.put(user.getAccountId(), user);
        return user;
    }

    @Override
    public boolean existsByAccountId(String accountId) {
        return userStore.containsKey(accountId);
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(userStore.values());
    }
}