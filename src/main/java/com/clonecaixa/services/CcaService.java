package com.clonecaixa.services;

import com.clonecaixa.dtos.CcaRecordDto;
import com.clonecaixa.repositorys.CcaRepository;
import org.springframework.stereotype.Service;

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
}
