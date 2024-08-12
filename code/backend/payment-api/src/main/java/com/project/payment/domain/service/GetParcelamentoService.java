package com.project.payment.domain.service;

import com.project.payment.domain.exception.NegocioException;
import com.project.payment.domain.model.ClienteModel;
import com.project.payment.domain.model.ParcelamentoModel;
import com.project.payment.domain.repository.ClienteRepository;
import com.project.payment.domain.repository.ParcelamentoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GetParcelamentoService {
    private final ParcelamentoRepository parcelamentoRepository;
    private final ClienteRepository clienteRepository;

    public List<ParcelamentoModel> getAllParcelamentos() {
        return parcelamentoRepository.findAll();
    }

    public Optional<ResponseEntity<ParcelamentoModel>> getParcelamentoById(Long id) {
        var parcelamento = parcelamentoRepository.findById(id);
        if(parcelamento.isEmpty()){
            throw new NegocioException("parcelamento com id = "+id+" nao encontrado");
        }
        return parcelamento.map(ResponseEntity::ok);
    }

    public ResponseEntity<List<ParcelamentoModel>> getParcelamentosByClienteId(Long id) {
        Optional<ClienteModel> clienteModel = clienteRepository.findById(id);
        System.out.println(clienteModel.isPresent());
        if(clienteModel.isPresent()) {
           return listParcelamentos(clienteModel.get());
        }
        throw new NegocioException("cliente nao encontrado");
    }

    public ResponseEntity<List<ParcelamentoModel>> getParcelamentosByClienteCpf(String cpf) {
        Optional<ClienteModel> cliente = clienteRepository.findByCpf(cpf);
        if(cliente.isPresent()) {
            return listParcelamentos(cliente.get());
        }
        throw new NegocioException("cliente nao encontrado");
    }

    private ResponseEntity<List<ParcelamentoModel>> listParcelamentos(ClienteModel clienteModel) {
        List<ParcelamentoModel> compras = parcelamentoRepository.findAllByClienteModel(clienteModel);
        return ResponseEntity.ok(compras);
    }

}
