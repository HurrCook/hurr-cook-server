package com.github.hurrcook.global.exception;

import com.github.hurrcook.global.response.ApiResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ApiResponse<Void> handleApiException(ApiException e){

        return ApiResponse.error(e);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ApiResponse<Void> AccessDeniedException(AccessDeniedException e){
        return ApiResponse.error("권한이 없습니다.");
    }
}
