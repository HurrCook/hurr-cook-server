package com.github.hurrcook.domain.chat.controller;

import com.github.hurrcook.domain.chat.dto.request.OcrRequest;
import com.github.hurrcook.domain.chat.dto.request.PromptRequest;
import com.github.hurrcook.domain.chat.dto.response.LlmResponse;
import com.github.hurrcook.domain.chat.dto.response.OcrResponse;
import com.github.hurrcook.domain.chat.service.ChatService;
import com.github.hurrcook.domain.user.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(name = "ocr 이미지 요청 후 겍체 인식 결과 반환(base64 인코딩 문자열)")
    @PostMapping("/ocr")
    public OcrResponse analyzeOcr(@RequestBody OcrRequest ocrRequest) {
        return chatService.analyzeOcr(ocrRequest);
    }

}
