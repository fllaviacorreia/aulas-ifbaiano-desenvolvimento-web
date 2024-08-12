package com.project.payment.domain.service;

import com.project.payment.domain.exception.NegocioException;
import com.project.payment.domain.model.ClienteModel;
import com.project.payment.domain.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GetClientService {
    private final ClienteRepository clienteRepository;

    // get all clients
    public List<ClienteModel> findAll() {
        return clienteRepository.findAll();
    }

    // get clients by full name
    public List<ClienteModel> findByNome(String nome) {
        return clienteRepository.findByNome(nome);
    }

    // get clients with an sequence of caracteres
    public List<ClienteModel> findByNomeContains(String nome) {
        return clienteRepository.findByNomeContains(nome);
    }

    // get client by id
    public ClienteModel findById(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(
                        () -> new NegocioException("Cliente nao encontrado.")
                );
    }

    // get clients by active [true or false]
    public List<ClienteModel> findByActive(Boolean active) {
        return clienteRepository.findByActive(active);
    }

    public ClienteModel findByCpf( String cpf) {
        return clienteRepository.findByCpf(cpf)
                                    .orElseThrow(
                                            () -> new NegocioException("Cliente nao encontrado.")
                                    );
    }
}
