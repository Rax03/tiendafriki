package org.example.model.entity;

import org.example.model.entity.Enum.Estado;

import java.util.Date;

public class Pedido {
    private int id;
    private int idCliente;
    private Date fecha;
    private Estado estado;
    private float total;

    public Pedido() {
    }

    public Pedido(int id, int idCliente, Date fecha, Estado estado, float total) {
        this.id = id;
        this.idCliente = idCliente;
        this.fecha = fecha;
        this.estado = estado;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "id=" + id +
                ", idCliente=" + idCliente +
                ", fecha=" + fecha +
                ", estado=" + estado +
                ", total=" + total +
                '}';
    }
}
