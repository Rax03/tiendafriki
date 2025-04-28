package org.example.model.dao;

import org.example.model.conection.ConexionBD;
import org.example.model.entity.Producto;
import org.example.model.entity.Proveedor;
import org.example.model.entity.ProductoProveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoProveedorDAO {

    // CREATE: Agregar un nuevo producto-proveedor
    public boolean agregarProductoProveedor(ProductoProveedor pp) {
        String sql = "INSERT INTO producto_proveedor (id_producto, id_proveedor, precio, stock, tiempo_entrega, fecha) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, pp.getProducto().getId_producto());
            stmt.setInt(2, pp.getProveedor().getId());
            stmt.setFloat(3, pp.getPrecio());
            stmt.setInt(4, pp.getStock());
            stmt.setInt(5, pp.getTiempoEntrega());
            stmt.setDate(6, new java.sql.Date(pp.getFecha().getTime()));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // READ: Obtener todos los registros
    public List<ProductoProveedor> obtenerTodos() {
        String sql = "SELECT * FROM producto_proveedor";
        List<ProductoProveedor> lista = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Producto producto = new ProductosDAO().obtenerProductoPorId(rs.getInt("id_producto"));
                Proveedor proveedor = new ProveedorDAO().obtenerProveedorPorId(rs.getInt("id_proveedor"));
                ProductoProveedor pp = new ProductoProveedor(
                        producto,
                        proveedor,
                        rs.getFloat("precio"),
                        rs.getInt("stock"),
                        rs.getInt("tiempo_entrega"),
                        rs.getDate("fecha")
                );
                lista.add(pp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // UPDATE: Actualizar un registro
    public boolean actualizarProductoProveedor(ProductoProveedor pp) {
        String sql = "UPDATE producto_proveedor SET precio = ?, stock = ?, tiempo_entrega = ?, fecha = ? WHERE id_producto = ? AND id_proveedor = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setFloat(1, pp.getPrecio());
            stmt.setInt(2, pp.getStock());
            stmt.setInt(3, pp.getTiempoEntrega());
            stmt.setDate(4, new java.sql.Date(pp.getFecha().getTime()));
            stmt.setInt(5, pp.getProducto().getId_producto());
            stmt.setInt(6, pp.getProveedor().getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE: Eliminar un registro
    public boolean eliminarProductoProveedor(int idProducto, int idProveedor) {
        String sql = "DELETE FROM producto_proveedor WHERE id_producto = ? AND id_proveedor = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            stmt.setInt(2, idProveedor);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
