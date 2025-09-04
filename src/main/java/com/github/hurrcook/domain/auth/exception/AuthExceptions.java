package com.github.hurrcook.domain.auth.exception;

import com.github.hurrcook.global.exception.ApiExceptions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthExceptions implements ApiExceptions {

    ACCESS_TOKEN_EXPIRED("엑세스 토큰이 만료되었습니다"),
    INVALID_TOKEN("유효하지 않은 토큰입니다."),
    AUTHENTICATION_FAILED("인증에 실패했습니다."),
    KAKAO_TOKEN_ISSUE_FAILED("카카오 토큰 발급에 실패했습니다."),
    KAKAO_USERINFO_REQUEST_FAILED("카카오 사용자 정보 조회에 실패했습니다."),
    INVALID_USER_REQUEST("잘못된 사용자 요청입니다.");


    private final String message;

}
