package com.wasin.backend.repository;

import com.wasin.backend.domain.entity.Router;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RouterJPARepository extends JpaRepository<Router, Long> {

    Optional<Router> findByMacAddress(String macAddress);

    @Query("select r from Router r join fetch r.company c where c.id = :companyId")
    List<Router> findAllRouterByCompanyId(@Param("companyId") Long companyId);

}
