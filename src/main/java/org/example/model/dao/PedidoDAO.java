package org.example.model.dao;

import org.example.model.conection.ConexionBD;
import org.example.model.entity.DetallesPedido;
import org.example.model.entity.Pedido;
import org.example.model.entity.Producto;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {

    public List<Pedido> obtenerTodosLosPedidos() {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT p.id_pedido, p.fecha_pedido, p.estado, p.total, u.nombre AS nombre_cliente, u.id AS id_cliente " +
                "FROM pedidos p " +
                "JOIN usuarios u ON p.id_cliente = u.id";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int idPedido = rs.getInt("id_pedido");
                int idCliente = rs.getInt("id_cliente");
                LocalDateTime fechaPedido = rs.getTimestamp("fecha_pedido").toLocalDateTime();
                String estado = rs.getString("estado");
                float total = rs.getFloat("total");
                String nombreCliente = rs.getString("nombre_cliente");

                Pedido pedido = new Pedido(idPedido, idCliente, fechaPedido, estado, total);
                // Utilizar la misma conexión para obtener los detalles
                pedido.setDetalles(obtenerDetallesPedido(conexion, idPedido));
                pedidos.add(pedido);

                System.out.println("Pedido ID: " + idPedido + " | Cliente: " + nombreCliente);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedidos;
    }

    public List<DetallesPedido> obtenerDetallesPedido(Connection conexion, int idPedido) {
        if (idPedido <= 0) {
            System.err.println("❌ Error: El ID de pedido debe ser mayor a 0.");
            return new ArrayList<>();
        }

        List<DetallesPedido> detalles = new ArrayList<>();
        String sql = "SELECT dp.id_producto, dp.cantidad, dp.precio, p.nombre " +
                "FROM detalles_pedidos dp " +
                "JOIN productos p ON dp.id_producto = p.id_producto " +
                "WHERE dp.id_pedido = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idPedido);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    DetallesPedido detalle = new DetallesPedido(
                            rs.getInt("id_producto"),
                            idPedido,
                            0,  // No se recupera el ID de usuario aquí
                            rs.getInt("cantidad"),
                            rs.getFloat("precio"),
                            rs.getString("nombre")
                    );
                    detalles.add(detalle);
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener detalles del pedido: " + e.getMessage());
        }
        return detalles;
    }

    public int registrarPedido(Pedido pedido, int idUsuario, List<Producto> carrito) {
        String sqlPedido = "INSERT INTO pedidos (id_cliente, fecha_pedido, estado, total) VALUES (?, ?, ?, ?)";
        // La consulta sigue esperando el campo "cantidad", pero ya no usaremos valores variables, se insertará 1.
        String sqlDetalle = "INSERT INTO detalles_pedidos (id_pedido, id_producto, cantidad, precio) VALUES (?, ?, ?, ?)";
        String sqlActualizarStock = "UPDATE productos SET stock = stock - ? WHERE id_producto = ?";
        String sqlVerificarProducto = "SELECT COUNT(*) FROM productos WHERE id_producto = ?";

        try (Connection conexion = ConexionBD.conectar()) {
            conexion.setAutoCommit(false); // Iniciar transacción

            int idPedido = -1;

            try (PreparedStatement stmtPedido = conexion.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
                stmtPedido.setInt(1, idUsuario);
                stmtPedido.setTimestamp(2, Timestamp.valueOf(pedido.getFechaPedido()));
                stmtPedido.setString(3, pedido.getEstado());
                stmtPedido.setDouble(4, pedido.getTotal());
                stmtPedido.executeUpdate();

                // Obtener el ID del pedido generado
                try (ResultSet rs = stmtPedido.getGeneratedKeys()) {
                    if (rs.next()) {
                        idPedido = rs.getInt(1);
                    } else {
                        conexion.rollback();
                        System.err.println("❌ Error: No se pudo obtener el ID del pedido.");
                        return -1;
                    }
                }
            }

            try (PreparedStatement stmtVerificarProducto = conexion.prepareStatement(sqlVerificarProducto);
                 PreparedStatement stmtDetalle = conexion.prepareStatement(sqlDetalle);
                 PreparedStatement stmtStock = conexion.prepareStatement(sqlActualizarStock)) {

                for (Producto p : carrito) {
                    // Verificar que el producto existe
                    stmtVerificarProducto.setInt(1, p.getId_producto());
                    try (ResultSet rs = stmtVerificarProducto.executeQuery()) {
                        if (rs.next() && rs.getInt(1) == 0) {
                            conexion.rollback();
                            System.err.println("❌ Error: El producto " + p.getNombre() + " no existe en la base de datos.");
                            return -1;
                        }
                    }

                    if (p.getStock() < 1) {
                        conexion.rollback();
                        System.err.println("❌ Error: Stock insuficiente para " + p.getNombre());
                        return -1;
                    }

                    // Insertar en detalles_pedidos usando 1 como cantidad (ya que quitamos el uso de 'cantidad')
                    stmtDetalle.setInt(1, idPedido);
                    stmtDetalle.setInt(2, p.getId_producto());
                    stmtDetalle.setInt(3, 1); // Se fija la cantidad en 1
                    stmtDetalle.setDouble(4, p.getPrecio());
                    stmtDetalle.addBatch();

                    // Reducir el stock del producto en 1 unidad
                    stmtStock.setInt(1, 1);
                    stmtStock.setInt(2, p.getId_producto());
                    stmtStock.addBatch();
                }

                stmtDetalle.executeBatch();
                stmtStock.executeBatch();
            }

            conexion.commit();
            System.out.println("✅ Pedido registrado y stock actualizado.");
            return idPedido;

        } catch (SQLException e) {
            System.err.println("❌ Error en la transacción: " + e.getMessage());
            return -1;
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
                            rs.getFloat("total")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el pedido por ID: " + e.getMessage());
        }
        return null;
    }
}
