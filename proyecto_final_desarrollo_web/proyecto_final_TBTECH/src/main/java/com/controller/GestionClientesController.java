package com.controller;

import com.domain.Cliente;
import com.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class GestionClientesController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/clientes")
    public String mostrarFormulario(Cliente cliente) {
        return "clientes/clientes"; 
    }

    @PostMapping("/clientes/guardar")
    public String guardarCliente(Cliente cliente) {
        clienteService.guardar(cliente);
        return "redirect:/clientes";
    }
}
