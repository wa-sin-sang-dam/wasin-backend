package com.wasin.backend.domain.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "refreshToken", timeToLive = 14440)
public class Token {

    @Id
    private String refreshToken;

    @Indexed
    private String accessToken;

    private Long userId;

    private String email;

    @Builder
    public Token(String refreshToken, String accessToken, Long userId, String email) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
        this.userId = userId;
        this.email = email;
    }
}
