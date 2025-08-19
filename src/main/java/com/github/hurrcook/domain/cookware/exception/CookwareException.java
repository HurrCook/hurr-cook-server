package com.github.hurrcook.domain.cookware.exception;

import com.github.hurrcook.global.exception.ApiExceptions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CookwareException implements ApiExceptions {

    COOKWARE_NOT_FOUND("요리도구 정보를 찾을 수 없습니다.");

    private final String message;
}
