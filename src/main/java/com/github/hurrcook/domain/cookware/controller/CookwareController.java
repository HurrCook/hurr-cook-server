package com.github.hurrcook.domain.cookware.controller;

import com.github.hurrcook.domain.cookware.dto.request.CookwareRequest;
import com.github.hurrcook.domain.cookware.dto.response.CookwareResponse;
import com.github.hurrcook.domain.cookware.service.CookwareService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CookwareController {

    private final CookwareService cookwareService;

    @GetMapping("/cookwares")
    public ResponseEntity<CookwareResponse> getCookware(@AuthenticationPrincipal CustomUserDetails userDetails) {

        UUID userId = userDetails.getUserId();
        CookwareResponse cookwareResponse = cookwareService.getCookwareByUserId(userId);
        return ResponseEntity.ok(cookwareResponse);
    }

    @PostMapping("/cookwares")
    public void saveCookware(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody CookwareRequest cookwareRequest) {

        UUID userId = userDetails.getUserId();
        cookwareService.saveCookware(userId, cookwareRequest);
    }
}
