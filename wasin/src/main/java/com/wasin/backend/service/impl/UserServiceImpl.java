package com.wasin.backend.service.impl;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.BadRequestException;
import com.wasin.backend._core.exception.error.NotFoundException;
import com.wasin.backend.domain.dto.UserRequest;
import com.wasin.backend.domain.entity.User;
import com.wasin.backend.domain.mapper.UserMapper;
import com.wasin.backend.domain.validation.UserValidation;
import com.wasin.backend.repository.UserJPARepository;
import com.wasin.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserValidation userValidation;
    private final UserJPARepository userJPARepository;

    @Transactional
    public void signup(UserRequest.SignUpDTO requestDTO) {
        userValidation.checkForSignup(requestDTO);
        userJPARepository.save(userMapper.signupToUser(requestDTO));
    }

    @Transactional
    public void withdraw(User user) {
        User u = userJPARepository.findById(user.getId()).orElseThrow(
                () -> new NotFoundException(BaseException.USER_NOT_FOUND)
        );
        userValidation.checkIsNotSuperAdmin(u);
        userJPARepository.delete(u);
    }

    public User login(UserRequest.LoginDTO requestDTO) {
        User user = findByEmail(requestDTO.email());
        userValidation.checkPasswordCorrect(user, requestDTO.password());
        return user;
    }

    @Transactional
    public void setLockPassword(UserRequest.LockDTO requestDTO, User requestUser) {
        User user = findUserById(requestUser.getId());
        String password = passwordEncoder.encode(requestDTO.password());
        user.setLockPassword(password);
    }

    public void checkLockPassword(UserRequest.LockConfirmDTO requestDTO, User requestUser) {
        User user = findUserById(requestUser.getId());
        userValidation.checkLockPasswordCorrect(user, requestDTO.password());
    }

    private User findUserById(Long id) {
        return userJPARepository.findById(id).orElseThrow(
                () -> new NotFoundException(BaseException.USER_NOT_FOUND)
        );
    }

    private User findByEmail(String email) {
        return userJPARepository.findByEmail(email).orElseThrow(
                () -> new BadRequestException(BaseException.USER_NOT_FOUND)
        );
    }

}
