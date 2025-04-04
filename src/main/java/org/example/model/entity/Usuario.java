package org.example.model.entity;

import org.example.model.entity.Enum.Rol;

import java.time.LocalDate;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String password; // Hash cifrado
    private Rol rol;
    private LocalDate fechaRegistro;

    // Constructor completo
    public Usuario(int id, String nombre, String email, String password, Rol rol, LocalDate fechaRegistro) {
        this.id = id;
        this.nombre = nombre;
        this.setEmail(email); // Validación de email en setter
        this.password = password; // Hash generado previamente
        this.rol = rol;
        this.setFechaRegistro(fechaRegistro); // Validación de fecha en setter
    }

    // Constructor para nuevos usuarios (sin ID)
    public Usuario(String nombre, String email, String password, Rol rol, LocalDate fechaRegistro) {
        this.nombre = nombre;
        this.setEmail(email); // Validación de email en setter
        this.password = password; // Hash generado previamente
        this.rol = rol;
        this.setFechaRegistro(fechaRegistro); // Validación de fecha en setter
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }
        this.nombre = nombre;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("El email no tiene un formato válido.");
        }
        this.email = email;
    }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; } // Encriptar en el DAO

    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }

    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) {
        if (fechaRegistro.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de registro no puede ser en el futuro.");
        }
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", rol=" + rol +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}
