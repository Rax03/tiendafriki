package org.example.model.entity;

import java.util.Date;

public class ProductoProveedor {
    private Producto producto;
    private Proveedor proveedor;
    private double precio;
    private int stock;
    private int tiempoEntrega;
    private Date fecha;

    public ProductoProveedor() {
    }

    public ProductoProveedor(Producto producto, Proveedor proveedor, double precio, int stock, int tiempoEntrega, Date fecha) {
        this.producto = producto;
        this.proveedor = proveedor;
        this.precio = precio;
        this.stock = stock;
        this.tiempoEntrega = tiempoEntrega;
        this.fecha = fecha;
    }

    // Getters y setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public float getPrecio() {
        return (float) precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getTiempoEntrega() {
        return tiempoEntrega;
    }

    public void setTiempoEntrega(int tiempoEntrega) {
        this.tiempoEntrega = tiempoEntrega;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "ProductoProveedor{" +
                "producto=" + producto.getNombre() +  // Muestra solo el nombre del producto
                ", proveedor=" + proveedor.getNombre() +  // Muestra solo el nombre del proveedor
                ", precio=" + precio +
                ", stock=" + stock +
                ", tiempoEntrega=" + tiempoEntrega +
                ", fecha=" + fecha +
                '}';
    }
}
