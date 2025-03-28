package org.example.view;

import javax.swing.*;
import java.awt.*;

public class AdminVista extends JFrame {

    public AdminVista() {
        setTitle("Panel de Administración - Tienda Friki");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Crear pestañas
        JTabbedPane pestañas = new JTabbedPane();

        // Agregar pestañas temáticas
        pestañas.addTab("Productos", crearPanelProductos());
        pestañas.addTab("Categorías", crearPanelCategorias());
        pestañas.addTab("Proveedores", crearPanelProveedores());
        pestañas.addTab("Pedidos", crearPanelPedidos());

        pestañas.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        pestañas.setForeground(new Color(255, 153, 51)); // Naranja vibrante

        // Agregar pestañas al marco
        add(pestañas);

        setVisible(true);
    }

    // Panel para Productos
    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Productos", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panel.setBackground(new Color(34, 34, 34));

        JTable tablaProductos = new JTable(
                new Object[][] {
                        {"1", "Funko Pop - Batman", "Figuras", "$15.00"},
                        {"2", "Camiseta Star Wars", "Ropa Geek", "$20.00"}
                },
                new String[] {"ID", "Nombre", "Categoría", "Precio"}
        );
        JScrollPane scroll = new JScrollPane(tablaProductos);

        JPanel botones = new JPanel(new FlowLayout());
        botones.setBackground(new Color(34, 34, 34));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        estilizarBoton(btnAgregar);
        estilizarBoton(btnEditar);
        estilizarBoton(btnEliminar);

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    // Panel para Categorías
    private JPanel crearPanelCategorias() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Categorías", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panel.setBackground(new Color(34, 34, 34));

        JTextArea listaCategorias = new JTextArea("1. Videojuegos\n2. Películas\n3. Ropa Geek\n4. Figuras de Colección");
        listaCategorias.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        listaCategorias.setBackground(Color.BLACK);
        listaCategorias.setForeground(Color.WHITE);
        listaCategorias.setEditable(false);

        JScrollPane scroll = new JScrollPane(listaCategorias);
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // Panel para Proveedores
    private JPanel crearPanelProveedores() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Proveedores", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panel.setBackground(new Color(34, 34, 34));

        JTable tablaProveedores = new JTable(
                new Object[][] {
                        {"1", "Proveedor Geekland", "contacto@geekland.com", "123456789", "Calle Friki 101"},
                        {"2", "Super Friki Supply", "ventas@frikisupply.com", "987654321", "Calle Star Wars 42"}
                },
                new String[] {"ID", "Nombre", "Contacto", "Teléfono", "Dirección"}
        );
        JScrollPane scroll = new JScrollPane(tablaProveedores);

        JPanel botones = new JPanel(new FlowLayout());
        botones.setBackground(new Color(34, 34, 34));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        estilizarBoton(btnAgregar);
        estilizarBoton(btnEditar);
        estilizarBoton(btnEliminar);

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    // Panel para Pedidos
    private JPanel crearPanelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Pedidos", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panel.setBackground(new Color(34, 34, 34));

        JTable tablaPedidos = new JTable(
                new Object[][] {
                        {"1001", "Cliente A", "2023-05-01", "Completado"},
                        {"1002", "Cliente B", "2023-05-03", "Pendiente"}
                },
                new String[] {"ID Pedido", "Cliente", "Fecha", "Estado"}
        );
        JScrollPane scroll = new JScrollPane(tablaPedidos);

        JPanel botones = new JPanel(new FlowLayout());
        botones.setBackground(new Color(34, 34, 34));
        JButton btnActualizar = new JButton("Actualizar");
        estilizarBoton(btnActualizar);

        botones.add(btnActualizar);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        boton.setBackground(new Color(0, 153, 76));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
    }
}
