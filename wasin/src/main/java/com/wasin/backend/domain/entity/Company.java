package com.wasin.backend.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "company_tb")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    @Column(unique = true, name="fss_id")
    private String fssId;

    @Column
    private String name;

    @Column
    private String location;

    @Builder
    public Company(Long id, String fssId, String name, String location) {
        this.id = id;
        this.fssId = fssId;
        this.name = name;
        this.location = location;
    }
}
