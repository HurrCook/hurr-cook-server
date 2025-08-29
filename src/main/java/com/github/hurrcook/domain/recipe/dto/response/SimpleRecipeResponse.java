package com.github.hurrcook.domain.recipe.dto.response;

import com.github.hurrcook.domain.recipe.entity.Recipe;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor(staticName = "of")
public class SimpleRecipeResponse {
    @NotNull
    String title;

    @NotNull
    UUID id;

    @Nullable
    String image;

    public static SimpleRecipeResponse from(Recipe recipe){
        return SimpleRecipeResponse.of(recipe.getTitle(), recipe.getId(), recipe.getImage());
    }
}
