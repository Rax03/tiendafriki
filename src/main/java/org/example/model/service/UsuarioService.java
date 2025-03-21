package org.example.model.service;

import org.example.model.entity.Usuario;
import org.example.model.entity.Enum.Rol;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UsuarioService {

    private static Map<String, Usuario> usuarios = new HashMap<>();

    static {
        usuarios.put("admin", new Usuario(1, "admin", "admin@example.com", "password", Rol.ADMin, new Date()));
        usuarios.put("cliente", new Usuario(2, "cliente", "cliente@example.com", "password", Rol.Cliente, new Date()));
    }

    public Usuario authenticate(String username, String password) {
        Usuario usuario = usuarios.get(username);
        if (usuario != null && usuario.getPassword().equals(password)) {
            return usuario;
        }
        return null;
    }
}
