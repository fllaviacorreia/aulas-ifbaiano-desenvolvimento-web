package com.project.payment.domain.dto;

import com.project.payment.domain.model.GenderTypes;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDTO {
    private String name;
    private String phone;
    private String cpf;
    private String email;
    private GenderTypes gender;
    private String password;
    private String authority;
    private Boolean enabled;
}
