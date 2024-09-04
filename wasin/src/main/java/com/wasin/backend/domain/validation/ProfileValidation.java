package com.wasin.backend.domain.validation;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.BadRequestException;
import com.wasin.backend.domain.entity.Company;
import com.wasin.backend.domain.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProfileValidation {

    public void checkIsAuto(Company company) {
        if (!company.getIsAuto()) {
            throw new BadRequestException(BaseException.PROFILE_ALREADY_MANUAL);
        }
    }

    public void checkIsManual(Company company) {
        if (company.getIsAuto()) {
            throw new BadRequestException(BaseException.PROFILE_ALREADY_AUTO);
        }
    }

    public void checkChangeable(Company company, Long profileId) {
        checkIsManual(company);
        checkIsProfileIdSame(company.getProfile(), profileId);
    }

    private void checkIsProfileIdSame(Profile profile, Long profileId) {
        if (profile.getId().equals(profileId)) {
            throw new BadRequestException(BaseException.PROFILE_MODE_ALREADY_BE);
        }
    }
}
