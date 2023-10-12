package com.example.prehack.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationUserDTO {
    @NotNull
    @Email
    private String email;

    @NotNull
    @Length(min = 2, max = 50, message = "firstName length must be from 2 to 30")
    private String password;
}
