//package com.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//public class GestionClientesController {
//
//    @GetMapping("/clientes")
//    public String mostrarGestionClientes() {
//        return "clientes/clientes"; 
//    }
//}


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
        return "clientes/clientes"; // tu vista Thymeleaf
    }

    @PostMapping("/clientes/guardar")
    public String guardarCliente(Cliente cliente) {
        clienteService.guardar(cliente);
        return "redirect:/clientes";
    }
}
