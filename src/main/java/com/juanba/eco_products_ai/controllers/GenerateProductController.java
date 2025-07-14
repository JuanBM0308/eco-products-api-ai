package com.juanba.eco_products_ai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eco/api/v1/generator")
public class GenerateProductController {

    private final ChatClient chatClient;

    public GenerateProductController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping
    public String generateProduct() {
        var userInput = "Genera 3 productos ecologicos";
        return this.chatClient.prompt()
                .user(userInput)
                .call()
                .content();
    }
}
