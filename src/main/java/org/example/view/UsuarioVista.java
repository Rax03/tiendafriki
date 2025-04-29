package org.example.view;

import org.example.model.dao.DetallePedidoDAO;
import org.example.model.entity.Pedido;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class UsuarioVista extends JFrame {

    private JTabbedPane pestañas;
    private JPanel panelProductos, panelCarrito;
    private JButton btnFinalizarPedido, btnCerrarSesion, btnVerHistorial;
    private JTable tablaProductos, tablaCarrito;
    private DefaultTableModel modeloTablaProductos, modeloTablaCarrito;
    private DetallePedidoDAO detallesPedidoDAO;
    private List<String[]> carrito; // Lista para almacenar los productos seleccionados

    public List<String[]> getCarrito() {
        return carrito;
    }

    public UsuarioVista() {
        setTitle("Tienda Friki - Usuario Estándar");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        detallesPedidoDAO = new DetallePedidoDAO(); // Inicializar el DAO
        carrito = new ArrayList<>(); // Inicializar el carrito
        inicializarComponentes();
        cargarProductosDesdeBD(); // Cargar productos al iniciar la vista
    }

    private void inicializarComponentes() {
        pestañas = new JTabbedPane();

        panelProductos = new JPanel(new BorderLayout());
        panelCarrito = new JPanel(new BorderLayout());


        crearMenu();
        crearTablaProductos();
        crearTablaCarrito();
        crearPanelBotones();

        pestañas.addTab("Productos", panelProductos);
        pestañas.addTab("Carrito", panelCarrito);

        add(pestañas, BorderLayout.CENTER);
    }

    private void crearMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menuOpciones = new JMenu("Opciones");
        JMenuItem itemCerrarSesion = new JMenuItem("Cerrar Sesión");

        itemCerrarSesion.addActionListener(e -> cerrarSesion());

        menuOpciones.add(itemCerrarSesion);
        menuBar.add(menuOpciones);
        setJMenuBar(menuBar);
    }

    private void crearTablaProductos() {
        modeloTablaProductos = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Stock"}, 0);
        tablaProductos = new JTable(modeloTablaProductos);

        // Detectar clic en una fila para agregar producto al carrito
        tablaProductos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int filaSeleccionada = tablaProductos.getSelectedRow();
                if (filaSeleccionada != -1) {
                    String[] productoSeleccionado = new String[]{
                            modeloTablaProductos.getValueAt(filaSeleccionada, 0).toString(),  // ID
                            modeloTablaProductos.getValueAt(filaSeleccionada, 1).toString(),  // Nombre
                            modeloTablaProductos.getValueAt(filaSeleccionada, 2).toString(),  // Precio
                            modeloTablaProductos.getValueAt(filaSeleccionada, 3).toString()   // Stock
                    };
                    carrito.add(productoSeleccionado);
                    actualizarTablaCarrito(); // Refrescar la tabla del carrito
                    JOptionPane.showMessageDialog(null, "Producto agregado al carrito: " + productoSeleccionado[1]);
                }
            }
        });

        panelProductos.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
    }

    private void crearTablaCarrito() {
        modeloTablaCarrito = new DefaultTableModel(new String[]{"ID", "Nombre", "Precio", "Stock"}, 0);
        tablaCarrito = new JTable(modeloTablaCarrito);
        panelCarrito.add(new JScrollPane(tablaCarrito), BorderLayout.CENTER);
    }

    public void actualizarTablaCarrito() {
        modeloTablaCarrito.setRowCount(0);
        for (String[] producto : carrito) {
            modeloTablaCarrito.addRow(producto);
        }
    }

    private void crearPanelBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout());

        btnFinalizarPedido = new JButton("Finalizar Pedido");
        btnVerHistorial = new JButton("Ver Historial");
        btnCerrarSesion = new JButton("Cerrar Sesión");

        panelBotones.add(btnFinalizarPedido);
        panelBotones.add(btnVerHistorial);
        panelBotones.add(btnCerrarSesion);

        // Agregar eventos básicos a los botones
        btnCerrarSesion.addActionListener(e -> cerrarSesion());
        btnVerHistorial.addActionListener(e -> JOptionPane.showMessageDialog(this, "Mostrando historial de pedidos..."));
        btnFinalizarPedido.addActionListener(e -> JOptionPane.showMessageDialog(this, "Pedido finalizado."));

        add(panelBotones, BorderLayout.SOUTH);
    }

    private void cerrarSesion() {
        JOptionPane.showMessageDialog(this, "Cerrando sesión...");
        dispose();
        new LoginVista(); // Redirige a la pantalla de inicio de sesión
    }

    private void cargarProductosDesdeBD() {
        List<String[]> productos = detallesPedidoDAO.obtenerProductosDesdeBD();
        if (productos != null && !productos.isEmpty()) {
            actualizarTablaProductos(productos);
        } else {
            JOptionPane.showMessageDialog(this, "No hay productos disponibles en la base de datos.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void actualizarTablaProductos(List<String[]> productos) {
        modeloTablaProductos.setRowCount(0);
        for (String[] producto : productos) {
            modeloTablaProductos.addRow(producto);
        }
    }
    public JButton getBtnFinalizarPedido() {
        return btnFinalizarPedido;
    }

    public JButton getBtnVerHistorial() {
        return btnVerHistorial;
    }

    public JButton getBtnCerrarSesion() {
        return btnCerrarSesion;
    }


    public void mostrarHistorial(List<Pedido> historial) {

        StringBuilder sb = new StringBuilder();
        for (Pedido pedido : historial) {
            sb.append("ID Pedido: ").append(pedido.getIdPedido())
                    .append(", Fecha: ").append(pedido.getFechaPedido())
                    .append(", Estado: ").append(pedido.getEstado())
                    .append(", Total: ").append(pedido.getTotal()).append("\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Historial de Pedidos", JOptionPane.INFORMATION_MESSAGE);
    }
}
