package com.wasin.backend.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/notifications")
public class AlertController {

    @PostMapping
    public ResponseEntity<String> receive() {
        log.debug("success");
        return ResponseEntity.ok("success");
    }
}
