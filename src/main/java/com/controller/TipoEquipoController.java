package com.controller;

import com.domain.TipoEquipo;
import com.services.TipoEquipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tipoequipo")
public class TipoEquipoController {

    @Autowired
    private TipoEquipoService tipoEquipoService;

    @GetMapping
    public String listarTiposEquipo(Model model) {
        model.addAttribute("tiposEquipo", tipoEquipoService.getAllTiposEquipo());
        return "tipoequipo/lista"; // templates/tipoequipo/lista.html
    }

    @GetMapping("/nuevo")
    public String nuevoTipoEquipo(Model model) {
        model.addAttribute("tipoEquipo", new TipoEquipo());
        return "tipoequipo/form"; // templates/tipoequipo/form.html
    }

    @GetMapping("/editar/{id}")
    public String editarTipoEquipo(@PathVariable Long id, Model model) {
        TipoEquipo tipoEquipo = tipoEquipoService.getTipoEquipoById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tipo de Equipo no encontrado: " + id));
        model.addAttribute("tipoEquipo", tipoEquipo);
        return "tipoequipo/form";
    }

    @PostMapping("/guardar")
    public String guardarTipoEquipo(@ModelAttribute("tipoEquipo") TipoEquipo tipoEquipo) {
        tipoEquipoService.saveTipoEquipo(tipoEquipo);
        return "redirect:/tipoequipo";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTipoEquipo(@PathVariable Long id) {
        tipoEquipoService.deleteTipoEquipo(id);
        return "redirect:/tipoequipo";
    }
}
