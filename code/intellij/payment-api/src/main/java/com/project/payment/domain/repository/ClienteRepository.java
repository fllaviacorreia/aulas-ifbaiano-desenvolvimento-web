package com.project.payment.domain.repository;

import com.project.payment.domain.model.ClienteModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<ClienteModel, Long> {
    List<ClienteModel> findByNome(String nome);
    List<ClienteModel> findByNomeContains(String nome);
    Optional<ClienteModel> findByEmail(String email);
}
