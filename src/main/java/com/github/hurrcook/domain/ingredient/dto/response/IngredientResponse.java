package com.github.hurrcook.domain.ingredient.dto.response;

import com.github.hurrcook.domain.ingredient.entity.Ingredient;
import com.github.hurrcook.global.common.Unit;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder @Getter
public class IngredientResponse {
    @Schema(description = "재료 id")
    UUID userFoodId;

    @Schema(description = "재료명")
    String name;

    @Schema(description = "재료의 양")
    int amount;

    @Schema(description = "재료 유통기한")
    LocalDateTime expireDate;

    @Schema(description = "재료 단위")
    Unit unit;

    public static IngredientResponse from(Ingredient ingredient) {
        return IngredientResponse.builder()
                .userFoodId(ingredient.getId())
                .name(ingredient.getName())
                .amount(ingredient.getAmount())
                .expireDate(ingredient.getExpireDate())
                .unit(ingredient.getUnit())
                .build();
    }
}