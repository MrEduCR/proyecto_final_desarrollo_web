
package com.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "arbol")
public class Arbol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_comun")
    private String nombreComun;

    @Column(name = "tipo_flor")
    private String tipoFlor;

    @Column(name = "dureza_madera")
    private String durezaMadera;

    @Column(name = "altura_promedio")
    private Double alturaPromedio;

    @Column(name = "ruta_imagen")
    private String rutaImagen;
    
    @Column(name = "id_estado")
    private Long estadoId;  // llave foranea hacia estado_arbol pero en todo eso en la BD

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreComun() { return nombreComun; }
    public void setNombreComun(String nombreComun) { this.nombreComun = nombreComun; }

    public String getTipoFlor() { return tipoFlor; }
    public void setTipoFlor(String tipoFlor) { this.tipoFlor = tipoFlor; }

    public String getDurezaMadera() { return durezaMadera; }
    public void setDurezaMadera(String durezaMadera) { this.durezaMadera = durezaMadera; }

    public Double getAlturaPromedio() { return alturaPromedio; }
    public void setAlturaPromedio(Double alturaPromedio) { this.alturaPromedio = alturaPromedio; }

    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
    
    public Long getEstadoId() {return estadoId;}
    public void setEstadoId(Long estadoId) {this.estadoId = estadoId;}
}
