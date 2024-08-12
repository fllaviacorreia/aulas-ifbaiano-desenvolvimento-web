package com.project.payment.api.controller;

import com.project.payment.domain.exception.NegocioException;
import com.project.payment.domain.model.ParcelamentoModel;
import com.project.payment.domain.service.GerenciamentoParcelamentoService;
import com.project.payment.domain.service.GetParcelamentoService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/parcelamentos")
public class ParcelamentoController {
    private final GerenciamentoParcelamentoService gerenciamentoParcelamentoService;
    private final GetParcelamentoService getParcelamentoService;

    @GetMapping
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public List<ParcelamentoModel> listAll(){
        return getParcelamentoService.getAllParcelamentos();
    }

    @GetMapping("/{id}")
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public Optional<ResponseEntity<ParcelamentoModel>> getById(@PathVariable Long id){
        return getParcelamentoService.getParcelamentoById(id);
    }

    @GetMapping("/clients/id/{id}")
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public ResponseEntity<List<ParcelamentoModel>> getByClientId(@PathVariable Long id){
        return getParcelamentoService.getParcelamentosByClienteId(id);
    }

    @GetMapping("/clients/cpf/{cpf}")
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public ResponseEntity<List<ParcelamentoModel>> getByClientCpf(@PathVariable String cpf){
        return getParcelamentoService.getParcelamentosByClienteCpf(cpf);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public ParcelamentoModel save(@Valid @RequestBody ParcelamentoModel parcelamentoModel){
        return gerenciamentoParcelamentoService.salvar(parcelamentoModel);
    }

    @PutMapping("/{id}")
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public ParcelamentoModel update(@PathVariable Long id, @Valid @RequestBody ParcelamentoModel parcelamentoModel){
        if(getParcelamentoService.getParcelamentoById(id).isPresent()){
            parcelamentoModel.setId(id);
            return gerenciamentoParcelamentoService.salvar(parcelamentoModel);
        }

        throw new NegocioException("parcelamento nao encontrado");
    }

    @PutMapping("/reactive/{id}")
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public ParcelamentoModel reativar(@PathVariable Long id){
        if(getParcelamentoService.getParcelamentoById(id).isPresent()){
            return gerenciamentoParcelamentoService.reativar(id);
        }

        throw new NegocioException("parcelamento nao encontrado");
    }

    @PutMapping("/inactive/{id}")
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public ParcelamentoModel inativar(@PathVariable Long id){
        if(getParcelamentoService.getParcelamentoById(id).isPresent()){
            return gerenciamentoParcelamentoService.reativar(id);
        }

        throw new NegocioException("parcelamento nao encontrado");
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capture(NegocioException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
