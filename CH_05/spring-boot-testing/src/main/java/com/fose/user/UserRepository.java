package com.fose.user;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    public final List<User> users = new ArrayList<>();

    public UserRepository() {
        users.add(new User("user","user@gmail.com"));
    }

    public boolean existsByUsername(String username) {
        return username.equals("user");
    }

    public void save(User user) {
        System.out.println("Saving user: " + user.username());
        users.add(user);
    }

    public int count() {
        return users.size();
    }

    public void deleteAll() {
        users.clear();
    }

    public Optional<User> findByUsername(String username) {
        return users.stream().filter(u -> u.username().equals(username)).findFirst();
    }
}
