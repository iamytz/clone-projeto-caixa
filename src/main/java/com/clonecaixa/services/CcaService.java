package com.clonecaixa.services;

import com.clonecaixa.dtos.CcaRecordDto;
import com.clonecaixa.dtos.CcaSaveRecord;
import com.clonecaixa.entitys.Cca;
import com.clonecaixa.entitys.ClientesHab;
import com.clonecaixa.repositorys.CcaRepository;
import com.clonecaixa.repositorys.ClientesHabRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CcaService {
    //injeção de dependencia
    private final CcaRepository repository;
    public CcaService(CcaRepository repository) {
        this.repository = repository;
    }

    public boolean verifyCca(String cca) {
        return repository.existsByCca(cca);
    }


    public Cca salvarCca(CcaSaveRecord dto) {
        Cca c = new Cca();
        c.setCca(dto.cca());
        c.setCorrespondente(dto.correspondente());
        c.setPrincipalContato(dto.principalContato());
        c.setNumero(dto.numero());

        return repository.save(c);
    }

    public List<Cca> listarTodosCca() {
        return repository.findAll();
    }

    public Cca findById(int id){
        return repository.findById(id);
    }

    public Cca saveById(int id,CcaSaveRecord dto) {
        Cca c = repository.findById(id);
        c.setCca(dto.cca());
        c.setCorrespondente(dto.correspondente());
        c.setPrincipalContato(dto.principalContato());
        c.setNumero(dto.numero());
        return repository.save(c);
    }

    public void deleteById(int id) {
        repository.deleteById(id);
    }

}
