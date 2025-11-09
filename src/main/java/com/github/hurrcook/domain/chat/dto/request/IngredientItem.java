package com.github.hurrcook.domain.chat.dto.request;

import com.github.hurrcook.domain.ingredient.entity.Ingredient;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@Builder
public class IngredientItem {

    private String ingredient;

    private double quantity;

    private String unit;

    private String expiration_date;

    public static IngredientItem from(Ingredient ingredient) {

        return IngredientItem.builder()
                .ingredient(ingredient.getName())
                .quantity(ingredient.getAmount())
                .unit(ingredient.getUnit().name())
                .expiration_date(ingredient.getExpireDate().format(DateTimeFormatter.ISO_LOCAL_DATE))
                .build();
    }
}
