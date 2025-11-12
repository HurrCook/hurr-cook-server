package com.github.hurrcook.domain.recipe.entity;

import com.github.hurrcook.domain.recipe._recipe_food.entity.RecipeFood;
import com.github.hurrcook.domain.recipe.dto.request.RecipeIngredientRequest;
import com.github.hurrcook.domain.user.entity.User;
import com.github.hurrcook.global.infra.BaseSchema;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe extends BaseSchema {

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    User user;

    @Column(nullable = false)
    String title;

    @Column(nullable = false)
    String time;

    @Column(columnDefinition = "MEDIUMTEXT")
    String image;

    @ElementCollection
    @Builder.Default
    List<String> steps = new ArrayList<>();

    @OneToMany(mappedBy = "recipe",  cascade = CascadeType.ALL, orphanRemoval = true,fetch = FetchType.LAZY)
    @Builder.Default
    List<RecipeFood> ingredients = new ArrayList<>();

    public void addIngredient(RecipeIngredientRequest request) {
        RecipeFood recipeFood = RecipeFood.from(request,this);
        this.ingredients.add(recipeFood);
    }

    public void addIngredients(List<RecipeIngredientRequest> requests) {
        requests.forEach(this::addIngredient);
    }

}
