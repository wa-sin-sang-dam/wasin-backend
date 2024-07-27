package com.wasin.backend.service;

import com.wasin.backend.domain.dto.UserRequest;
import com.wasin.backend.domain.dto.UserResponse;
import com.wasin.backend.domain.entity.User;

public interface UserService {

    void signup(UserRequest.SignUpDTO requestDTO);

    User login(UserRequest.LoginDTO requestDTO);

    void withdraw(User user, String accessToken);

}
