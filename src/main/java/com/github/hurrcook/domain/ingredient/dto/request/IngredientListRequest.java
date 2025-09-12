package com.github.hurrcook.domain.ingredient.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class IngredientListRequest {
    @NotNull
    @Schema(description = "재료 등록 리스트")
    private List<IngredientRequest> ingredients;
}
