package com.project.payment.domain.repository;

import com.project.payment.domain.model.ClienteModel;
import com.project.payment.domain.model.ParcelamentoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParcelamentoRepository extends JpaRepository<ParcelamentoModel, Long> {
    List<ParcelamentoModel> findAllByClienteModel(ClienteModel cliente);
}
