package com.wasin.backend.domain.dto;

import lombok.Getter;

@Getter
public class ProfileDTO {

    private Long index;
    private String title;
    private String description;
    private String tip;

    public ProfileDTO(Long index, String title, String description, String tip) {
        this.index = index;
        this.title = title;
        this.description = description;
        this.tip = tip;
    }
}
