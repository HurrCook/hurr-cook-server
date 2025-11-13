package com.github.hurrcook.domain.recipe.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ModifyRecipeRequest {
    @NotNull
    @Schema(description = "레시피 제목")
    String title;

    @NotNull
    @Schema(description = "재료 리스트")
    List<ModifyIngredient> ingredients;

    @NotNull
    @Schema(description = "레시피 내용")
    List<String> steps;

    @NotNull
    @Schema(description = "레시피 이미지")
    String imageUrl;


}
