package com.wasin.backend.service.impl;

import com.jcraft.jsch.*;
import com.wasin.backend.service.AlertService;
import org.springframework.stereotype.Service;

@Service
public class AlertServiceImpl implements AlertService {

    @Override
    public void receiveAlert() {
        String host = "219.241.29.68";
        int    port = 1222;
        String username = "root";
        String password = "pica1104";

        Session session = null;
        Channel channel = null;

        try {
            // 01. JSch 객체를 생성한다.
            JSch jsch = new JSch();
            session = jsch.getSession(username, host, port);

            // 2. 패스워드를 설정한다.
            session.setPassword(password);

            // 3. 세션과 관련된 정보를 설정한다.
            java.util.Properties config = new java.util.Properties();
            // 3-1. 호스트 정보를 검사하지 않는다.
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);

            // 4. 접속한다.
            session.connect();

            // 5. sftp 채널을 연다.
            channel = session.openChannel("exec");

            // 6. 채널을 SSH용 채널 객체로 캐스팅한다
            ChannelExec channelExec = (ChannelExec) channel;

            channelExec.setCommand("touch new.txt");
            channelExec.connect();

        } catch (JSchException e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
    }
}
