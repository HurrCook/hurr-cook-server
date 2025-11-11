package com.github.hurrcook.domain.ingredient.service;

import com.github.hurrcook.domain.ingredient.dto.request.*;
import com.github.hurrcook.domain.ingredient.dto.response.IngredientReduceResponse;
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
import java.util.stream.Stream;

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
    public List<IngredientReduceResponse> getUsageIngredient(User user, List<IngredientUseCheckRequest> requests) {
        return requests.stream()
                .flatMap(required -> {
                    String name = required.getName();
                    int remaining = required.getUseAmount(); // 필요 재료량 상태

                    // 유저가 가진 동일 이름의 재료를 유통기한 오름차순으로 조회
                    List<Ingredient> ownedIngredients = ingredientRepository.findAllByUserAndName(user, name)
                            .stream()
                            .sorted(Comparator.comparing(Ingredient::getExpireDate))
                            .collect(Collectors.toList());

                    // 재료가 하나도 없는 경우
                    if (ownedIngredients.isEmpty()) {
                        return Stream.of(IngredientReduceResponse.builder()
                                .userFoodId(null)
                                .name(name)
                                .imageUrl(null)
                                .useAmount(required.getUseAmount())
                                .currentAmount(0)
                                .expireDate(null)
                                .sufficient(false)
                                .build());
                    }

                    List<IngredientReduceResponse> result = new ArrayList<>();

                    // 유통기한 빠른 재료부터 먼저 사용 가능한지 확인
                    for (Ingredient ingredient : ownedIngredients) {
                        if (remaining <= 0) break; // 더 이상 차감 필요 없음

                        int available = ingredient.getAmount(); // 기존 db 재료가 가진 양
                        int used = Math.min(available, remaining);
                        remaining -= used;

                        result.add(IngredientReduceResponse.builder()
                                .userFoodId(ingredient.getId())
                                .name(ingredient.getName())
                                .imageUrl(ingredient.getImageUrl())
                                .useAmount(used)
                                .currentAmount(ingredient.getAmount())
                                .expireDate(ingredient.getExpireDate())
                                .sufficient(true)
                                .build());
                    }

                    // 남은 양이 있으면 재료 부족 처리
                    if (remaining > 0) {
                        // 마지막 항목에 부족 상태 표시
                        result.add(IngredientReduceResponse.builder()
                                .userFoodId(null)
                                .name(name)
                                .useAmount(remaining)
                                .currentAmount(0)
                                .expireDate(null)
                                .sufficient(false)
                                .build());
                    }

                    return result.stream();
                })
                .toList();
    }

    @Transactional
    public void deleteIngredient(User user, UUID ingredientId) {
        Ingredient ingredient = ingredientRepository.findByIdAndUser(ingredientId, user)
                .orElseThrow(IngredientExceptions.INGREDIENT_NOT_FOUND::toApiException);

        ingredientRepository.delete(ingredient);
    }

    @Transactional
    public void updateIngredient(User user, UUID ingredientId, IngredientUpdateRequest ingredientUpdateRequest) {

        Ingredient ingredient = ingredientRepository.findByIdAndUser(ingredientId, user)
                .orElseThrow(IngredientExceptions.INGREDIENT_NOT_FOUND::toApiException);

        ingredient.setName(ingredientUpdateRequest.getName());
        ingredient.setImageUrl(ingredientUpdateRequest.getImageUrl());
        ingredient.setAmount(ingredientUpdateRequest.getAmount());
        ingredient.setExpireDate(ingredientUpdateRequest.getExpireDate());

        ingredientRepository.save(ingredient);
    }
}
