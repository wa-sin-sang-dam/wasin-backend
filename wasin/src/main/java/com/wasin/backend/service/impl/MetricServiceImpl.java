package com.wasin.backend.service.impl;

import com.wasin.backend.domain.dto.MetricResponse;
import com.wasin.backend.service.MetricService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class MetricServiceImpl implements MetricService {

    @Value("${jang.url}")
    private String url;

    @Value("${jang.header}")
    private String header;

    private final RestTemplate restTemplate;

    public MetricServiceImpl(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    @Override
    public String getGrafanaDashboard() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + header);
        HttpEntity<Map<String, String>> httpEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, MetricResponse.GetMetricDashboard.class)
                .getBody().dashboard().title();
    }
}
