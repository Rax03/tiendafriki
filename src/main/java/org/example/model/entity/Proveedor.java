package org.example.model.entity;
public class Proveedor {
    private int id;
    private String nombre;
    private String direccion;
    private String telefono;
    private String email;

    // Constructor por defecto
    public Proveedor(int idProveedor, String nombre) {
    }

    // Constructor con parámetros
    public Proveedor(int id, String nombre, String direccion, String telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }

    // Getters y Setters
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
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombre = nombre;
        } else {
            throw new IllegalArgumentException("El nombre del proveedor no puede estar vacío.");
        }
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        if (direccion != null && !direccion.trim().isEmpty()) {
            this.direccion = direccion;
        } else {
            throw new IllegalArgumentException("La dirección del proveedor no puede estar vacía.");
        }
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono != null && !telefono.trim().isEmpty()) {
            this.telefono = telefono;
        } else {
            throw new IllegalArgumentException("El teléfono del proveedor no puede estar vacío.");
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email != null && !email.trim().isEmpty()) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("El email del proveedor no puede estar vacío.");
        }
    }

    // Método toString (usado en JComboBox)
    @Override
    public String toString() {
        return nombre;
    }
}
