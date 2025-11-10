package com.github.hurrcook.domain.chat.controller;

import com.github.hurrcook.domain.chat.dto.request.ImageRequest;
import com.github.hurrcook.domain.chat.dto.request.PromptRequest;
import com.github.hurrcook.domain.chat.dto.response.LlmResponse;
import com.github.hurrcook.domain.chat.dto.response.OcrResponse;
import com.github.hurrcook.domain.chat.dto.response.YoloResponse;
import com.github.hurrcook.domain.chat.service.ChatService;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.global.response.ApiResponse;
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
    public ApiResponse<LlmResponse> getRecipeRecommend(@RequestBody PromptRequest promptRequest,
                                                       @AuthenticationPrincipal User user) {

        return ApiResponse.ok(chatService.RecommendRecipe(promptRequest, user));
    }

    @Operation(summary = "OCR 이미지 분석", description = "base64 인코딩된 이미지를 전송하여 객체 인식 결과를 반환")
    @PostMapping("/ocr")
    public ApiResponse<OcrResponse> analyzeOcr(@RequestBody ImageRequest imageRequest) {
        return ApiResponse.ok(chatService.analyzeOcr(imageRequest));
    }

    @Operation(summary = "이미지 속 재료 추출", description = "base64 인코딩된 이미지에서 재료를 추출하여 반환")
    @PostMapping("/yolo")
    public ApiResponse<YoloResponse> analyzeYolo(@RequestBody ImageRequest imageRequest) {
        YoloResponse yoloResponse = chatService.analyzeYolo(imageRequest);
        return ApiResponse.ok(yoloResponse);
    }


}
