package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
public class PharmacyFilterDTO {
    @NotEmpty(message = "name is Null or is Empty")
    private String name;

    @NotEmpty(message = "phone is Null or is Empty")
    private String phone;

    @Email
    private String email;

    @NotEmpty(message = "adress is Null or is Empty")
    private String adress;

    @NotNull
    private Integer regionId;
    @Past
    private LocalDate fromDate;
    @FutureOrPresent
    private LocalDate toDate;


}
