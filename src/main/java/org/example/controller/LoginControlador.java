package org.example.controller;

import org.example.view.LoginVista;
import org.example.view.RegistroUsuarioVista;

import javax.swing.*;

public class LoginControlador {
    private LoginVista vista;

    public LoginControlador(LoginVista vista) {
        this.vista = vista;

        vista.getBotonLogin().addActionListener(e -> autenticarUsuario());
        vista.getBotonRegistrar().addActionListener(e -> abrirVentanaRegistro());
    }

    private void autenticarUsuario() {
        // Aquí puedes agregar la lógica para autenticar al usuario
        JOptionPane.showMessageDialog(vista, "Autenticación no implementada aún.");
    }

    private void abrirVentanaRegistro() {
        RegistroUsuarioVista registroVista = new RegistroUsuarioVista();
        new RegistroUsuarioControlador(registroVista); // Vincula el registro con su controlador
        registroVista.setVisible(true);
        vista.dispose(); // Cierra la ventana de inicio de sesión
    }
}
