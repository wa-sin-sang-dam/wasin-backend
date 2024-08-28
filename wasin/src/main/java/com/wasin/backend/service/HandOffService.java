package com.wasin.backend.service;

import com.wasin.backend.domain.dto.HandOffRequest;
import com.wasin.backend.domain.dto.HandOffResponse;
import com.wasin.backend.domain.entity.User;

public interface HandOffService {
    HandOffResponse.UserRouter findAll(User user, HandOffRequest.UserRouterDTO requestDTO);

    HandOffResponse.BestRouter findBestRouter(User user, HandOffRequest.UserRouterDTO requestDTO);

    void changeModeAuto(User user);

    void changeModeManual(User user);
}
