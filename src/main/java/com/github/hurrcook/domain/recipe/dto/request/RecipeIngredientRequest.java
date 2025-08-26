package com.github.hurrcook.domain.recipe.dto.request;

import com.github.hurrcook.global.common.Unit;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RecipeIngredientRequest {

    @NotNull
    String name;

    @NotNull
    Integer amount;

    @NotNull
    Unit unit;
}
