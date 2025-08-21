package com.github.hurrcook.global.exception;

import com.github.hurrcook.global.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ApiResponse<Void> handleApiException(ApiException e){

        return ApiResponse.error(e);
    }
}
