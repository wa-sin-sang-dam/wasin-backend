package com.wasin.backend.controller;

import com.wasin.backend._core.security.CustomUserDetails;
import com.wasin.backend._core.util.ApiUtils;
import com.wasin.backend.domain.dto.BackOfficeRequest;
import com.wasin.backend.domain.dto.BackOfficeResponse;
import com.wasin.backend.service.BackOfficeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/backoffice")
public class BackOfficeController {

    private final BackOfficeService backOfficeService;

    @PostMapping("/accept")
    public ResponseEntity<?> accept(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @RequestBody @Valid BackOfficeRequest.AcceptDTO requestDTO) {
        backOfficeService.accept(userDetails.getUser(), requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @GetMapping()
    public ResponseEntity<?> findWaitingList(@AuthenticationPrincipal CustomUserDetails userDetails) {
        BackOfficeResponse.WaitingList response = backOfficeService.findWaitingList(userDetails.getUser());
        return ResponseEntity.ok().body(ApiUtils.success(response));
    }

}
