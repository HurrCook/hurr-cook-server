package com.github.hurrcook.domain.chat.service;

import com.github.hurrcook.domain.chat.dto.request.IngredientItem;
import com.github.hurrcook.domain.chat.dto.request.OcrRequest;
import com.github.hurrcook.domain.chat.dto.request.PromptRequest;
import com.github.hurrcook.domain.chat.dto.request.RecipeRequest;
import com.github.hurrcook.domain.chat.dto.response.LlmResponse;
import com.github.hurrcook.domain.chat.dto.response.OcrResponse;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.domain.user.repository.UserRepository;
import com.github.hurrcook.global.exception.ApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

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

    // ai서버로 string 전달 -> 응답 반환
    @Transactional
    public OcrResponse analyzeOcr(OcrRequest ocrRequest) {
        try {
            OcrResponse response = webClient.post()
                    .uri("/ocr/ocr-receipt/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(ocrRequest)
                    .retrieve()
                    .bodyToMono(OcrResponse.class)
                    .timeout(Duration.ofSeconds(30))
                    .block();

            return response;
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

}
