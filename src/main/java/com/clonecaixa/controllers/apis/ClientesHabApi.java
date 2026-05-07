package com.clonecaixa.controllers.apis;

import com.clonecaixa.dtos.*;
import com.clonecaixa.entitys.Cca;
import com.clonecaixa.entitys.ClientesHab;
import com.clonecaixa.services.CcaService;
import com.clonecaixa.services.ClientesHabService;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api")
public class ClientesHabApi {

    private final ClientesHabService service;
    private final CcaService ccaService;
    public ClientesHabApi(ClientesHabService service, CcaService ccaService) {
        this.service = service;
        this.ccaService = ccaService;
    }

    @GetMapping("/clientes/{cpf}")
    public ClientesHabRecordDto retornandoCpf(@PathVariable String cpf) {
        return service.acharPorCpf(cpf);
    }

    @GetMapping("/clientes")
    public List<HabCcaRecordDto> retornancoCca(){
        return service.acharCca();
    }
    @PostMapping("/post/cliente")
    public ClientesHab addCliente(@RequestBody ClientesHabRecordDto dto) {
        return service.salvarCliente(dto);
    }
    @PostMapping("/momento_vendedor")
    public List<ClientesHab> returMomentVendedor(@RequestBody FiltroMomentoVendedorRecord dto) {
        return service.filtroMomentoVendedor(dto);
    }
    @PostMapping("/atualizar_cliente/{cpf}")
    public ClientesHab editCliente(@PathVariable String cpf,@RequestBody EditCLienteHabRecord dto) {
        return service.salvarByCpf(cpf,dto);
    }
    @GetMapping("/validar-cca/{cca}")
    public boolean verifyCca(@PathVariable String cca) {
        return ccaService.verifyCca(cca);
    }
    @PostMapping("/get_data2")
    public List<EsteiraHabRecord> esteiraHab(@RequestBody FiltroEsteiraHabRecord dto) {
        return service.filtroEsteira(dto);
    }
    @PostMapping("/post/cca")
    public Cca salvarCca(@RequestBody CcaSaveRecord dto) {
        return ccaService.salvarCca(dto);
    }
    @GetMapping("/get/cca")
    public List<Cca> todosCca() {
        return ccaService.listarTodosCca();

    }
    @GetMapping("/get/cca/{id}")
    public Cca findById(@PathVariable int id) {
        return ccaService.findById(id);
    }
    @PutMapping("/put/cca/{id}")
    public Cca salvarPorId(@PathVariable int id, @RequestBody CcaSaveRecord dto) {
        return ccaService.saveById(id,dto);
    }
    @DeleteMapping("/delete/cca/{id}")
    public void deleteById(@PathVariable int id) {
        ccaService.deleteById(id);
    }

}
