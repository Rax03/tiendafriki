package org.example.model.dao;

import org.example.model.conection.ConexionBD;
import org.example.model.entity.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProveedorDAO {

    public List<Proveedor> obtenerTodosLosProveedores() {
        String sql = "SELECT * FROM proveedores";
        List<Proveedor> proveedores = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Proveedor proveedor = new Proveedor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("gmail")
                );
                proveedores.add(proveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return proveedores;
    }

    public boolean agregarProveedor(Proveedor proveedor) {
        String sql = "INSERT INTO proveedores (nombre, contacto, telefono, direccion) VALUES (?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getTelefono());
            stmt.setString(3, proveedor.getDireccion());
            stmt.setString(4, proveedor.getEmail());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminarProveedor(int id) {
        String sql = "DELETE FROM proveedores WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean actualizarProveedor(Proveedor proveedor) {
        String sql = "UPDATE proveedores SET nombre = ?, contacto = ?, telefono = ?, direccion = ? WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, proveedor.getNombre());
            stmt.setString(2, proveedor.getDireccion());
            stmt.setString(3, proveedor.getTelefono());
            stmt.setString(4, proveedor.getEmail());
            stmt.setInt(5, proveedor.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

