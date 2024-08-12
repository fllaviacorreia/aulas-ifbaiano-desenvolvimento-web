package com.project.payment.api.controller;

import com.project.payment.domain.exception.NegocioException;
import com.project.payment.domain.model.ClienteModel;
import com.project.payment.domain.repository.ClienteRepository;
import com.project.payment.domain.service.GerenciamentoClienteService;
import com.project.payment.domain.service.GetClientService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final GerenciamentoClienteService gerenciamentoClienteService;
    private final ClienteRepository clienteRepository;
    private final GetClientService getClientService;

    // retorna todos os clientes encontrados, se não []
    @GetMapping()
    @Secured({"ADMIN"})
    public List<ClienteModel> listAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("Authorities: " + authentication.getAuthorities());
        return getClientService.findAll();
    }

    @GetMapping("/actives")
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public List<ClienteModel> listAllActives(){
            return getClientService.findByActive(true);
    }

    @GetMapping("/inactives")
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public List<ClienteModel> listAllInactives(){
            return getClientService.findByActive(false);
    }

    @GetMapping("/findByNome")
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public List<ClienteModel> listByName(Authentication authentication, @RequestParam String nome) {
            return getClientService.findByNome(nome);
    }

    @GetMapping("/findWithNome")
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public List<ClienteModel> listWithName(Authentication authentication, @RequestParam String nome) {
            return getClientService.findByNomeContains(nome);
    }

    // retorna o cliente encontrado pelo id, se não uma mensagem de erro
    @GetMapping("/{id}")
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public ClienteModel findOne(@PathVariable Long id) {
            return getClientService.findById(id);
    }

    @GetMapping("/email/{cpf}")
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public ClienteModel findByCpf(@PathVariable String cpf) {
        return getClientService.findByCpf(cpf);
    }

    // cadastra um novo cliente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public ClienteModel create(@Valid @RequestBody ClienteModel cliente) {
        return gerenciamentoClienteService.save(cliente);
    }

    // altera um cliente cadastrado se não retorna erro 404
    @PutMapping
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public ResponseEntity<ClienteModel> update(
            @Valid @RequestBody ClienteModel cliente) {
        if (!clienteRepository.existsById(cliente.getId())) {
            throw new NegocioException("cliente com "+cliente.getId()+"nao encontrado");
        }

        cliente = gerenciamentoClienteService.save(cliente);

        return ResponseEntity.ok(cliente);
    }

    // desativa um cliente
    @DeleteMapping("/{id}")
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public ResponseEntity<ClienteModel> delete(@PathVariable Long id) {
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        gerenciamentoClienteService.delete(id);

        return ResponseEntity.ok().build();
    }

    //re-ativa um cliente
    @PatchMapping("/{id}")
    @Secured({"ADMIN", "IS_AUTHENTICATED_FULLY"})
    public ResponseEntity<ClienteModel> reactive(@PathVariable Long id){
        if (!clienteRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        gerenciamentoClienteService.activate(id);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<String> capture(NegocioException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
