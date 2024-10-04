package com.wasin.wasin.controller;

import com.wasin.wasin._core.security.CustomUserDetails;
import com.wasin.wasin._core.util.ApiUtils;
import com.wasin.wasin.domain.dto.MonitorResponse;
import com.wasin.wasin.service.MonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/monitoring")
public class MonitoringController {

    private final MonitoringService monitoringService;

    // 단일 모니터 라우터링
    @GetMapping()
    public ResponseEntity<?> monitorById(@RequestParam(value = "routerId") Long routerId,
                                         @RequestParam(value = "metricId", required = false) Long metricId,
                                         @RequestParam(value = "time", required = false) Long time,
                                         @AuthenticationPrincipal CustomUserDetails userDetails) {
        MonitorResponse.FindById response = monitoringService.monitorById(routerId, metricId, time, userDetails.getUser());
        return ResponseEntity.ok().body(ApiUtils.success(response));
    }

    // 관리자가 속한 라우터 그룹의 모든 라우터 조회
    @GetMapping("/routers")
    public ResponseEntity<?> findAllRouter(@AuthenticationPrincipal CustomUserDetails userDetails) {
        MonitorResponse.FindAllRouter response = monitoringService.findAllRouter(userDetails.getUser());
        return ResponseEntity.ok().body(ApiUtils.success(response));
    }

}
