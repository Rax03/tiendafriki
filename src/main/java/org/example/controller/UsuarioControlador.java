package org.example.controller;

import org.example.model.dao.PedidoDAO;
import org.example.model.dao.ProductosDAO;
import org.example.model.entity.Pedido;
import org.example.model.entity.Producto;
import org.example.view.UsuarioVista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class UsuarioControlador {
    private final UsuarioVista vista;
    private final PedidoDAO pedidoDAO;
    private final ProductosDAO productoDAO;
    private final int idUsuario;
    private final List<Producto> carrito;
    private List<Producto> listaProductos; // Lista completa de productos obtenidos de la BD

    public UsuarioControlador(UsuarioVista vista, int idUsuario) {
        this.vista = vista;
        this.pedidoDAO = new PedidoDAO();
        this.productoDAO = new ProductosDAO();
        this.idUsuario = idUsuario;
        this.carrito = new ArrayList<>();

        inicializarEventos();
        cargarProductosEnTabla();
    }

    private void inicializarEventos() {
        vista.getBotonAgregarCarrito().addActionListener(e -> agregarProductoAlCarrito());
        vista.getBotonEliminarCarrito().addActionListener(e -> eliminarProductoDelCarrito());
        vista.getBotonVerCarrito().addActionListener(e -> mostrarCarrito());
        vista.getBotonFinalizarCompra().addActionListener(e -> finalizarCompra());
        vista.getBotonBuscar().addActionListener(e -> cargarProductosEnTabla());
    }

    private void cargarProductosEnTabla() {
        // Guardamos la lista completa de productos obtenida de la BD.
        listaProductos = productoDAO.obtenerTodosLosProductos();
        DefaultTableModel modelo = vista.getModeloTabla();
        modelo.setRowCount(0);

        for (Producto p : listaProductos) {
            ImageIcon icono;
            try {
                icono = new ImageIcon(p.getImagen());
                Image img = icono.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                icono = new ImageIcon(img);
            } catch (Exception e) {
                icono = new ImageIcon(); // Imagen por defecto si falla
            }
            modelo.addRow(new Object[]{
                    icono,
                    p.getNombre(),
                    p.getPrecio(),
                    p.getStock()
            });
        }
    }

    private void agregarProductoAlCarrito() {
        int filaSeleccionada = vista.getTablaProductos().getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(vista, "Selecciona un producto.");
            return;
        }

        // Recuperar el producto completo usando la listaProductos
        Producto productoSeleccionado = listaProductos.get(filaSeleccionada);

        int cantidad;
        try {
            cantidad = Integer.parseInt(vista.getCampoCantidad().getText());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Cantidad no válida.");
            return;
        }

        if (cantidad > productoSeleccionado.getStock() || cantidad <= 0) {
            JOptionPane.showMessageDialog(vista, "Cantidad no disponible en stock.");
            return;
        }

        // Clonar el objeto para el carrito para no modificar la lista original.
        // Se asume que el constructor básico (nombre, precio, imagen) existe y se añadiría la cantidad.
        Producto productoCarrito = new Producto(
                productoSeleccionado.getNombre(),
                productoSeleccionado.getDescripcion(),
                productoSeleccionado.getPrecio(),
                productoSeleccionado.getStock(), 
                productoSeleccionado.getImagen(),
                productoSeleccionado.getId_categoria()
        );
        productoCarrito.setId_producto(productoSeleccionado.getId_producto()); // Asignar ID
        productoCarrito.setStock(cantidad); // Asignar la cantidad a comprar

        carrito.add(productoCarrito);
        mostrarCarrito();
    }

    private void eliminarProductoDelCarrito() {
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El carrito está vacío.");
            return;
        }

        String productoAEliminar = JOptionPane.showInputDialog(vista, "Ingresa el nombre del producto a eliminar:");
        carrito.removeIf(p -> p.getNombre().equalsIgnoreCase(productoAEliminar));
        mostrarCarrito();
    }

    private void mostrarCarrito() {
        StringBuilder texto = new StringBuilder();
        for (Producto p : carrito) {
            // Se usa 'cantidad' para calcular el subtotal del producto y no el stock general.
            texto.append(p.getStock())
                    .append(" x ")
                    .append(p.getNombre())
                    .append(" = $")
                    .append(p.getPrecio() * p.getStock())
                    .append("\n");
        }
        vista.getAreaCarrito().setText(texto.toString());
    }

    private void finalizarCompra() {
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El carrito está vacío.");
            return;
        }

        float total = 0;
        for (Producto p : carrito) {
            total += p.getPrecio() * p.getStock();
        }

        Pedido pedido = new Pedido();
        pedido.setEstado("Pendiente");
        pedido.setFechaPedido(LocalDateTime.now());
        pedido.setTotal(total);
        pedido.setIdCliente(idUsuario);

        int idPedido = pedidoDAO.registrarPedido(pedido, idUsuario, carrito);

        if (idPedido > 0) {
            JOptionPane.showMessageDialog(vista, "Compra realizada con éxito. ID Pedido: " + idPedido);
            carrito.clear();
            vista.getAreaCarrito().setText("");
            cargarProductosEnTabla(); // Actualiza el stock en la tabla si se modificó
        } else {
            JOptionPane.showMessageDialog(vista, "Error al registrar la compra.");
        }
    }
}
