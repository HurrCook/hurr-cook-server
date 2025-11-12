package com.github.hurrcook.domain.user.controller;

import com.github.hurrcook.domain.user.dto.request.SetPreferenceRequest;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.domain.user.service.UserService;
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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Operation(summary = "개인 맞춤 설정 하기")
    @PostMapping
    public ApiResponse<Void> setPersonalPreference(@AuthenticationPrincipal User user, @RequestBody
                                                   SetPreferenceRequest request) {

        userService.setPersonalPreference(user, request.getPersonalPreference());
        return ApiResponse.ok();
    }

}
