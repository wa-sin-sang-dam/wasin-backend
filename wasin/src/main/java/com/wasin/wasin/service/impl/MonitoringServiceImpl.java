package com.wasin.wasin.service.impl;

import com.wasin.wasin._core.exception.BaseException;
import com.wasin.wasin._core.exception.error.NotFoundException;
import com.wasin.wasin._core.util.web_api.MonitoringApiUtil;
import com.wasin.wasin.domain._const.Metric;
import com.wasin.wasin.domain.dto.MonitorResponse;
import com.wasin.wasin.domain.dto.RouterResponse;
import com.wasin.wasin.domain.entity.Router;
import com.wasin.wasin.domain.entity.User;
import com.wasin.wasin.domain.mapper.MonitoringMapper;
import com.wasin.wasin.repository.RouterJPARepository;
import com.wasin.wasin.service.MonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MonitoringServiceImpl implements MonitoringService {

    private final RouterJPARepository routerJPARepository;
    private final MonitoringApiUtil monitoringApiUtil;
    private final MonitoringMapper monitoringMapper;

    public MonitorResponse.FindById monitorById(Long routerId, Long metricId, Long time, User user) {
        // default values
        if (metricId == null) metricId = 0L;
        if (time == null) time = 60L;

        Metric metric = Metric.findByMetricId(metricId);
        Router router = findByRouterId(routerId);
        RouterResponse.MonitorResult results = monitoringApiUtil.getMetric(metric, router, time);

        return monitoringMapper.resultToDTO(results, metricId, time);
    }

    public MonitorResponse.FindAllRouter findAllRouter(User _user) {
        User user = findUserByUserId(_user.getId());
        Company company = user.getCompany();
        List<Router> routerList = routerJPARepository.findAllRouterByCompanyId(company.getId());
        return monitoringMapper.toRouterListDTO(routerList);
    }

    private User findUserByUserId(Long userId) {
        return userJPARepository.findActiveUserWithCompanyById(userId).orElseThrow(
                () -> new NotFoundException(BaseException.USER_NOT_FOUND)
        );
    }

    private Router findByRouterId(Long routerId) {
        return routerJPARepository.findById(routerId).orElseThrow(
                () -> new NotFoundException(BaseException.ROUTER_NOT_EXIST_IN_DB)
        );
    }
}
