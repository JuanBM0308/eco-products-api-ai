package com.juanba.eco_products_ai.controllers;

import com.knuddels.jtokkit.Encodings;
import com.knuddels.jtokkit.api.Encoding;
import com.knuddels.jtokkit.api.EncodingRegistry;
import com.knuddels.jtokkit.api.EncodingType;
import com.knuddels.jtokkit.api.ModelType;
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
        this.chatClient = chatClientBuilder
                .defaultOptions(
                        ChatOptions.builder()
                                .model("gpt-4o-mini")
                                .build()
                )
                .build();
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
        var tokens = tokenCounter(system, product);
        System.out.println(tokens);

        return this.chatClient.prompt()
                .system(system)
                .user(product)
                .options(
                        ChatOptions.builder()
                                .model("gpt-4o-mini")
                                .temperature(0.8)
                                .build()
                )
                .call()
                .content();
    }

    private int tokenCounter(String system, String user) {
        EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();
        Encoding enc = registry.getEncodingForModel(ModelType.GPT_4O_MINI);
        return enc.countTokens(system + user);
    }
}
