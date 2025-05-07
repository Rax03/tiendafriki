package org.example.model.dao;

import org.example.model.conection.ConexionBD;
import org.example.model.entity.Enum.Rol;
import org.example.model.entity.Usuario;
import org.example.utils.HashUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {

    private static final Logger logger = Logger.getLogger(UsuarioDAO.class.getName());

    public List<Usuario> obtenerTodosLosUsuarios() {
        String sql = "SELECT * FROM Usuarios";
        List<Usuario> usuarios = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("contraseña_hash"),
                        rs.getString("salt"), // Añadir sal
                        Rol.valueOf(rs.getString("rol").toUpperCase()),
                        rs.getDate("fecha_registro").toLocalDate()
                );
                usuario.setSalt(rs.getString("salt")); // Añadir sal
                usuarios.add(usuario);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener todos los usuarios", e);
        }
        return usuarios;
    }

    public Usuario buscarPorEmail(String email) {
        String sql = "SELECT * FROM Usuarios WHERE email = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("contraseña_hash"),
                            rs.getString("salt"), // Añadir sal
                            Rol.valueOf(rs.getString("rol").toUpperCase()),
                            rs.getDate("fecha_registro").toLocalDate()
                    );
                    usuario.setSalt(rs.getString("salt")); // Añadir sal
                    return usuario;
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar usuario por email", e);
        }
        return null;
    }

    public boolean registrarUsuario(Usuario usuario) {
        String sql = "INSERT INTO Usuarios (nombre, email, contraseña_hash, salt, rol, fecha_registro) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getPassword());
            stmt.setString(4, usuario.getSalt());
            stmt.setString(5, usuario.getRol().name());
            stmt.setDate(6, java.sql.Date.valueOf(usuario.getFechaRegistro()));

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al registrar usuario", e);
            return false;
        }
    }



    public boolean correoExiste(String email) {
        String sql = "SELECT COUNT(*) FROM Usuarios WHERE email = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al verificar si el correo existe", e);
        }
        return false;
    }

    public boolean eliminarUsuario(int idUsuario) {
        String sql = "DELETE FROM Usuarios WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar el usuario", e);
            return false;
        }
    }

    public boolean actualizarUsuario(Usuario usuario) {
        String sql = "UPDATE Usuarios SET nombre = ?, email = ?, contraseña_hash = ?, rol = ?, salt = ? WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            // Se asume que la contraseña ya ha sido hasheada previamente
            String hash = HashUtil.hashearConSalt(usuario.getPassword(), usuario.getSalt());

            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, hash);  // Se almacena el hash de la contraseña
            stmt.setString(4, usuario.getRol().name());
            stmt.setString(5, usuario.getSalt());
            stmt.setInt(6, usuario.getId());
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar el usuario", e);
            return false;
        }
    }


    public Usuario obtenerDatosUsuario(int idUsuario) {
        String sql = "SELECT * FROM Usuarios WHERE id = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("email"),
                            rs.getString("contraseña_hash"),
                            rs.getString("salt"), // Añadir sal
                            Rol.valueOf(rs.getString("rol").toUpperCase()),
                            rs.getDate("fecha_registro").toLocalDate()
                    );
                    usuario.setSalt(rs.getString("salt")); // Añadir sal
                    return usuario;
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener datos del usuario", e);
        }
        return null;
    }

    public String obtenerRolPorEmail(String email) {
        String sql = "SELECT rol FROM Usuarios WHERE email = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("rol").toUpperCase();
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener el rol del usuario por email", e);
        }
        return null;
    }

}
