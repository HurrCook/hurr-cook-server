package com.github.hurrcook.domain.chat.dto.request;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipeRequest {

    private String user_query;

    @Builder.Default
    private String personal_preferences = null;

    private List<IngredientItem> ingredients;

    private List<String> tools;
}
