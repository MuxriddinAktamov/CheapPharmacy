package com.company.dto;

import com.company.enums.RegionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;


@Setter
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionDTO {
    private Integer id;

    @NotEmpty(message = "district is NUll or is Empty")
    private String district;

    @NotEmpty(message = "region is Null or is Empty")
    private RegionStatus region;

    private Integer parentId;

    @Positive
    private LocalDateTime created_date;

}
