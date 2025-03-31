package org.example.model.entity;

public class Categoria {
    private int id_categoria;
    private String Nombre;

    // Constructores
    public Categoria() {
    }

    public Categoria(int idCategoria, String nombre) {
        this.id_categoria = idCategoria;
        this.Nombre = nombre;
    }

    public Categoria(String nombre) {
        this.Nombre = nombre;
    }

    // Getters y Setters
    public int getIdCategoria() {
        return id_categoria;
    }

    public void setIdCategoria(int idCategoria) {
        if (idCategoria <= 0) {
            throw new IllegalArgumentException("El ID de la categoría debe ser mayor que 0.");
        }
        this.id_categoria = idCategoria;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la categoría no puede estar vacío.");
        }
        this.Nombre = nombre;
    }

    // Método toString (usado en JComboBox)
    @Override
    public String toString() {
        return Nombre;
    }

    // Métodos equals y hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Categoria categoria = (Categoria) obj;
        return id_categoria == categoria.id_categoria;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id_categoria);
    }

}
