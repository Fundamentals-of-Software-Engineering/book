package com.fose;

public interface BlogPostRepository {
    void save(BlogPost post);
    int count();
}
