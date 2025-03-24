package org.example;

import com.formdev.flatlaf.FlatLightLaf; // O FlatDarkLaf para el tema oscuro
import org.example.controller.LoginControlador;
import org.example.view.LoginVista;

import javax.swing.UIManager;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // Tema claro
        } catch (Exception e) {
            System.err.println("Error al configurar FlatLaf: " + e.getMessage());
        }

        // Inicia tu aplicación
        LoginVista loginVista = new LoginVista();
        new LoginControlador(loginVista);
        loginVista.setVisible(true);
    }
}
