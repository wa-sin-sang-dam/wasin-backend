package com.wasin.backend.controller;

import com.wasin.backend._core.security.CustomUserDetails;
import com.wasin.backend._core.security.JWTProvider;
import com.wasin.backend._core.util.ApiUtils;
import com.wasin.backend.domain.dto.UserRequest;
import com.wasin.backend.domain.dto.UserResponse;
import com.wasin.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final JWTProvider jwtProvider;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserRequest.SignUpDTO requestDTO) {
        userService.signup(requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO) {
        UserResponse.Token token = userService.login(requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(token));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String accessToken = request.getHeader(jwtProvider.AUTHORIZATION_HEADER);
        userService.logout(accessToken);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // 토큰 리프레시
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody @Valid UserRequest.ReissueDTO requestDTO) {
        UserResponse.Token token = userService.reissue(requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(token));
    }

    // 회원탈퇴
    @DeleteMapping("/withdraw")
    public ResponseEntity<?> withdraw(HttpServletRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        String accessToken = request.getHeader(jwtProvider.AUTHORIZATION_HEADER);
        userService.withdraw(userDetails.getUser(), accessToken);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // TODO: 이메일 확인

}
