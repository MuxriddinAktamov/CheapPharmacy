package com.company.dto;

import com.company.enums.RegionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Past;
import java.time.LocalDate;

@Getter
@Setter
public class RegionFilterDTO {
    @NotEmpty(message = "district is NUll or is Empty")
    private String district;

    @NotEmpty(message = "region is Null or is Empty")
    private RegionStatus region;

    private Integer parentId;
    @Past
    private LocalDate fromDate;
    @FutureOrPresent
    private LocalDate toDate;
}
