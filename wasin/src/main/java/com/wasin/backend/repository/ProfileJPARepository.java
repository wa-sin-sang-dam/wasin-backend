package com.wasin.backend.repository;

import com.wasin.backend.domain.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileJPARepository extends JpaRepository<Profile, Long> {

}
