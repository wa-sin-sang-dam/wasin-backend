package com.wasin.backend.service.impl;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.NotFoundException;
import com.wasin.backend._core.util.WebApiUtil;
import com.wasin.backend.domain.dto.HandOffRequest;
import com.wasin.backend.domain.dto.HandOffResponse;
import com.wasin.backend.domain.entity.Router;
import com.wasin.backend.domain.entity.User;
import com.wasin.backend.domain.validation.HandOffValidation;
import com.wasin.backend.repository.RouterJPARepository;
import com.wasin.backend.repository.UserJPARepository;
import com.wasin.backend.service.HandOffService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class HandOffServiceImpl implements HandOffService {

    private final UserJPARepository userJPARepository;
    private final RouterJPARepository routerJPARepository;
    private final HandOffValidation handOffValidation;
    private final WebApiUtil webApiUtil;

    public HandOffResponse.UserRouter findAll(User userDetails, HandOffRequest.UserRouterDTO requestDTO) {
        User user = findUserById(userDetails.getId());
        return  new HandOffResponse.UserRouter(
                user.getIsModeAuto(),
                findSortedRouterList(requestDTO)
        );
    }

    public HandOffResponse.BestRouter findBestRouter(User userDetails, HandOffRequest.UserRouterDTO requestDTO) {
        User user = findUserById(userDetails.getId());
        return new HandOffResponse.BestRouter(
                user.getIsModeAuto(),
                findSortedRouterList(requestDTO).get(0)
        );
    }

    @Transactional
    public void changeModeAuto(User userDetails) {
        User user = findUserById(userDetails.getId());
        handOffValidation.checkIsManual(user.getIsModeAuto());
        user.changeModeAuto();
    }

    @Transactional
    public void changeModeManual(User userDetails) {
        User user = findUserById(userDetails.getId());
        handOffValidation.checkIsAuto(user.getIsModeAuto());
        user.changeModeManual();
    }

    private List<HandOffResponse.RouterWithStateDTO> findSortedRouterList(HandOffRequest.UserRouterDTO requestDTO) {
        return requestDTO.router().stream().map(it -> {
            Optional<Router> router = routerJPARepository.findByMacAddress(it.macAddress());
            return new HandOffResponse.RouterWithStateDTO(
                    it.level(),
                    getScore(router),
                    it.ssid(),
                    it.macAddress(),
                    router.isPresent()
            );
        }).sorted().toList();
    }

    private Long getScore(Optional<Router> router) {
        if (router.isPresent()) {
            return webApiUtil.getWifiState(router.get());
        }
        return 0L;
    }

    private User findUserById(Long id) {
        return userJPARepository.findById(id).orElseThrow(
                () -> new NotFoundException(BaseException.USER_NOT_FOUND)
        );
    }

}
