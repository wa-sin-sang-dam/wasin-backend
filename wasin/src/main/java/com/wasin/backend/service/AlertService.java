package com.wasin.backend.service;

import com.wasin.backend.domain.dto.AlertRequest;

public interface AlertService {
    void receiveAlert(AlertRequest.ProfileChangeDTO request);
}
