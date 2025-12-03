package com.services;

import com.domain.Factura;
import com.domain.Pieza; // Importar Pieza
import com.domain.Servicio;
import com.repository.FacturaRepository;
import com.repository.ServicioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

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

        // Verificar si ya existe factura
        if (facturaRepository.findByServicio(servicio).isPresent()) {
            // Nota: Podrías retornar la existente en vez de lanzar error si prefieres
            throw new RuntimeException("El servicio ya ha sido facturado.");
        }

        // 1. Calcular Subtotales Reales
        BigDecimal subtotalPiezas = calcularSubtotalPiezas(servicio);
        
        // Obtener precio base del tipo de servicio (Mano de obra)
        BigDecimal subtotalManoObra = servicio.getTipoServicio().getPrecioBase();

        // 2. Subtotal Neto
        BigDecimal subtotalNeto = subtotalPiezas.add(subtotalManoObra);

        // 3. Descuento
        BigDecimal descuento = subtotalNeto
                .multiply(BigDecimal.valueOf(descuentoPorcentaje).divide(BigDecimal.valueOf(100)))
                .setScale(2, RoundingMode.HALF_UP);

        BigDecimal subtotalConDescuento = subtotalNeto.subtract(descuento)
                .setScale(2, RoundingMode.HALF_UP);

        // 4. Impuestos
        BigDecimal impuestos = subtotalConDescuento
                .multiply(TASA_IMPUESTO)
                .setScale(2, RoundingMode.HALF_UP);

        // 5. Total Final
        BigDecimal totalFinal = subtotalConDescuento.add(impuestos)
                .setScale(2, RoundingMode.HALF_UP);

        // Construcción y guardado de factura
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
        List<Pieza> piezas = servicio.getPiezas();
        
        if (piezas == null || piezas.isEmpty()) {
            return BigDecimal.ZERO;
        }

        // Sumar el precio de cada pieza en la lista
        return piezas.stream()
                .map(Pieza::getPrecioPieza) // Obtener precio
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sumar acumulativamente
    }
}