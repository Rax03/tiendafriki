package org.example.model.dao;

import org.example.model.conection.ConexionBD;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    // Método para registrar un usuario en la base de datos
    public boolean registrarUsuario(String nombre, String email, String contraseñaHash) {
        String sql = "INSERT INTO Usuarios (nombre, email, contraseña_hash) VALUES (?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            stmt.setString(2, email);
            stmt.setString(3, contraseñaHash);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para autenticar al usuario con email y contraseña
    public static boolean autenticarUsuario(String email, String contraseñaHash) {
        String sql = "SELECT * FROM Usuarios WHERE email = ? AND contraseña_hash = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.setString(2, contraseñaHash);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Devuelve true si existe el usuario
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método opcional para verificar si un correo ya está registrado
    public boolean correoExiste(String email) {
        String sql = "SELECT * FROM Usuarios WHERE email = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Devuelve true si el correo ya está registrado
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
