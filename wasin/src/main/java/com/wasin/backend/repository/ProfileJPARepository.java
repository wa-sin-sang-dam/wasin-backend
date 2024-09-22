package com.wasin.backend.repository;

import com.wasin.backend.domain.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileJPARepository extends JpaRepository<Profile, Long> {
    Optional<Profile> findByIndex(Long profileIndex);
}
