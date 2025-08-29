package com.github.hurrcook.domain.recipe.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class ModifyRecipeRequest {
    @NotNull
    String title;

    @NotNull
    List<ModifyIngredient> ingredients;

    @NotNull
    List<String> steps;


}
