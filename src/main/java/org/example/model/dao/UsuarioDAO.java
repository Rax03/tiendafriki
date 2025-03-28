package org.example.model.dao;

import org.example.model.conection.ConexionBD;
import org.example.model.entity.Enum.Rol;
import org.example.model.entity.Usuario;
import org.example.utils.Validacion;

import java.sql.*;
import java.time.LocalDate;

public class UsuarioDAO {

    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM Usuarios WHERE email = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("contraseña_hash"),
                            Rol.valueOf(rs.getString("rol").toUpperCase()),
                            rs.getDate("fecha_registro").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (nombre, email, contraseña_hash, rol, fecha_registro) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getPassword());
            stmt.setString(4, usuario.getRol().name());
            stmt.setDate(5, java.sql.Date.valueOf(usuario.getFechaRegistro()));
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean autenticarUsuario(String email, String contraseñaIngresada) {
        Usuario usuario = buscarPorEmail(email);
        if (usuario != null) {
            String hashIngresado = Validacion.encryptClave(contraseñaIngresada);
            return hashIngresado.equals(usuario.getPassword());
        }
        return false;
    }

    public boolean correoExiste(String email) {
        String sql = "SELECT 1 FROM Usuarios WHERE email = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String obtenerRolPorEmail(String email) {
        String sql = "SELECT rol FROM Usuarios WHERE email = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("rol").toUpperCase(); // Convertir a mayúsculas
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Retorna null si no encuentra al usuario
    }
    public Usuario obtenerDatosUsuario(int idUsuario) {
        String sql = "SELECT * FROM Usuarios WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("contraseña_hash"),
                            Rol.valueOf(rs.getString("rol").toUpperCase()),
                            rs.getDate("fecha_registro").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Si no se encuentra el usuario, retorna null
    }

}
