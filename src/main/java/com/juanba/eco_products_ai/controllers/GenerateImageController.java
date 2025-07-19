package com.juanba.eco_products_ai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImageOptionsBuilder;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eco/api/v1/image")
public class GenerateImageController {

    private final ImageModel imageModel;

    public GenerateImageController(ImageModel imageModel) {
        this.imageModel = imageModel;
    }

    @GetMapping
    public String generateImage(String prompt) {
        var options = ImageOptionsBuilder.builder()
                .height(1024)
                .width(1024)
                .build();

        var response = imageModel.call(new ImagePrompt(prompt, options));
        return response.getResult().getOutput().getUrl();
    }
}
