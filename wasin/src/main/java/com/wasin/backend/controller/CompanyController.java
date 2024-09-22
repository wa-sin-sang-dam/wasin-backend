package com.wasin.backend.controller;

import com.wasin.backend._core.security.CustomUserDetails;
import com.wasin.backend._core.util.ApiUtils;
import com.wasin.backend.domain.dto.CompanyRequest;
import com.wasin.backend.domain.dto.CompanyResponse;
import com.wasin.backend.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequiredArgsConstructor
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService companyService;

    // 오픈 API 내 회사 목록 조회
    @PostMapping("/open-api/list")
    public ResponseEntity<?> findAllCompanyByOpenAPI(@RequestBody @Valid CompanyRequest.FindCompanyByOpenAPI request) {
        CompanyResponse.OpenAPIList response = companyService.findAllCompanyByOpenAPI(request);
        return ResponseEntity.ok().body(ApiUtils.success(response));
    }

    // DB 내 회사 목록 조회
    @GetMapping("/db")
    public ResponseEntity<?> findAllCompanyByDB() {
        CompanyResponse.DBList response = companyService.findAllCompanyByDB();
        return ResponseEntity.ok().body(ApiUtils.success(response));
    }

    // 오픈 API 내 회사 등록
    @PostMapping("/open-api")
    public ResponseEntity<?> saveCompanyByOpenAPI(@RequestPart(value = "data") @Valid CompanyRequest.CompanyDTO request,
                                                  @RequestPart(value = "file") MultipartFile file,
                                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        companyService.saveCompanyByOpenAPI(request, file, userDetails.getUser());
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

    // DB 내 회사 목록 등록
    @PostMapping("/db")
    public ResponseEntity<?> saveCompanyByDB(@RequestBody @Valid CompanyRequest.CompanyByDB request,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        companyService.saveCompanyByDB(request, userDetails.getUser());
        return ResponseEntity.ok().body(ApiUtils.success(null));
    }

}
