package com.wasin.backend._core.exception.error;


import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.CustomException;
import com.wasin.backend._core.util.ApiUtils;
import org.springframework.http.HttpStatus;


public class UnauthorizedException extends CustomException {
    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(BaseException exception){
        super(exception.getMessage());
    }

    @Override
    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(getMessage(), status());
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.UNAUTHORIZED;
    }
}
