package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PharmacyMedicineDTO {
    private Integer id;
    @NotNull(message = "medicineId is Null")
    private Integer mediciId;
    @NotNull(message = "PharmacyId is Null")
    private Integer pharmacyId;
    @Positive
    private LocalDateTime createdDate;

}
