package org.example.model.entity;

public class DetallesPedido {
    private int idPedido;
    private int idUsuario;
    private int idProducto;
    private int cantidad;
    private float subtotal;
    private String nombreProducto;

    public DetallesPedido(int idPedido, int idUsuario, int idProducto, int cantidad, float subtotal) {
        this.idPedido = idPedido;
        this.idUsuario = idUsuario;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }
}
