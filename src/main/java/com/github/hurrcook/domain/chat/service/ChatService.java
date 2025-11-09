package com.github.hurrcook.domain.chat.service;

import com.github.hurrcook.domain.chat.dto.request.IngredientItem;
import com.github.hurrcook.domain.chat.dto.request.PromptRequest;
import com.github.hurrcook.domain.chat.dto.request.RecipeRequest;
import com.github.hurrcook.domain.chat.dto.response.LlmResponse;
import com.github.hurrcook.domain.ingredient.entity.Ingredient;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final WebClient webClient;

    public LlmResponse RecommendRecipe(PromptRequest promptRequest, User user) {

        List<Ingredient> ingredients = user.getIngredients();

        RecipeRequest recipeRequest = RecipeRequest.builder()
                .prompt(promptRequest.getMessage())
                .ingredients(ingredients.stream().map(IngredientItem::from).toList())
                .build();

        try {
            LlmResponse response = webClient.post()
                    .uri("/recommend")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(recipeRequest)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                            Mono.error(new RuntimeException("AI 서버 요청 오류")))
                    .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                            Mono.error(new RuntimeException("AI 서버 내부 오류")))
                    .bodyToMono(LlmResponse.class)
                    .block();

            return response;

        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }
}
