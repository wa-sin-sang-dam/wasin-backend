package com.wasin.wasin.domain.mapper;

import com.wasin.wasin.domain._const.Metric;
import com.wasin.wasin.domain.dto.MonitorResponse;
import com.wasin.wasin.domain.dto.RouterResponse;
import com.wasin.wasin.domain.entity.Router;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class MonitoringMapper {

    public MonitorResponse.FindById resultToDTO(RouterResponse.MonitorResult results, Long metricId, Long time) {
        return new MonitorResponse.FindById(
                metricId,
                time,
                getMetricList(),
                getGraphDataList(results)
        );
    }

    private List<MonitorResponse.MonitorGraph> getGraphDataList(RouterResponse.MonitorResult results) {
        return results.results().A().frames().stream().map(it -> {
                    RouterResponse.Label label = it.schema().fields().get(1).labels();
                    return new MonitorResponse.MonitorGraph(
                            label.device() == null ? "" : label.device(),
                            it.data().values().get(0),
                            it.data().values().get(1)
                    );
                }
        ).toList();
    }

    private List<MonitorResponse.MonitorMetric> getMetricList() {
        return Arrays.stream(Metric.values()).map(it ->
                new MonitorResponse.MonitorMetric(
                        it.getKor(),
                        it.getIndex()
                )
        ).toList();
    }

    public MonitorResponse.FindAllRouter toRouterListDTO(List<Router> routerList) {
        return new MonitorResponse.FindAllRouter(
            routerList.stream().map(it ->
                new MonitorResponse.MonitorRouter(
                        it.getId(),
                        it.getName(),
                        it.getInstance()
                )
            ).toList()
        );
    }
}
