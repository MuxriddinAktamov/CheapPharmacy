package com.company.dto;

import com.company.enums.ProfileRole;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class ProfileJwtDTO {
    private Integer id;

    @NotEmpty(message = "role is Null or Empty")
    private ProfileRole role;

    @NotNull(message = "profileId is Null")
    private Integer profileId;

    public ProfileJwtDTO(int profileId, ProfileRole role) {
        this.profileId = profileId;
        this.role = role;
    }
}
