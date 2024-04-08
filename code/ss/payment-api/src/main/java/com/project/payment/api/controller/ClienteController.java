package com.project.payment.api.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.payment.domain.model.ClienteModel;
@RestController
public class ClienteController {
	
	@GetMapping("/clientes")
	public List<ClienteModel> listar() {
		var cliente1 = new ClienteModel();
        cliente1.setId(1L);
        cliente1.setName("Bruno S");
        cliente1.setEmail("bruno.s@email.com");
        cliente1.setPhone("77 9 0000-1111");

        var cliente2 = new ClienteModel();
        cliente2.setId(2L);
        cliente2.setName("Luana B");
        cliente2.setEmail("luana.b@email.com");
        cliente2.setPhone("77 9 0000-5555");

        return Arrays.asList(cliente1, cliente2);
	}
	
}
