package org.example.view;

import javax.swing.*;
import java.awt.*;

public class AdminVista extends JFrame {

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
        lblTitulo.setFont(new Font("Press Start 2P", Font.BOLD, 22)); // Fuente estilo pixel art
        lblTitulo.setForeground(new Color(255, 204, 0)); // Color dorado friki
        panelSuperior.add(lblTitulo);

        // Panel principal con botones para diferentes secciones
        JPanel panelPrincipal = new JPanel(new GridLayout(3, 2, 20, 20));
        panelPrincipal.setBackground(new Color(28, 28, 30));

        // Crear botones principales personalizados
        JButton btnProductos = crearBoton("🎮 Productos", new Color(0, 153, 255));
        JButton btnProveedores = crearBoton("🚚 Proveedores", new Color(102, 204, 0));
        JButton btnCategorias = crearBoton("📂 Categorías", new Color(255, 102, 102));
        JButton btnClientes = crearBoton("🧑‍🤝‍🧑 Clientes", new Color(255, 153, 0));
        JButton btnPedidos = crearBoton("📦 Pedidos", new Color(102, 102, 255));
        JButton btnUsuarios = crearBoton("👨‍💻 Usuarios", new Color(204, 102, 255));

        // Añadir acciones a los botones
        btnProductos.addActionListener(e -> abrirProductos());
        btnProveedores.addActionListener(e -> abrirProveedores());
        btnCategorias.addActionListener(e -> abrirCategorias());
        btnClientes.addActionListener(e -> abrirClientes());
        btnPedidos.addActionListener(e -> abrirPedidos());
        btnUsuarios.addActionListener(e -> abrirUsuarios());

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
        JButton btnCerrarSesion = crearBoton("Cerrar Sesión", new Color(255, 69, 0));
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
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

    private void abrirProductos() {
        new ProductoVista(); // Abre la vista de Productos
    }

    private void abrirProveedores() {
        new ProveedorVista(); // Abre la vista de Proveedores
    }

    private void abrirCategorias() {
        new CategoriaVista(); // Abre la vista de Categorías
    }

    private void abrirClientes() {
        JOptionPane.showMessageDialog(this, "Vista de Clientes en construcción...");
    }

    private void abrirPedidos() {
        JOptionPane.showMessageDialog(this, "Vista de Pedidos en construcción...");
    }

    private void abrirUsuarios() {
        JOptionPane.showMessageDialog(this, "Vista de Usuarios en construcción...");
    }

    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas cerrar sesión?",
                "Cerrar Sesión", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            dispose();
            new LoginVista();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AdminVista::new); // Ejecuta la vista de administración
    }
}
