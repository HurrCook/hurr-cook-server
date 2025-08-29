package com.github.hurrcook.domain.recipe.dto.response;

import com.github.hurrcook.domain.recipe.entity.Recipe;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
public class RecipeListResponse {

    @Schema(description = "레시피 목록")
    List<SimpleRecipeResponse> recipes;

    public static RecipeListResponse from(List<Recipe> recipes){
        return RecipeListResponse.of(recipes.stream().map(SimpleRecipeResponse::from).toList());
    }
}
