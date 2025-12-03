package com.controller;

import com.domain.Servicio;
import com.services.EstadoServicioService;
import com.services.ServicioService;
import com.services.TipoEquipoService;
import com.services.TipoServicioService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/servicio")
public class ServicioController {

    @Autowired
    private ServicioService servicioService;
    @Autowired
    private TipoEquipoService tipoEquipoService;

    @Autowired
    private TipoServicioService tipoServicioService;

    @Autowired
    private EstadoServicioService estadoServicioService;

    

    @GetMapping
    public String listarServicios(Model model) {
        model.addAttribute("servicios", servicioService.getAllServicios());
        return "servicio/lista"; // templates/servicio/lista.html
    }

    @GetMapping("/servicios-sin-finalizar")
    public String listarServiciosNoFinalizados(Model model) {
        model.addAttribute("serviciosNoFinalizados", servicioService.getServiciosNoFinalizados());
        return "servicio/servicios-sin-finalizar"; // templates/servicio/servicios-sin-finalizar.html
    }

    @GetMapping("/nuevo")
    public String nuevoServicio(Model model) {
        model.addAttribute("servicio", new Servicio());
        model.addAttribute("tiposEquipo", tipoEquipoService.getAllTiposEquipo());
        model.addAttribute("tiposServicio", tipoServicioService.getAllTipoServicio());
        model.addAttribute("estadosServicio", estadoServicioService.getAllEstadosServicio());

        return "servicio/form";
    }

    @GetMapping("/editar/{id}")
    public String editarServicio(@PathVariable Long id, Model model) {

        Servicio servicio = servicioService.getServicioById(id)
                .orElseThrow(() -> new IllegalArgumentException("Servicio no encontrado: " + id));
        model.addAttribute("servicio", servicio);
        model.addAttribute("tiposEquipo", tipoEquipoService.getAllTiposEquipo());
        model.addAttribute("tiposServicio", tipoServicioService.getAllTipoServicio());
        model.addAttribute("estadosServicio", estadoServicioService.getAllEstadosServicio());

        return "servicio/form";
    }

    @PostMapping("/guardar")
    public String guardarServicio(@ModelAttribute("servicio") Servicio servicio) {
        servicioService.saveServicio(servicio);
        return "redirect:/servicio";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarServicio(@PathVariable Long id) {
        servicioService.deleteServicio(id);
        return "redirect:/servicio";
    }

    @GetMapping("/buscar")//es para buscar x id speficifo
    public String buscarServicio(
            @RequestParam(required = false) Long id,
            Model model) {

        if (id == null) {
            return "servicio/buscar-servicio"; // x si el usuario no ha mandado nada
        }

        Optional<Servicio> servicio = servicioService.buscarPorId(id);

        if (servicio.isPresent()) {
            model.addAttribute("servicio", servicio.get());
        } else {
            model.addAttribute("error", "No hay servicios con este ID: " + id);
        }

        return "servicio/buscar-servicio";
    }
}
