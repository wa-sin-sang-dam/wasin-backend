package com.wasin.backend.domain.mapper;

import com.wasin.backend.domain.dto.CompanyRequest;
import com.wasin.backend.domain.dto.CompanyResponse;
import com.wasin.backend.domain.entity.Company;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CompanyMapper {

    public Company openAPIDTOToCompany(CompanyRequest.CompanyDTO dto) {
        return Company.builder()
                .fssId(dto.companyFssId())
                .name(dto.companyName())
                .location(dto.location())
                .build();
    }

    public CompanyResponse.DBList entityToDBListDTO(List<Company> companyList) {
        return new CompanyResponse.DBList(
                companyList.stream()
                        .map(company -> new CompanyResponse.DBList.CompanyDBItem(
                                company.getId(), company.getLocation(), company.getName())
                        )
                        .collect(Collectors.toList())
        );
    }
}
