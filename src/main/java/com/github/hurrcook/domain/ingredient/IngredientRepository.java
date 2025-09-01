package com.github.hurrcook.domain.ingredient;

import com.github.hurrcook.domain.ingredient.entity.Ingredient;
import com.github.hurrcook.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID> {
    List<Ingredient> findAllByUser(User user);
    Optional<Ingredient> findByIdAndUser(UUID ingredientId, User user);
}