package org.example.model.dao;

import org.example.model.conection.ConexionBD;
import org.example.model.entity.DetallesPedido;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DetallePedidoDAO {

    // Método para insertar un nuevo detalle de pedido
    public boolean agregarDetallePedido(DetallesPedido detalle) {
        if (detalle == null || detalle.getIdUsuario() <= 0 || detalle.getIdProducto() <= 0) {
            throw new IllegalArgumentException("Datos del pedido inválidos.");
        }

        String sql = "INSERT INTO detalles_pedidos (id_pedido, id_cliente, id_producto, cantidad, subtotal, nombre) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, detalle.getIdPedido());
            stmt.setInt(2, detalle.getIdUsuario());
            stmt.setInt(3, detalle.getIdProducto());
            stmt.setInt(4, detalle.getCantidad());
            stmt.setFloat(5, detalle.getSubtotal());
            stmt.setString(6, detalle.getNombreProducto());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al agregar detalle de pedido: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener los detalles de un pedido por su ID
    public List<DetallesPedido> obtenerDetallesPorPedido(int idPedido) {
        if (idPedido <= 0) return new ArrayList<>();

        String sql = "SELECT * FROM detalles_pedidos WHERE id_pedido = ?";
        List<DetallesPedido> detalles = new ArrayList<>();

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DetallesPedido detalle = new DetallesPedido(
                            rs.getInt("id_pedido"),
                            rs.getInt("id_usuario"),
                            rs.getInt("id_producto"),
                            rs.getInt("cantidad"),
                            rs.getFloat("subtotal")
                    );
                    detalle.setNombreProducto(rs.getString("nombre_producto"));
                    detalles.add(detalle);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener detalles del pedido: " + e.getMessage());
        }
        return detalles;
    }
    public boolean registrarDetallePedido(DetallesPedido detalle) {
        if (detalle == null || detalle.getIdUsuario() <= 0 || detalle.getIdProducto() <= 0) {
            throw new IllegalArgumentException("Datos del pedido inválidos.");
        }

        String sql = "INSERT INTO detalles_pedidos (id_pedido, id_cliente, id_producto, cantidad, subtotal, nombre) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, detalle.getIdPedido());
            stmt.setInt(2, detalle.getIdUsuario());
            stmt.setInt(3, detalle.getIdProducto());
            stmt.setInt(4, detalle.getCantidad());
            stmt.setFloat(5, detalle.getSubtotal());
            stmt.setString(6, detalle.getNombreProducto());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar detalle de pedido: " + e.getMessage());
            return false;
        }
    }

    public List<String[]> obtenerProductosDesdeBD() {
        String sql = "SELECT id_producto, nombre, precio, stock FROM productos";
        List<String[]> productos = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String[] producto = new String[4];
                producto[0] = String.valueOf(rs.getInt("id_producto"));
                producto[1] = rs.getString("nombre");
                producto[2] = String.valueOf(rs.getFloat("precio"));
                producto[3] = String.valueOf(rs.getInt("stock"));
                productos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener productos: " + e.getMessage());
        }
        return productos;
    }

}
