package com.clonecaixa.services;

import com.clonecaixa.dtos.*;
import com.clonecaixa.entitys.ClientesHab;
import com.clonecaixa.repositorys.ClientesHabRepository;
import org.springframework.stereotype.Service;

import java.io.PipedOutputStream;
import java.util.List;
import java.util.Locale;

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
                cliente.getVendedorCpfCnpj(),
                cliente.getContaCaixa(),
                cliente.getConformidade());
    }

    public List<HabCcaRecordDto> acharCca(){
        return repository.findAll().stream().map(cliente-> new HabCcaRecordDto(cliente.getProponente(),cliente.getCpf(), cliente.getCca(), cliente.getNumContrato())).toList();
    }



    public ClientesHab salvarByCpf(String cpf, EditCLienteHabRecord dto) {
        ClientesHab cliente = repository.findByCpf(cpf);
        cliente.setDataEntrada(dto.dataEntrada());
        cliente.setProponente(dto.proponente());
        cliente.setCca(dto.cca());
        cliente.setNumContrato(dto.numContrato());
        cliente.setValorFinanciado(dto.valorFinanciado());
        cliente.setStatus(dto.status());
        cliente.setModalidade(dto.modalidade());
        cliente.setReciprocidade(dto.reciprocidade());
        cliente.setIntervenienteQuitante(dto.intervenienteQuitante());
        cliente.setDataDevGarantia(dto.dataDevGarantia());
        cliente.setObs(dto.obs());
        cliente.setNomeVendedor(dto.nomeVendedor());
        cliente.setVendedorCpfCnpj(dto.vendedorCpfCnpj());
        cliente.setContaCaixa(dto.contaCaixa());
        cliente.setConformidade(dto.conformidade());
        return repository.save(cliente);

    }


    public ClientesHab salvarCliente(ClientesHabRecordDto dto) {
        ClientesHab cliente = new ClientesHab();
        cliente.setDataEntrada(dto.dataEntrada());
        cliente.setProponente(dto.proponente());
        cliente.setCpf(dto.cpf());
        cliente.setCca(dto.cca());
        cliente.setNumContrato(dto.numContrato());
        cliente.setValorFinanciado(dto.valorFinanciado());
        cliente.setStatus(dto.reciprocidade());
        cliente.setModalidade(dto.modalidade());
        cliente.setReciprocidade(dto.reciprocidade());
        cliente.setIntervenienteQuitante(dto.intervenienteQuitante());
        cliente.setDataDevGarantia(dto.dataDevGarantia());
        cliente.setObs(dto.obs());
        cliente.setNomeVendedor(dto.nomeVendedor());
        cliente.setVendedorCpfCnpj(dto.vendedorCpfCnpj());
        cliente.setContaCaixa(dto.contaCaixa());
        cliente.setConformidade(dto.conformidade());
        return repository.save(cliente);
    }

    public boolean returnValidCca(String cca) {
        return repository.existsByCca(cca);
    }

    public List<ClientesHab> filtroMomentoVendedor(FiltroMomentoVendedorRecord dto) {
        List<String> listaStatus = dto.statuses();
        List<String> listaContas = dto.contas();
        List<String> listaDev = dto.dev();
        boolean statusesExists = listaStatus.isEmpty();
        boolean contasExists = listaContas.isEmpty();

        boolean devCom = listaDev.isEmpty() || listaDev.contains("com");
        boolean devSem = listaDev.isEmpty() || listaDev.contains("sem");

        if (statusesExists) {
            listaStatus = List.of("_sem_filtro_status_");
        }
        if (contasExists) {
            listaContas = List.of("_sem_filtr_contas_");
        }

        return repository.buscarPorFiltro(dto.startDate(),dto.endDate(),listaStatus,statusesExists,listaContas,contasExists,devCom,devSem);
    }

    public List<EsteiraHabRecord> filtroEsteira(FiltroEsteiraHabRecord dto) {
        //mapeando atributos
        String cca = dto.cca();
        List<String>modalidades = dto.modalidade();
        String modalidade;
        List<String>conformidades = dto.conformidade();

        if (modalidades.isEmpty() || modalidades.size()==2) {
            modalidade = "";
        } else {
            modalidade = modalidades.getFirst();
        }

        if (conformidades.isEmpty()) {
            conformidades = List.of("conforme","naoConforme");
        }

        List<ClientesHab> clientes = repository.buscaFiltroEsteira(cca,modalidade,conformidades);

        return clientes.stream().map(c-> new EsteiraHabRecord(c.getDataEntrada(),c.getProponente(),c.getCpf(),c.getCca(),c.getNumContrato(),c.getModalidade(),c.getConformidade(),c.getValorFinanciado())).toList();

    }




}
