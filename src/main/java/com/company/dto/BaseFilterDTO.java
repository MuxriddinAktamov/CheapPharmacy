package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@Setter
public class BaseFilterDTO {
    @Positive
    private Integer medicineId;

    @Positive
    private Integer profileId;

    @Positive
    private Integer pharmacyid;
    @Past
    private LocalDate fromDate;
    @FutureOrPresent
    private LocalDate toDate;

}
