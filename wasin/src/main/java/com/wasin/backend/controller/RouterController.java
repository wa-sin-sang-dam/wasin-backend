package com.wasin.backend.controller;

import com.wasin.backend._core.security.CustomUserDetails;
import com.wasin.backend._core.util.ApiUtils;
import com.wasin.backend.domain.dto.RouterRequest;
import com.wasin.backend.domain.dto.RouterResponse;
import com.wasin.backend.service.RouterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/router")
public class RouterController {

    private final RouterService routerService;

    @GetMapping("")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal CustomUserDetails userDetails) {
        RouterResponse.FindALl response = routerService.findAll(userDetails.getUser());
        return ResponseEntity.ok().body(ApiUtils.success(response));
    }

    @GetMapping("/{router_id}")
    public ResponseEntity<?> findByRouterId(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @PathVariable(name="router_id") Long router_id) {
        RouterResponse.FindByRouterId response = routerService.findByRouterId(userDetails.getUser(), router_id);
        return ResponseEntity.ok().body(ApiUtils.success(response));
    }

    @PostMapping("")
    public ResponseEntity<?> findByRouterId(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @RequestBody @Valid RouterRequest.CreateDTO requestDTO) {
        routerService.create(userDetails.getUser(), requestDTO);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @PutMapping("/{router_id}")
    public ResponseEntity<?> update(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @RequestBody @Valid RouterRequest.UpdateDTO requestDTO,
                                    @PathVariable(name="router_id") Long router_id) {
        routerService.update(userDetails.getUser(), requestDTO, router_id);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    @DeleteMapping("/{router_id}")
    public ResponseEntity<?> delete(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @PathVariable(name="router_id") Long router_id) {
        routerService.delete(userDetails.getUser(), router_id);
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }
}
