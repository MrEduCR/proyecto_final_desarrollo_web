package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GestionClientesController {

    @GetMapping("/clientes")
    public String mostrarGestionClientes() {
        return "clientes/clientes"; 
    }
}
