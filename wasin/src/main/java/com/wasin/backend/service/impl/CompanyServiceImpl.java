package com.wasin.backend.service.impl;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.BadRequestException;
import com.wasin.backend._core.exception.error.NotFoundException;
import com.wasin.backend._core.util.WebApiUtil;
import com.wasin.backend.domain.dto.CompanyDTO;
import com.wasin.backend.domain.dto.CompanyRequest;
import com.wasin.backend.domain.dto.CompanyResponse;
import com.wasin.backend.domain.entity.Company;
import com.wasin.backend.domain.entity.User;
import com.wasin.backend.domain.mapper.CompanyMapper;
import com.wasin.backend.domain.validation.CompanyValidation;
import com.wasin.backend.repository.CompanyRepository;
import com.wasin.backend.repository.UserJPARepository;
import com.wasin.backend.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CompanyServiceImpl implements CompanyService {

    private final WebApiUtil webApiUtil;
    private final CompanyRepository companyRepository;
    private final CompanyValidation companyValidation;
    private final CompanyMapper companyMapper;
    private final UserJPARepository userJPARepository;

    public CompanyResponse.OpenAPIList findAllCompanyByOpenAPI(String name, Long page) {
        CompanyDTO.ResponseValue companyList = webApiUtil.getCompanyList(name, page);
        return new CompanyResponse.OpenAPIList(
                companyList.response().body().items().item()
                        .stream()
                        .map(CompanyServiceImpl::getCompanyOpenAPIItem)
                        .collect(Collectors.toList())
        );
    }

    public CompanyResponse.DBList findAllCompanyByDB() {
        List<Company> companyList = companyRepository.findAll();
        return companyMapper.entityToDBListDTO(companyList);
    }

    @Transactional
    public void saveCompanyByOpenAPI(CompanyRequest.CompanyByOpenAPI request, User user) {
        companyValidation.checkIfCompanyExist(request.companyFssId());
        Company company = companyMapper.openAPIDTOToCompany(request);
        companyRepository.save(company);
    }

    @Transactional
    public void saveCompanyByDB(CompanyRequest.CompanyByDB request, User user) {
        Company company = companyRepository.findById(request.companyId()).orElseThrow(
                () -> new NotFoundException(BaseException.COMPANY_NOT_FOUND)
        );
        User admin = userJPARepository.findById(user.getId()).orElseThrow(
                () -> new NotFoundException(BaseException.USER_NOT_FOUND)
        );
        admin.joinCompany(company);
    }

    private static CompanyResponse.OpenAPIList.CompanyOpenAPIItem getCompanyOpenAPIItem(CompanyDTO.Item item) {
        return new CompanyResponse.OpenAPIList.CompanyOpenAPIItem(
                item.crno() + "-" + item.bzno() + "-" + item.fssCorpUnqNo(),
                item.enpBsadr(),
                item.corpNm()
        );
    }
}
