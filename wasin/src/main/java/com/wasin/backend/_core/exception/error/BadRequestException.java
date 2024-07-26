package com.wasin.backend._core.exception.error;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.CustomException;
import com.wasin.backend._core.util.ApiUtils;
import org.springframework.http.HttpStatus;


// 유효성 검사 실패, 잘못된 파라메터 요청 400
public class BadRequestException extends CustomException {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(BaseException exception){
        super(exception.getMessage());
    }

    @Override
    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(getMessage(), status());
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.BAD_REQUEST;
    }

}
