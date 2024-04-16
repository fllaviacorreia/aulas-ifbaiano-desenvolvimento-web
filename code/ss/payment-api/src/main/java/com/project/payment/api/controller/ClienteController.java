package com.project.payment.api.controller;

import java.util.List;
import java.util.Optional;

import com.project.payment.domain.exception.NegocioException;
import com.project.payment.domain.service.GerenciamentoClienteService;
import jakarta.validation.Valid;

import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.payment.domain.repository.ClienteRepository;
import com.project.payment.domain.model.ClienteModel;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final GerenciamentoClienteService gerenciamentoClienteService;
    private final ClienteRepository clienteRepository;

    // retorna todos os clientes encontrados, se não []
    @GetMapping()
    public List<ClienteModel> listAll() {

        return clienteRepository.findAll();
    }

    @GetMapping("/findByNome")
    public List<ClienteModel> listByName(@RequestParam String nome) {
        return clienteRepository.findByNome(nome);
    }

    @GetMapping("/findWithNome")
    public List<ClienteModel> listWithName(@RequestParam String nome) {
        return clienteRepository.findByNomeContains(nome);
    }

    // retorna o cliente encontrado pelo id, se não uma mensagem de erro
    @GetMapping("/{id}")
    public Optional<ResponseEntity> findOne(@PathVariable Long id) {
        var client = clienteRepository.findById(id);
        if (client.isEmpty()) {

                throw new NegocioException("cliente com id = "+id+" nao encontrado");

//            return Optional.of(ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body("Cliente nao encontrado"));
        }

        //op 1
        // return client.map(clienteModel -> ResponseEntity.ok(clienteModel));
        return client.map(ResponseEntity::ok);

        //op 2
        // return clienteRepository.findById(id)
        // .map(cliente -> ResponseEntity.ok(cliente))
        // .orElse(ResponseEntity.notFound().build());

        //op 3
        // return clienteRepository.findById(id)
        // .map(ResponseEntity::ok())
        // .orElse(ResponseEntity.notFound().build());
    }

    // cadastra um novo cliente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteModel create(@Valid @RequestBody ClienteModel cliente) {
        return gerenciamentoClienteService.salvar(cliente);
    }

    // altera um cliente cadastrado se não retorna erro 404
    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> update(
            @PathVariable Long id,
            @Valid @RequestBody ClienteModel cliente) {
        if (!clienteRepository.existsById(id)) {
            throw new NegocioException("cliente com "+id+"nao encontrado");
        }

        cliente.setId(id);
        cliente = gerenciamentoClienteService.salvar(cliente);

        return ResponseEntity.ok(cliente);
    }

    // remove um cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<ClienteModel> delete(@PathVariable Long id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        gerenciamentoClienteService.excluir(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capture(NegocioException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
