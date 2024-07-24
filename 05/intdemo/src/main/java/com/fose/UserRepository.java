package com.fose;

import java.util.ArrayList;
import java.util.List;

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

}
