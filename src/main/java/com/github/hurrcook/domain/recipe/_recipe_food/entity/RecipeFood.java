package com.github.hurrcook.domain.recipe._recipe_food.entity;

import com.github.hurrcook.domain.recipe.dto.request.RecipeIngredientRequest;
import com.github.hurrcook.domain.recipe.entity.Recipe;
import com.github.hurrcook.global.common.Unit;
import com.github.hurrcook.global.infra.BaseSchema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode(callSuper = true)
public class RecipeFood extends BaseSchema {

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    Integer amount;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Unit unit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id",nullable = false)
    Recipe recipe;

    public static RecipeFood from(RecipeIngredientRequest request,Recipe recipe) {
        return RecipeFood.of(request.getName(),request.getAmount(),request.getUnit(),recipe);
    }
}
