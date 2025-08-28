package com.github.hurrcook.domain.recipe._recipe_food.repository;

import com.github.hurrcook.domain.recipe._recipe_food.entity.RecipeFood;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RecipeFoodRepository extends JpaRepository<RecipeFood, UUID> {
}
