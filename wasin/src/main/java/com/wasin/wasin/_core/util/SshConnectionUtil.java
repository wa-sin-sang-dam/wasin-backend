package com.wasin.wasin._core.util;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class SshConnectionUtil {

    @Value("${kwon.username}")
    private String username;

    @Value("${kwon.password}")
    private String password;

    private Session session = null;
    private Channel channel = null;

    public String connect(String command, Router router) {
        try {
            String host = router.getInstance().split(":")[0];
            int port = router.getPort();

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

            byte[] buffer = new byte[8192];
            int decodedLength;
            InputStream inputStream = channelExec.getInputStream();
            StringBuilder response = new StringBuilder();
            while ((decodedLength = inputStream.read(buffer, 0, buffer.length)) > 0)
                response.append(new String(buffer, 0, decodedLength));

            log.debug("SSH 연결 성공 {}, {}", response, host);

        } catch (Exception e) {
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
