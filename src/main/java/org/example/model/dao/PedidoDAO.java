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

    // Obtener pedidos por cliente
    public List<Pedido> obtenerPedidosPorCliente(int idCliente) {
        if (idCliente <= 0) {
            throw new IllegalArgumentException("El ID del cliente debe ser mayor a cero.");
        }

        String sql = "SELECT * FROM pedidos WHERE id_cliente = ?";
        List<Pedido> pedidos = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(new Pedido(
                            rs.getInt("id_pedido"),
                            rs.getInt("id_cliente"),
                            rs.getTimestamp("fecha_pedido").toLocalDateTime(),
                            rs.getString("estado"),
                            rs.getDouble("total")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los pedidos del cliente: " + e.getMessage());
        }
        return pedidos;
    }

    // Obtener pedidos por estado
    public List<Pedido> obtenerPedidosPorEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) {
            throw new IllegalArgumentException("El estado no puede ser nulo o vacío.");
        }

        String sql = "SELECT * FROM pedidos WHERE estado = ?";
        List<Pedido> pedidos = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, estado);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(new Pedido(
                            rs.getInt("id_pedido"),
                            rs.getInt("id_cliente"),
                            rs.getTimestamp("fecha_pedido").toLocalDateTime(),
                            rs.getString("estado"),
                            rs.getDouble("total")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los pedidos con estado " + estado + ": " + e.getMessage());
        }
        return pedidos;
    }

    // Contar pedidos
    public int contarPedidos() {
        String sql = "SELECT COUNT(*) FROM pedidos";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.err.println("Error al contar los pedidos: " + e.getMessage());
        }
        return 0;
    }

    // Paginación de pedidos
    public List<Pedido> obtenerPedidosPaginados(int pagina, int tamanoPagina) {
        if (pagina <= 0 || tamanoPagina <= 0) {
            throw new IllegalArgumentException("La página y el tamaño de página deben ser mayores a cero.");
        }

        String sql = "SELECT * FROM pedidos LIMIT ? OFFSET ?";
        List<Pedido> pedidos = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, tamanoPagina);
            stmt.setInt(2, (pagina - 1) * tamanoPagina);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    pedidos.add(new Pedido(
                            rs.getInt("id_pedido"),
                            rs.getInt("id_cliente"),
                            rs.getTimestamp("fecha_pedido").toLocalDateTime(),
                            rs.getString("estado"),
                            rs.getDouble("total")
                    ));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener pedidos paginados: " + e.getMessage());
        }
        return pedidos;
    }

    // Registrar un nuevo pedido
    public boolean registrarPedido(Pedido pedido) {
        if (pedido == null || pedido.getFechaPedido() == null || pedido.getIdCliente() <= 0) {
            throw new IllegalArgumentException("El pedido o sus datos no son válidos.");
        }

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
        if (pedido == null || pedido.getFechaPedido() == null || pedido.getIdCliente() <= 0) {
            throw new IllegalArgumentException("El pedido o sus datos no son válidos.");
        }

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
        if (idPedido <= 0) {
            throw new IllegalArgumentException("El ID del pedido debe ser mayor a cero.");
        }

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
        if (idPedido <= 0) {
            throw new IllegalArgumentException("El ID del pedido debe ser mayor a cero.");
        }

        String sql = "SELECT * FROM pedidos WHERE id_pedido = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Pedido(
                            rs.getInt("id_pedido"),
                            rs.getInt("id_cliente"),
                            rs.getTimestamp("fecha_pedido").toLocalDateTime(),
                            rs.getString("estado"),
                            rs.getDouble("total")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el pedido por ID: " + e.getMessage());
        }
        return null;
    }
}
