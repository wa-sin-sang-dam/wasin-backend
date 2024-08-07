package com.wasin.backend.domain.validation;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.BadRequestException;
import com.wasin.backend.domain.dto.CompanyRequest;
import com.wasin.backend.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyValidation {

    @Value("${security.service-key}")
    private String serviceKey;

    private final CompanyRepository companyRepository;

    public void checkCompanyByOpenAPI(CompanyRequest.CompanyDTO companyDTO) {
        checkServiceKey(companyDTO.serviceKey());
        checkCompanyNotExist(companyDTO);
    }

    private void checkServiceKey(String key) {
        if (!serviceKey.equals(key)) {
            throw new BadRequestException(BaseException.WRONG_WASIN_SERVICE_KEY);
        }
    }

    private void checkCompanyNotExist(CompanyRequest.CompanyDTO companyDTO) {
        if (companyRepository.findByFssId(companyDTO.companyFssId()).isPresent()) {
            throw new BadRequestException(BaseException.COMPANY_ALREADY_EXIST);
        }
    }

}
