package org.example.controller;

import org.example.model.dao.UsuarioDAO;
import org.example.model.entity.Enum.Rol;
import org.example.model.entity.Usuario;
import org.example.view.LoginVista;
import org.example.view.RegistroUsuarioVista;

import javax.swing.*;
import java.time.LocalDate;

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
        try {
            // Captura los datos desde la vista
            String nombre = vista.getNombre();
            String email = vista.getEmail();
            String contraseña = vista.getContraseña();
            String confirmarContraseña = vista.getConfirmarContraseña();
            String rolSeleccionado = vista.getRolSeleccionado(); // Captura el rol seleccionado

            // Validación de campos obligatorios
            if (nombre.isEmpty() || email.isEmpty() || contraseña.isEmpty() || confirmarContraseña.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Todos los campos son obligatorios.");
                return;
            }

            // Validación del formato del correo electrónico
            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                JOptionPane.showMessageDialog(vista, "El correo no tiene un formato válido.");
                return;
            }

            // Verificación de contraseñas coincidentes
            if (!contraseña.equals(confirmarContraseña)) {
                JOptionPane.showMessageDialog(vista, "Las contraseñas no coinciden.");
                return;
            }

            // Verifica si el correo ya está registrado
            if (usuarioDAO.correoExiste(email)) {
                JOptionPane.showMessageDialog(vista, "El correo ya está registrado.");
                return;
            }

            // Mapeo del rol seleccionado al Enum
            Rol rol = Rol.valueOf(rolSeleccionado); // Asegúrate de que el rol esté en mayúsculas

            // Cifrado de la contraseña antes de registrar al usuario
            String contraseñaHash = org.mindrot.jbcrypt.BCrypt.hashpw(contraseña, org.mindrot.jbcrypt.BCrypt.gensalt());

            // Crear el objeto Usuario
            Usuario usuario = new Usuario(0, nombre, email, contraseñaHash, rol, LocalDate.now());

            // Registrar al usuario en la base de datos
            if (usuarioDAO.registrarUsuario(usuario)) {
                JOptionPane.showMessageDialog(vista, "Usuario registrado exitosamente como " + rol + ".");
                vista.dispose(); // Cierra la ventana de registro
                abrirInicioSesion(); // Regresa al inicio de sesión
            } else {
                JOptionPane.showMessageDialog(vista, "Error al registrar usuario. Inténtalo nuevamente.");
            }
        } catch (IllegalArgumentException e) {
            // Error en caso de que el rol no sea válido
            JOptionPane.showMessageDialog(vista, "Rol desconocido. Verifica los valores en el ComboBox.");
        } catch (Exception e) {
            // Manejo de errores inesperados
            e.printStackTrace();
            JOptionPane.showMessageDialog(vista, "Se produjo un error inesperado: " + e.getMessage());
        }
    }

    private void cancelarRegistro() {
        vista.dispose(); // Cierra la ventana de registro
        abrirInicioSesion(); // Regresa a la ventana de inicio de sesión
    }

    private void abrirInicioSesion() {
        LoginVista loginVista = new LoginVista();
        new LoginControlador(loginVista);
        loginVista.setVisible(true);
    }
}
