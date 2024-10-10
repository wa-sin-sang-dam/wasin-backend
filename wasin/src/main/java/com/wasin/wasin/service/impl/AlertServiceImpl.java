package com.wasin.wasin.service.impl;

import com.wasin.wasin._core.exception.BaseException;
import com.wasin.wasin._core.exception.error.NotFoundException;
import com.wasin.wasin._core.util.SendFirebaseMessage;
import com.wasin.wasin._core.util.SshConnectionUtil;
import com.wasin.wasin.domain.dto.AlertRequest;
import com.wasin.wasin.domain.entity.Company;
import com.wasin.wasin.domain.entity.Profile;
import com.wasin.wasin.domain.entity.Router;
import com.wasin.wasin.domain.entity.User;
import com.wasin.wasin.repository.ProfileJPARepository;
import com.wasin.wasin.repository.RouterJPARepository;
import com.wasin.wasin.repository.UserJPARepository;
import com.wasin.wasin.service.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
@Service
public class AlertServiceImpl implements AlertService {

    private final SshConnectionUtil sshConnectionUtil;
    private final SendFirebaseMessage sendFirebaseMessage;

    private final RouterJPARepository routerJPARepository;
    private final ProfileJPARepository profileJPARepository;
    private final UserJPARepository userJPARepository;

    private final String HEALTH_CHECK = "Health Check";

    @Transactional
    public void receiveAlert(AlertRequest.ProfileChangeDTO request) {
        request.alerts().forEach(alert -> {
            Router router = findRouter(alert);
            Company company = router.getCompany();
            List<User> userList = userJPARepository.findAllAdminByCompanyId(company.getId());

            boolean isHealthCheck = alert.labels().alertname().equals(HEALTH_CHECK);
            if (isHealthCheck) {
                String title = "[긴급🚨] 라우터 고장";
                String body = router.getName() + "라우터가 고장났습니다.";
                sendFirebaseMessage.sendFcmAlert(userList, router, title, body);
            }
            else {
                List<Router> routerList = routerJPARepository.findAllRouterByCompanyId(company.getId());
                Profile profile = findDefaultProfile(alert.labels().alertname());
                company.addProfile(profile);
                sshConnectionUtil.profileChangeAndSendAlarm(userList, routerList, profile);
            }
        });
    }

    private Router findRouter(AlertRequest.AlertDTO alert) {
        return routerJPARepository.findByInstance(alert.labels().instance()).orElseThrow(
                () -> new NotFoundException(BaseException.ROUTER_NOT_EXIST_IN_DB)
        );
    }

    private Profile findDefaultProfile(String ssh) {
        return profileJPARepository.findBySsh(ssh).orElseThrow(
                () -> new NotFoundException(BaseException.PROFILE_NOT_FOUND)
        );
    }
}
