package com.github.hurrcook.domain.recipe.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ModifyIngredient {

    @NotNull
    @Schema(description = "재료 ID")
    UUID id;

    @NotNull
    @Schema(description = "수량")
    Integer amount;
}
