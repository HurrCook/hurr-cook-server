package com.github.hurrcook.domain.recipe.controller;

import com.github.hurrcook.domain.recipe.dto.request.ModifyRecipeRequest;
import com.github.hurrcook.domain.recipe.dto.request.SaveRecipeRequest;
import com.github.hurrcook.domain.recipe.dto.response.RecipeListResponse;
import com.github.hurrcook.domain.recipe.dto.response.RecipeResponse;
import com.github.hurrcook.domain.recipe.entity.Recipe;
import com.github.hurrcook.domain.recipe.service.RecipeService;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Operation(summary = "레시피 저장")
    public ApiResponse<Void> saveRecipe(@RequestBody @Valid SaveRecipeRequest saveRecipeRequest, @AuthenticationPrincipal User user) {

        recipeService.saveRecipe(saveRecipeRequest,user);

        return ApiResponse.ok();
    }

    @PutMapping("/{recipe}")
    @Operation(summary = "레시피 수정")
    public ApiResponse<Void> updateRecipe(@RequestBody @Valid ModifyRecipeRequest modifyRecipeRequest, @Parameter(description = "레시피 ID",schema = @Schema(type = "string", format = "uuid")) @PathVariable Recipe recipe) {

        recipeService.updateRecipe(modifyRecipeRequest,recipe);

        return ApiResponse.ok();
    }

    @DeleteMapping("/{recipe}")
    @Operation(summary = "레시피 삭제")
    public ApiResponse<Void> deleteRecipe(@Parameter(description = "레시피 ID",schema = @Schema(type = "string", format = "uuid")) @PathVariable Recipe recipe) {

        recipeService.deleteRecipe(recipe);

        return ApiResponse.ok();
    }

    @GetMapping
    @Operation(summary = "레시피 목록 조회")
    public ApiResponse<RecipeListResponse> getRecipeList(@AuthenticationPrincipal User user) {

        return ApiResponse.ok(recipeService.getRecipeList(user));
    }

    @GetMapping("/{recipe}")
    @Operation(summary = "레시피 상세 조회")
    public ApiResponse<RecipeResponse> getRecipeDetail(@Parameter(description = "레시피 ID",schema = @Schema(type = "string", format = "uuid")) @PathVariable Recipe recipe) {
        return ApiResponse.ok(recipeService.getRecipe(recipe));
    }

}
