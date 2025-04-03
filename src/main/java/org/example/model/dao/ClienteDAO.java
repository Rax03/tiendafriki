package org.example.model.dao;

import org.example.model.conection.ConexionBD;
import org.example.model.entity.Cliente;
import org.example.model.entity.Usuario;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // Método para obtener todos los clientes
    public List<Cliente> obtenerTodosLosClientes() {
        String sql = "SELECT * FROM clientes";
        List<Cliente> clientes = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                clientes.add(new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getTimestamp("fecha_registro")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener los clientes: " + e.getMessage());
        }
        return clientes;
    }

    // Método para buscar cliente por email
    public Cliente obtenerClientePorEmail(String email) {
        String sql = "SELECT * FROM clientes WHERE email = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getTimestamp("fecha_registro")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente por email: " + e.getMessage());
        }
        return null;
    }

    // Método para agregar un nuevo cliente
    public boolean agregarCliente(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, email, telefono, direccion) VALUES (?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getDireccion());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al agregar cliente: " + e.getMessage());
            return false;
        }
    }

    // Método para actualizar un cliente existente
    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, email = ?, telefono = ?, direccion = ? WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setString(3, cliente.getTelefono());
            stmt.setString(4, cliente.getDireccion());
            stmt.setInt(5, cliente.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        }
    }

    // Método para eliminar un cliente por su ID
    public boolean eliminarCliente(int idCliente) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
            return false;
        }
    }

    // Método para obtener un cliente por su ID
    public Cliente obtenerClientePorId(int idCliente) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idCliente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("direccion"),
                        rs.getTimestamp("fecha_registro")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener cliente por ID: " + e.getMessage());
        }
        return null;
    }
    public boolean registrarClienteDesdeUsuario(Usuario usuario) {
        String sql = "INSERT INTO clientes (nombre, email, telefono, direccion, fecha_registro) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, ""); // Teléfono vacío inicial
            stmt.setString(4, ""); // Dirección vacía inicial
            stmt.setTimestamp(5, Timestamp.valueOf(LocalDate.now().atStartOfDay())); // Fecha actual
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Error al registrar cliente desde usuario: " + e.getMessage());
            return false;
        }
    }

}
