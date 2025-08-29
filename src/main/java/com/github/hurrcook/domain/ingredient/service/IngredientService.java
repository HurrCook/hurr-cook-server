package com.github.hurrcook.domain.ingredient.service;

import com.github.hurrcook.domain.ingredient.IngredientRepository;
import com.github.hurrcook.domain.ingredient.dto.request.IngredientRequest;
import com.github.hurrcook.domain.ingredient.entity.Ingredient;
import com.github.hurrcook.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public void addIngredient(User user, IngredientRequest ingredientRequest) {

        Ingredient ingredient = Ingredient.from(user, ingredientRequest);

        ingredientRepository.save(ingredient);
    }
}
