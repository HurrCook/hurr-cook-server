package com.github.hurrcook.domain.ingredient.service;

import com.github.hurrcook.domain.ingredient.IngredientRepository;
import com.github.hurrcook.domain.ingredient.dto.request.IngredientReduceRequest;
import com.github.hurrcook.domain.ingredient.dto.request.IngredientRequest;
import com.github.hurrcook.domain.ingredient.entity.Ingredient;
import com.github.hurrcook.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Transactional
    public void reduceIngredient(User user, List<IngredientReduceRequest> ingredientReduceRequests) {

        Map<UUID, Integer> reduceMap = new LinkedHashMap<>();

        for (IngredientReduceRequest r : ingredientReduceRequests) {
            reduceMap.merge(r.getUserFoodId(), r.getUseAmount(), Integer::sum);
        }

        List<Ingredient> ingredients = ingredientRepository.findByUserAndIdIn(user, reduceMap.keySet());

        List<Ingredient> toDelete = new ArrayList<>();

        for (Ingredient ing : ingredients) {
            Integer deduct = reduceMap.remove(ing.getId());
            if (deduct == null) continue;

            int after = ing.getAmount() - deduct;
            if (after <= 0) toDelete.add(ing);
            else ing.setAmount(after);
        }

        if (!toDelete.isEmpty()) {
            ingredientRepository.deleteAllInBatch(toDelete);
        }
    }
}
