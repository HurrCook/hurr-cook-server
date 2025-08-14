package com.github.hurrcook.domain.auth.exception;

import com.github.hurrcook.global.exception.ApiExceptions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthExceptions implements ApiExceptions {

    ACCESS_TOKEN_EXPIRED("엑세스 토큰이 만료되었습니다"),
    AUTHENTICATION_FAILED("인증에 실패했습니다.");


    private final String message;

}
