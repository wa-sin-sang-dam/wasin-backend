package com.wasin.backend.service;

import com.wasin.backend.domain.dto.CompanyRequest;
import com.wasin.backend.domain.dto.CompanyResponse;
import com.wasin.backend.domain.entity.User;
import org.springframework.web.multipart.MultipartFile;

public interface CompanyService {
    CompanyResponse.OpenAPIList findAllCompanyByOpenAPI(CompanyRequest.FindCompanyByOpenAPI request);

    CompanyResponse.DBList findAllCompanyByDB();

    void saveCompanyByOpenAPI(CompanyRequest.CompanyDTO request, MultipartFile file, User user);

    void saveCompanyByDB(CompanyRequest.CompanyByDB request, User user);
}
