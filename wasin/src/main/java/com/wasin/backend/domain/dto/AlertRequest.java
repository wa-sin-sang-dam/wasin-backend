package com.wasin.backend.domain.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AlertRequest {
    public record ProfileChangeDTO(
            @NotEmpty(message = "알림 이름은 비어있으면 안됩니다.")
            @Size(max = 255, message = "알림 이름은  255자 이내여야 합니다.")
            String alertname,

            @NotEmpty(message = "폴더 이름은 비어있으면 안됩니다.")
            @Size(max = 255, message = "폴더 이름은  255자 이내여야 합니다.")
            String grafana_folder,

            @NotEmpty(message = "알림 인스턴스는 비어있으면 안됩니다.")
            @Size(max = 255, message = "알림 인스턴스는  255자 이내여야 합니다.")
            String instance
    ) {
    }
}
