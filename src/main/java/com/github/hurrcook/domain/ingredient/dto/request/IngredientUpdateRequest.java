package com.github.hurrcook.domain.ingredient.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IngredientUpdateRequest {

    @NotBlank
    @Schema(description = "재료 이름")
    String name;

    @Positive
    @Schema(description = "수량")
    int amount;

    @Schema(description = "재료 이미지")
    String imageUrl;

    @NotNull
    @Schema(description = "유통기한")
    LocalDateTime expireDate;
}
