package com.fose.aidemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class TestDataGenerator {

    private final ChatClient chatClient;

    public TestDataGenerator(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/test-data")
    public List<User> generateTestUsers(int count) {
        var userList = chatClient.prompt()
                .user(u -> {
                    var prompt = """
                            Generate {n} realistic user profiles with
                            name, email, age, and interests.
                            Return as JSON array with format:
                            [{"name": "...", "email": "...", "age": ..., "interests": "..."}]
                            """;
                    u.text(prompt).param("n", count);
                })
                .call()
                .entity(UserList.class);

        return userList.users();
    }

    public record UserList(List<User> users) {
    }

    public record User(String name, String email, int age, String interests) {
    }
}
