package com.wasin.backend.service;

import com.wasin.backend.domain.dto.UserRequest;
import com.wasin.backend.domain.dto.UserResponse;
import com.wasin.backend.domain.entity.User;

public interface TokenService {

    UserResponse.Token save(User user);

    void delete(String accessToken);

    UserResponse.Token reissue(UserRequest.ReissueDTO requestDTO);
}
