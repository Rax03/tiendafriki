package org.example.controller;

import org.example.model.entity.Usuario;
import org.example.model.service.LoginService;
import org.example.view.LoginVista;

import javax.swing.*;

public class LoginControlador {
    private LoginVista vista;
    private LoginService loginService;

    public LoginControlador(LoginVista vista, LoginService loginService) {
        this.vista = vista;
        this.loginService = loginService;

        // Conectar eventos de botones
        this.vista.getBotonLogin().addActionListener(e -> autenticarUsuario());
        this.vista.getBotonRegistrar().addActionListener(e -> abrirVentanaRegistro());
    }

    private void autenticarUsuario() {
        try {
            String email = vista.getEmail();
            String contraseña = vista.getContraseña();

            // Validar campos no vacíos
            if (email.isEmpty() || contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, ingresa tanto el correo como la contraseña.");
                return;
            }

            Usuario usuario = loginService.autenticarUsuario(email, contraseña);

            if (usuario != null) {
                // Navegación según el rol del usuario
                switch (usuario.getRol()) {
                    case ADMIN -> {
                        JOptionPane.showMessageDialog(vista, "Inicio de sesión como Administrador.");
                        vista.dispose();
                        // Lógica para abrir AdminVista
                    }
                    case CLIENTE -> {
                        JOptionPane.showMessageDialog(vista, "Inicio de sesión como Cliente.");
                        vista.dispose();
                        // Lógica para abrir UsuarioVista
                    }
                    default -> JOptionPane.showMessageDialog(vista, "Rol desconocido. Consulta con soporte.");
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Correo o contraseña incorrectos.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Ocurrió un error inesperado: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void abrirVentanaRegistro() {
        try {
            // Implementación de lógica para abrir la vista de registro
            JOptionPane.showMessageDialog(vista, "Ventana de registro no implementada.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al abrir la ventana de registro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
