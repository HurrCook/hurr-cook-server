package com.github.hurrcook.domain.recipe.repository;

import com.github.hurrcook.domain.recipe.entity.Recipe;
import com.github.hurrcook.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RecipeRepository extends JpaRepository<Recipe, UUID> {
    List<Recipe> findAllByUser(User user);
}
