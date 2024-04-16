package com.project.payment.api.controller;

import java.util.List;
import java.util.Optional;

import com.project.payment.domain.exception.NegocioException;
import com.project.payment.domain.repository.ClienteRepository;
import com.project.payment.domain.service.GerenciamentoClienteService;
import com.project.payment.domain.model.ClienteModel;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;

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
    public List<ClienteModel> findByNome(@RequestParam String nome) {
        return clienteRepository.findByNome(nome);
    }
    
    @GetMapping("/findWithNome")
    public List<ClienteModel> findByNomeContains(@RequestParam String nome) {
        return clienteRepository.findByNomeContains(nome);
    }
    // retorna o cliente encontrado pelo id, se não uma mensagem de erro
    @GetMapping("/{id}")
    public Optional<ResponseEntity<?>> findOne(@PathVariable Long id) {
        var client = clienteRepository.findById(id);
        if (client.isEmpty()) throw new NegocioException("Cliente nao encontrado.");
        return client.map(ResponseEntity::ok);
    }

    // cadastra um novo cliente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteModel create(@Valid @RequestBody ClienteModel cliente) {
        return gerenciamentoClienteService.salvar(cliente);
    }

    // altera um cliente cadastrado se não retorna erro 404
    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @PathVariable Long id,
            @Valid @RequestBody ClienteModel cliente) {
        if (!clienteRepository.existsById(id)) {
            throw new NegocioException("Cliente nao encontrado.");
        }
        cliente.setId(id);
        cliente = gerenciamentoClienteService.salvar(cliente);

        return ResponseEntity.ok(cliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new NegocioException("Cliente nao encontrado.");
        }
        gerenciamentoClienteService.excluir(id);
        return ResponseEntity.ok("Cliente excluido com sucesso");
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handle(Exception exception) {
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

}
