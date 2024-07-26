package com.wasin.backend.service.impl;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.BadRequestException;
import com.wasin.backend._core.exception.error.NotFoundException;
import com.wasin.backend.domain.dto.UserRequest;
import com.wasin.backend.domain.dto.UserResponse;
import com.wasin.backend.domain.entity.User;
import com.wasin.backend.domain.mapper.UserMapper;
import com.wasin.backend.domain.validation.UserValidation;
import com.wasin.backend.repository.UserJPARepository;
import com.wasin.backend.service.TokenService;
import com.wasin.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserValidation userValidation;
    private final UserJPARepository userJPARepository;
    private final TokenService tokenService;

    @Transactional
    public void signup(UserRequest.SignUpDTO requestDTO) {
        userValidation.checkSignup(requestDTO);
        userJPARepository.save(userMapper.signupToUser(requestDTO));
    }

    @Transactional
    public UserResponse.Token login(UserRequest.LoginDTO requestDTO) {
        User user = findByEmail(requestDTO.email());
        userValidation.checkLogin(user, requestDTO.password());
        return tokenService.save(user);
    }

    @Transactional
    public void withdraw(User user, String accessToken) {
        User u = userJPARepository.findById(user.getId()).orElseThrow(
                () -> new NotFoundException(BaseException.USER_NOT_FOUND)
        );
        userJPARepository.delete(u);
    }

    public void logout(String accessToken) {
        tokenService.deleteByAccessToken(accessToken);
    }

    public UserResponse.Token reissue(UserRequest.ReissueDTO requestDTO) {
        String refreshToken = requestDTO.refreshToken();
        User user = tokenService.getUserByRefreshToken(refreshToken);
        userValidation.checkReissue(refreshToken);
        return tokenService.reissue(user, refreshToken);
    }


    private User findByEmail(String email) {
        return userJPARepository.findByEmail(email).orElseThrow(
                () -> new BadRequestException(BaseException.USER_NOT_FOUND)
        );
    }

}
