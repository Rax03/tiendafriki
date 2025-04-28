package org.example.controller;

import org.example.model.dao.UsuarioDAO;
import org.example.model.entity.Enum.Rol;
import org.example.model.entity.Usuario;
import org.example.model.service.LoginService;
import org.example.model.service.UsuarioService;
import org.example.view.LoginVista;
import org.example.view.RegistroUsuarioVista;

import javax.swing.*;
import java.time.LocalDate;

public class RegistroUsuarioControlador {
    private final RegistroUsuarioVista vista;
    private final UsuarioService usuarioService;

    public RegistroUsuarioControlador(RegistroUsuarioVista vista) {
        this.vista = vista;
        this.usuarioService = new UsuarioService(new UsuarioDAO());

        inicializarEventos();
    }

    private void inicializarEventos() {
        System.out.println("📌 Registrando eventos de botones...");

        if (vista.getBotonRegistrar() == null || vista.getBotonCancelar() == null) {
            System.out.println("❌ Error: Botones no fueron creados correctamente.");
            return;
        }

        vista.getBotonRegistrar().addActionListener(e -> {
            System.out.println("✅ Botón Registrar presionado");
            registrarUsuario();
        });

        vista.getBotonCancelar().addActionListener(e -> {
            System.out.println("❌ Botón Cancelar presionado");
            cancelarRegistro();
        });
    }

    private void registrarUsuario() {
        System.out.println("📌 Iniciando registro de usuario...");

        try {
            String nombre = vista.getNombre();
            String email = vista.getEmail();
            String contraseña = vista.getContraseña();
            String confirmarContraseña = vista.getConfirmarContraseña();
            String rolSeleccionado = vista.getRolSeleccionado();

            if (nombre.isEmpty() || email.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios.");
                return;
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(vista, "El correo no tiene un formato válido.");
                return;
            }

            if (!contraseña.equals(confirmarContraseña)) {
                JOptionPane.showMessageDialog(vista, "Las contraseñas no coinciden.");
                return;
            }

            if (usuarioService.correoExiste(email)) {
                JOptionPane.showMessageDialog(vista, "El correo ya está registrado.");
                return;
            }

            Rol rol = Rol.valueOf(rolSeleccionado);
            String contraseñaHash = org.mindrot.jbcrypt.BCrypt.hashpw(contraseña, org.mindrot.jbcrypt.BCrypt.gensalt());

            Usuario usuario = new Usuario(0, nombre, email, contraseñaHash, rol, LocalDate.now());

            System.out.println("🔄 Intentando registrar usuario...");

            if (usuarioService.registrarUsuario(usuario.getNombre(), usuario.getEmail(), usuario.getPassword(), usuario.getRol())) {
                JOptionPane.showMessageDialog(vista, "✅ Usuario registrado exitosamente como " + usuario.getRol() + ".");
                vista.dispose();
                abrirInicioSesion();
            } else {
                JOptionPane.showMessageDialog(vista, "❌ Error al registrar usuario. Inténtalo nuevamente.");
            }
        } catch (Exception e) {
            System.err.println("🚨 Error al registrar usuario: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Se produjo un error inesperado: " + e.getMessage());
        }
    }

    private void cancelarRegistro() {
        System.out.println("🔄 Redirigiendo a Login...");
        vista.dispose();
        abrirInicioSesion();
    }

    private void abrirInicioSesion() {
        System.out.println("📌 Abriendo pantalla de inicio de sesión...");
        LoginVista loginVista = new LoginVista();
        new LoginControlador(loginVista, new LoginService(new UsuarioDAO()));
        loginVista.setVisible(true);
    }
}
