package com.github.hurrcook.domain.ingredient.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class IngredientUseListRequest {
    @NotNull
    @Schema(description = "차감 재료 목록")
    List<IngredientUseRequest> ingredientUseList;
}
