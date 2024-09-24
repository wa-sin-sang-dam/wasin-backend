package com.wasin.wasin._core.util;

import com.jcraft.jsch.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SshConnectionUtil {

    @Value("${kwon.username}")
    private String username;

    @Value("${kwon.password}")
    private String password;

    private int port = 1222;
    private Session session = null;
    private Channel channel = null;

    public void connect(String command, String host) {
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

            channelExec.setCommand(command);
            channelExec.connect();

        } catch (JSchException e) {
            log.debug(e.getMessage());
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
