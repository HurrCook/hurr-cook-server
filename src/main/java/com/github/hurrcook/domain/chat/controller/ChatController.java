package com.github.hurrcook.domain.chat.controller;

import com.github.hurrcook.domain.chat.dto.request.OcrRequest;
import com.github.hurrcook.domain.chat.dto.request.PromptRequest;
import com.github.hurrcook.domain.chat.dto.response.LlmResponse;
import com.github.hurrcook.domain.chat.dto.response.OcrResponse;
import com.github.hurrcook.domain.chat.service.ChatService;
import com.github.hurrcook.domain.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public LlmResponse getRecipeRecommend(@RequestBody PromptRequest promptRequest, @AuthenticationPrincipal User user) {

        return chatService.RecommendRecipe(promptRequest, user);
    }

    @Operation(summary = "OCR 이미지 분석", description = "base64 인코딩된 이미지를 전송하여 객체 인식 결과를 반환")
    @PostMapping("/ocr")
    public OcrResponse analyzeOcr(@RequestBody OcrRequest ocrRequest) {
        return chatService.analyzeOcr(ocrRequest);
    }

}
