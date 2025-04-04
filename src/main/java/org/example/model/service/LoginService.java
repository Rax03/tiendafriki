package org.example.model.service;

import org.example.model.dao.UsuarioDAO;
import org.example.model.entity.Usuario;

public class LoginService {
    private UsuarioDAO usuarioDAO;

    public LoginService(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    /**
     * Método para autenticar un usuario según su correo y contraseña.
     * @param email Correo del usuario.
     * @param contraseña Contraseña del usuario.
     * @return Usuario autenticado o null si la autenticación falla.
     */
    public Usuario autenticarUsuario(String email, String contraseña) {
        Usuario usuario = usuarioDAO.buscarPorEmail(email);

        // Validar usuario encontrado y autenticar
        if (usuario != null && usuarioDAO.autenticarUsuario(email, contraseña)) {
            return usuario;
        }
        return null; // Retorna null si la autenticación falla
    }
}
