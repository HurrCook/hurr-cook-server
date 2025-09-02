package com.github.hurrcook.domain.ingredient.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.UUID;

@Data
public class IngredientReduceRequest {

    @NotNull
    UUID userFoodId;

    @PositiveOrZero
    Integer useAmount;
}
