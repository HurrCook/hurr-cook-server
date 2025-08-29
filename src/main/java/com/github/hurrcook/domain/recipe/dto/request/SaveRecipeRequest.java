package com.github.hurrcook.domain.recipe.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class SaveRecipeRequest {

    @NotNull
    String title;

    @NotNull
    List<RecipeIngredientRequest> ingredients;

    @NotNull
    List<String> steps;

    @NotNull
    String time;
}
