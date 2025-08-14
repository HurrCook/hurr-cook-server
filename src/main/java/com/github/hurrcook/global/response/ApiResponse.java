package com.github.hurrcook.global.response;

import com.github.hurrcook.global.exception.ApiException;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ApiResponse<T>{

    private final boolean success;
    private final String message;
    private final T data;

    public static ApiResponse<Void> ok(){
        return ok(null);
    }

    public static <T> ApiResponse<T> ok(T data){
        return new ApiResponse<>(true,null, data);
    }

    public static ApiResponse<Void> error(ApiException e){
        return error(e.getMessage());
    }

    public static ApiResponse<Void> error(String message){
        return new ApiResponse<>(false,message,null);
    }
}
