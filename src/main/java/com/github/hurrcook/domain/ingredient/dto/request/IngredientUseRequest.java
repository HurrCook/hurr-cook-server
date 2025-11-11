package com.github.hurrcook.domain.ingredient.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.util.UUID;

@Data
public class IngredientUseRequest {

    @NotNull
    @Schema(description = "음식 id")
    UUID userFoodId;

    @Schema(description = "재료 이미지")
    String imageUrl;

    @NotNull
    @PositiveOrZero
    @Schema(description = "사용 수량")
    int useAmount;
}
