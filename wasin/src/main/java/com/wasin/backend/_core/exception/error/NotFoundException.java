package com.wasin.backend._core.exception.error;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.CustomException;
import com.wasin.backend._core.util.ApiUtils;
import lombok.Getter;
import org.springframework.http.HttpStatus;


// 데이터를 찾을 수 없음 404
@Getter
public class NotFoundException extends CustomException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(BaseException exception){
        super(exception.getMessage());
    }

    @Override
    public ApiUtils.ApiResult<?> body() {
        return ApiUtils.error(getMessage(), status());
    }

    @Override
    public HttpStatus status() {
        return HttpStatus.NOT_FOUND;
    }
}
