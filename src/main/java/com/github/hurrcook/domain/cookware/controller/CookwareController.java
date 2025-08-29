package com.github.hurrcook.domain.cookware.controller;

import com.github.hurrcook.domain.cookware.dto.request.CookwareRequest;
import com.github.hurrcook.domain.cookware.dto.response.CookwareResponse;
import com.github.hurrcook.domain.cookware.service.CookwareService;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "요리도구")
public class CookwareController {

    private final CookwareService cookwareService;

    @GetMapping("/cookwares")
    @Operation(summary = "요리도구 조회")
    public ApiResponse<CookwareResponse> getCookware(@AuthenticationPrincipal User user) {

        CookwareResponse cookwareResponse = cookwareService.getCookwareByUser(user);
        return ApiResponse.ok(cookwareResponse);
    }

    @PostMapping("/cookwares")
    @Operation(summary = "요리도구 등록")
    public ApiResponse<Void> saveCookware(@AuthenticationPrincipal User user, @RequestBody @Valid CookwareRequest cookwareRequest) {

        cookwareService.saveCookware(user, cookwareRequest);

        return ApiResponse.ok();
    }
}
