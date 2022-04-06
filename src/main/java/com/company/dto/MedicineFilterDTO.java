package com.company.dto;

import com.company.enums.MedicineKategory;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

@Getter
@Setter
public class MedicineFilterDTO {
    @NotEmpty(message = "name is Null or is Empty")
    private String name;

    @NotEmpty(message = "price is Null or is Empty")
    private Double price;

    @NotEmpty(message = "MedicineKategory  is null or empty")
    private MedicineKategory medicineKategory;

    @NotEmpty(message = "Description is null or empty")
    private String description;

    @NotEmpty(message = "amount is null or empty")
    @Positive
    private Integer amount;

    @NotEmpty(message = "companyId is null or empty")
    @Positive
    private Integer companyId;

    @NotEmpty(message = "code is null or empty")
    private String code;
    @Past
    private LocalDate fromDate;
    @FutureOrPresent
    private LocalDate toDate;

}
