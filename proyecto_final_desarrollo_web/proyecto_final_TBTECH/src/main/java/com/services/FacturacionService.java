package com.services;

import com.domain.Factura;
import com.domain.Servicio;
import com.repository.FacturaRepository;
import com.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class FacturacionService {

    @Autowired
    private FacturaRepository facturaRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    // Impuesto fijo 13%
    private final BigDecimal TASA_IMPUESTO = new BigDecimal("0.13");

    @Transactional
    public Factura generarFactura(Long servicioId, int descuentoPorcentaje) {

        Servicio servicio = servicioRepository.findById(servicioId)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));

        if (facturaRepository.findByServicio(servicio).isPresent()) {
            throw new RuntimeException("El servicio ya ha sido facturado.");
        }

        // Subtotales
        BigDecimal subtotalPiezas = calcularSubtotalPiezas(servicio);
        BigDecimal subtotalManoObra = servicio.getTipoServicio().getPrecioBase();

        BigDecimal subtotalNeto = subtotalPiezas.add(subtotalManoObra);

        // Descuento (redondeado a 2 decimales)
        BigDecimal descuento = subtotalNeto
                .multiply(BigDecimal.valueOf(descuentoPorcentaje).divide(BigDecimal.valueOf(100)))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal subtotalConDescuento = subtotalNeto.subtract(descuento)
                .setScale(2, RoundingMode.HALF_UP);

        // Impuesto (13%)
        BigDecimal impuestos = subtotalConDescuento
                .multiply(TASA_IMPUESTO)
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal totalFinal = subtotalConDescuento.add(impuestos)
                .setScale(2, RoundingMode.HALF_UP);

        // Construcción de factura
        Factura factura = new Factura();
        factura.setServicio(servicio);
        factura.setSubtotalPiezas(subtotalPiezas);
        factura.setSubtotalManoObra(subtotalManoObra);
        factura.setDescuentoAplicado(descuento);
        factura.setImpuestosAplicados(impuestos);
        factura.setTotalFinal(totalFinal);
        factura.setFechaEmision(LocalDateTime.now());

        return facturaRepository.save(factura);
    }

    private BigDecimal calcularSubtotalPiezas(Servicio servicio) {
        return new BigDecimal("25.00"); // temporal
    }
}
