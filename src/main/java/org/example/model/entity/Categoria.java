package org.example.model.entity;

public class Categoria {
    private int idCategoria;
    private String Nombre;

    public Categoria() {
    }

    public Categoria(int idCategoria, String nombre) {
        this.idCategoria = idCategoria;
        Nombre = nombre;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    @Override
    public String toString() {
        return "Categoria{" +
                "idCategoria=" + idCategoria +
                ", Nombre='" + Nombre + '\'' +
                '}';
    }
}
