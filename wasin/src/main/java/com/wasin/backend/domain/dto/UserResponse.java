package com.wasin.backend.domain.dto;

public class UserResponse {

    public record Token (
            String accessToken,
            String refreshToken
    ) {
    }

}
