package com.company.dto;

import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Getter
@Setter
public class ProfileFilterDTO {
    @NotEmpty(message = "name is Null or is Empty")
    private String name;

    @NotEmpty(message = "surname is Null or is Empty")
    private String surname;

    @NotEmpty(message = "Login is Null or is Empty")
    @Size(min = 5, max = 15, message = "Login 5-15 oralig'ida bo'lsa kerak")
    private String login;

    @NotEmpty(message = "password is Null or is Empty")
    @Size(min = 5, max = 15, message = "password 5-15 oralig'ida bo'lsa kerak")
    private String pswd;

    @NotEmpty(message = "Email is NUll or is Empty")
    @Email
    private String email;

    @NotNull(message = "aptekaId is null")
    private Integer aptekaId;

    @NotEmpty(message = "Email is NUll or is Empty")
    @Size(min = 12, max = 13, message = "Phone 12 -13 oralig'ida emas")
    private String phone;

    @NotEmpty(message = "role is empty or is null")
    private ProfileRole role;

    @NotEmpty(message = "status is Null or is Empty")
    private ProfileStatus status;
    @Past
    private LocalDate fromDate;
    @FutureOrPresent
    private LocalDate toDate;

}
