package com.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "clientes")  // Mapea la tabla "clientes"
public class Cliente {

    @Id
    @Column(name = "cedula_cliente")  // Llave primaria de la tabla
    private int cedula;

    @Column(name = "nombre_cliente")
    private String nombre;

    @Column(name = "telefono_cliente")
    private String telefono;

    @Column(name = "correo_cliente")
    private String correo;

    // Constructor vacío
    public Cliente() {}

    // Constructor con parámetros
    public Cliente(int cedula, String nombre, String telefono, String correo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }

    // Getters y Setters
    public int getCedula() { return cedula; }
    public void setCedula(int cedula) { this.cedula = cedula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
}
