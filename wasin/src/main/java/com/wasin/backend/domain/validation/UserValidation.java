package com.wasin.backend.domain.validation;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.BadRequestException;
import com.wasin.backend._core.exception.error.NotFoundException;
import com.wasin.backend.domain.dto.UserRequest;
import com.wasin.backend.domain.entity.User;
import com.wasin.backend.repository.TokenRepository;
import com.wasin.backend.repository.UserJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class UserValidation {

    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final UserJPARepository userJPARepository;

    public void checkSignup(UserRequest.SignUpDTO requestDTO) {
        checkEmailAlreadyExist(requestDTO.email());
        checkPasswordSame(requestDTO.password(), requestDTO.password2());
    }

    public void checkLogin(User user, String password) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadRequestException(BaseException.USER_PASSWORD_WRONG);
        }
    }

    public void checkReissue(String refreshToken) {
        if (!tokenRepository.existsById(refreshToken)) {
            throw new NotFoundException(BaseException.REFRESH_TOKEN_NOT_FOUND);
        }
    }

    private void checkPasswordSame(String password, String password2) {
        if (!Objects.equals(password, password2)) {
            throw new BadRequestException(BaseException.USER_PASSWORD_NOT_SAME);
        }
    }

    private void checkEmailAlreadyExist(String email) {
        if (isEmailExist(email)) {
            throw new BadRequestException(BaseException.USER_EMAIL_EXIST);
        }
    }

    private Boolean isEmailExist(String email) {
        return userJPARepository.findByEmail(email).isPresent();
    }

}
