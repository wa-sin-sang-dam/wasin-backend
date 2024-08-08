package com.wasin.backend.service;

import com.wasin.backend.domain.dto.ProfileResponse;
import com.wasin.backend.domain.entity.User;

public interface ProfileService {
    ProfileResponse.FindAll findAll();

    void changeModeAuto(User user);

    void changeModeManual(User use);

    void changeProfile(User user, Long profileId);

}
