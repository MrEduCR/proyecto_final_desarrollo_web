package com.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "tipos_servicio")
public class TipoServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_servicio_id")
    private Long tipoServicioId;

    @Column(name = "tipo_servicio_descripcion", length = 50, nullable = false)
    private String tipoServicioDescripcion;

    @Column(name = "precio_base", nullable = false)
    private BigDecimal precioBase = BigDecimal.ZERO;  // Nuevo campo para precio por mano de obra

    // --- GETTERS & SETTERS ---

    public Long getTipoServicioId() {
        return tipoServicioId;
    }

    public void setTipoServicioId(Long tipoServicioId) {
        this.tipoServicioId = tipoServicioId;
    }

    public String getTipoServicioDescripcion() {
        return tipoServicioDescripcion;
    }

    public void setTipoServicioDescripcion(String tipoServicioDescripcion) {
        this.tipoServicioDescripcion = tipoServicioDescripcion;
    }

    public BigDecimal getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(BigDecimal precioBase) {
        this.precioBase = precioBase;
    }
}
