package com.wasin.backend.repository;

import com.wasin.backend.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByFssId(String fssId);

}
