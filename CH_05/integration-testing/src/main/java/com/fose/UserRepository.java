package com.fose;

public interface UserRepository {
    boolean existsByUsername(String username);
    void save(User user);
    int count();
}
