package com.github.hurrcook.domain.recipe.dto.request;

import com.github.hurrcook.global.common.Unit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RecipeIngredientRequest {

    @NotNull
    @Schema(description = "재료 명")
    String name;

    @NotNull
    @Schema(description = "재료 양")
    Integer amount;

    @NotNull
    @Schema(description = "재료 단위")
    Unit unit;
}
