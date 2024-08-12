package com.project.payment.domain.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "parcelamento")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ParcelamentoModel {
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ClienteModel clienteModel;

    @NotBlank
    @Size(max = 150)
    private String descricao;

    @NotNull
    private BigDecimal valorTotal;

    @NotNull
    private Integer quantidadeParcelas;

    private boolean active;

    private LocalDateTime dataCriacao;

    @Column(name = "updatedAt")
    private LocalDateTime dataAtualizacao;
}
