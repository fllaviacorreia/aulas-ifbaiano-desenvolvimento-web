package com.project.payment.domain.service;

import com.project.payment.domain.exception.NegocioException;
import com.project.payment.domain.model.ClienteModel;
import com.project.payment.domain.model.ParcelamentoModel;
import com.project.payment.domain.repository.ParcelamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class GerenciamentoParcelamentoService {
    private final ParcelamentoRepository parcelamentoRepository;
    private final GetClientService getClientService;

    @Transactional
    public ParcelamentoModel salvar(ParcelamentoModel parcelamentoModel) {
        ClienteModel clienteModel;

        if(parcelamentoModel.getClienteModel().getCpf() == null || parcelamentoModel.getClienteModel().getCpf().isEmpty()) {
            throw new NegocioException("Parcelamento deve pertencer a um cliente.");
        }else{
            String cpf = parcelamentoModel.getClienteModel().getCpf();
            clienteModel = getClientService.findByCpf(cpf);
        }

        parcelamentoModel.setActive(true);
        parcelamentoModel.setClienteModel(clienteModel);
        parcelamentoModel.setDataCriacao(LocalDateTime.now());

        return parcelamentoRepository.save(parcelamentoModel);
    }

    @Transactional
    public ParcelamentoModel inativar(Long id) {
        ParcelamentoModel parcelamentoModel = parcelamentoRepository.findById(id).get();
        parcelamentoModel.setActive(false);
        parcelamentoModel.setDataAtualizacao(LocalDateTime.now());
        return parcelamentoRepository.save(parcelamentoModel);
    }

    @Transactional
    public ParcelamentoModel reativar(Long id) {
        ParcelamentoModel parcelamentoModel = parcelamentoRepository.findById(id).get();
        parcelamentoModel.setActive(true);
        parcelamentoModel.setDataAtualizacao(LocalDateTime.now());
        return parcelamentoRepository.save(parcelamentoModel);
    }



}
