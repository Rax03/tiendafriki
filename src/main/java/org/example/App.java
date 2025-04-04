package org.example;

import com.formdev.flatlaf.FlatLightLaf;
import org.example.controller.LoginControlador;
import org.example.controller.AdminControlador;
import org.example.model.entity.Usuario;
import org.example.view.AdminVista;
import org.example.view.LoginVista;
import org.example.view.UsuarioVista;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import static org.example.model.entity.Enum.Rol.ADMIN;
import static org.example.model.entity.Enum.Rol.CLIENTE;

public class App {
    public static void main(String[] args) {
        // Configurar el tema visual FlatLaf
        configurarTemaFlatLaf();

        SwingUtilities.invokeLater(() -> {
            try {
                // Crear la vista de inicio de sesión
                LoginVista loginVista = new LoginVista();

                // Controlador para manejar la lógica de inicio de sesión y navegación según el rol
                LoginControlador loginControlador = new LoginControlador(loginVista, (usuario) -> {
                    loginVista.dispose(); // Cerrar la ventana de inicio de sesión

                    // Navegación según el rol del usuario
                    switch (usuario.getRol()) {
                        case ADMIN -> abrirAdminVista();
                        case CLIENTE -> abrirUsuarioVista(usuario);
                        default -> {
                            System.err.println("Rol desconocido: " + usuario.getRol());
                            throw new IllegalStateException("Rol inesperado encontrado: " + usuario.getRol());
                        }
                    }
                });

                // Mostrar la vista de inicio de sesión
                loginVista.setVisible(true);
            } catch (Exception e) {
                manejarErrorInicializacion(e);
            }
        });
    }

    private static void configurarTemaFlatLaf() {
        try {
            // Aplicar el tema visual FlatLightLaf
            UIManager.setLookAndFeel(new FlatLightLaf());
            System.out.println("Tema FlatLaf aplicado exitosamente.");
        } catch (Exception e) {
            System.err.println("No se pudo aplicar el tema FlatLaf: " + e.getMessage());
        }
    }

    private static void manejarErrorInicializacion(Exception e) {
        // Manejar errores de inicialización de la aplicación
        System.err.println("Error durante la inicialización de la aplicación: " + e.getMessage());
        e.printStackTrace();
    }

    private static void abrirAdminVista() {
        try {
            // Inicializar y mostrar la vista de administrador
            System.out.println("Abriendo AdminVista...");
            AdminVista adminVista = new AdminVista();
            new AdminControlador(adminVista); // Controlador correspondiente
            adminVista.setVisible(true);
        } catch (Exception e) {
            System.err.println("Error al abrir AdminVista: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void abrirUsuarioVista(Usuario usuario) {
        try {
            // Inicializar y mostrar la vista de usuario con información personalizada
            System.out.println("Abriendo UsuarioVista...");
            UsuarioVista usuarioVista = new UsuarioVista(usuario.getId());
            usuarioVista.setVisible(true);
        } catch (Exception e) {
            System.err.println("Error al abrir UsuarioVista: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
