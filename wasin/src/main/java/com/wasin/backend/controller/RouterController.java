package com.wasin.backend.controller;

import com.wasin.backend._core.util.ApiUtils;
import com.wasin.backend.service.RouterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/router")
public class RouterController {

    private final RouterService routerService;

    @GetMapping("/monitoring")
    public ResponseEntity<?> getGrafanaDashboard() {
        String response = routerService.getGrafanaDashboard();
        return ResponseEntity.ok(ApiUtils.success(response));
    }
}
