package org.example.view;

import javax.swing.*;
import java.awt.*;

public class AdminVista extends JFrame {

    private JButton btnProductos, btnProveedores, btnCategorias, btnClientes, btnPedidos, btnUsuarios, btnCerrarSesion;

    public AdminVista() {
        // Configuración principal de la ventana
        setTitle("Panel de Administración - Tienda Friki");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel superior con un título personalizado
        JPanel panelSuperior = new JPanel();
        panelSuperior.setBackground(new Color(52, 58, 64));
        JLabel lblTitulo = new JLabel("👾 Panel de Administración - Tienda Friki 👾");
        lblTitulo.setFont(new Font("Press Start 2P", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(255, 204, 0));
        panelSuperior.add(lblTitulo);

        // Panel principal con botones para diferentes secciones
        JPanel panelPrincipal = new JPanel(new GridLayout(3, 2, 20, 20));
        panelPrincipal.setBackground(new Color(28, 28, 30));

        btnProductos = crearBoton("🎮 Productos", new Color(0, 153, 255));
        btnProveedores = crearBoton("🚚 Proveedores", new Color(102, 204, 0));
        btnCategorias = crearBoton("📂 Categorías", new Color(255, 102, 102));
        btnClientes = crearBoton("🧑‍🤝‍🧑 Clientes", new Color(255, 153, 0));
        btnPedidos = crearBoton("📦 Pedidos", new Color(102, 102, 255));
        btnUsuarios = crearBoton("👨‍💻 Usuarios", new Color(204, 102, 255));
        btnCerrarSesion = crearBoton("Cerrar Sesión", new Color(255, 69, 0));

        // Agregar botones al panel principal
        panelPrincipal.add(btnProductos);
        panelPrincipal.add(btnProveedores);
        panelPrincipal.add(btnCategorias);
        panelPrincipal.add(btnClientes);
        panelPrincipal.add(btnPedidos);
        panelPrincipal.add(btnUsuarios);

        // Panel inferior con el botón "Cerrar Sesión"
        JPanel panelInferior = new JPanel();
        panelInferior.setBackground(new Color(52, 58, 64));
        panelInferior.add(btnCerrarSesion);

        // Agregar los paneles al marco principal
        add(panelSuperior, BorderLayout.NORTH);
        add(panelPrincipal, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JButton crearBoton(String texto, Color colorFondo) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 16));
        boton.setBackground(colorFondo);
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        return boton;
    }

    // Métodos para que el controlador obtenga los botones
    public JButton getBtnProductos() {
        return btnProductos;
    }

    public JButton getBtnProveedores() {
        return btnProveedores;
    }

    public JButton getBtnCategorias() {
        return btnCategorias;
    }

    public JButton getBtnClientes() {
        return btnClientes;
    }

    public JButton getBtnPedidos() {
        return btnPedidos;
    }

    public JButton getBtnUsuarios() {
        return btnUsuarios;
    }

    public JButton getBtnCerrarSesion() {
        return btnCerrarSesion;
    }
}
