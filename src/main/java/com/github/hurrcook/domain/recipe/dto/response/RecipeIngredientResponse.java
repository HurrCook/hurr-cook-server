package com.github.hurrcook.domain.recipe.dto.response;

import com.github.hurrcook.domain.recipe._recipe_food.entity.RecipeFood;
import com.github.hurrcook.global.common.Unit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor(staticName = "of")
@Schema(description = "재료 응답")
public class RecipeIngredientResponse {

    @Schema(description = "재료 id")
    UUID id;

    @Schema(description = "재료 명")
    String name;

    @Schema(description = "재료 양")
    Integer amount;

    @Schema(description = "단위")
    Unit unit;

    public static RecipeIngredientResponse from(RecipeFood recipeFood) {
        return RecipeIngredientResponse.of(recipeFood.getId(),recipeFood.getName(),recipeFood.getAmount(),recipeFood.getUnit());
    }
}
