package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PharmacyDTO {
    private Integer id;

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


    @PastOrPresent
    private String startDate;

    @PastOrPresent
    private String endDate;

}
