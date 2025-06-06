package org.example.model.entity;

import java.util.List;

public class Producto {

    private int id_producto;
    private String nombre;
    private String descripcion;
    private float precio;
    private int stock;
    private String imagen;
    private Categoria id_categoria; // Se recomienda que el atributo sea "categoria" para mayor claridad
    private List<Proveedor> proveedores;

    // Constructor vacío
    public Producto() {
    }

   public Producto(int id_producto, String nombre, String descripcion, float precio, int stock, String imagen,
                    Categoria id_categoria, List<Proveedor> proveedores) {
        this.id_producto = id_producto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
        this.id_categoria = id_categoria;
        this.proveedores = proveedores;
    }



    // Constructor para creaciones rápidas (sin id ni proveedores)
    public Producto(String nombre, String descripcion, float precio, int stock, String imagen, Categoria id_categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.imagen = imagen;
        this.id_categoria = id_categoria;
    }

    // Constructor básico solo con nombre (útil para búsquedas o por conveniencia)
    public Producto(String nombre) {
        this.nombre = nombre;
    }

    public Producto(String nombre, float precio, String s) {

    }

    // Getters y setters
    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }



    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Categoria getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(Categoria id_categoria) {
        this.id_categoria = id_categoria;
    }

    public List<Proveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<Proveedor> proveedores) {
        this.proveedores = proveedores;
    }

    @Override
    public String toString() {
        return "Producto{" +
                "id_producto=" + id_producto +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                ", stock=" + stock +
                ", imagen='" + imagen + '\'' +
                ", id_categoria=" + id_categoria +
                ", proveedores=" + proveedores +
                '}';
    }
}
