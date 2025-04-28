package org.example.model.entity;

import java.util.Date;

public class DetallesPedido {

    private Usuario idUsuario;
    private Producto idProducto;
    private int cantidad;
    private float subtotal;
    private Date fecha;

    public DetallesPedido() {
    }

    public DetallesPedido(Usuario idCliente, Producto idProducto, int cantidad, float subtotal, Date fecha) {
        this.idUsuario = idCliente;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.fecha = fecha;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Producto getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Producto idProducto) {
        this.idProducto = idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "DetallesPedido{" +
                "idCliente=" + idUsuario +
                ", idProducto=" + idProducto +
                ", cantidad=" + cantidad +
                ", subtotal=" + subtotal +
                ", fecha=" + fecha +
                '}';
    }
}
