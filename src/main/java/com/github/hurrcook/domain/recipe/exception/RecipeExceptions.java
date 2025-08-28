package com.github.hurrcook.domain.recipe.exception;

import com.github.hurrcook.global.exception.ApiExceptions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RecipeExceptions implements ApiExceptions {

    RECIPE_ACCESS_DENIED("자신의 레시피만 수정할 수 있습니다.");

    private final String message;
}
