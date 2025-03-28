package org.example.controller;

import org.example.model.dao.UsuarioDAO;
import org.example.model.entity.Usuario;
import org.example.view.AdminVista;
import org.example.view.ClienteVista;
import org.example.view.LoginVista;
import org.example.view.RegistroUsuarioVista;

import javax.swing.*;

public  class LoginControlador {
    private LoginVista vista;

    public LoginControlador(LoginVista vista) {
        this.vista = vista;

        // Vincular los botones con sus respectivos métodos
        vista.getBotonLogin().addActionListener(e -> autenticarUsuario());
        vista.getBotonRegistrar().addActionListener(e -> abrirVentanaRegistro());
    }

    private void autenticarUsuario() {
        try {
            String email = vista.getEmail();
            String contraseña = vista.getContraseña();

            // Validar que los campos no estén vacíos
            if (email.isEmpty() || contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Por favor, ingresa tanto el correo como la contraseña.");
                return;
            }

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = usuarioDAO.buscarPorEmail(email); // Busca el usuario por su email

            // Validar si se encontró un usuario y autenticarlo
            if (usuario != null && usuarioDAO.autenticarUsuario(email, contraseña)) {
                String rol = usuario.getRol().name(); // Obtener el rol directamente del usuario

                switch (rol.toUpperCase()) {
                    case "ADMIN":
                        JOptionPane.showMessageDialog(vista, "Inicio de sesión como Administrador.");
                        vista.dispose(); // Cierra la ventana de inicio de sesión
                        new AdminVista(); // Abre la ventana de administrador
                        break;
                    case "CLIENTE":
                        JOptionPane.showMessageDialog(vista, "Inicio de sesión como Cliente.");
                        vista.dispose(); // Cierra la ventana de inicio de sesión
                        new ClienteVista(usuario.getId()); // Abre la ventana de cliente con el ID del usuario
                        break;
                    default:
                        JOptionPane.showMessageDialog(vista, "Rol desconocido. Consulta con soporte.");
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(vista, "Correo o contraseña incorrectos.");
            }
        } catch (Exception e) {
            // Manejo de errores inesperados
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Ocurrió un error inesperado: " + e.getMessage());
        }
    }


    private void abrirVentanaRegistro() {
        try {
            RegistroUsuarioVista registroVista = new RegistroUsuarioVista();
            new RegistroUsuarioControlador(registroVista); // Vincula la vista de registro con su controlador
            registroVista.setVisible(true); // Muestra la ventana de registro
            vista.dispose(); // Cierra la ventana de inicio de sesión
        } catch (Exception e) {
            // Manejo de errores al abrir la ventana de registro
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Ocurrió un error al intentar abrir la ventana de registro: " + e.getMessage());
        }
    }
}
