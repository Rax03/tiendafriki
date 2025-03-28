package org.example.view;

import org.example.model.dao.UsuarioDAO;
import org.example.model.entity.Usuario;

import javax.swing.*;
import java.awt.*;

public class ClienteVista extends JFrame {

    private JTable tablaProductos;
    private JTable tablaCarrito;
    private JTable tablaPedidos;
    private JButton btnAgregarCarrito;
    private JButton btnProcesarPedido;
    private JButton btnActualizarDatos;

    public ClienteVista(int idUsuario) { // Recibe el ID del usuario para cargar datos personales
        setTitle("Panel de Cliente - Tienda Friki");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Crear pestañas
        JTabbedPane pestañas = new JTabbedPane();

        // Pestañas funcionales
        pestañas.addTab("Catálogo de Productos", crearPanelCatalogoProductos());
        pestañas.addTab("Carrito de Compras", crearPanelCarrito());
        pestañas.addTab("Historial de Pedidos", crearPanelHistorialPedidos());
        pestañas.addTab("Mis Datos", crearPanelDatosPersonales(idUsuario)); // Carga dinámica de datos

        pestañas.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        pestañas.setForeground(new Color(255, 153, 51)); // Naranja vibrante

        // Agregar pestañas al marco
        add(pestañas);

        setVisible(true);
    }

    // Panel para visualizar productos
    private JPanel crearPanelCatalogoProductos() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Catálogo de Productos", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(new Color(255, 255, 255));
        panel.setBackground(new Color(34, 34, 34));

        tablaProductos = new JTable(
                new Object[][] {
                        {"1", "Funko Pop - Spider-Man", "Figuras", "$15.00"},
                        {"2", "Camiseta Star Wars", "Ropa Geek", "$20.00"}
                },
                new String[] {"ID", "Nombre", "Categoría", "Precio"}
        );
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaProductos.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tablaProductos);

        JPanel botones = new JPanel(new FlowLayout());
        botones.setBackground(new Color(34, 34, 34));
        btnAgregarCarrito = new JButton("Agregar al Carrito");
        estilizarBoton(btnAgregarCarrito);
        botones.add(btnAgregarCarrito);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    // Panel para cargar datos personales desde la base de datos
    private JPanel crearPanelDatosPersonales(int idUsuario) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Mis Datos Personales", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panel.setBackground(new Color(34, 34, 34));

        // Panel interno para mostrar los datos personales
        JPanel datosPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        datosPanel.setBackground(new Color(34, 34, 34));

        // Recuperar datos del usuario desde la base de datos
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.obtenerDatosUsuario(idUsuario); // Usamos el ID del usuario

        JLabel lblNombre = new JLabel("Nombre: " + (usuario != null ? usuario.getNombre() : "No disponible"));
        JLabel lblEmail = new JLabel("Email: " + (usuario != null ? usuario.getEmail() : "No disponible"));
        JLabel lblRol = new JLabel("Rol: " + (usuario != null ? usuario.getRol().name() : "No disponible"));
        JLabel lblFechaRegistro = new JLabel("Fecha de Registro: " + (usuario != null ? usuario.getFechaRegistro() : "No disponible"));

        // Configuración visual de etiquetas
        lblNombre.setForeground(Color.WHITE);
        lblEmail.setForeground(Color.WHITE);
        lblRol.setForeground(Color.WHITE);
        lblFechaRegistro.setForeground(Color.WHITE);

        datosPanel.add(lblNombre);
        datosPanel.add(lblEmail);
        datosPanel.add(lblRol);
        datosPanel.add(lblFechaRegistro);

        // Botón para actualizar datos
        JPanel botones = new JPanel(new FlowLayout());
        botones.setBackground(new Color(34, 34, 34));
        btnActualizarDatos = new JButton("Actualizar Datos");
        estilizarBoton(btnActualizarDatos);

        botones.add(btnActualizarDatos);

        // Agregar todo al panel principal
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(datosPanel, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelCarrito() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Carrito de Compras", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(new Color(255, 255, 255));
        panel.setBackground(new Color(34, 34, 34));

        tablaCarrito = new JTable(
                new Object[][] {
                        {"1", "Funko Pop - Spider-Man", "$15.00", "1", "$15.00"}
                },
                new String[] {"ID", "Producto", "Precio Unitario", "Cantidad", "Total"}
        );
        tablaCarrito.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaCarrito.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tablaCarrito);

        JPanel botones = new JPanel(new FlowLayout());
        botones.setBackground(new Color(34, 34, 34));
        btnProcesarPedido = new JButton("Procesar Pedido");
        estilizarBoton(btnProcesarPedido);
        botones.add(btnProcesarPedido);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelHistorialPedidos() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Historial de Pedidos", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(new Color(255, 255, 255));
        panel.setBackground(new Color(34, 34, 34));

        tablaPedidos = new JTable(
                new Object[][] {
                        {"1001", "2023-05-01", "Entregado", "$50.00"},
                        {"1002", "2023-05-03", "Pendiente", "$30.00"}
                },
                new String[] {"ID Pedido", "Fecha", "Estado", "Total"}
        );
        tablaPedidos.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaPedidos.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tablaPedidos);
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        boton.setBackground(new Color(0, 153, 76));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
    }
}
