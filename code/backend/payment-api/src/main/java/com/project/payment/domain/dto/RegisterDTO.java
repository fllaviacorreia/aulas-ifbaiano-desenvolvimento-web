package com.project.payment.domain.dto;

import com.project.payment.domain.model.ClienteModel;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDTO {
    @NotBlank
    LoginDTO loginDTO;

    @NotBlank
    ClienteModel clienteModel;
}
