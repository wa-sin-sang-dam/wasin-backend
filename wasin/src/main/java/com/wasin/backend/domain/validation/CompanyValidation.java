package com.wasin.backend.domain.validation;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.BadRequestException;
import com.wasin.backend.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CompanyValidation {

    private final CompanyRepository companyRepository;

    public void checkIfCompanyExist(String fssId) {
        if (companyRepository.findByFssId(fssId).isPresent()) {
            throw new BadRequestException(BaseException.COMPANY_ALREADY_EXIST);
        }
    }

}
