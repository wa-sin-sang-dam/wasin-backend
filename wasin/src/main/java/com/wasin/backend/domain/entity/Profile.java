package com.wasin.backend.domain.entity;


import com.wasin.backend.domain.dto.ProfileDTO;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "profile_tb")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @Column(unique = true, name = "profile_index")
    private Long index;

    @Column(length = 1000)
    private String title;

    @Column(length = 3000)
    private String description;

    @Column(length = 3000)
    private String tip;

    @Builder
    public Profile(Long id, Long index, String title, String description, String tip) {
        this.id = id;
        this.index = index;
        this.title = title;
        this.description = description;
        this.tip = tip;
    }

    public void update(ProfileDTO dto) {
        this.index = dto.getIndex();
        this.title = title;
        this.description = description;
        this.tip = tip;
    }
}
