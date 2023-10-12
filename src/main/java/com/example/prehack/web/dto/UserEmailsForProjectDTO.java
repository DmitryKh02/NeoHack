package com.example.prehack.web.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEmailsForProjectDTO {

    @NotNull
    @Email
    private String email;
}
