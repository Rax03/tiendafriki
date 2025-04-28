package org.example.model.service;

import org.example.model.conection.ConexionBD;
import org.example.model.dao.UsuarioDAO;
import org.example.model.entity.Enum.Rol;
import org.example.model.entity.Usuario;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    // Autenticación de usuario con verificación de contraseña cifrada
    public Usuario autenticarUsuario(String email, String password) {
        try {
            Usuario usuario = usuarioDAO.buscarPorEmail(email);

            if (usuario == null) {
                System.out.println("❌ Usuario no encontrado: " + email);
                return null;
            }

            if (BCrypt.checkpw(password, usuario.getPassword())) {
                System.out.println("✅ Usuario autenticado correctamente: " + email);
                return usuario;
            } else {
                System.out.println("❌ Contraseña incorrecta para el usuario: " + email);
            }
        } catch (Exception e) {
            System.err.println("Error al autenticar usuario: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    // Registro de usuario con validación de correo y cifrado de contraseña
    public boolean registrarUsuario(String nombre, String email, String password, Rol rol) {
        try {
            if (usuarioDAO.correoExiste(email)) {
                System.out.println("❌ El correo ya está registrado: " + email);
                return false;
            }

            String contraseñaHash = BCrypt.hashpw(password, BCrypt.gensalt()); // Cifrar contraseña

            Usuario nuevoUsuario = new Usuario(
                    0,
                    nombre,
                    email,
                    contraseñaHash, // Contraseña cifrada
                    rol,
                    java.time.LocalDate.now()
            );

            boolean registrado = usuarioDAO.registrarUsuario(nuevoUsuario);
            if (registrado) {
                System.out.println("✅ Usuario registrado exitosamente: " + email);
            } else {
                System.out.println("❌ Error al registrar usuario: " + email);
            }
            return registrado;

        } catch (Exception e) {
            System.err.println("Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    public boolean correoExiste(String email) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Si COUNT(*) > 0, el correo ya existe
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
