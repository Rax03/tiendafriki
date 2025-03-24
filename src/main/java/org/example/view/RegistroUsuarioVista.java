package org.example.view;


import javax.swing.*;

public class RegistroUsuarioVista extends JFrame {
    private JTextField campoNombre, campoEmail;
    private JPasswordField campoContraseña, campoConfirmarContraseña;
    private JButton botonRegistrar, botonCancelar;

    public RegistroUsuarioVista() {
        setTitle("Registro de Usuario - Tienda Friki");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel etiquetaTitulo = new JLabel("Registrar Usuario");
        etiquetaTitulo.setFont(etiquetaTitulo.getFont().deriveFont(24f));
        etiquetaTitulo.setBounds(100, 20, 200, 30);
        panel.add(etiquetaTitulo);

        JLabel etiquetaNombre = new JLabel("Nombre:");
        etiquetaNombre.setBounds(30, 70, 100, 30);
        panel.add(etiquetaNombre);

        campoNombre = new JTextField();
        campoNombre.setBounds(150, 70, 200, 30);
        panel.add(campoNombre);

        JLabel etiquetaEmail = new JLabel("Email:");
        etiquetaEmail.setBounds(30, 110, 100, 30);
        panel.add(etiquetaEmail);

        campoEmail = new JTextField();
        campoEmail.setBounds(150, 110, 200, 30);
        panel.add(campoEmail);

        JLabel etiquetaContraseña = new JLabel("Contraseña:");
        etiquetaContraseña.setBounds(30, 150, 100, 30);
        panel.add(etiquetaContraseña);

        campoContraseña = new JPasswordField();
        campoContraseña.setBounds(150, 150, 200, 30);
        panel.add(campoContraseña);

        JLabel etiquetaConfirmar = new JLabel("Confirmar Contraseña:");
        etiquetaConfirmar.setBounds(30, 190, 150, 30);
        panel.add(etiquetaConfirmar);

        campoConfirmarContraseña = new JPasswordField();
        campoConfirmarContraseña.setBounds(150, 190, 200, 30);
        panel.add(campoConfirmarContraseña);

        botonRegistrar = new JButton("Registrar");
        botonRegistrar.setBounds(80, 250, 100, 30);
        panel.add(botonRegistrar);

        botonCancelar = new JButton("Cancelar");
        botonCancelar.setBounds(220, 250, 100, 30);
        panel.add(botonCancelar);

        add(panel);
    }

    public String getNombre() { return campoNombre.getText(); }
    public String getEmail() { return campoEmail.getText(); }
    public String getContraseña() { return new String(campoContraseña.getPassword()); }
    public String getConfirmarContraseña() { return new String(campoConfirmarContraseña.getPassword()); }
    public JButton getBotonRegistrar() { return botonRegistrar; }
    public JButton getBotonCancelar() { return botonCancelar; }
}
