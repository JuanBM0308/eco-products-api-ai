package com.juanba.eco_products_ai.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/eco/api/v1/categorize")
public class GenerateCategoryController {

    private final ChatClient chatClient;

    public GenerateCategoryController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    @GetMapping
    public String categorizeProducts(String product) {
        var system = """
                Actúa como un categorizador de productos y debes responder solo el nombre de la categoría del producto no des explicaciones sí el nombre llega a ser generalista.
                
                Escoge una categoria de la siguiente lista:
                
                1. Higiene personal
                2. Electrónicos
                3. Deportes
                4. Vehículos
                5. Respuestos
                6. Hogar
                7. Herramientas
                8. Otros
                
                Ejemplo de uso:
                
                Producto: Pelota de golf
                Respuesta: Deportes
                """;
        return this.chatClient.prompt()
                .system(system)
                .user(product)
                .options(
                        ChatOptions.builder()
                                .temperature(0.8)
                                .build()
                )
                .call()
                .content();
    }
}
