package com.github.hurrcook.domain.ingredient.dto.request;

import com.github.hurrcook.global.common.Unit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IngredientRequest {

    @NotBlank
    String name;

    @Positive
    int amount;

    @NotNull
    Unit unit;

    @NotNull
    LocalDateTime expire_time;
}
