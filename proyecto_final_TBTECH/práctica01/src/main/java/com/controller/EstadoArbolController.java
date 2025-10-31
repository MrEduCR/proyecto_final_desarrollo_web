package com.controller;

import com.domain.EstadoArbol;
import com.services.EstadoArbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estado")
public class EstadoArbolController {

    @Autowired
    private EstadoArbolService estadoService;

    @GetMapping
    public String listarEstados(Model model) {
        model.addAttribute("estados", estadoService.getAllEstados());
        return "estado/lista"; // templates/estado/lista.html
    }

    @GetMapping("/nuevo")
    public String nuevoEstado(Model model) {
        model.addAttribute("estado", new EstadoArbol()); //ya esta en los imports
        return "estado/form"; // templates/estado/form.html
    }

    @GetMapping("/editar/{id}")
    public String editarEstado(@PathVariable Long id, Model model) {
        EstadoArbol estado = estadoService.getEstadoById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estado no encontrado: " + id));
        model.addAttribute("estado", estado);
        return "estado/form";
    }

    @PostMapping("/guardar")
    public String guardarEstado(@ModelAttribute("estado") EstadoArbol estadoArbol) {
        estadoService.saveEstado(estadoArbol);
        return "redirect:/estado";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEstado(@PathVariable Long id) {
        estadoService.deleteEstado(id);
        return "redirect:/estado";
    }
   
}
