package com.wasin.backend.domain.dto;

import java.util.List;

public class RouterResponse {
    public record FindALl(CompanyImageDTO image, List<EachRouter> routerList) { }

    public record FindByRouterId(CompanyImageDTO image, RouterInformationDTO information) { }

    public record prometheusRouter(String status, List<WifiNetworkQuality> data) { }

    public record WifiNetworkQuality(String __name__, String bssid, String channel, String country, String device,
                 String frequency, String ifname, String instance, String job, String mode, String ssid) { }

    public record EachRouter (Long routerId, String name, Long score, Double positionX, Double positionY) { }

    public record CompanyImageDTO(String companyImage, Integer imageHeight, Integer imageWidth){ }

    public record RouterInformationDTO(String name, String ssid, String macAddress, String instance, Long state, Double positionX, Double positionY){ }

    public record RouterResult(Results results) {}

    public record Results(Instant A) {}

    public record Instant(int status, List<Frame> frames) {}

    public record Frame(Data data) {}

    public record Data(List<List<Long>> values) {}

    public record Queries(List<Query> queries, String from, String to) {}

    public record Query(String refId, String expr, Boolean instant, Datasource datasource) {}

    public record Datasource(String type, String uid) {}
}
