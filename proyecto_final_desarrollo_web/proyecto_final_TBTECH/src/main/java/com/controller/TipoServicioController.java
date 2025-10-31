package com.controller;

import com.domain.TipoServicio;
import com.services.TipoServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tiposervicio")//direcion actualizada para el mapping
public class TipoServicioController {

    @Autowired
    private TipoServicioService tipoServicioService;

    @GetMapping
    public String listarTipoServicio(Model model) {
        model.addAttribute("tiposServicio", tipoServicioService.getAllTipoServicio());
        return "tipoServicio/lista"; // templates/tipoServicio/lista.html
    }

    @GetMapping("/nuevo")
    public String nuevoTipoServicio(Model model) {
        model.addAttribute("tipoServicio", new TipoServicio());
        return "tipoServicio/form"; // templates/tipoServicio/form.html
    }

    @GetMapping("/editar/{id}")
    public String editarTipoServicio(@PathVariable Long id, Model model) {
        TipoServicio tipoServicio = tipoServicioService.getTipoServicioById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de servicio no encontrado: " + id));
        model.addAttribute("tipoServicio", tipoServicio);
        return "tipoServicio/form";
    }

    @PostMapping("/guardar")
    public String guardarTipoServicio(@ModelAttribute("tipoServicio") TipoServicio tipoServicio) {
        tipoServicioService.saveTipoServicio(tipoServicio);
        return "redirect:/tiposervicio";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTipoServicio(@PathVariable Long id) {
        tipoServicioService.deleteTipoServicio(id);
        return "redirect:/tiposervicio";
    }
}
