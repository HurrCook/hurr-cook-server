package com.github.hurrcook.domain.chat.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecipeRequest {

    @JsonProperty("user_query")
    private String user_query;

    @JsonProperty("personal_preferences")
    @Builder.Default
    private String personal_preferences = null;

    private List<IngredientItem> ingredients;

    private List<String> tools;
}
