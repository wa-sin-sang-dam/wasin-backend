package com.wasin.backend.service;

import com.wasin.backend.domain.dto.UserRequest;
import com.wasin.backend.domain.dto.UserResponse;
import com.wasin.backend.domain.entity.User;

public interface UserService {

    void signup(UserRequest.SignUpDTO requestDTO);

    UserResponse.Token login(UserRequest.LoginDTO requestDTO);

    void withdraw(User user, String accessToken);

    void logout(String accessToken);

    UserResponse.Token reissue(UserRequest.ReissueDTO requestDTO);
}
