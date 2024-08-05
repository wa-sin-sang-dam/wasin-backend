package com.wasin.backend.domain.mapper;

import com.wasin.backend.domain.entity.Company;
import com.wasin.backend.domain.entity.CompanyImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyImageMapper {
    public CompanyImage urlToCompanyImage(String url, Company company) {
        return CompanyImage.builder()
                .url(url)
                .company(company)
                .build();
    }
}
