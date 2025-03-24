package org.example.model.entity;

import org.example.model.entity.Enum.Rol;

import java.util.Date;

public class Usuario {
    private int id;
    private String nombre;
    private String email;
    private String password; // Asegúrate de no almacenar contraseñas en texto plano.
    private Rol rol;
    private Date fecha_registro;

    // Constructor
    public Usuario(int id, String nombre, String email, String password, Rol rol, Date fecha_registro) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.password = password;  // Este debe ser un hash, por ejemplo.
        this.rol = rol;
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(Date fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    public boolean validarCredenciales(String email, String password) {
        // Aquí iría la lógica para verificar las credenciales. Por ejemplo:
        return this.email.equals(email) && this.password.equals(password); // Comparar contraseñas
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rol=" + rol +
                ", fecha_registro=" + fecha_registro +
                '}';
    }
}
