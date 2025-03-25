package org.example.view;

import javax.swing.*;
import java.awt.*;

public class RegistroUsuarioVista extends JFrame {
    private JTextField campoNombre;
    private JTextField campoEmail;
    private JPasswordField campoContraseña;
    private JPasswordField campoConfirmarContraseña;
    private JComboBox<String> comboRol;
    private JButton botonRegistrar;
    private JButton botonCancelar;

    public RegistroUsuarioVista() {
        setTitle("Registro de Usuario");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setResizable(false);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Márgenes
        add(panelPrincipal);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Espaciado entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("Registro de Usuario");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Ocupa dos columnas
        panelPrincipal.add(titulo, gbc);

        // Etiqueta y campo: Nombre
        gbc.gridwidth = 1; // Restablece a una columna
        gbc.gridy++;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        campoNombre = new JTextField(20);
        panelPrincipal.add(campoNombre, gbc);

        // Etiqueta y campo: Email
        gbc.gridy++;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        campoEmail = new JTextField(20);
        panelPrincipal.add(campoEmail, gbc);

        // Etiqueta y campo: Contraseña
        gbc.gridy++;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Contraseña:"), gbc);

        gbc.gridx = 1;
        campoContraseña = new JPasswordField(20);
        panelPrincipal.add(campoContraseña, gbc);

        // Etiqueta y campo: Confirmar Contraseña
        gbc.gridy++;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Confirmar Contraseña:"), gbc);

        gbc.gridx = 1;
        campoConfirmarContraseña = new JPasswordField(20);
        panelPrincipal.add(campoConfirmarContraseña, gbc);

        // Etiqueta y desplegable: Rol
        gbc.gridy++;
        gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Rol:"), gbc);

        gbc.gridx = 1;
        comboRol = new JComboBox<>(new String[]{"ADMIN", "CLIENTE"});
        comboRol.setFont(new Font("Arial", Font.PLAIN, 14));
        panelPrincipal.add(comboRol, gbc);

        // Botones: Registrar y Cancelar
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botonRegistrar = new JButton("Registrar");
        botonCancelar = new JButton("Cancelar");
        panelBotones.add(botonRegistrar);
        panelBotones.add(botonCancelar);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2; // Ocupa dos columnas
        panelPrincipal.add(panelBotones, gbc);

        // Mostrar la ventana
        setVisible(true);
    }

    // Métodos para obtener datos de los campos
    public String getNombre() {
        return campoNombre.getText();
    }

    public String getEmail() {
        return campoEmail.getText();
    }

    public String getContraseña() {
        return new String(campoContraseña.getPassword());
    }

    public String getConfirmarContraseña() {
        return new String(campoConfirmarContraseña.getPassword());
    }

    public String getRolSeleccionado() {
        return comboRol.getSelectedItem().toString().toUpperCase(); // Asegúrate de usar `toUpperCase()`
    }


    public JButton getBotonRegistrar() {
        return botonRegistrar;
    }

    public JButton getBotonCancelar() {
        return botonCancelar;
    }
}
