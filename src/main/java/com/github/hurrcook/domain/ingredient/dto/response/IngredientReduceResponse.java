package com.github.hurrcook.domain.ingredient.dto.response;

import com.github.hurrcook.domain.ingredient.dto.request.IngredientUseRequest;
import com.github.hurrcook.domain.ingredient.entity.Ingredient;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter @Builder
public class IngredientReduceResponse {
    @Schema(description = "재료 아이디")
    private UUID userFoodId;

    @Schema(description = "재료명")
    private String name;

    @Schema(description = "레시피에 필요한 재료 수량")
    private int useAmount;

    @Schema(description = "유저가 가진 재료 수량")
    private int currentAmount;

    @Schema(description = "유저가 가진 재료의 유통기한")
    private LocalDateTime expireDate;

    @Schema(description = "재료량의 충분 상태 여부")
    private boolean sufficient;

    public static IngredientReduceResponse from(IngredientUseRequest request, Ingredient ingredient,
                                                int currentAmount, boolean sufficient) {
        return IngredientReduceResponse.builder()
                .userFoodId(request.getUserFoodId())
                .name(ingredient.getName())
                .useAmount(request.getUseAmount())
                .currentAmount(currentAmount)
                .expireDate(ingredient.getExpireDate())
                .sufficient(sufficient)
                .build();
    }

    public static IngredientReduceResponse fromNotOwned(IngredientUseRequest request, String foodName) {
        return IngredientReduceResponse.builder()
                .userFoodId(request.getUserFoodId())
                .name(foodName)
                .useAmount(request.getUseAmount())
                .currentAmount(0)
                .expireDate(null)
                .sufficient(false)
                .build();
    }
}
