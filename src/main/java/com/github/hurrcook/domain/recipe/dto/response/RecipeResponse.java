package com.github.hurrcook.domain.recipe.dto.response;

import com.github.hurrcook.domain.recipe.entity.Recipe;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor(staticName = "of")
@Schema(description = "레시피 응답")
public class RecipeResponse {

    @Schema(description = "레시피 제목")
    String title;

    @Schema(description = "재료 리스트")
    List<RecipeIngredientResponse> ingredients;

    @Schema(description = "요리법")
    List<String> steps;

    @Schema(description = "이미지 url")
    String image;

    @Schema(description = "레시피 소요 시간")
    String time;

    public static RecipeResponse from(Recipe recipe) {
        return RecipeResponse.of(
                recipe.getTitle(),
                recipe.getIngredients().stream().map(RecipeIngredientResponse::from).toList(),
                recipe.getSteps(),
                recipe.getImage(),
                recipe.getTime()
        );
    }
}
