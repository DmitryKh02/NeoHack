package com.example.prehack.web.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationUserDTO {

    @NotNull
    @Length(min = 2, max = 30, message = "firstName length must be from 2 to 30")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "firstName must contain only letters a-z and A-Z")
    private String userName;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Length(min = 2, max = 50, message = "firstName length must be from 2 to 30")
    private String password;

}
