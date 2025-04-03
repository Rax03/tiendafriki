package org.example.model.entity;

import java.sql.Timestamp;

public class Cliente {
    private int id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private Timestamp fecha_registro;

    public Cliente() {
    }

    public Cliente(int id, String nombre, String email, String telefon, String direccion, Timestamp fecha_registro) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefon;
        this.direccion = direccion;
        this.fecha_registro = fecha_registro;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Timestamp getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Timestamp fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", fecha_registro=" + fecha_registro +
                '}';
    }
}
