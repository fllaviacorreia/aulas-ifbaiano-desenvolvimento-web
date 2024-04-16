package com.project.payment.domain.service;

import com.project.payment.domain.exception.NegocioException;
import com.project.payment.domain.model.ClienteModel;
import com.project.payment.domain.repository.ClienteRepository;

import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class GerenciamentoClienteService {
    private final ClienteRepository clienteRepository;

    @Transactional
    public ClienteModel salvar(ClienteModel clienteModel) {
        boolean emailExistente = clienteRepository
                            .findByEmail(clienteModel.getEmail())
                            .filter(cliente -> !cliente.equals(clienteModel))
                            .isPresent();
        if (emailExistente) {
           throw new NegocioException("email ja em uso");
        }
        return clienteRepository.save(clienteModel);
    }

    @Transactional
    public void excluir(Long id) {
        clienteRepository.deleteById(id);
    }
}
