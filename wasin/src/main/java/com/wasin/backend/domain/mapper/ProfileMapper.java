package com.wasin.backend.domain.mapper;

import com.wasin.backend.domain.dto.ProfileDTO;
import com.wasin.backend.domain.dto.ProfileResponse;
import com.wasin.backend.domain.entity.Profile;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ProfileMapper {

    public ProfileResponse.FindAll profileToFindAllDTO(List<Profile> profileList) {
        return new ProfileResponse.FindAll(
                profileList.stream().map(it -> new ProfileResponse.ProfileEachDTO(
                        it.getId(),
                        it.getTitle(),
                        it.getDescription(),
                        it.getTip()
                )).toList()
        );
    }

    public List<Profile> dtoListToEntityList(List<ProfileDTO> profileList) {
        return profileList.stream()
                .map(profileDTO ->
                    Profile.builder()
                        .index(profileDTO.getIndex())
                        .title(profileDTO.getTitle())
                        .description(profileDTO.getDescription())
                        .tip(profileDTO.getTip())
                        .build()
                ).toList();
    }

}
