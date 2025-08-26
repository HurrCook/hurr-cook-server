package com.github.hurrcook.domain.cookware.controller;

import com.github.hurrcook.domain.cookware.dto.request.CookwareRequest;
import com.github.hurrcook.domain.cookware.dto.response.CookwareResponse;
import com.github.hurrcook.domain.cookware.service.CookwareService;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CookwareController {

    private final CookwareService cookwareService;

    @GetMapping("/cookwares")
    public ApiResponse<CookwareResponse> getCookware(@AuthenticationPrincipal User user) {

        CookwareResponse cookwareResponse = cookwareService.getCookwareByUser(user);
        return ApiResponse.ok(cookwareResponse);
    }

    @PostMapping("/cookwares")
    public ApiResponse<Void> saveCookware(@AuthenticationPrincipal User user, @RequestBody @Valid CookwareRequest cookwareRequest) {

        cookwareService.saveCookware(user, cookwareRequest);

        return ApiResponse.ok();
    }
}
