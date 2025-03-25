package org.example.model.service;

import org.example.model.dao.UsuarioDAO;
import org.example.model.entity.Enum.Rol;
import org.example.model.entity.Usuario;
import org.mindrot.jbcrypt.BCrypt;

public class UsuarioService {

    private UsuarioDAO usuarioDAO;

    public UsuarioService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public Usuario autenticarUsuario(String email, String password) {
        Usuario usuario = usuarioDAO.buscarPorEmail(email);
        if (usuario != null && BCrypt.checkpw(password, usuario.getPassword())) {
            System.out.println("✅ Usuario autenticado correctamente: " + email);
            return usuario;
        }
        System.out.println("❌ Falló la autenticación para el usuario: " + email);
        return null;
    }

    public boolean registrarUsuario(String nombre, String email, String password, Rol rol) {
        if (usuarioDAO.correoExiste(email)) {
            System.out.println("❌ El correo ya está registrado: " + email);
            return false;
        }

        Usuario nuevoUsuario = new Usuario(
                0,
                nombre,
                email,
                BCrypt.hashpw(password, BCrypt.gensalt()), // Cifrar contraseña
                rol, // Rol dinámico según lo que se pase al método
                java.time.LocalDate.now()
        );

        return usuarioDAO.registrarUsuario(nuevoUsuario);
    }
}

