package com.github.hurrcook.domain.chat.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.hurrcook.domain.chat.dto.request.ImageRequest;
import com.github.hurrcook.domain.chat.dto.request.IngredientItem;
import com.github.hurrcook.domain.chat.dto.request.PromptRequest;
import com.github.hurrcook.domain.chat.dto.request.RecipeRequest;
import com.github.hurrcook.domain.chat.dto.response.LlmResponse;
import com.github.hurrcook.domain.chat.dto.response.OcrResponse;
import com.github.hurrcook.domain.chat.dto.response.YoloResponse;
import com.github.hurrcook.domain.cookware.entity.Cookware;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.domain.user.repository.UserRepository;
import com.github.hurrcook.global.exception.ApiException;
import java.time.Duration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatService {
    private final WebClient webClient;
    private final UserRepository userRepository;

    @Transactional
    public LlmResponse RecommendRecipe(PromptRequest promptRequest, User user) {

        User currentUser = userRepository.findByIdWithIngredients(user.getId());
        Cookware cookware = currentUser.getCookware();
        log.info(promptRequest.getMessage());

        RecipeRequest recipeRequest = RecipeRequest.builder()
                .user_query(promptRequest.getMessage())
                .personal_preferences(user.getPersonalPreference())
                .ingredients(currentUser.getIngredients().stream().map(IngredientItem::from).toList())
                .tools(cookware.getAvailableToolNames())
                .build();
        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(recipeRequest);
            log.info("📤 전송될 JSON: {}", json);
        } catch (Exception e) {
            log.error("JSON 직렬화 실패", e);
        }

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
    public OcrResponse analyzeOcr(ImageRequest imageRequest) {
        try {
            OcrResponse response = webClient.post()
                    .uri("/ocr/ocr-receipt/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(imageRequest)
                    .retrieve()
                    .bodyToMono(OcrResponse.class)
                    .timeout(Duration.ofSeconds(30))
                    .block();

            return response;
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

    @Transactional
    public YoloResponse analyzeYolo(ImageRequest imageRequest) {
        try {
            YoloResponse response = webClient.post()
                    .uri("/yolo/detect-base64/")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(imageRequest)
                    .retrieve()
                    .bodyToMono(YoloResponse.class)
                    .timeout(Duration.ofSeconds(30))
                    .block();

            return response;
        } catch (Exception e) {
            throw new ApiException(e.getMessage());
        }
    }

}
