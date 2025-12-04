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
//probar otra vez a ver queo que con lo del pdf
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;

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
    
    @GetMapping("/descargar/{servicioId}")
    public void descargarFacturaPDF(@PathVariable Long servicioId, HttpServletResponse response) throws Exception {

        Servicio servicio = servicioService.getServicioById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        Factura factura = facturaRepository.findByServicio(servicio)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada para este servicio"));

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=factura_" + factura.getFacturaId() + ".pdf");
        OutputStream out = response.getOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, out);
        document.open();
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Factura N° " + factura.getFacturaId(), titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        document.add(new Paragraph(" ")); 
        Font sectionFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        document.add(new Paragraph("Datos del Cliente", sectionFont));
        document.add(new Paragraph("Nombre: " + factura.getServicio().getCliente().getNombre()));
        document.add(new Paragraph("Correo: " + factura.getServicio().getCliente().getCorreo()));
        document.add(new Paragraph("Teléfono: " + factura.getServicio().getCliente().getTelefono()));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Detalles del Servicio", sectionFont));
        document.add(new Paragraph(
                "Tipo de Servicio: " + factura.getServicio().getTipoServicio().getTipoServicioDescripcion()));
        document.add(new Paragraph("Equipo: " + factura.getServicio().getTipoEquipo().getTipoEquipoDescripcion()));
        document.add(new Paragraph("Problema: " + factura.getServicio().getDescripcionProblema()));
        document.add(new Paragraph("Fecha ingreso: " + factura.getServicio().getFechaIngresoEquipo()));
        document.add(new Paragraph(" "));
        if (!servicio.getPiezas().isEmpty()) {
            document.add(new Paragraph("Piezas utilizadas", sectionFont));
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.addCell("Descripción");
            table.addCell("Precio");
            servicio.getPiezas().forEach(pieza -> {
                table.addCell(pieza.getPiezaDescripcion());
                table.addCell("₡ " + pieza.getPrecioPieza());
            });
            document.add(table);
            document.add(new Paragraph(" "));
        }
        document.add(new Paragraph("Resumen de Cargos", sectionFont));
        document.add(new Paragraph("Subtotal piezas: ₡ " + factura.getSubtotalPiezas()));
        document.add(new Paragraph("Mano de obra: ₡ " + factura.getSubtotalManoObra()));
        document.add(new Paragraph("Descuento aplicado: ₡ " + factura.getDescuentoAplicado()));
        document.add(new Paragraph("Impuestos: ₡ " + factura.getImpuestosAplicados()));
        document.add(new Paragraph("Total final: ₡ " + factura.getTotalFinal()));

        document.close();
        out.close();
    }
}