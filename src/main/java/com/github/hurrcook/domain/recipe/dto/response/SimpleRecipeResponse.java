package com.github.hurrcook.domain.recipe.dto.response;

import com.github.hurrcook.domain.recipe.entity.Recipe;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor(staticName = "of")
public class SimpleRecipeResponse {

    @Schema(description = "레시피 제목")
    String title;

    @Schema(description = "레시피 ID")
    UUID id;

    @Schema(description = "레시피 사진")
    String image;

    public static SimpleRecipeResponse from(Recipe recipe){
        return SimpleRecipeResponse.of(recipe.getTitle(), recipe.getId(), recipe.getImage());
    }
}
