package com.project.payment.domain.dto;

import com.project.payment.domain.model.RolesUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    @NotBlank
    @Size(min = 5, max = 200)
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    @Size(min = 5, max = 200)
    private String nome;

    @NotBlank
    @Size(max = 16)
    private String phone;

    @NotBlank
    @Size(min = 11, max = 14)
    private String cpf;
}
