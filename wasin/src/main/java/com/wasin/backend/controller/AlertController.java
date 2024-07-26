package com.wasin.backend.controller;

import com.wasin.backend._core.util.ApiUtils;
import com.wasin.backend.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alert")
public class AlertController {

    private final AlertService alertService;

    @PostMapping("/notification")
    public ResponseEntity<?> receive() {
        alertService.receiveAlert();
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
