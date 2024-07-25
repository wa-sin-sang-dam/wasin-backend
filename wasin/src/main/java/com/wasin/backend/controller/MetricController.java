package com.wasin.backend.controller;

import com.wasin.backend.service.MetricService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/metric")
public class MetricController {

    private final MetricService metricService;

    @GetMapping
    public ResponseEntity<String> getGrafanaDashboard() {
        String response = metricService.getGrafanaDashboard();
        return ResponseEntity.ok(response);
    }
}
