package com.wasin.backend.controller;

import com.wasin.backend._core.security.CustomUserDetails;
import com.wasin.backend._core.security.JWTProvider;
import com.wasin.backend._core.util.ApiUtils;
import com.wasin.backend.domain.dto.UserRequest;
import com.wasin.backend.domain.dto.UserResponse;
import com.wasin.backend.domain.entity.User;
import com.wasin.backend.service.MailService;
import com.wasin.backend.service.TokenService;
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

    private final TokenService tokenService;
    private final UserService userService;
    private final MailService mailService;
    private final JWTProvider jwtProvider;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserRequest.SignUpDTO requestDTO) {
        mailService.checkVerified(requestDTO.email());
        userService.signup(requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserRequest.LoginDTO requestDTO) {
        User user = userService.login(requestDTO);
        UserResponse.Token response = tokenService.save(user);
        return ResponseEntity.ok().body(ApiUtils.success(response));
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        tokenService.delete(getAccessToken(request));
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // 토큰 리프레시
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody @Valid UserRequest.ReissueDTO requestDTO) {
        UserResponse.Token response = tokenService.reissue(requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(response));
    }

    // 회원탈퇴
    @DeleteMapping("/withdraw")
    public ResponseEntity<?> withdraw(HttpServletRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.withdraw(userDetails.getUser(), getAccessToken(request));
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // 이메일 인증 코드 전송
    @PostMapping("/email")
    public ResponseEntity<?> sendEmail(@RequestBody @Valid UserRequest.EmailDTO requestDTO) {
        mailService.sendMail(requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // 이메일 인증 확인
    @PostMapping("/email/check")
    public ResponseEntity<?> checkEmailCode(@RequestBody @Valid UserRequest.EmailCheckDTO requestDTO) {
        mailService.checkMailCode(requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    private String getAccessToken(HttpServletRequest request) {
        return request.getHeader(jwtProvider.AUTHORIZATION_HEADER);
    }
}
