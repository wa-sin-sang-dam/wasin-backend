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
    INVALID_ARGUMENT("잘못된 argument가 입력되었습니다.", HttpStatus.BAD_REQUEST),
    UNEXPECTED_EXCEPTION("예상치 못한 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    MESSAGE_NOT_READABLE("메시지를 읽을 수 없습니다.", HttpStatus.BAD_REQUEST),
    COMPANY_OPEN_API_FAIL("Open API를 이용하여 회사 목록을 불러오는데 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    AWS_IMAGE_FAIL("aws에 이미지를 올리는 것을 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_PATH("잘못된 url path입니다.", HttpStatus.NOT_FOUND),
    MAX_FILE_SIZE("이미지 크기가 너무 큽니다.", HttpStatus.BAD_REQUEST),
    FILE_READ_FAIL("파일을 읽을 수 없습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    ROUTER_PERMISSION_DENIED("회사가 다르므로 라우터에 대한 접근 권한이 없습니다.", HttpStatus.UNAUTHORIZED),

    // 유저
    USER_NOT_FOUND("서비스를 탈퇴했거나 가입하지 않은 유저의 요청입니다.", HttpStatus.NOT_FOUND),
    USER_EMAIL_EXIST("동일한 이메일이 존재합니다.", HttpStatus.BAD_REQUEST),
    USER_EMAIL_NOT_FOUND("이메일을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),
    USER_PASSWORD_WRONG("패스워드를 잘못 입력하셨습니다.", HttpStatus.BAD_REQUEST),
    USER_LOCK_PASSWORD_WRONG("잠금해제 패스워드를 잘못 입력하셨습니다.", HttpStatus.BAD_REQUEST),
    USER_PASSWORD_NOT_SAME("패스워드1과 패스워드2는 동일해야 합니다.", HttpStatus.BAD_REQUEST),
    USER_ROLE_WRONG("잘못된 역할입니다.", HttpStatus.BAD_REQUEST),
    USER_INACTIVE("아직 활성화되지 않은 유저입니다.", HttpStatus.BAD_REQUEST),

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
    ROLE_NOT_ADMIN("사용자는 일반 관리자가 아닙니다.", HttpStatus.BAD_REQUEST),
    STATUS_NOT_STANDBY("대기 상태가 아닙니다.", HttpStatus.BAD_REQUEST),

    // 회사
    COMPANY_ALREADY_EXIST("이미 존재하는 회사입니다.", HttpStatus.BAD_REQUEST),
    COMPANY_NOT_FOUND("회사가 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    COMPANY_USER_NOT_FOUND("해당 관리자는 회사에 속해있지 않습니다.", HttpStatus.NOT_FOUND),
    NOT_SAME_COMPANY("일반 관리자와 최종 관리자의 회사가 다릅니다.", HttpStatus.BAD_REQUEST),
    WRONG_WASIN_SERVICE_KEY("와신 상담의 서비스키가 잘못되었습니다.", HttpStatus.BAD_REQUEST),
    COMPANY_IMAGE_NOT_FOUND("회사 이미지를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 라우터
    ROUTER_NOT_EXIST("해당 MAC 주소를 가진 라우터가 프로메테우스 서버에 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ROUTER_NOT_EXIST_IN_DB("라우터가 데이터베이스에 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    ROUTER_EXIST_IN_DB("라우터가 이미 데이터베이스에 존재합니다.", HttpStatus.BAD_REQUEST),
    ROUTER_NOT_EXIST_IN_PROMETHEUS("라우터가 프로메테우스 서버에 존재하지 않습니다.", HttpStatus.NOT_FOUND),
    PROMETHUS_SERVER_FAIL("프로메테우스 서버의 데이터를 불러오는데 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    GRAFANA_SERVER_FAIL("그라파나 서버의 데이터를 불러오는데 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    ;


    @Getter
    private final String message;

    @Getter
    private final HttpStatus status;

}
