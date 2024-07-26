package com.wasin.backend.domain.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Status {
    STAND_BY("stand_by"),
    ACTIVE("active"),
    INACTIVE("inactive");

    @Getter
    private final String eng;

    public static Status getDefaultStatusByRole(Role role) {
        if (role.equals(Role.USER)) return ACTIVE;
        return STAND_BY;
    }
}
