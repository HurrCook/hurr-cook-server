package com.github.hurrcook.global.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{

    public ApiException(String message){
        super(message);
    }
}
