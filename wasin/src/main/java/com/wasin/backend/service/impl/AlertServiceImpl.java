package com.wasin.backend.service.impl;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.NotFoundException;
import com.wasin.backend._core.exception.error.ServerException;
import com.wasin.backend._core.util.SshConnectionUtil;
import com.wasin.backend.domain.dto.AlertRequest;
import com.wasin.backend.domain.entity.Company;
import com.wasin.backend.domain.entity.Profile;
import com.wasin.backend.domain.entity.Router;
import com.wasin.backend.repository.ProfileJPARepository;
import com.wasin.backend.repository.RouterJPARepository;
import com.wasin.backend.service.AlertService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AlertServiceImpl implements AlertService {

    private final SshConnectionUtil sshConnectionUtil;
    private final RouterJPARepository routerJPARepository;
    private final ProfileJPARepository profileJPARepository;

    public void receiveAlert(AlertRequest.ProfileChangeDTO request) {
        request.alerts().forEach(alert -> {
            Router router = findRouter(alert);
            Company company = router.getCompany();
            changeProfile(alert, company);
        });
    }

    private void changeProfile(AlertRequest.AlertDTO alert, Company company) {
        List<Router> routerList = routerJPARepository.findAllRouterByCompanyId(company.getId());
        Profile profile = findDefaultProfile(alert.labels().alertname());
        company.addProfile(profile);

        try {
            for (Router r : routerList) {
                String command = "cd ./test_excute; ./" + profile.getSsh();
                String instance = r.getInstance().split(":")[0];
                sshConnectionUtil.connect(command, instance);
            }
        } catch(Exception e) {
            log.debug(e.getMessage());
            throw new ServerException(BaseException.SSH_CONNECTION_FAIL);
        }
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
