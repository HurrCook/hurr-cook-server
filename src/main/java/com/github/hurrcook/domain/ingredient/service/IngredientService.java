package com.github.hurrcook.domain.ingredient.service;

import com.github.hurrcook.domain.ingredient.IngredientRepository;
import com.github.hurrcook.domain.ingredient.dto.request.IngredientRequest;
import com.github.hurrcook.domain.ingredient.dto.response.IngredientResponse;
import com.github.hurrcook.domain.ingredient.entity.Ingredient;
import com.github.hurrcook.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Transactional
    public void addIngredient(User user, List<IngredientRequest> ingredientRequests) {

        List<Ingredient> ingredients = new ArrayList<>();

        for (IngredientRequest ingredientRequest : ingredientRequests) {
            ingredients.add(Ingredient.from(user, ingredientRequest));

        }

        ingredientRepository.saveAll(ingredients);
    }

    // 유저가 가진 모든 재료 조회
    @Transactional(readOnly = true)
    public List<IngredientResponse> getIngredients(User user) {
        return ingredientRepository.findIngredientByUser(user)
                .stream()
                .map(IngredientResponse::from)
                .toList();
    }
}