package com.wasin.backend.repository;

import com.wasin.backend.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserJPARepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
