package com.wasin.backend._core.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;


@RequiredArgsConstructor
public enum BaseException {
    // 공통
    AUTH_PERMISSION_DENIED("접근 권한이 없습니다.", HttpStatus.FORBIDDEN),
    DATABASE_ERROR("데이터베이스 에러입니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_UNAUTHORIZED("인증되지 않았습니다.", HttpStatus.UNAUTHORIZED),
    INVALID_METHOD_ARGUMENTS("잘못된 매개변수가 입력되었습니다.", HttpStatus.BAD_REQUEST),
    UNEXPECTED_EXCEPTION("예상치 못한 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    MESSAGE_NOT_READABLE("메시지를 읽을 수 없습니다.", HttpStatus.BAD_REQUEST),

    // 유저
    USER_NOT_FOUND("서비스를 탈퇴했거나 가입하지 않은 유저의 요청입니다.", HttpStatus.NOT_FOUND),
    USER_EMAIL_EXIST("동일한 이메일이 존재합니다.", HttpStatus.BAD_REQUEST),
    USER_EMAIL_NOT_FOUND("이메일을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    USER_PASSWORD_WRONG("패스워드를 잘못 입력하셨습니다.", HttpStatus.BAD_REQUEST),
    USER_PASSWORD_NOT_SAME("패스워드1과 패스워드2는 동일해야 합니다.", HttpStatus.BAD_REQUEST),
    USER_ROLE_WRONG("잘못된 역할입니다.", HttpStatus.BAD_REQUEST),

    // 토큰
    ACCESS_TOKEN_NOT_FOUND("access token이 존재하지 않습니다." , HttpStatus.NOT_FOUND),
    REFRESH_TOKEN_NOT_FOUND("refresh token이 존재하지 않습니다." , HttpStatus.NOT_FOUND),
    REFRESH_TOKEN_INVALID("refresh token이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    ACCESS_TOKEN_EXPIRED("access token이 만료되었습니다.", HttpStatus.BAD_REQUEST),
    REFRESH_TOKEN_EXPIRED("refresh token이 만료되었습니다.", HttpStatus.BAD_REQUEST),

    // 이메일
    EMAIL_CODE_WRONG("잘못된 인증번호입니다.", HttpStatus.BAD_REQUEST),
    EMAIL_NOT_VERIFIED("이메일 인증이 완료되지 않았습니다.", HttpStatus.BAD_REQUEST),
    EMAIL_EXPIRED("만료된 이메일 인증 코드입니다.", HttpStatus.BAD_REQUEST),
    ;

    @Getter
    private final String message;

    @Getter
    private final HttpStatus status;

}
