package com.wasin.wasin.domain.dto;

import java.util.List;

public class MonitorResponse {
    public record FindById(
            Long activeMetricId,
            Long settingTime,
            List<MonitorMetric> metricList,
            List<MonitorGraph> graphList
    ) {
    }

    public record MonitorMetric(
        String metric,
        Long metricId
    ){
    }

    public record MonitorGraph(
        String labels,
        List<Long> timeList,
        List<Long> valueList
    ) {
    }

    public record FindAllRouter (
        List<MonitorRouter> routerList
    ) {
    }

    public record MonitorRouter(
        Long routerId,
        String name,
        String instance
    ) {
    }
}
