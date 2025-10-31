package com.controller;

import com.domain.Pieza;
import com.services.PiezaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pieza")
public class PiezaController {

    @Autowired
    private PiezaService piezaService;

    @GetMapping
    public String listarPiezas(Model model) {
        model.addAttribute("piezas", piezaService.getAllPiezas());
        return "pieza/lista"; // templates/pieza/lista.html
    }

    @GetMapping("/nuevo")
    public String nuevaPieza(Model model) {
        model.addAttribute("pieza", new Pieza());
        return "pieza/form"; // templates/pieza/form.html
    }

    @GetMapping("/editar/{id}")
    public String editarPieza(@PathVariable Long id, Model model) {
        Pieza pieza = piezaService.getPiezaById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pieza no encontrada: " + id));
        model.addAttribute("pieza", pieza);
        return "pieza/form";
    }

    @PostMapping("/guardar")
    public String guardarPieza(@ModelAttribute("pieza") Pieza pieza) {
        piezaService.savePieza(pieza);
        return "redirect:/pieza";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarPieza(@PathVariable Long id) {
        piezaService.deletePieza(id);
        return "redirect:/pieza";
    }
}
