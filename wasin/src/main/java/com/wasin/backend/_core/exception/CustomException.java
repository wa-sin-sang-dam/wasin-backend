package com.wasin.backend._core.exception;

import com.wasin.backend._core.util.ApiUtils;
import org.springframework.http.HttpStatus;

public abstract class CustomException extends RuntimeException {

    public CustomException(String message) {
        super(message);
    }

    public abstract ApiUtils.ApiResult<?> body();

    public abstract HttpStatus status();

}