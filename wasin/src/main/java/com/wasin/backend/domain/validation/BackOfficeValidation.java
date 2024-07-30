package com.wasin.backend.domain.validation;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.BadRequestException;
import com.wasin.backend.domain.entity.User;
import com.wasin.backend.domain.entity.enums.Role;
import com.wasin.backend.domain.entity.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BackOfficeValidation {

    public void checkAccept(User superAdmin, User admin) {
        // TODO: checkIfCompanySame();
        checkIsAdmin(admin);
        checkIsStandBy(admin);
    }

    private static void checkIsStandBy(User admin) {
        if (admin.getStatus() != Status.STAND_BY) {
            throw new BadRequestException(BaseException.STATUS_NOT_STANDBY);
        }
    }

    private static void checkIsAdmin(User admin) {
        if (admin.getRole() != Role.ADMIN) {
            throw new BadRequestException(BaseException.ROLE_NOT_ADMIN);
        }
    }

}
