package com.github.hurrcook.domain.ingredient.controller;

import com.github.hurrcook.domain.ingredient.dto.request.IngredientRequest;
import com.github.hurrcook.domain.ingredient.service.IngredientService;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    @PostMapping("/add")
    public ApiResponse<Void> addIngredient(@AuthenticationPrincipal User user, @RequestBody @Valid List<IngredientRequest> ingredientRequests) {

        ingredientService.addIngredient(user, ingredientRequests);

        return ApiResponse.ok();
    }
}
