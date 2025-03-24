package org.example.view;

import javax.swing.*;
import java.awt.*;

public class LoginVista extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoContraseña;
    private JButton botonLogin, botonRegistrar;

    public LoginVista() {
        setTitle("Inicio de Sesión - Tienda Friki");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear los componentes
        JLabel etiquetaTitulo = new JLabel("¡Bienvenido a Tienda Friki!");
        etiquetaTitulo.setFont(new Font("Comic Sans MS", Font.BOLD, 22)); // Fuente divertida y llamativa
        etiquetaTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaTitulo.setForeground(new Color(128, 0, 128)); // Púrpura
        
        JLabel etiquetaLogo = new JLabel();
        etiquetaLogo.setHorizontalAlignment(SwingConstants.CENTER);
        etiquetaLogo.setIcon(new ImageIcon(getClass().getResource("/icons/ico.jpg"))); // Cargar el logo

        JLabel etiquetaEmail = new JLabel("Correo Electrónico:");
        etiquetaEmail.setForeground(new Color(0, 102, 204)); // Azul

        JLabel etiquetaContraseña = new JLabel("Contraseña:");
        etiquetaContraseña.setForeground(new Color(0, 102, 204)); // Azul

        campoEmail = new JTextField(20);
        campoContraseña = new JPasswordField(20);

        botonLogin = new JButton("Iniciar Sesión");
        botonLogin.setBackground(new Color(0, 204, 102)); // Verde brillante
        botonLogin.setForeground(Color.WHITE);
        botonLogin.setFocusPainted(false);

        botonRegistrar = new JButton("Registrarse");
        botonRegistrar.setBackground(new Color(255, 153, 0)); // Naranja
        botonRegistrar.setForeground(Color.WHITE);
        botonRegistrar.setFocusPainted(false);

        // Fondo de la ventana
        JPanel panel = new JPanel();
        panel.setBackground(new Color(240, 240, 240)); // Gris claro
        panel.setLayout(null);

        // Posiciones de los elementos
        etiquetaTitulo.setBounds(50, 20, 300, 30);
        etiquetaEmail.setBounds(50, 80, 150, 20);
        campoEmail.setBounds(180, 80, 150, 25);
        etiquetaContraseña.setBounds(50, 120, 150, 20);
        campoContraseña.setBounds(180, 120, 150, 25);
        botonLogin.setBounds(50, 180, 130, 30);
        botonRegistrar.setBounds(200, 180, 130, 30);

        // Agregar elementos al panel
        panel.add(etiquetaTitulo);
        panel.add(etiquetaEmail);
        panel.add(campoEmail);
        panel.add(etiquetaContraseña);
        panel.add(campoContraseña);
        panel.add(botonLogin);
        panel.add(botonRegistrar);

        // Agregar el panel a la ventana

// Establece un icono para la ventana
        ImageIcon icono = new ImageIcon(getClass().getResource("/icons/ico.jpg"));
        setIconImage(icono.getImage());

        add(panel);
    }

    public String getEmail() {
        return campoEmail.getText();
    }

    public String getContraseña() {
        return new String(campoContraseña.getPassword());
    }

    public JButton getBotonLogin() {
        return botonLogin;
    }

    public JButton getBotonRegistrar() {
        return botonRegistrar;
    }
}
