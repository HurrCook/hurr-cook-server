package com.github.hurrcook.domain.cookware.controller;

import com.github.hurrcook.domain.cookware.dto.request.CookwareRequest;
import com.github.hurrcook.domain.cookware.dto.response.CookwareResponse;
import com.github.hurrcook.domain.cookware.service.CookwareService;
import com.github.hurrcook.domain.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CookwareController {

    private final CookwareService cookwareService;

    @GetMapping("/cookwares")
    public ResponseEntity<CookwareResponse> getCookware(@AuthenticationPrincipal User user) {

        CookwareResponse cookwareResponse = cookwareService.getCookwareByUser(user);
        return ResponseEntity.ok(cookwareResponse);
    }

    @Valid
    @PostMapping("/cookwares")
    public void saveCookware(@AuthenticationPrincipal User user, @RequestBody CookwareRequest cookwareRequest) {

        cookwareService.saveCookware(user, cookwareRequest);
    }
}
