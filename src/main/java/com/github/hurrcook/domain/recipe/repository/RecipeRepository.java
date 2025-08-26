package com.github.hurrcook.domain.recipe.repository;

import com.github.hurrcook.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Recipe, String> {
}
