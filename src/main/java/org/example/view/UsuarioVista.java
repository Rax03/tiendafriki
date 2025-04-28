package org.example.view;

import org.example.model.entity.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsuarioVista extends JFrame {

    // Datos del usuario
    private String nombreUsuario;
    private String correoUsuario;
    private int totalPedidos;

    // Componentes principales
    private JTable tablaProductos;
    private JTable tablaCarrito;
    private DefaultTableModel modeloProductos;
    private DefaultTableModel modeloCarrito;

    // Botones y campos de búsqueda
    private JTextField campoBusqueda;
    private JButton btnBuscar;
    private JButton btnAgregarCarrito;
    private JButton btnEliminarCarrito;
    private JButton btnFinalizarCompra;
    private JButton btnVerHistorial;
    private JButton btnVaciarCarrito;

    // Constructor sin parámetros (para mantener compatibilidad)
    public UsuarioVista() {
        this("Usuario", "correo@email.com", 0); // Valores por defecto si no se pasan parámetros
    }

    // Constructor con datos del usuario
    public UsuarioVista(String nombreUsuario, String correoUsuario, int totalPedidos) {
        this.nombreUsuario = nombreUsuario;
        this.correoUsuario = correoUsuario;
        this.totalPedidos = totalPedidos;

        setTitle("Tienda Friki - Bienvenido, " + nombreUsuario);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Panel de bienvenida
        JPanel panelBienvenida = crearPanelBienvenida();
        panelPrincipal.add(panelBienvenida, BorderLayout.NORTH);

        // Panel de búsqueda y botones principales
        JPanel panelBusqueda = crearPanelBusqueda();
        panelPrincipal.add(panelBusqueda, BorderLayout.CENTER);

        // Pestañas para Productos y Carrito
        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setFont(new Font("Arial", Font.BOLD, 14));

        inicializarTablas();
        pestañas.addTab("Productos", new JScrollPane(tablaProductos));
        pestañas.addTab("Carrito", new JScrollPane(tablaCarrito));

        panelPrincipal.add(pestañas, BorderLayout.CENTER);

        // Panel de botones para el carrito
        JPanel panelBotones = crearPanelBotones();
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Configuración final
        add(panelPrincipal);
        setVisible(true);
    }

    private JPanel crearPanelBienvenida() {
        JPanel panel = new JPanel(new GridLayout(1, 3));
        panel.setBackground(new Color(52, 58, 64));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblNombre = new JLabel("Usuario: " + nombreUsuario);
        JLabel lblCorreo = new JLabel("Correo: " + correoUsuario);
        JLabel lblPedidos = new JLabel("Total Pedidos: " + totalPedidos);

        lblNombre.setForeground(Color.WHITE);
        lblCorreo.setForeground(Color.WHITE);
        lblPedidos.setForeground(Color.WHITE);

        panel.add(lblNombre);
        panel.add(lblCorreo);
        panel.add(lblPedidos);

        return panel;
    }

    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblBuscar = new JLabel("Buscar Producto:");
        campoBusqueda = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        panel.add(lblBuscar);
        panel.add(campoBusqueda);
        panel.add(btnBuscar);
        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAgregarCarrito = new JButton("Agregar al Carrito");
        btnEliminarCarrito = new JButton("Eliminar del Carrito");
        btnFinalizarCompra = new JButton("Finalizar Compra");
        btnVaciarCarrito = new JButton("Vaciar Carrito");
        btnVerHistorial = new JButton("Ver Historial");

        panel.add(btnAgregarCarrito);
        panel.add(btnEliminarCarrito);
        panel.add(btnFinalizarCompra);
        panel.add(btnVaciarCarrito);
        panel.add(btnVerHistorial);
        return panel;
    }

    private void inicializarTablas() {
        // Tabla de productos
        modeloProductos = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Stock"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaProductos = new JTable(modeloProductos);

        // Tabla del carrito
        modeloCarrito = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Cantidad"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Solo la columna cantidad es editable
            }
        };
        tablaCarrito = new JTable(modeloCarrito);
    }

    // Métodos para actualizar las tablas
    public void mostrarProductos(List<Producto> productos) {
        modeloProductos.setRowCount(0);
        for (Producto producto : productos) {
            modeloProductos.addRow(new Object[]{
                    producto.getId_producto(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    producto.getStock()
            });
        }
    }

    public void mostrarCarrito(List<Producto> carrito) {
        modeloCarrito.setRowCount(0);
        for (Producto producto : carrito) {
            modeloCarrito.addRow(new Object[]{
                    producto.getId_producto(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    1 // Inicialmente se agrega un producto con cantidad 1
            });
        }
    }

    public void vaciarCarrito() {
        modeloCarrito.setRowCount(0);
    }

    // Métodos para acceder a los componentes de la vista
    public JTextField getCampoBusqueda() {
        return campoBusqueda;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnAgregarCarrito() {
        return btnAgregarCarrito;
    }

    public JButton getBtnEliminarCarrito() {
        return btnEliminarCarrito;
    }

    public JButton getBtnFinalizarCompra() {
        return btnFinalizarCompra;
    }

    public JButton getBtnVaciarCarrito() {
        return btnVaciarCarrito;
    }

    public JButton getBtnVerHistorial() {
        return btnVerHistorial;
    }

    public JTable getTablaProductos() {
        return tablaProductos;
    }

    public JTable getTablaCarrito() {
        return tablaCarrito;
    }
}
