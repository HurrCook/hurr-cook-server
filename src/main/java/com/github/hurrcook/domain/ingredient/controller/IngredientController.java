package com.github.hurrcook.domain.ingredient.controller;

import com.github.hurrcook.domain.ingredient.dto.request.IngredientReduceRequest;
import com.github.hurrcook.domain.ingredient.dto.request.IngredientRequest;
import com.github.hurrcook.domain.ingredient.dto.response.IngredientResponse;
import com.github.hurrcook.domain.ingredient.service.IngredientService;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PutMapping
    @Operation(summary = "재료 차감")
    public ApiResponse<Void> reduceIngredient(@AuthenticationPrincipal User user, @RequestBody @Valid @NotEmpty List<@Valid IngredientReduceRequest> ingredientReduceRequests) {

        ingredientService.reduceIngredient(user, ingredientReduceRequests);

        return ApiResponse.ok();
    }
  
    @GetMapping()
    @Operation(summary = "재료 목록 조회")
    public ApiResponse<List<IngredientResponse>> getAllIngredients(@AuthenticationPrincipal User user) {
        return ApiResponse.ok(ingredientService.getIngredients(user));
    }

    @GetMapping("/{ingredientId}")
    @Operation(summary = "단일 재료 조회")
    public ApiResponse<IngredientResponse> getIngredient(@AuthenticationPrincipal User user, @PathVariable UUID ingredientId) {
        return ApiResponse.ok(ingredientService.getIngredient(user, ingredientId));
    }
}
