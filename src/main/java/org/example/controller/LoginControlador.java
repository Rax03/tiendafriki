package org.example.controller;

import org.example.model.entity.Usuario;
import org.example.model.service.LoginService;
import org.example.view.AdminVista;
import org.example.view.RegistroUsuarioVista;
import org.example.view.UsuarioVista;
import org.example.view.LoginVista;

import javax.swing.*;

public class LoginControlador {
    private LoginVista vista;
    private LoginService loginService;

    /**
     * Constructor para el controlador de login.
     * @param vista La vista de login.
     * @param loginService El servicio de login que maneja la lógica de negocio.
     */
    public LoginControlador(LoginVista vista, LoginService loginService) {
        this.vista = vista;
        this.loginService = loginService;

        // Conectar los eventos de los botones en la vista
        this.vista.getBotonLogin().addActionListener(e -> autenticarUsuario());
        this.vista.getBotonRegistrar().addActionListener(e -> abrirVentanaRegistro());
    }

    /**
     * Método para autenticar al usuario.
     * Valida los campos y utiliza el servicio de login para la autenticación.
     */
    private void autenticarUsuario() {
        try {
            String email = vista.getEmail();
            String contraseña = vista.getContraseña();

            // Validar campos no vacíos
            if (email.isEmpty() || contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, ingresa tanto el correo como la contraseña.");
                return;
            }

            // Autenticar usuario utilizando el servicio
            Usuario usuario = loginService.autenticarUsuario(email, contraseña);

            if (usuario != null) {
                // Navegación según el rol del usuario
                switch (usuario.getRol()) {
                    case ADMIN -> {
                        JOptionPane.showMessageDialog(vista, "Inicio de sesión como Administrador.");
                        vista.dispose();
                        abrirAdminVista(); // Método para abrir la vista de administrador
                    }
                    case CLIENTE -> {
                        JOptionPane.showMessageDialog(vista, "Inicio de sesión como Cliente.");
                        vista.dispose();
                        abrirUsuarioVista(usuario); // Método para abrir la vista de cliente
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

    /**
     * Método para abrir la ventana de registro.
     * Maneja la acción del botón de registro en la vista.
     */
    private void abrirVentanaRegistro() {
        try {
            RegistroUsuarioVista registroVista = new RegistroUsuarioVista(); // Crear una instancia de RegistroUsuarioVista
            registroVista.setVisible(true); // Mostrar la ventana de registro
            vista.dispose(); // Cierra la ventana de login
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al abrir la ventana de registro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método para abrir la vista de administrador.
     * Ajusta la lógica y navegación para administradores.
     */
    private void abrirAdminVista() {
        try {
            // Crear instancia de AdminVista
            AdminVista adminVista = new AdminVista();

            // Vincular AdminVista con AdminControlador
            new AdminControlador(adminVista);

            // Hacer visible la vista de administrador
            adminVista.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al abrir la vista de administrador: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Método para abrir la vista de cliente.
     * @param usuario El usuario autenticado.
     */
    private void abrirUsuarioVista(Usuario usuario) {
        try {
            UsuarioVista usuarioVista = new UsuarioVista(usuario.getId()); // Crear una instancia de UsuarioVista
            usuarioVista.setVisible(true); // Mostrar la ventana
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al abrir la vista de cliente: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
