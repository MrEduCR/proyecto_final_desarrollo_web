package com.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "estados_de_servicios")
public class EstadoServicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "estado_servicio_id")
    private Long estadoServicioId;

    @Column(name = "estado_servicio_descripcion", length = 50)
    private String estadoServicioDescripcion;

    public Long getEstadoServicioId() {
        return estadoServicioId;
    }

    public void setEstadoServicioId(Long estadoServicioId) {
        this.estadoServicioId = estadoServicioId;
    }

    public String getEstadoServicioDescripcion() {
        return estadoServicioDescripcion;
    }

    public void setEstadoServicioDescripcion(String estadoServicioDescripcion) {
        this.estadoServicioDescripcion = estadoServicioDescripcion;
    }
}
