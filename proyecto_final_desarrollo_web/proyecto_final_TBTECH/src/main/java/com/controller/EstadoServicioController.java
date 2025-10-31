package com.controller;

import com.domain.EstadoServicio;
import com.services.EstadoServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/estadoservicio")
public class EstadoServicioController {

    @Autowired
    private EstadoServicioService estadoServicioService;

    @GetMapping
    public String listarEstadosServicio(Model model) {
        model.addAttribute("estadosServicio", estadoServicioService.getAllEstadosServicio());
        return "estadoservicio/lista"; // templates/estadoservicio/lista.html
    }

    @GetMapping("/nuevo")
    public String nuevoEstadoServicio(Model model) {
        model.addAttribute("estadoServicio", new EstadoServicio());
        return "estadoservicio/form"; // templates/estadoservicio/form.html
    }

    @GetMapping("/editar/{id}")
    public String editarEstadoServicio(@PathVariable Long id, Model model) {
        EstadoServicio estadoServicio = estadoServicioService.getEstadoServicioById(id)
                .orElseThrow(() -> new IllegalArgumentException("Estado de servicio no encontrado: " + id));
        model.addAttribute("estadoServicio", estadoServicio);
        return "estadoservicio/form";
    }

    @PostMapping("/guardar")
    public String guardarEstadoServicio(@ModelAttribute("estadoServicio") EstadoServicio estadoServicio) {
        estadoServicioService.saveEstadoServicio(estadoServicio);
        return "redirect:/estadoservicio";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarEstadoServicio(@PathVariable Long id) {
        estadoServicioService.deleteEstadoServicio(id);
        return "redirect:/estadoservicio";
    }
}
