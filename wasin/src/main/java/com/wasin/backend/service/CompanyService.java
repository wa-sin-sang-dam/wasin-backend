package com.wasin.backend.service;

import com.wasin.backend.domain.dto.CompanyRequest;
import com.wasin.backend.domain.dto.CompanyResponse;
import com.wasin.backend.domain.entity.User;

public interface CompanyService {
    CompanyResponse.OpenAPIList findAllCompanyByOpenAPI(String name, Long page);

    CompanyResponse.DBList findAllCompanyByDB();

    void saveCompanyByOpenAPI(CompanyRequest.CompanyByOpenAPI request, User user);

    void saveCompanyByDB(CompanyRequest.CompanyByDB request, User user);
}
