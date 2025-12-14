package com.controller;

import com.domain.Cliente;
import com.services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

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

    @GetMapping("/clientes/buscar")
    public String mostrarFormularioBusqueda() {
        return "clientes/buscarCliente";
    }


    @PostMapping("/clientes/buscar")
    public String buscarCliente(@RequestParam("cedula") int cedula, Model model) {
        var cliente = clienteService.buscarPorCedula(cedula);
        if (cliente.isPresent()) {
            model.addAttribute("cliente", cliente.get());
        } else {
            model.addAttribute("error", "Cliente no encontrado");
        }
        return "clientes/buscarCliente";
    }
}

