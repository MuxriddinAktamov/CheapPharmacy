package com.company.dto;

import com.company.enums.MedicineKategory;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MedicineDTO {
    private Integer id;

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

//    @NotEmpty(message = "attachId is null or empty")
//    @Positive
//    private Integer attachId;

    @NotEmpty(message = "code is null or empty")
    private String code;

    @PastOrPresent
    private LocalDate manufacture_date;

    @PastOrPresent
    private LocalDate expiration_date;

    @PastOrPresent
    private LocalDateTime created_date;


}
