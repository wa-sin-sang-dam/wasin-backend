package com.wasin.backend.service.impl;

import com.wasin.backend._core.util.SshConnectionUtil;
import com.wasin.backend.domain.dto.AlertRequest;
import com.wasin.backend.service.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlertServiceImpl implements AlertService {

    private final SshConnectionUtil sshConnectionUtil;

    public void receiveAlert(AlertRequest.ProfileChangeDTO request) {
        String command = "cd ./test_excute; ./" + request.alertname();
        String instance = request.instance().split(":")[0];
        sshConnectionUtil.connect(command, instance);
    }
}
