package com.wasin.wasin.repository;

import com.wasin.wasin.domain.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    Optional<Company> findByFssId(String fssId);

}
