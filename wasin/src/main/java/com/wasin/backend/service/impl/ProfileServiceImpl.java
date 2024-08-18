package com.wasin.backend.service.impl;

import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.NotFoundException;
import com.wasin.backend.domain.dto.ProfileResponse;
import com.wasin.backend.domain.entity.Profile;
import com.wasin.backend.domain.entity.User;
import com.wasin.backend.domain.mapper.ProfileMapper;
import com.wasin.backend.domain.validation.ProfileValidation;
import com.wasin.backend.repository.ProfileJPARepository;
import com.wasin.backend.repository.UserJPARepository;
import com.wasin.backend.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ProfileServiceImpl implements ProfileService {

    private final ProfileMapper profileMapper;
    private final ProfileValidation profileValidation;
    private final ProfileJPARepository profileJPARepository;
    private final UserJPARepository userJPARepository;

    public ProfileResponse.FindAll findAll() {
        List<Profile> profileList = profileJPARepository.findAll();
        return profileMapper.profileToFindAllDTO(profileList);
    }

    @Transactional
    public void changeModeAuto(User userDetails) {
        User user = findUserById(userDetails.getId());
        profileValidation.checkIsManual(user.getCompany());
        user.getCompany().changeModeAuto();

        // todo: 그라파나 alert 활성화
    }

    @Transactional
    public void changeModeManual(User userDetails) {
        User user = findUserById(userDetails.getId());
        profileValidation.checkIsAuto(user.getCompany());
        user.getCompany().changeModeManual();

        // todo: 그라파나 alert 비활성화
    }

    @Transactional
    public void changeProfile(User userDetails, Long profileId) {
        User user = findUserById(userDetails.getId());
        Profile profile = profileJPARepository.findById(profileId).orElseThrow(
                () -> new NotFoundException(BaseException.PROFILE_NOT_FOUND));

        // 모드가 수동일 경우에만 변경 가능
        profileValidation.checkIsManual(user.getCompany());

        user.getCompany().addProfile(profile);

        // todo: 내현 서버 ssh 접속해서 프로필 변경 실행
    }

    private User findUserById(Long userId) {
        return userJPARepository.findActiveUserWithCompanyById(userId)
                .orElseThrow(() -> new NotFoundException(BaseException.USER_NOT_FOUND));
    }

}
