package com.wasin.backend.domain.validation;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.NotFoundException;
import com.wasin.backend.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenValidation {

    private final TokenRepository tokenRepository;

    public void checkRefreshTokenExist(String refreshToken) {
        if (!tokenRepository.existsById(refreshToken)) {
            throw new NotFoundException(BaseException.REFRESH_TOKEN_NOT_FOUND);
        }
    }
}
