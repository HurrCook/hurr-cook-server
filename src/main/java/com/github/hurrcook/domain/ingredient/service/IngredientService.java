package com.github.hurrcook.domain.ingredient.service;

import com.github.hurrcook.domain.ingredient.dto.request.*;
import com.github.hurrcook.domain.ingredient.repository.IngredientRepository;
import com.github.hurrcook.domain.ingredient.dto.response.IngredientResponse;
import com.github.hurrcook.domain.ingredient.entity.Ingredient;
import com.github.hurrcook.domain.ingredient.exception.IngredientExceptions;
import com.github.hurrcook.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Transactional
    public void addIngredient(User user, IngredientListRequest ingredientRequests) {

        List<Ingredient> ingredients = new ArrayList<>();

        for (IngredientRequest ingredientRequest : ingredientRequests.getIngredients()) {
            ingredients.add(Ingredient.from(user, ingredientRequest));

        }

        ingredientRepository.saveAll(ingredients);
    }
    

    @Transactional
    public void reduceIngredient(User user, IngredientUseListRequest ingredientUseListRequest) {


        List<UUID> ingredientIds = ingredientUseListRequest.getIngredientUseList().stream().map(IngredientUseRequest::getUserFoodId).toList();
        Map<UUID,Ingredient> ingredients = ingredientRepository.findByUserAndIdIn(user,ingredientIds).stream().collect(Collectors.toMap(Ingredient::getId, Function.identity()));

        List<Ingredient> toDelete = new ArrayList<>();

        for (IngredientUseRequest request : ingredientUseListRequest.getIngredientUseList()) {
            Ingredient ingredient = ingredients.get(request.getUserFoodId());
            int remain = ingredient.getAmount() - request.getUseAmount();

            if (remain == 0){
                toDelete.add(ingredient);
            } else{
                ingredient.setAmount(remain);
            }
        }

        if (!toDelete.isEmpty()) {
            ingredientRepository.deleteAllInBatch(toDelete);
        }
    }


    // 유저가 가진 모든 재료 조회
    @Transactional(readOnly = true)
    public List<IngredientResponse> getIngredients(User user) {
        return ingredientRepository.findAllByUser(user)
                .stream()
                .map(IngredientResponse::from)
                .toList();
    }

    // 유저가 가진 단일 재료 조회
    @Transactional(readOnly = true)
    public IngredientResponse getIngredient(User user, UUID ingredientId) {
        Ingredient ingredient = ingredientRepository.findByIdAndUser(ingredientId, user)
                .orElseThrow(IngredientExceptions.INGREDIENT_NOT_FOUND::toApiException);

        return IngredientResponse.from(ingredient);
    }

    @Transactional
    public void updateIngredient(User user, UUID ingredientId, IngredientUpdateRequest ingredientUpdateRequest) {

        Ingredient ingredient = ingredientRepository.findByIdAndUser(ingredientId, user)
                .orElseThrow(IngredientExceptions.INGREDIENT_NOT_FOUND::toApiException);

        ingredient.setName(ingredientUpdateRequest.getName());
        ingredient.setAmount(ingredientUpdateRequest.getAmount());
        ingredient.setExpireDate(ingredientUpdateRequest.getExpireDate());

        ingredientRepository.save(ingredient);
    }
}
