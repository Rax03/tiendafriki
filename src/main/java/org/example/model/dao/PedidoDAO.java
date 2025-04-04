package org.example.model.dao;

import org.example.model.conection.ConexionBD;
import org.example.model.entity.Pedido;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    // Obtener todos los pedidos
    public List<Pedido> obtenerTodosLosPedidos() {
        String sql = "SELECT * FROM pedidos";
        List<Pedido> pedidos = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                pedidos.add(new Pedido(
                        rs.getInt("id_pedido"),
                        rs.getInt("id_cliente"),
                        rs.getTimestamp("fecha_pedido").toLocalDateTime(),
                        rs.getString("estado"),
                        rs.getDouble("total")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los pedidos: " + e.getMessage());
        }
        return pedidos;
    }

    // Registrar un nuevo pedido
    public boolean registrarPedido(Pedido pedido) {
        String sql = "INSERT INTO pedidos (id_cliente, fecha_pedido, estado, total) VALUES (?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getIdCliente());
            stmt.setTimestamp(2, Timestamp.valueOf(pedido.getFechaPedido()));
            stmt.setString(3, pedido.getEstado());
            stmt.setDouble(4, pedido.getTotal());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar el pedido: " + e.getMessage());
            return false;
        }
    }

    // Actualizar un pedido existente
    public boolean actualizarPedido(Pedido pedido) {
        String sql = "UPDATE pedidos SET id_cliente = ?, fecha_pedido = ?, estado = ?, total = ? WHERE id_pedido = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, pedido.getIdCliente());
            stmt.setTimestamp(2, Timestamp.valueOf(pedido.getFechaPedido()));
            stmt.setString(3, pedido.getEstado());
            stmt.setDouble(4, pedido.getTotal());
            stmt.setInt(5, pedido.getIdPedido());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar el pedido: " + e.getMessage());
            return false;
        }
    }

    // Eliminar un pedido
    public boolean eliminarPedido(int idPedido) {
        String sql = "DELETE FROM pedidos WHERE id_pedido = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar el pedido: " + e.getMessage());
            return false;
        }
    }

    // Obtener un pedido por su ID
    public Pedido obtenerPedidoPorId(int idPedido) {
        String sql = "SELECT * FROM pedidos WHERE id_pedido = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Pedido(
                        rs.getInt("id_pedido"),
                        rs.getInt("id_cliente"),
                        rs.getTimestamp("fecha_pedido").toLocalDateTime(),
                        rs.getString("estado"),
                        rs.getDouble("total")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el pedido por ID: " + e.getMessage());
        }
        return null;
    }
}
