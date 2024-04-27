package com.project.payment.api.controller;

import com.project.payment.domain.exception.NegocioException;
import com.project.payment.domain.model.ParcelamentoModel;
import com.project.payment.domain.repository.ParcelamentoRepository;
import com.project.payment.domain.service.GerenciamentoParcelamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/parcelamentos")
public class ParcelamentoController {
    private final ParcelamentoRepository parcelamentoRepository;
    private final GerenciamentoParcelamentoService gerenciamentoParcelamentoService;

    @GetMapping
    public List<ParcelamentoModel> listAll(){
        return parcelamentoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<ResponseEntity<?>> getById(@PathVariable Long id){
        var parcelamento = parcelamentoRepository.findById(id);
        if(parcelamento.isEmpty()){
            throw new NegocioException("parcelamento com id = "+id+" nao encontrado");
        }
        return parcelamento.map(ResponseEntity::ok);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParcelamentoModel save(@RequestBody ParcelamentoModel parcelamentoModel){
        return gerenciamentoParcelamentoService.salvar(parcelamentoModel);
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capture(NegocioException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
