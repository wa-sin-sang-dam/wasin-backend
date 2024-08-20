package com.wasin.backend.domain.mapper;

import com.wasin.backend._core.util.WebApiUtil;
import com.wasin.backend.domain.dto.RouterRequest;
import com.wasin.backend.domain.dto.RouterResponse;
import com.wasin.backend.domain.entity.Company;
import com.wasin.backend.domain.entity.CompanyImage;
import com.wasin.backend.domain.entity.Router;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class RouterMapper {

    private final WebApiUtil webApiUtil;

    public Router dtoToRouter(RouterRequest.CreateDTO requestDTO,
                              RouterResponse.WifiNetworkQuality prometheusRouter,
                              Company company) {
        return Router.builder()
                .macAddress(prometheusRouter.bssid().toLowerCase())
                .name(requestDTO.name())
                .ssid(prometheusRouter.ssid())
                .instance(prometheusRouter.instance())
                .job(prometheusRouter.job())
                .positionX(requestDTO.positionX())
                .positionY(requestDTO.positionY())
                .company(company)
                .build();
    }

    public RouterResponse.FindByRouterId routerToRouterByIdDTO(CompanyImage companyImage, Router router) {
        return new RouterResponse.FindByRouterId(
                new RouterResponse.CompanyImageDTO(
                        companyImage.getUrl(),
                        companyImage.getHeight(),
                        companyImage.getWidth()
                ),
                new RouterResponse.RouterInformationDTO(
                        router.getName(),
                        router.getSsid(),
                        router.getMacAddress(),
                        router.getInstance(),
                        webApiUtil.getRouterState(router),
                        router.getPositionX(),
                        router.getPositionY()
                )
        );
    }

    public RouterResponse.FindALl routerFindAllDTO(CompanyImage companyImage, List<Router> router) {
        return new RouterResponse.FindALl(
                new RouterResponse.CompanyImageDTO(
                        companyImage.getUrl(),
                        companyImage.getHeight(),
                        companyImage.getWidth()
                ),
                router.stream().map(r -> new RouterResponse.EachRouter(
                        r.getId(),
                        r.getName(),
                        webApiUtil.getRouterState(r),
                        r.getPositionX(),
                        r.getPositionY()
                )).toList()
        );
    }

    public RouterResponse.CompanyImageDTO imageEntityToDTO(CompanyImage image) {
        return new RouterResponse.CompanyImageDTO(
                image.getUrl(),
                image.getHeight(),
                image.getWidth()
        );
    }
}
