package com.wasin.backend._core.util;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.ServerException;
import com.wasin.backend.domain.dto.CompanyDTO;
import com.wasin.backend.domain.dto.RouterResponse;
import com.wasin.backend.domain.entity.Router;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebApiUtil {

    private final WebClient webClient;

    @Value("${security.open-api.secret-key}")
    String serviceKey;

    @Value("${security.open-api.token}")
    String apiToken;

    @Value("${jang.header}")
    String grafanaHeader;

    @Value("${jang.datasource-uid}")
    String grafanaDatasourceUID;

    public CompanyDTO.ResponseValue getCompanyList(String name) {
        try {
            return webClient.mutate()
                    .build()
                    .get()
                    .uri("http://apis.data.go.kr/1160100/service/GetCorpBasicInfoService_V2/getCorpOutline_V2", getURI(name))
                    .header("Authorization", "Bearer " + apiToken)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                              clientResponse -> clientResponse
                                    .bodyToMono(String.class)
                                    .map(body -> new ServerException(BaseException.COMPANY_OPEN_API_FAIL)))
                    .bodyToMono(CompanyDTO.ResponseValue.class)
                    .block();
        } catch(Exception e) {
            log.debug(e.getMessage());
            throw new ServerException(BaseException.COMPANY_OPEN_API_FAIL);
        }
    }

    public RouterResponse.prometheusRouter getPrometheusRouter() {
        try {
            return webClient.mutate()
                    .build()
                    .get()
                    .uri("http://prometheus.daily-cotidie.com/api/v1/series?match[]=wifi_network_quality")
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            clientResponse -> clientResponse
                                    .bodyToMono(String.class)
                                    .map(body -> new ServerException(BaseException.COMPANY_OPEN_API_FAIL)))
                    .bodyToMono(RouterResponse.prometheusRouter.class)
                    .block();
        } catch(Exception e) {
            log.debug(e.getMessage());
            throw new ServerException(BaseException.PROMETHUS_SERVER_FAIL);
        }
    }

    public Long getWifiState(Router router) {
        try {
            String expr = "wifi_network_quality{instance=~\""           + router.getInstance() + "\",job=~\"" + router.getJob() + "\", ifname=\"wlan0\"} " +
                    "* (1 - 0.3 * (wifi_network_bitrate{instance=~\""   + router.getInstance() + "\",job=~\"" + router.getJob() + "\", ifname=\"wlan0\"} " +
                    "- min_over_time(wifi_network_bitrate{instance=~\"" + router.getInstance() + "\",job=~\"" + router.getJob() + "\", ifname=\"wlan0\"}[3h])) " +
                    "/ (1 + max_over_time(wifi_network_bitrate{instance=~\""  + router.getInstance() + "\",job=~\"" + router.getJob() + "\", ifname=\"wlan0\"}[3h]) " +
                    "- min_over_time(wifi_network_bitrate{instance=~\"" + router.getInstance() + "\",job=~\"" + router.getJob() + "\", ifname=\"wlan0\"}[3h])))";
            RouterResponse.Queries requestDTO = getRequestDTO(expr);

            return getResult(requestDTO);
        } catch(Exception e) {
            log.debug(e.getMessage());
            throw new ServerException(BaseException.GRAFANA_SERVER_FAIL);
        }
    }


    public Long getRouterState(Router router) {
        try  {
            String expr = "avg(wifi_network_quality{instance=~\"" + router.getInstance() + "\",job=~\"" + router.getJob() + "\"}) * " +
                    "(1 - avg(node_load1{instance=~\"" + router.getInstance() + "\",job=~\"" + router.getJob() + "\"}) / " +
                    " count(count(node_cpu_seconds_total{instance=~\"" + router.getInstance() + "\",job=~\"" + router.getJob() + "\"}) by (cpu)))";
            RouterResponse.Queries requestDTO = getRequestDTO(expr);
            return getResult(requestDTO);
        } catch(Exception e) {
            log.debug(e.getMessage());
            throw new ServerException(BaseException.GRAFANA_SERVER_FAIL);
        }
    }

    private Long getResult(RouterResponse.Queries requestDTO) {
        return Objects.requireNonNull(webClient.mutate()
                        .build()
                        .post()
                        .uri("http://grafana.daily-cotidie.com/api/ds/query")
                        .bodyValue(requestDTO)
                        .header("Authorization", "Bearer " + grafanaHeader)
                        .retrieve()
                        .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                                clientResponse -> clientResponse
                                        .bodyToMono(String.class)
                                        .map(body -> new ServerException(BaseException.GRAFANA_REQUEST_FAIL)))
                        .bodyToMono(RouterResponse.RouterResult.class)
                        .block())
                .results().A().frames().get(0).data().values().get(1).get(0);
    }

    private RouterResponse.Queries getRequestDTO(String expr) {
        String type = "prometheus";
        String refId = "A";
        RouterResponse.Queries requestDTO = new RouterResponse.Queries(
                List.of(new RouterResponse.Query(refId, expr, true, new RouterResponse.Datasource(type, grafanaDatasourceUID))),
                "now-5m",
                "now"
        );
        return requestDTO;
    }

    private Function<UriBuilder, URI> getURI(String name) {
        return uriBuilder -> uriBuilder
                .queryParam("corpNm", URLEncoder.encode(name))
                .queryParam("pageNo", 1)
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", 100000)
                .queryParam("resultType", "json")
                .build();
    }
}
