package com.company.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
public class CompanyFilterDTO {
    @NotEmpty(message = "name is null or is empty")
    private String name;

    @NotEmpty(message = "phone is null or is empty")
    private String phone;

    @NotEmpty(message = "email is null or is empty")
    private String email;

    @NotEmpty(message = "adress is null or is empty")
    private String adress;

    @NotEmpty(message = "region is null or is empty")
    private String region;

    @Past
    private LocalDate fromDate;

    @FutureOrPresent
    private LocalDate toDate;

}
