package com.wasin.backend._core.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wasin.backend._core.exception.BaseException;
import com.wasin.backend._core.exception.error.ServerException;
import com.wasin.backend.domain.dto.ProfileDTO;
import com.wasin.backend.domain.entity.Profile;
import com.wasin.backend.domain.mapper.ProfileMapper;
import com.wasin.backend.repository.ProfileJPARepository;
import com.wasin.backend.repository.ProfileJdbcRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ScheduleConfig {

    private final ObjectMapper om;
    private final ProfileMapper profileMapper;
    private final ProfileJdbcRepository profileJdbcRepository;
    private final ProfileJPARepository profileJPARepository;

    @PostConstruct
    public void init() {
        scheduleProfileSave();
    }

    @Scheduled(cron = "0 0 3 * * *", zone = "Asia/Seoul")
    public void scheduleProfileSave() {
        try {
            ClassPathResource file = new ClassPathResource("static/profiles.json");
            InputStream inputstream = file.getInputStream();
            List<ProfileDTO> profileFileList = Arrays.asList(om.readValue(inputstream, ProfileDTO[].class));
            List<Profile> profileDBList = profileJPARepository.findAll();

            // DB 상에 있는 프로파일과 일치하지 않으면 업데이트
            if (shouldUpdate(profileFileList, profileDBList)) {
                List<Profile> profileList = profileMapper.dtoListToEntityList(profileFileList);
                profileJPARepository.deleteAllInBatch();
                profileJdbcRepository.saveAll(profileList);
            }
        } catch (Exception e) {
            log.debug(e.getMessage());
            throw new ServerException(BaseException.FILE_READ_FAIL);
        }
    }

    private boolean shouldUpdate(List<ProfileDTO> profileFileList, List<Profile> profileDBList) {
        List<ProfileDTO> profileDTODBList = profileMapper.entityListToDtoList(profileDBList);

        return profileFileList.size() != profileDTODBList.size() ||
                !new HashSet<>(profileFileList).containsAll(profileDTODBList);
    }


}
