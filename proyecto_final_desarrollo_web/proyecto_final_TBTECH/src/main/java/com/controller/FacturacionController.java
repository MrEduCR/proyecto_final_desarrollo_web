package com.controller;

import com.domain.Servicio;
import com.domain.Factura;
import com.repository.FacturaRepository;
import com.services.ServicioService;
import com.services.FacturacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/factura")
public class FacturacionController {

    @Autowired
    private ServicioService servicioService;

    @Autowired
    private FacturacionService facturacionService;

    @Autowired
    private FacturaRepository facturaRepository;


    // ✔️ LISTA DE SERVICIOS CON BOTONES GENERAR / VER FACTURA
    // Retorna: /templates/factura/factura-lista.html
    @GetMapping("/lista")
    public String listaServicios(Model model) {

        List<Servicio> servicios = servicioService.getAllServicios();

        Map<Long, Factura> facturasMap = new HashMap<>();

        for (Servicio s : servicios) {
            facturaRepository.findByServicio(s)
                    .ifPresent(factura -> facturasMap.put(s.getServicioId(), factura));
        }

        model.addAttribute("servicios", servicios);
        model.addAttribute("facturasMap", facturasMap);

        // Se usa "factura/factura-lista" para apuntar a la subcarpeta
        return "factura/factura-lista";
    }


    // ✔️ FORMULARIO PARA ELEGIR DESCUENTO
    // Retorna: /templates/factura/factura-generar.html
    @GetMapping("/form-generar/{servicioId}")
    public String mostrarFormularioGenerar(@PathVariable Long servicioId, Model model) {

        Servicio servicio = servicioService.getServicioById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        model.addAttribute("servicio", servicio);
        // Se usa "factura/factura-generar" para apuntar a la subcarpeta
        return "factura/factura-generar";
    }


    // ✔️ PROCESO PARA GENERAR FACTURA
    @PostMapping("/generar")
    public String generarFactura(@RequestParam Long servicioId,
                                 @RequestParam int descuento) {

        facturacionService.generarFactura(servicioId, descuento);

        return "redirect:/factura/lista";
    }


    // ✔️ VER FACTURA YA GENERADA
    // Retorna: /templates/factura/factura-ver.html
    @GetMapping("/ver/{servicioId}")
    public String verFactura(@PathVariable Long servicioId, Model model) {

        Servicio servicio = servicioService.getServicioById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        Factura factura = facturaRepository.findByServicio(servicio)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada para este servicio"));

        model.addAttribute("factura", factura);
        model.addAttribute("servicio", servicio);

        // Se usa "factura/factura-ver" para apuntar a la subcarpeta
        return "factura/factura-ver";
    }
}