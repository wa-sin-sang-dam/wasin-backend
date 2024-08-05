package com.wasin.backend.service;

import com.wasin.backend.domain.dto.UserRequest;
import com.wasin.backend.domain.entity.User;

public interface UserService {

    void signup(UserRequest.SignUpDTO requestDTO);

    User login(UserRequest.LoginDTO requestDTO);

    void withdraw(User user);

    void setLockPassword(UserRequest.LockDTO requestDTO, User user);

    void checkLockPassword(UserRequest.LockConfirmDTO requestDTO, User user);

}
