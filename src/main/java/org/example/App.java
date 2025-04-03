package org.example;

import com.formdev.flatlaf.FlatLightLaf; // Puedes cambiar por FlatDarkLaf para el tema oscuro
import org.example.controller.LoginControlador;
import org.example.view.LoginVista;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class App {
    public static void main(String[] args) {
        // Configurar el tema FlatLaf
        try {
            UIManager.setLookAndFeel(new FlatLightLaf()); // Tema claro
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("Error al configurar el tema FlatLaf: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error inesperado al configurar el tema: " + e.getMessage());
        }

        // Iniciar la aplicación
        javax.swing.SwingUtilities.invokeLater(() -> {
            LoginVista loginVista = new LoginVista();
            new LoginControlador(loginVista); // Vincular la vista con el controlador
            loginVista.setVisible(true); // Mostrar la ventana de inicio de sesión
        });
    }
}
