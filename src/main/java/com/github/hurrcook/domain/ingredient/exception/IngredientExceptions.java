package com.github.hurrcook.domain.ingredient.exception;

import com.github.hurrcook.global.exception.ApiExceptions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum IngredientExceptions implements ApiExceptions {

    INGREDIENT_NOT_FOUND("해당 재료를 찾을 수 없습니다.");

    private final String message;
}