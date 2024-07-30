package com.wasin.backend.domain.entity;


import com.wasin.backend.domain.entity.enums.Role;
import com.wasin.backend.domain.entity.enums.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_tb")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column
    private String username;

    @Column(unique = true)
    private String email;

    @Column
    private String password;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "is_mode_auto")
    private Boolean isModeAuto;

    @Builder
    public User(Long id, String username, String email, String password, Role role, Status status, Boolean isModeAuto) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
        this.status = status;
        this.isModeAuto = isModeAuto;
    }

}
