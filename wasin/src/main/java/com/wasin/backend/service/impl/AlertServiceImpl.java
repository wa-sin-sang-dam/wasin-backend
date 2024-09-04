package com.wasin.backend.service.impl;

import com.wasin.backend._core.util.SshConnectionUtil;
import com.wasin.backend.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AlertServiceImpl implements AlertService {

    private final SshConnectionUtil sshConnectionUtil;

    @Override
    public void receiveAlert() {
        sshConnectionUtil.connect("touch new_file.txt");
    }
}
