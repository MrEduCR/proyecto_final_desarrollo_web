package com.controller;

import com.domain.Pieza;
import com.services.PiezaService;
import com.services.ServicioPiezaService;
import com.services.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/serviciopieza")
public class ServicioPiezaController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private PiezaService piezaService;

    @Autowired
    private ServicioPiezaService servicioPiezaService;

    @GetMapping
    public String mostrarFormulario(Model model) {
        model.addAttribute("piezas", piezaService.getAllPiezas());
        return "servicioPieza/form"; // templates/servicioPieza/form.html
    }

    @PostMapping("/agregar")
    public String agregarPieza(
            @RequestParam Long servicioId,
            @RequestParam Long piezaId,
            Model model) {

        Pieza pieza = piezaService.getPiezaById(piezaId).orElse(null);
        if (pieza != null) {
            servicioPiezaService.agregarPieza(servicioId, pieza);
        }

        model.addAttribute("piezas", piezaService.getAllPiezas());
        model.addAttribute("mensaje", "Pieza agregada al servicio " + servicioId);
        return "servicioPieza/form";
    }

    @PostMapping("/eliminar")
    public String eliminarPieza(
            @RequestParam Long servicioId,
            @RequestParam Long piezaId,
            Model model) {

        Pieza pieza = piezaService.getPiezaById(piezaId).orElse(null);
        if (pieza != null) {
            servicioPiezaService.eliminarPieza(servicioId, pieza);
        }

        model.addAttribute("piezas", piezaService.getAllPiezas());
        model.addAttribute("mensaje", "Pieza eliminada del servicio " + servicioId);
        return "servicioPieza/form";
    }
}
