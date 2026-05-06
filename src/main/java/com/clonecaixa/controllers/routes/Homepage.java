package com.clonecaixa.controllers.routes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("")
public class Homepage {
    @GetMapping("")
    public String center() {
        return "central";
    }

    @GetMapping("homepage")
    public String homepage() {
        return "homepage";
    }

    @GetMapping("buscar_cliente")
    public String buscarCliente() {
        return "buscar_cliente";
    }

    @GetMapping("/cadastro")
    public String cadastro() {
        return "cadastro";
    }
    @GetMapping("buscar_cliente/edit/{cpf}")
    public String editarCliente() {
        return "editar_cliente_novo";
    }
    @GetMapping("/momento_vendedor")
    public String momentoVendedor() {
        return "momento_vendedor";
    }
    @GetMapping("/cadastro_cca")
    public String cadastroCca() {
        return "cadastro_cca";
    }
    @GetMapping("/esteira_habitacao")
    public String esteiraHab() {
        return "esteira_hab";
    }

}
