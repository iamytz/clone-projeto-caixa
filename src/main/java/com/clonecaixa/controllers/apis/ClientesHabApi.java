package com.clonecaixa.controllers.apis;

import com.clonecaixa.dtos.ClientesHabRecordDto;
import com.clonecaixa.dtos.HabCcaRecordDto;
import com.clonecaixa.repositorys.ClientesHabRepository;
import com.clonecaixa.services.ClientesHabService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientesHabApi {

    private final ClientesHabService service;
    public ClientesHabApi(ClientesHabService service) {
        this.service = service;
    }

    @GetMapping("/clientes/{cpf}")
    public ClientesHabRecordDto retornandoCpf(@PathVariable String cpf) {
        return service.acharPorCpf(cpf);
    }

    @GetMapping("/clientes")
    public List<HabCcaRecordDto> retornancoCca(){
        return service.acharCca();
    }
}
