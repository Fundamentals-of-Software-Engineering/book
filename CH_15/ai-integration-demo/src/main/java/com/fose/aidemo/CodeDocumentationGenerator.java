package com.fose.aidemo;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/code")
public class CodeDocumentationGenerator {

    private final ChatClient chatClient;

    public CodeDocumentationGenerator(ChatClient.Builder builder) {
        this.chatClient = builder.build();
    }

    @GetMapping("/documentation")
    public String generateDocumentation(String code) {
        var userMessage = """
                Generate comprehensive JavaDoc comments for this method.
                Include:
                - Method description
                - @param tags for each parameter
                - @return tag if applicable
                - @throws tags for exceptions

                Code:
                {code}

                Return only the JavaDoc comment block, properly formatted.
                """;

        return chatClient.prompt()
                .user( u -> {
                    u.text(userMessage);
                    u.param("code", code);
                })
                .call()
                .content();
    }
}
