package com.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "piezas")
public class Pieza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pieza_id")
    private Long piezaId;

    @Column(name = "pieza_descripcion", length = 50)
    private String piezaDescripcion;

    @Column(name = "precio_pieza", nullable = false)
    private BigDecimal precioPieza;

    @Column(name = "ruta_imagen", length = 1024)
    private String rutaImagen;

    // Getters y Setters
    public Long getPiezaId() {
        return piezaId;
    }

    public void setPiezaId(Long piezaId) {
        this.piezaId = piezaId;
    }

    public String getPiezaDescripcion() {
        return piezaDescripcion;
    }

    public void setPiezaDescripcion(String piezaDescripcion) {
        this.piezaDescripcion = piezaDescripcion;
    }

    public BigDecimal getPrecioPieza() {
        return precioPieza;
    }

    public void setPrecioPieza(BigDecimal precioPieza) {
        this.precioPieza = precioPieza;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }
}
