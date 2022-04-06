package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class PharmacyMedicineFilterDTO {
    @NotNull(message = "medicineId is Null")
    private Integer mediciId;
    @NotNull(message = "PharmacyId is Null")
    private Integer pharmacyId;
    @Past
    private LocalDate fromDate;
    @FutureOrPresent
    private LocalDate toDate;

}
