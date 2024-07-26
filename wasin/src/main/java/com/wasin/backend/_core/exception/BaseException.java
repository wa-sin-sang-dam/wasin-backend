package com.wasin.backend._core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
public enum BaseException {
    AUTH_PERMISSION_DENIED("접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    DATABASE_ERROR("데이터베이스 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_UNAUTHORIZED("인증되지 않았습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_METHOD_ARGUMENTS("잘못된 매개변수가 입력되었습니다.", HttpStatus.BAD_REQUEST),
    UNEXPECTED_EXCEPTION("예상치 못한 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    MESSAGE_NOT_READABLE("메시지를 읽을 수 없습니다.", HttpStatus.BAD_REQUEST),

    ;

    @Getter
    private final String message;

    @Getter
    private final HttpStatus status;

}
