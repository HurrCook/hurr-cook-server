package com.github.hurrcook.domain.recipe.controller;

import com.github.hurrcook.domain.recipe.dto.request.ModifyRecipeRequest;
import com.github.hurrcook.domain.recipe.dto.request.SaveRecipeRequest;
import com.github.hurrcook.domain.recipe.entity.Recipe;
import com.github.hurrcook.domain.recipe.service.RecipeService;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.global.response.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ApiResponse<Void> saveRecipe(@RequestBody @Valid SaveRecipeRequest saveRecipeRequest, @AuthenticationPrincipal User user) {

        recipeService.saveRecipe(saveRecipeRequest,user);

        return ApiResponse.ok();
    }

    @PutMapping("/{recipe}")
    public ApiResponse<Void> updateRecipe(@RequestBody @Valid ModifyRecipeRequest modifyRecipeRequest, @PathVariable Recipe recipe, @AuthenticationPrincipal User user) {
        recipeService.updateRecipe(modifyRecipeRequest,recipe,user);

        return ApiResponse.ok();
    }
}
