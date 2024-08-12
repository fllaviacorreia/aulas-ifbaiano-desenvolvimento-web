package com.project.payment.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name="client")
public class ClienteModel {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 200)
    private String nome;

    @NotBlank
    @Size(min = 11 , max = 14)
    private String cpf;

    @NotBlank
    @Size(max = 16)
    private String phone;

    @Enumerated(EnumType.STRING)
    private GenderTypes gender;

    private boolean active;

}
