package org.example.controller;

import org.example.model.entity.Usuario;
import org.example.model.service.LoginService;
import org.example.view.AdminVista;
import org.example.view.RegistroUsuarioVista;
import org.example.view.UsuarioVista;
import org.example.view.LoginVista;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginControlador {

    private static final Logger logger = Logger.getLogger(LoginControlador.class.getName());
    private final LoginVista vista;
    private final LoginService loginService;

    public LoginControlador(LoginVista vista, LoginService loginService) {
        this.vista = vista;
        this.loginService = loginService;

        // Conectar los eventos de los botones en la vista
        this.vista.getBotonLogin().addActionListener(e -> autenticarUsuario());
        this.vista.getBotonRegistrar().addActionListener(e -> abrirVentanaRegistro());
    }

    private void autenticarUsuario() {
        try {
            String email = vista.getEmail();
            String contraseña = vista.getContraseña();

            if (!validarCredenciales(email, contraseña)) {
                return;
            }

            Usuario usuario = loginService.autenticarUsuario(email, contraseña);

            if (usuario != null) {
                manejarRolUsuario(usuario);
            } else {
                mostrarMensaje("Correo o contraseña incorrectos.");
            }
        } catch (Exception e) {
            mostrarError("Error de autenticación", e);
        }
    }

    private boolean validarCredenciales(String email, String contraseña) {
        if (email == null || email.isEmpty() || contraseña == null || contraseña.isEmpty()) {
            mostrarMensaje("Por favor, ingresa tanto el correo como la contraseña.");
            return false;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            mostrarMensaje("El email no tiene un formato válido.");
            return false;
        }

        return true;
    }

    private void manejarRolUsuario(Usuario usuario) {
        switch (usuario.getRol()) {
            case ADMIN -> abrirAdminVista();
            case CLIENTE -> abrirUsuarioVista(usuario);
            default -> {
                mostrarMensaje("Rol desconocido. Consulta con soporte.");
                logger.log(Level.WARNING, "Rol desconocido para el usuario con email: " + usuario.getEmail());
            }
        }
    }

    private void abrirVentanaRegistro() {
        try {
            RegistroUsuarioVista registroVista = new RegistroUsuarioVista();
            new RegistroUsuarioControlador(registroVista); // ✅ Conecta eventos aquí
            registroVista.setVisible(true); // ✅ Luego muestra la vista
            vista.dispose();
        } catch (Exception e) {
            mostrarError("Error al abrir la ventana de registro", e);
        }
    }

    private void abrirAdminVista() {
        try {
            AdminVista adminVista = new AdminVista();
            new AdminControlador(adminVista);
            adminVista.setVisible(true);
            vista.dispose();
        } catch (Exception e) {
            mostrarError("Error al abrir la vista de administrador", e);
        }
    }

    private void abrirUsuarioVista(Usuario usuario) {
        if (usuario == null) {
            mostrarMensaje("El usuario no existe o no se pudo autenticar.");
            return;
        }

        try {
            UsuarioVista usuarioVista = new UsuarioVista(usuario.getNombre(), usuario.getEmail(), usuario.getId());
            new UsuarioControlador(usuarioVista);
            usuarioVista.setVisible(true);
            vista.dispose();
        } catch (IllegalArgumentException e) {
            mostrarMensaje("Se produjo un error con los datos del usuario.");
            logger.log(Level.WARNING, "Datos incorrectos al abrir UsuarioVista para el usuario: " + usuario.getEmail(), e);
        } catch (Exception e) {
            mostrarError("Error al abrir la vista de cliente", e);
        }
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String titulo, Exception e) {
        JOptionPane.showMessageDialog(vista, titulo + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        logger.log(Level.SEVERE, titulo, e);
    }
}
