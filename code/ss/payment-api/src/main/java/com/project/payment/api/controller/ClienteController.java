package com.project.payment.api.controller;

import java.util.List;
import java.util.Optional;

import com.project.payment.domain.repository.ClienteRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.project.payment.domain.model.ClienteModel;

@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {
    private final ClienteRepository clienteRepository;

    //retorna todos os clientes encontrados se não []
    @GetMapping()
    public List<ClienteModel> listAll() {
        return clienteRepository.findAll();
    }

//    @GetMapping("/{nome}")
//    public List<ClienteModel> listByName(@PathVariable String nome) {
//        return clienteRepository.findByNome(nome);
//    }
    // retorna o cliente encontrado pelo id, se não uma mensagem de erro
    @GetMapping("/{id}")
        public Optional<ResponseEntity> findOne(@PathVariable Long id) {
        var client = clienteRepository.findById(id);
         if(client.isEmpty()) {
             return Optional.of(ResponseEntity
                     .status(HttpStatus.NOT_FOUND)
                     .body("Cliente nao encontrado"));
         }

//         return client.map(clienteModel -> ResponseEntity.ok(clienteModel));
         return client.map(ResponseEntity::ok);

         // return clienteRepository.findById(id)
        //          .map(cliente -> ResponseEntity.ok(cliente))
        //          .orElse(ResponseEntity.notFound().build());

        // return clienteRepository.findById(id)
        //          .map(ResponseEntity::ok())
        //          .orElse(ResponseEntity.notFound().build());
    }

    // cadastra um novo cliente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ClienteModel create(@RequestBody ClienteModel cliente){
        return clienteRepository.save(cliente);
    }

    // altera um cliente cadastrado se não retorna erro 404
    @PutMapping("/{id}")
    public ResponseEntity<ClienteModel> update(
            @PathVariable Long id,
            @RequestBody ClienteModel cliente
            ){
        if(!clienteRepository.existsById(id)){
            return ResponseEntity.notFound().build();
        }
        cliente.setId(id);
        cliente = clienteRepository.save(cliente);

        return ResponseEntity.ok(cliente);
    }


}
