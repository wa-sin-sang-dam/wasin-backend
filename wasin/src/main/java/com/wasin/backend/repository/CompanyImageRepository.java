package com.wasin.backend.repository;

import com.wasin.backend.domain.entity.CompanyImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyImageRepository extends JpaRepository<CompanyImage, Long> {

    Optional<CompanyImage> findByCompanyId(Long companyId);

}
