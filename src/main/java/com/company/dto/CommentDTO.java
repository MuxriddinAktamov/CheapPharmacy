package com.company.dto;

import com.company.entity.MedicineEntity;
import com.company.entity.ProfileEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentDTO {
    private Integer id;
    @NotEmpty(message = "content is null or empty")
    private String content;

    @NotNull(message = "medicineId is null")
    @Positive
    private Integer medicineId;

    @NotNull(message = "aptekaId is null")
    @Positive
    private Integer aptekaId;

    @NotNull(message = "profileId is null")
    @Positive
    private Integer profileId;

    @PastOrPresent
    private LocalDateTime created_date;

}
