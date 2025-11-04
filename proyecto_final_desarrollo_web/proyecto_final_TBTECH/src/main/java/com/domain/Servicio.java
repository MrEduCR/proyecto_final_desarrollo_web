package com.domain;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Entity
@Table(name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "servicio_id")
    private Long servicioId;

    @ManyToOne
    @JoinColumn(name = "cedula_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "tipo_equipo_id", nullable = false)
    private TipoEquipo tipoEquipo;

    @ManyToOne
    @JoinColumn(name = "tipo_servicio_id", nullable = false)
    private TipoServicio tipoServicio;

    @ManyToOne
    @JoinColumn(name = "estado_servicio_id", nullable = false)
    private EstadoServicio estadoServicio;

    @Column(name = "numero_serie_equipo", length = 500)
    private String numeroSerieEquipo;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_ingreso_equipo")
    private Date fechaIngresoEquipo;

    @Column(name = "ruta_imagen", length = 1024)
    private String rutaImagen;

    @Column(name = "descripcion_problema", length = 500)
    private String descripcionProblema;

    public Long getServicioId() {
        return servicioId;
    }

    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public TipoEquipo getTipoEquipo() {
        return tipoEquipo;
    }

    public void setTipoEquipo(TipoEquipo tipoEquipo) {
        this.tipoEquipo = tipoEquipo;
    }

    public TipoServicio getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(TipoServicio tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    public EstadoServicio getEstadoServicio() {
        return estadoServicio;
    }

    public void setEstadoServicio(EstadoServicio estadoServicio) {
        this.estadoServicio = estadoServicio;
    }

    public String getNumeroSerieEquipo() {
        return numeroSerieEquipo;
    }

    public void setNumeroSerieEquipo(String numeroSerieEquipo) {
        this.numeroSerieEquipo = numeroSerieEquipo;
    }

    public Date getFechaIngresoEquipo() {
        return fechaIngresoEquipo;
    }

    public void setFechaIngresoEquipo(Date fechaIngresoEquipo) {
        this.fechaIngresoEquipo = fechaIngresoEquipo;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getDescripcionProblema() {
        return descripcionProblema;
    }

    public void setDescripcionProblema(String descripcionProblema) {
        this.descripcionProblema = descripcionProblema;
    }


}
