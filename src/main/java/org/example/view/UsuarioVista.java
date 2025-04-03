package org.example.view;

import org.example.model.dao.ProductoDAO;
import org.example.model.dao.UsuarioDAO;
import org.example.model.entity.Producto;
import org.example.model.entity.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class UsuarioVista extends JFrame {

    private JTable tablaProductos;
    private JTable tablaCarrito;
    private JTable tablaPedidos;
    private JButton btnAgregarCarrito;
    private JButton btnProcesarPedido;
    private JButton btnActualizarDatos;

    public UsuarioVista(int idUsuario) {
        setTitle("Panel de Cliente - Tienda Friki");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Crear un panel superior para el botón de cerrar sesión
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        estilizarBoton(btnCerrarSesion);

        // Acción del botón "Cerrar Sesión"
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        panelSuperior.setBackground(new Color(34, 34, 34)); // Fondo oscuro
        panelSuperior.add(btnCerrarSesion);

        // Crear pestañas
        JTabbedPane pestañas = new JTabbedPane();

        // Pestañas funcionales
        pestañas.addTab("Catálogo de Productos", crearPanelCatalogoProductos());
        pestañas.addTab("Carrito de Compras", crearPanelCarrito());
        pestañas.addTab("Historial de Pedidos", crearPanelHistorialPedidos());
        pestañas.addTab("Mis Datos", crearPanelDatosPersonales(idUsuario)); // Carga dinámica de datos

        pestañas.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        pestañas.setForeground(new Color(255, 153, 51)); // Naranja vibrante

        // Agregar panel superior y pestañas al marco
        add(panelSuperior, BorderLayout.NORTH);
        add(pestañas, BorderLayout.CENTER);

        setVisible(true);
    }
    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas cerrar sesión?",
                "Cerrar Sesión",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            dispose(); // Cierra la ventana actual
            // Aquí puedes redirigir al usuario al inicio de sesión
            new LoginVista(); // Asegúrate de que esta clase muestra la pantalla de inicio de sesión
        }
    }


    // Panel para visualizar productos
    private JPanel crearPanelCatalogoProductos() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Catálogo de Productos", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(new Color(255, 255, 255));
        panel.setBackground(new Color(34, 34, 34));

        // Configuración de la tabla
        DefaultTableModel modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Categoría", "Precio", "Imagen"}, 0);
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaProductos.setRowHeight(50); // Ajustar altura para las imágenes

        // Renderizador de la columna de imágenes
        tablaProductos.getColumnModel().getColumn(4).setCellRenderer(new ImageRenderer());

        // Obtener productos desde la base de datos
        ProductoDAO productoDAO = new ProductoDAO();
        List<Producto> productos = productoDAO.obtenerTodosLosProductos();
        for (Producto producto : productos) {
            modeloTabla.addRow(new Object[]{
                    producto.getId(),
                    producto.getNombre(),
                    producto.getId_categoria().getNombre(), // Mostrar el nombre de la categoría
                    "$" + producto.getPrecio(),
                    producto.getImagen() // Ruta de la imagen
            });
        }

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

    // Panel para carrito de compras
    private JPanel crearPanelCarrito() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Carrito de Compras", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(new Color(255, 255, 255));
        panel.setBackground(new Color(34, 34, 34));

        tablaCarrito = new JTable(new DefaultTableModel(
                new String[]{"ID", "Producto", "Precio Unitario", "Cantidad", "Total"}, 0
        ));
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

    // Panel para historial de pedidos
    private JPanel crearPanelHistorialPedidos() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Historial de Pedidos", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(new Color(255, 255, 255));
        panel.setBackground(new Color(34, 34, 34));

        tablaPedidos = new JTable(new DefaultTableModel(
                new String[]{"ID Pedido", "Fecha", "Estado", "Total"}, 0
        ));
        tablaPedidos.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaPedidos.setRowHeight(25);

        JScrollPane scroll = new JScrollPane(tablaPedidos);
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // Método para estilizar botones
    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        boton.setBackground(new Color(0, 153, 76));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
    }

    private class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER);

            if (value != null) {
                String imagePath = value.toString(); // Ruta de la imagen
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    // Cargar la imagen desde la ruta
                    ImageIcon icon = new ImageIcon(imagePath);
                    // Escalar la imagen para ajustarse a las celdas de la tabla
                    Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(scaledImage));
                } else {
                    // Mostrar texto si la imagen no existe
                    label.setText("Sin imagen");
                    label.setForeground(Color.RED); // Destacar con color rojo si falta la imagen
                }
            } else {
                label.setText("Sin imagen");
                label.setForeground(Color.RED);
            }

            return label;
        }
    }
}