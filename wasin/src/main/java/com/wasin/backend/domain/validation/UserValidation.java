package com.wasin.backend.domain.validation;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.BadRequestException;
import com.wasin.backend.domain.dto.UserRequest;
import com.wasin.backend.domain.entity.User;
import com.wasin.backend.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final PasswordEncoder passwordEncoder;
    private final UserJPARepository userJPARepository;

    public void checkForSignup(UserRequest.SignUpDTO requestDTO) {
        checkEmailAlreadyExist(requestDTO.email());
        checkPasswordSame(requestDTO.password(), requestDTO.password2());
    }

    public void checkPasswordCorrect(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException(BaseException.USER_PASSWORD_WRONG);
        }
    }

    public void checkEmailAlreadyExist(String email) {
        if (isEmailExist(email)) {
            throw new BadRequestException(BaseException.USER_EMAIL_EXIST);
        }
    }

    private void checkPasswordSame(String password, String password2) {
        if (!Objects.equals(password, password2)) {
            throw new BadRequestException(BaseException.USER_PASSWORD_NOT_SAME);
        }
    }

    private Boolean isEmailExist(String email) {
        return userJPARepository.findByEmail(email).isPresent();
    }

    public void checkLockPasswordCorrect(User user, String password) {
        if (!passwordEncoder.matches(password, user.getLockPassword())) {
            throw new BadRequestException(BaseException.USER_LOCK_PASSWORD_WRONG);
        }
    }

}
