package com.github.hurrcook.domain.recipe.dto.response;

import com.github.hurrcook.domain.recipe.entity.Recipe;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor(staticName = "of")
public class SimpleRecipeResponse {

    String title;

    UUID id;

    String image;

    public static SimpleRecipeResponse from(Recipe recipe){
        return SimpleRecipeResponse.of(recipe.getTitle(), recipe.getId(), recipe.getImage());
    }
}
