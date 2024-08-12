package com.project.payment.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateProfileDTO {
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 5, max = 200)
    private String nome;

    @NotBlank
    @Size(max = 16)
    private String phone;

}
