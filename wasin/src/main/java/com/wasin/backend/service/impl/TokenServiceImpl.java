package com.wasin.backend.service.impl;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.BadRequestException;
import com.wasin.backend._core.exception.error.NotFoundException;
import com.wasin.backend._core.security.JWTProvider;
import com.wasin.backend.domain.dto.UserResponse;
import com.wasin.backend.domain.entity.Token;
import com.wasin.backend.domain.entity.User;
import com.wasin.backend.domain.entity.enums.Role;
import com.wasin.backend.domain.mapper.TokenMapper;
import com.wasin.backend.repository.TokenRepository;
import com.wasin.backend.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class TokenServiceImpl implements TokenService {

    private final JWTProvider jwtProvider;
    private final TokenRepository tokenRepository;
    private final TokenMapper tokenMapper;

    public UserResponse.Token save(User user) {
        return saveToken(user);
    }

    public UserResponse.Token reissue(User user, String refreshToken) {
        tokenRepository.deleteById(refreshToken);
        return saveToken(user);
    }

    public void deleteByAccessToken(String accessToken) {
        Token token = tokenRepository.findByAccessToken(accessToken).orElseThrow(
                () -> new NotFoundException(BaseException.ACCESS_TOKEN_NOT_FOUND)
        );
        tokenRepository.delete(token);
    }

    public User getUserByRefreshToken(String refreshToken) {
        try{
            DecodedJWT decodedJWT = jwtProvider.verifyRefreshToken(refreshToken);
            Long id = decodedJWT.getClaim("id").asLong();
            String role = decodedJWT.getClaim("role").asString();

            return User.builder().id(id).role(Role.valueOfRole(role)).build();
        } catch (SignatureVerificationException | JWTDecodeException e) {
            throw new BadRequestException(BaseException.REFRESH_TOKEN_INVALID);
        } catch (TokenExpiredException tee) {
            throw new BadRequestException(BaseException.REFRESH_TOKEN_EXPIRED);
        }
    }

    private UserResponse.Token saveToken(User user) {
        String accessToken = jwtProvider.createAccessToken(user);
        String refreshToken = jwtProvider.createRefreshToken(user);
        Token token = tokenMapper.stringToRefreshToken(accessToken, refreshToken, user);
        tokenRepository.save(token);

        return new UserResponse.Token(accessToken, refreshToken);
    }


}
