package com.github.hurrcook.domain.ingredient.controller;

import com.github.hurrcook.domain.ingredient.dto.request.IngredientRequest;
import com.github.hurrcook.domain.ingredient.dto.response.IngredientResponse;
import com.github.hurrcook.domain.ingredient.service.IngredientService;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredients")
@Tag(name = "재료")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping
    @Operation(summary = "재료 등록")
    public ApiResponse<Void> addIngredient(@AuthenticationPrincipal User user, @RequestBody @Valid List<IngredientRequest> ingredientRequests) {

        ingredientService.addIngredient(user, ingredientRequests);

        return ApiResponse.ok();
    }

    @GetMapping()
    @Operation(summary = "재료 목록 조회")
    public ApiResponse<List<IngredientResponse>> getAllIngredients(@AuthenticationPrincipal User user) {
        return ApiResponse.ok(ingredientService.getIngredients(user));
    }
}
