package com.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facturaId;

    // Patrón ORM: Relación 1:1 con Servicio. Un servicio solo puede tener una factura.
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "servicio_id", nullable = false, unique = true)
    private Servicio servicio;

    @Column(name = "subtotal_piezas", nullable = false)
    private BigDecimal subtotalPiezas = BigDecimal.ZERO;

    @Column(name = "subtotal_mano_obra", nullable = false)
    private BigDecimal subtotalManoObra = BigDecimal.ZERO;

    @Column(name = "descuento_aplicado")
    private BigDecimal descuentoAplicado = BigDecimal.ZERO;

    @Column(name = "impuestos_aplicados", nullable = false)
    private BigDecimal impuestosAplicados;

    @Column(name = "total_final", nullable = false)
    private BigDecimal totalFinal;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDateTime fechaEmision;

    // Patrón ORM: Constructor vacío (necesario para JPA/Hibernate)
    public Factura() {
    }

  

    public Long getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Long facturaId) {
        this.facturaId = facturaId;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public BigDecimal getSubtotalPiezas() {
        return subtotalPiezas;
    }

    public void setSubtotalPiezas(BigDecimal subtotalPiezas) {
        this.subtotalPiezas = subtotalPiezas;
    }

    public BigDecimal getSubtotalManoObra() {
        return subtotalManoObra;
    }

    public void setSubtotalManoObra(BigDecimal subtotalManoObra) {
        this.subtotalManoObra = subtotalManoObra;
    }

    public BigDecimal getDescuentoAplicado() {
        return descuentoAplicado;
    }

    public void setDescuentoAplicado(BigDecimal descuentoAplicado) {
        this.descuentoAplicado = descuentoAplicado;
    }

    public BigDecimal getImpuestosAplicados() {
        return impuestosAplicados;
    }

    public void setImpuestosAplicados(BigDecimal impuestosAplicados) {
        this.impuestosAplicados = impuestosAplicados;
    }

    public BigDecimal getTotalFinal() {
        return totalFinal;
    }

    public void setTotalFinal(BigDecimal totalFinal) {
        this.totalFinal = totalFinal;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDateTime fechaEmision) {
        this.fechaEmision = fechaEmision;
    }
}