package org.example.model.entity;

import java.time.LocalDateTime;

public class Pedido {
    private int idPedido;
    private int idCliente;
    private LocalDateTime fechaPedido;
    private String estado;
    private Float total= 0.0f;

    public Pedido() {
    }
    public Pedido(int idPedido, int idUsuario, LocalDateTime fecha) {
        this.idPedido = idPedido;
        this.idCliente = idUsuario;
        this.fechaPedido = fecha;
    }


    public Pedido(int idPedido, int idCliente, LocalDateTime fechaPedido, String estado, Float total) {
        this.idPedido = idPedido;
        this.idCliente = idCliente;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.total = total;
    }
    public Pedido(int idPedido, int idCliente, LocalDateTime fechaPedido, String estado) {}

    public int getIdPedido() {
        return idPedido;
    }


    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public LocalDateTime getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(LocalDateTime fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "idPedido=" + idPedido +
                ", idCliente=" + idCliente +
                ", fechaPedido=" + fechaPedido +
                ", estado='" + estado + '\'' +
                ", total=" + total +
                '}';
    }
}
