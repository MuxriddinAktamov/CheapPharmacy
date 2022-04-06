package com.company.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;


@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AttachDTO {
    private Integer id;
    @NotEmpty(message = "url is null or empty")
    private String url;
    @NotEmpty(message = "path is null or empty")
    private String path;
    @NotEmpty(message = "size is null or empty")
    private long size;
    @NotEmpty(message = "type is null or empty")
    private String type;
    @NotEmpty(message = "token is null or empty")
    private String token;
    @PastOrPresent
    private LocalDateTime createdDate;

}
