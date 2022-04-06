package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
public class CommentFilterDTO {
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
    @Past
    private LocalDate fromDate;
    @FutureOrPresent
    private LocalDate toDate;
}
