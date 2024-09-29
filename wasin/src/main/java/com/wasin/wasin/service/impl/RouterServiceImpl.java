package com.wasin.wasin.service.impl;

import com.wasin.wasin._core.exception.BaseException;
import com.wasin.wasin._core.exception.error.NotFoundException;
import com.wasin.wasin._core.util.web_api.WebApiUtil;
import com.wasin.wasin.domain.dto.RouterRequest;
import com.wasin.wasin.domain.dto.RouterResponse;
import com.wasin.wasin.domain.entity.CompanyImage;
import com.wasin.wasin.domain.entity.Router;
import com.wasin.wasin.domain.entity.User;
import com.wasin.wasin.domain.mapper.RouterMapper;
import com.wasin.wasin.domain.validation.RouterValidation;
import com.wasin.wasin.repository.CompanyImageRepository;
import com.wasin.wasin.repository.RouterJPARepository;
import com.wasin.wasin.repository.UserJPARepository;
import com.wasin.wasin.service.RouterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class RouterServiceImpl implements RouterService {

    private final UserJPARepository userJPARepository;

    private final CompanyImageRepository companyImageRepository;
    private final RouterJPARepository routerJPARepository;
    private final RouterValidation routerValidation;
    private final RouterMapper routerMapper;
    private final WebApiUtil webApiUtil;

    public RouterResponse.FindALl findAll(User userDetails) {
        User user = findUserById(userDetails.getId());
        List<Router> router = routerJPARepository.findAllRouterByCompanyId(user.getCompany().getId());
        CompanyImage image = getCompanyImage(user.getCompany().getId());

        return routerMapper.routerFindAllDTO(image, router);
    }

    public RouterResponse.FindByRouterId findByRouterId(User userDetails, Long routerId) {
        User user = findUserById(userDetails.getId());
        Router router = findRouterById(routerId);
        routerValidation.checkRouterPermission(router, user);
        CompanyImage image = getCompanyImage(user.getCompany().getId());

        return routerMapper.routerToRouterByIdDTO(image, router);
    }

    @Transactional
    public void create(User userDetails, RouterRequest.CreateDTO requestDTO) {
        // 1. 요청받은 라우터가 프로메테우스 서버에 있는 라우터인지 확인
        RouterResponse.WifiNetworkQuality prometheusRouter = getPrometheusRouter(requestDTO);

        // 2. 현재 서버에 없는 라우터인지 확인
        routerValidation.checkRouterNonExistInDB(requestDTO);

        // 3. 현재 서버에 라우터 추가
        User user = findUserById(userDetails.getId());
        Router router = routerMapper.dtoToRouter(requestDTO, prometheusRouter, user.getCompany());
        routerJPARepository.save(router);
    }

    @Transactional
    public void update(User userDetails, RouterRequest.UpdateDTO requestDTO, Long routerId) {
        Router router = findRouterById(routerId);
        User user = findUserById(userDetails.getId());
        routerValidation.checkRouterPermission(router, user);

        router.updateColumns(requestDTO);
    }

    @Transactional
    public void delete(User userDetails, Long routerId) {
        Router router = findRouterById(routerId);
        User user = findUserById(userDetails.getId());
        routerValidation.checkRouterPermission(router, user);

        routerJPARepository.deleteById(router.getId());
    }

    public RouterResponse.CompanyImageDTO findCompanyImage(User userDetails) {
        User user = findUserById(userDetails.getId());
        CompanyImage image = getCompanyImage(user.getCompany().getId());

        return routerMapper.imageEntityToDTO(image);
    }

    private CompanyImage getCompanyImage(Long companyId) {
        return companyImageRepository.findByCompanyId(companyId)
                .orElseThrow(() -> new NotFoundException(BaseException.COMPANY_IMAGE_NOT_FOUND));
    }

    private User findUserById(Long userId) {
        return userJPARepository.findActiveUserWithCompanyById(userId)
                .orElseThrow(() -> new NotFoundException(BaseException.USER_NOT_FOUND));
    }

    private Router findRouterById(Long routerId) {
        return routerJPARepository.findById(routerId)
                .orElseThrow(() -> new NotFoundException(BaseException.ROUTER_NOT_EXIST_IN_DB));
    }

    private RouterResponse.WifiNetworkQuality getPrometheusRouter(RouterRequest.CreateDTO requestDTO) {
        // 프로메테우스 서버 내에 있는 모든 라우터
        RouterResponse.prometheusRouter prometheusRouterList = webApiUtil.getPrometheusRouter();

        // 요청받은 라우터의 MAC 주소와 일치하는게 있는지 확인 -> 없으면 에러 / 있으면 반환
        return prometheusRouterList.data().stream()
                .filter(it -> it.bssid().equals(requestDTO.macAddress().toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(BaseException.ROUTER_NOT_EXIST_IN_PROMETHEUS));
    }

}
