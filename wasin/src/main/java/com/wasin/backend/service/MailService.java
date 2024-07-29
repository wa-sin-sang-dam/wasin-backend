package com.wasin.backend.service;

import com.wasin.backend.domain.dto.UserRequest;

public interface MailService {
    void sendMail(UserRequest.EmailDTO requestDTO);

    void checkMailCode(UserRequest.EmailCheckDTO requestDTO);

    void checkVerified(String email);
}
