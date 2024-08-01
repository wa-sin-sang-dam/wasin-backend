package com.wasin.backend._core.util;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.ServerException;
import com.wasin.backend.domain.dto.CompanyDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.net.URLEncoder;
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

    public CompanyDTO.ResponseValue getCompanyList(String name, Long page) {
        try {
            return webClient.mutate()
                    .build()
                    .get()
                    .uri("http://apis.data.go.kr/1160100/service/GetCorpBasicInfoService_V2/getCorpOutline_V2", getURI(name, page))
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

    private Function<UriBuilder, URI> getURI(String name, Long page) {
        return uriBuilder -> uriBuilder
                .queryParam("corpNm", URLEncoder.encode(name))
                .queryParam("pageNo", page)
                .queryParam("serviceKey", serviceKey)
                .queryParam("numOfRows", 100L)
                .queryParam("resultType", "json")
                .build();
    }
}
