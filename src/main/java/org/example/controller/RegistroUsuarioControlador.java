package org.example.controller;

import org.example.model.dao.UsuarioDAO;
import org.example.view.LoginVista;
import org.example.view.RegistroUsuarioVista;

import javax.swing.*;

public class RegistroUsuarioControlador {
    private RegistroUsuarioVista vista;
    private UsuarioDAO usuarioDAO;

    public RegistroUsuarioControlador(RegistroUsuarioVista vista) {
        this.vista = vista;
        this.usuarioDAO = new UsuarioDAO();

        vista.getBotonRegistrar().addActionListener(e -> registrarUsuario());
        vista.getBotonCancelar().addActionListener(e -> cancelarRegistro());
    }

    private void registrarUsuario() {
        String nombre = vista.getNombre();
        String email = vista.getEmail();
        String contraseña = vista.getContraseña();
        String confirmarContraseña = vista.getConfirmarContraseña();

        if (nombre.isEmpty() || email.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios.");
            return;
        }

        if (!contraseña.equals(confirmarContraseña)) {
            JOptionPane.showMessageDialog(vista, "Las contraseñas no coinciden.");
            return;
        }

        String contraseñaHash = contraseña; // En un entorno real, debes cifrar la contraseña

        if (!usuarioDAO.correoExiste(email)) {
            if (usuarioDAO.registrarUsuario(nombre, email, contraseñaHash)) {
                JOptionPane.showMessageDialog(vista, "Usuario registrado exitosamente.");
                vista.dispose(); // Cierra la ventana de registro
                abrirInicioSesion(); // Regresa al inicio de sesión
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar usuario.");
            }
        } else {
            JOptionPane.showMessageDialog(vista, "El correo ya está registrado.");
        }
    }

    private void cancelarRegistro() {
        vista.dispose(); // Cierra la ventana de registro
        abrirInicioSesion(); // Abre la ventana de inicio de sesión
    }

    private void abrirInicioSesion() {
        LoginVista loginVista = new LoginVista();
        new LoginControlador(loginVista); // Vincula la vista de inicio de sesión con su controlador
        loginVista.setVisible(true);
    }
}
