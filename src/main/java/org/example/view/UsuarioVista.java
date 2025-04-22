package org.example.view;

import org.example.model.entity.Pedido;
import org.example.model.entity.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UsuarioVista extends JFrame {

    // Componentes principales
    private JTable tablaProductos;
    private JTable tablaCarrito;
    private DefaultTableModel modeloProductos;
    private DefaultTableModel modeloCarrito;

    // Botones
    private JButton btnBuscar;
    private JButton btnFinalizarCompra;
    private JButton btnVaciarCarrito;
    private JTextField campoBusqueda;

    public UsuarioVista() {
        setTitle("Tienda Friki");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Panel de búsqueda
        JPanel panelBusqueda = crearPanelBusqueda();
        panelPrincipal.add(panelBusqueda, BorderLayout.NORTH);

        // Pestañas principales
        JTabbedPane pestañas = new JTabbedPane();
        pestañas.setFont(new Font("Arial", Font.BOLD, 14));

        inicializarTablas();
        pestañas.addTab("Productos", new JScrollPane(tablaProductos));
        pestañas.addTab("Carrito", new JScrollPane(tablaCarrito));

        panelPrincipal.add(pestañas, BorderLayout.CENTER);

        // Panel de botones
        JPanel panelBotones = crearPanelBotones();
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Configuración final
        add(panelPrincipal);
        setVisible(true);
    }

    private JPanel crearPanelBusqueda() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblBuscar = new JLabel("Buscar:");
        campoBusqueda = new JTextField(20);
        btnBuscar = new JButton("Buscar");
        panel.add(lblBuscar);
        panel.add(campoBusqueda);
        panel.add(btnBuscar);
        return panel;
    }

    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnFinalizarCompra = new JButton("Finalizar Compra");
        btnVaciarCarrito = new JButton("Vaciar Carrito");
        panel.add(btnFinalizarCompra);
        panel.add(btnVaciarCarrito);
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
                    producto.getId(),
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
                    producto.getId(),
                    producto.getNombre(),
                    producto.getPrecio(),
                    1 // Inicialmente se agrega un producto con cantidad 1
            });
        }
    }

    public void vaciarCarrito() {
        modeloCarrito.setRowCount(0);
    }

    public JTextField getCampoBusqueda() {
        return campoBusqueda;
    }

    public JButton getBtnBuscar() {
        return btnBuscar;
    }

    public JButton getBtnFinalizarCompra() {
        return btnFinalizarCompra;
    }

    public JButton getBtnVaciarCarrito() {
        return btnVaciarCarrito;
    }

    public JTable getTablaProductos() {
        return tablaProductos;
    }

    public JTable getTablaCarrito() {
        return tablaCarrito;
    }
}
