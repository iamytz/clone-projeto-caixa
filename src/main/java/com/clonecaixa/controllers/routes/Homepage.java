package com.clonecaixa.controllers.routes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
