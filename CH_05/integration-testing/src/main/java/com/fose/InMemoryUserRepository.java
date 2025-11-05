package com.fose;

import java.util.ArrayList;
import java.util.List;

public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = new ArrayList<>();

    @Override
    public boolean existsByUsername(String username) {
        return users.stream()
                .anyMatch(user -> user.username().equals(username));
    }

    @Override
    public void save(User user) {
        System.out.println("Saving user: " + user.username());
        users.add(user);
    }

    @Override
    public int count() {
        return users.size();
    }
}
