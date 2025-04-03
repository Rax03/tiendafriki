package org.example.controller;

import org.example.model.dao.ClienteDAO;
import org.example.model.dao.UsuarioDAO;
import org.example.model.entity.Usuario;
import org.example.model.entity.Enum.Rol;
import org.example.view.AdminVista;
import org.example.view.UsuarioVista;
import org.example.view.LoginVista;
import org.example.view.RegistroUsuarioVista;

import javax.swing.*;

public class LoginControlador {
    private LoginVista vista;

    public LoginControlador(LoginVista vista) {
        this.vista = vista;

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

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = usuarioDAO.buscarPorEmail(email);

            // Validar usuario encontrado y autenticar
            if (usuario != null && usuarioDAO.autenticarUsuario(email, contraseña)) {
                Rol rol = usuario.getRol();

                switch (rol) {
                    case ADMIN:
                        JOptionPane.showMessageDialog(vista, "Inicio de sesión como Administrador.");
                        vista.dispose();
                        new AdminVista(); // Abre la ventana de administrador
                        break;

                    case CLIENTE:
                        // Verificar si el cliente está registrado
                        ClienteDAO clienteDAO = new ClienteDAO();
                        if (clienteDAO.obtenerClientePorEmail(email) == null) {
                            boolean registrado = clienteDAO.registrarClienteDesdeUsuario(usuario);
                            if (registrado) {
                                JOptionPane.showMessageDialog(vista, "Datos del cliente registrados exitosamente.");
                            } else {
                                JOptionPane.showMessageDialog(vista, "Error al registrar los datos del cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        JOptionPane.showMessageDialog(vista, "Inicio de sesión como Cliente.");
                        vista.dispose();
                        new UsuarioVista(usuario.getId()); // Abre la vista para clientes
                        break;

                    default:
                        JOptionPane.showMessageDialog(vista, "Rol desconocido. Consulta con soporte.");
                        break;
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
            RegistroUsuarioVista registroVista = new RegistroUsuarioVista();
            registroVista.setVisible(true);
            vista.dispose(); // Cerrar login actual
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al abrir la ventana de registro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
