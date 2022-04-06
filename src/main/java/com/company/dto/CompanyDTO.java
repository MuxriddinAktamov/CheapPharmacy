package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CompanyDTO {
    private Integer id;

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

    @PastOrPresent
    private LocalDateTime created_date;
}
