package com.wasin.backend.domain.validation;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.BadRequestException;
import com.wasin.backend._core.exception.error.ForbiddenException;
import com.wasin.backend.domain.dto.RouterRequest;
import com.wasin.backend.domain.entity.Router;
import com.wasin.backend.domain.entity.User;
import com.wasin.backend.repository.RouterJPARepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RouterValidation {

    private final RouterJPARepository routerJPARepository;

    public void checkRouterNonExistInDB(RouterRequest.CreateDTO requestDTO) {
        if(routerJPARepository.findByMacAddress(requestDTO.macAddress()).isPresent()) {
            throw new BadRequestException(BaseException.ROUTER_EXIST_IN_DB);
        }
    }

    public void checkRouterPermission(Router router, User user) {
        if (!user.getCompany().equals(router.getCompany())) {
            throw new ForbiddenException(BaseException.ROUTER_PERMISSION_DENIED);
        }
    }
}
