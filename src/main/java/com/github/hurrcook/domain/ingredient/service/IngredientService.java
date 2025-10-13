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

  
    // 재료 차감 목록 불러오기
    @Transactional(readOnly = true)
    public List<IngredientReduceResponse> getUsageIngredient(User user, List<IngredientUseRequest> request) {
        // 유저의 모든 재료 조회
        Map<UUID, Ingredient> allUserIngredients = ingredientRepository.findAllByUser(user)
                .stream()
                .collect(Collectors.toMap(Ingredient::getId, ingredient -> ingredient));

        return request.stream() // 요청으로 받은 재료 리스트
                .map(required -> {
                    // 유저가 가진 재료 중에서 요청 받은 재료를 찾음
                    Ingredient owned = allUserIngredients.get(required.getUserFoodId());

                    if (owned == null)  return null;

                    int currentAmount = owned.getAmount();
                    boolean isSufficient = currentAmount >= required.getUseAmount();

                    return IngredientReduceResponse.from(required, owned, currentAmount, isSufficient);

                })
                .collect(Collectors.toList());

  
    @Transactional
    public void updateIngredient(User user, UUID ingredientId, IngredientUpdateRequest ingredientUpdateRequest) {

        Ingredient ingredient = ingredientRepository.findByIdAndUser(ingredientId, user)
                .orElseThrow(IngredientExceptions.INGREDIENT_NOT_FOUND::toApiException);

        ingredient.setName(ingredientUpdateRequest.getName());
        ingredient.setAmount(ingredientUpdateRequest.getAmount());
        ingredient.setExpireDate(ingredientUpdateRequest.getExpireDate());

        ingredientRepository.save(ingredient);
    }
    
      
    @Transactional
    public void deleteIngredient(User user, UUID ingredientId) {
        Ingredient ingredient = ingredientRepository.findByIdAndUser(ingredientId, user)
                .orElseThrow(IngredientExceptions.INGREDIENT_NOT_FOUND::toApiException);

        ingredientRepository.delete(ingredient);
    }
}
