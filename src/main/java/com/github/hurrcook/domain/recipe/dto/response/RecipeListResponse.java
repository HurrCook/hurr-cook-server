package com.github.hurrcook.domain.recipe.dto.response;

import com.github.hurrcook.domain.recipe.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
public class RecipeListResponse {

    List<SimpleRecipeResponse> recipes;

    public static RecipeListResponse from(List<Recipe> recipes){
        return RecipeListResponse.of(recipes.stream().map(SimpleRecipeResponse::from).toList());
    }
}
