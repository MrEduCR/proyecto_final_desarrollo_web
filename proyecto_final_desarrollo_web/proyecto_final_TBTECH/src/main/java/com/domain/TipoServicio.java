package com.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tipos_servicio")
public class TipoServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_servicio_id")
    private Long tipoServicioId;

    @Column(name = "tipo_servicio_descripcion", length = 50, nullable = false)
    private String tipoServicioDescripcion;

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
}
