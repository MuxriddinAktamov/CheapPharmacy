package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseDTO {
    private Integer id;
    @Positive
    private Integer medicineId;

    @Positive
    private Integer profileId;

    @Positive
    private Integer pharmacyid;

    @PastOrPresent
    private LocalDateTime updateDate;

    @PastOrPresent
    private LocalDateTime created_date;
}
