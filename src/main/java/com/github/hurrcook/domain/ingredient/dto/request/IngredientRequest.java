
package com.github.hurrcook.domain.ingredient.dto.request;

import com.github.hurrcook.global.common.Unit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class IngredientRequest {

    @NotBlank
    @Schema(description = "재료 이름")
    String name;

    @Positive
    @Schema(description = "수량")
    int amount;

    @NotNull
    @Schema(description = "재료 단위")
    Unit unit;

    @NotNull
    @Schema(description = "유통기한")
    LocalDateTime expireDate;
}
