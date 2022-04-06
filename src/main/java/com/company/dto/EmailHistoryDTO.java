package com.company.dto;

import com.company.enums.EmailUsedStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.time.LocalDateTime;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailHistoryDTO {
    private Integer id;
    private String toEmail;
    private String fromEmail;
    private EmailUsedStatus status;
    private LocalDateTime used_Date;
    private LocalDateTime crated_date;

}
