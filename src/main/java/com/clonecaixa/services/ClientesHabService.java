package com.clonecaixa.services;

import com.clonecaixa.dtos.HabCcaRecordDto;
import com.clonecaixa.dtos.ClientesHabRecordDto;
import com.clonecaixa.entitys.ClientesHab;
import com.clonecaixa.repositorys.ClientesHabRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientesHabService {
    //injelção de dependencia (metodo construtor)
    public ClientesHabRepository repository;

    public ClientesHabService(ClientesHabRepository repository) {
        this.repository = repository;
    }

    public ClientesHabRecordDto acharPorCpf(String cpf){
        ClientesHab cliente = repository.findByCpf(cpf);
        return new ClientesHabRecordDto(cliente.getDataEntrada(),
                cliente.getProponente(),
                cliente.getCpf(),
                cliente.getCca(),
                cliente.getNumContrato(),
                cliente.getValorFinanciado(),
                cliente.getStatus(),
                cliente.getModalidade(),
                cliente.getReciprocidade(),
                cliente.getIntervenienteQuitante(),
                cliente.getDataDevGarantia(),
                cliente.getObs(),
                cliente.getNomeVendedor(),
                cliente.getVenededorCpfCnpj(),
                cliente.getContaCaixa());
    }

    public List<HabCcaRecordDto> acharCca(){
        return repository.findAll().stream().map(cliente-> new HabCcaRecordDto(cliente.getProponente(),cliente.getCpf(), cliente.getCca(), cliente.getNumContrato())).toList();
    }

}
