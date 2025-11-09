package com.github.hurrcook.domain.chat.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RecipeRequest {

    private String user_query;

    private List<IngredientItem> ingredients;
}
