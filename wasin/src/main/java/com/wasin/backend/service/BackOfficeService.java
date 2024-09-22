package com.wasin.backend.service;

import com.wasin.backend.domain.dto.BackOfficeRequest;
import com.wasin.backend.domain.dto.BackOfficeResponse;
import com.wasin.backend.domain.entity.User;

public interface BackOfficeService {

    void accept(User user, BackOfficeRequest.AcceptDTO requestDTO);

    BackOfficeResponse.WaitingList findWaitingList(User user);

}
