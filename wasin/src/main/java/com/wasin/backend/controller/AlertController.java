package com.wasin.backend.controller;

import com.wasin.backend.domain.alert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notifications")
public class AlertController {

    private final AlertService alertService;

    @PostMapping
    public ResponseEntity<String> receive() {
        alertService.receiveAlert();
        return ResponseEntity.ok("success");
    }

}
