package com.project.payment.domain.service;

import com.project.payment.domain.exception.NegocioException;
import com.project.payment.domain.model.ClienteModel;
import com.project.payment.domain.model.ParcelamentoModel;
import com.project.payment.domain.repository.ClienteRepository;
import com.project.payment.domain.repository.ParcelamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class GerenciamentoParcelamentoService {
    private final ParcelamentoRepository parcelamentoRepository;
    private final ClienteRepository clienteRepository;
    private final GerenciamentoClienteService gerenciamentoClienteService;

    @Transactional
    public ParcelamentoModel salvar(ParcelamentoModel parcelamentoModel) {
        if(parcelamentoModel.getId() != null) {
            throw new NegocioException("Parcelamento nao pode possuir um codigo.");
        }

        ClienteModel clienteModel = gerenciamentoClienteService.findById(parcelamentoModel.getClienteModel().getId());

        parcelamentoModel.setClienteModel(clienteModel);
        parcelamentoModel.setDataCriacao(LocalDateTime.now());
        
        return parcelamentoRepository.save(parcelamentoModel);
    }
}
