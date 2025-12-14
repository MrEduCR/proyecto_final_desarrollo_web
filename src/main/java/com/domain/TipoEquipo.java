package com.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "tipos_equipo")
public class TipoEquipo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tipo_equipo_id")
    private Long tipoEquipoId;

    @Column(name = "tipo_equipo_descripcion", length = 50)
    private String tipoEquipoDescripcion;

    public Long getTipoEquipoId() {
        return tipoEquipoId;
    }

    public void setTipoEquipoId(Long tipoEquipoId) {
        this.tipoEquipoId = tipoEquipoId;
    }

    public String getTipoEquipoDescripcion() {
        return tipoEquipoDescripcion;
    }

    public void setTipoEquipoDescripcion(String tipoEquipoDescripcion) {
        this.tipoEquipoDescripcion = tipoEquipoDescripcion;
    }
}
