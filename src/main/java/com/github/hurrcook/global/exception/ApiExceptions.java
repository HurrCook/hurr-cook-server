package com.github.hurrcook.global.exception;

public interface ApiExceptions {

    String getMessage();

    default ApiException toApiException(){

        return new ApiException(this);
    }
}
