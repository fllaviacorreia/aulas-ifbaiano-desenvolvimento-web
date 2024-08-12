package com.project.payment.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginDTO {
    @NotBlank
    @Size(min = 5, max = 200)
    @Email
    private String email;

    @NotBlank
    @Size(min = 8)
    private String password;
}
