package com.github.hurrcook.domain.recipe.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ModifyIngredient {

    @NotNull
    UUID id;

    @NotNull
    Integer amount;
}
