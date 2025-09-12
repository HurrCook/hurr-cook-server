package com.github.hurrcook.domain.ingredient.repository;

import com.github.hurrcook.domain.ingredient.entity.Ingredient;
import com.github.hurrcook.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
    
    List<Ingredient> findAllByUser(User user);
    Optional<Ingredient> findByIdAndUser(UUID ingredientId, User user);
    List<Ingredient> findByUserAndIdIn(User user, List<UUID> ingredientIds);
}
