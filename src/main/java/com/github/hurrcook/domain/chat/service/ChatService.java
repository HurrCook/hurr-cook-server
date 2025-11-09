package com.github.hurrcook.domain.chat.service;

import com.github.hurrcook.domain.chat.dto.request.IngredientItem;
import com.github.hurrcook.domain.chat.dto.request.PromptRequest;
import com.github.hurrcook.domain.chat.dto.request.RecipeRequest;
import com.github.hurrcook.domain.chat.dto.response.LlmResponse;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.domain.user.repository.UserRepository;
import com.github.hurrcook.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final WebClient webClient;
    private final UserRepository userRepository;

    @Transactional
    public LlmResponse RecommendRecipe(PromptRequest promptRequest, User user) {


        User currentUser = userRepository.findByIdWithIngredients(user.getId());

        RecipeRequest recipeRequest = RecipeRequest.builder()
                .user_query(promptRequest.getMessage())
                .ingredients(currentUser.getIngredients().stream().map(IngredientItem::from).toList())
                .build();

        try {
            LlmResponse response = webClient.post()
                    .uri("/llm/recommend")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(recipeRequest)
                    .retrieve()
                    .bodyToMono(LlmResponse.class)
                    .block();

            return response;

        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }
}
