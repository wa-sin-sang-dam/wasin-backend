package com.wasin.backend.service;

import com.wasin.backend.domain.dto.UserResponse;
import com.wasin.backend.domain.entity.User;

public interface TokenService {

    UserResponse.Token save(User user);

    void deleteByAccessToken(String accessToken);

    User getUserByRefreshToken(String refreshToken);

    UserResponse.Token reissue(User user, String refreshToken);
}
