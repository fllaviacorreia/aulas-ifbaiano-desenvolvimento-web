package com.project.payment.domain.service;

import com.project.payment.domain.exception.NegocioException;
import com.project.payment.domain.model.ClienteModel;
import com.project.payment.domain.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@AllArgsConstructor
@Service
public class GerenciamentoClienteService {
    private final ClienteRepository clienteRepository;

    // create or update client
    @Transactional
    public ClienteModel save(ClienteModel clienteModel) {
        boolean cpfExistente = clienteRepository
                            .findByCpf(clienteModel.getCpf())
                            .filter(cliente -> !cliente.equals(clienteModel))
                            .isPresent();
        if (cpfExistente) {
           throw new NegocioException("cpf já em uso");
        }

        return clienteRepository.save(clienteModel);
    }

    // delete client (innactive)
    @Transactional
    public void delete(Long id) {
        Optional<ClienteModel> cliente = clienteRepository.findById(id);
        cliente.get().setActive(false);
        clienteRepository.save(cliente.get());
    }

    // re-active clients
    @Transactional
    public void activate(Long id) {
        Optional<ClienteModel> cliente = clienteRepository.findById(id);
        cliente.get().setActive(true);
        clienteRepository.save(cliente.get());
    }
}
