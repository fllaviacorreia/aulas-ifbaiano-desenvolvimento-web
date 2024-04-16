package com.project.payment.domain.model;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name="client")
@Data
// adicionado
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClienteModel {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 5, max = 200)
    private String nome;

    @NotBlank
    @Email
    @Size(max = 250)
    private String email;

    @NotBlank
    @Size(max = 16)
    private String phone;
}
