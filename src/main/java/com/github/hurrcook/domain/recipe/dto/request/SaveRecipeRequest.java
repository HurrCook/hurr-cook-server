package com.github.hurrcook.domain.recipe.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SaveRecipeRequest {

    @NotNull
    @Schema(description = "레시피 제목")
    String title;

    @NotNull
    @Schema(description = "레시피 재료")
    List<RecipeIngredientRequest> ingredients;

    @NotNull
    @Schema(description = "레시피 내용")
    List<String> steps;

    @NotNull
    @Schema(description = "걸리는 시간")
    String time;

    @Nullable
    @Schema(description = "레시피 사진")
    String image;
}
