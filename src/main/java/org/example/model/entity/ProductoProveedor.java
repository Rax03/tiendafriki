package org.example.model.entity;

import java.util.Date;

public class ProductoProveedor {
    private Producto id_producto;
    private Proveedor id_proveedor;
    private float precio;
    private int stock;
    private int TiempoEntrega;
    private Date fecha;

    public ProductoProveedor() {
    }

    public ProductoProveedor(Producto id_producto, Proveedor id_proveedor, float precio, int stock, int tiempoEntrega, Date fecha) {
        this.id_producto = id_producto;
        this.id_proveedor = id_proveedor;
        this.precio = precio;
        this.stock = stock;
        TiempoEntrega = tiempoEntrega;
        this.fecha = fecha;
    }

    public Producto getId_producto() {
        return id_producto;
    }

    public void setId_producto(Producto id_producto) {
        this.id_producto = id_producto;
    }

    public Proveedor getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(Proveedor id_proveedor) {
        this.id_proveedor = id_proveedor;
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

    public int getTiempoEntrega() {
        return TiempoEntrega;
    }

    public void setTiempoEntrega(int tiempoEntrega) {
        TiempoEntrega = tiempoEntrega;
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
                "id_producto=" + id_producto +
                ", id_proveedor=" + id_proveedor +
                ", precio=" + precio +
                ", stock=" + stock +
                ", TiempoEntrega=" + TiempoEntrega +
                ", fecha=" + fecha +
                '}';
    }
}
