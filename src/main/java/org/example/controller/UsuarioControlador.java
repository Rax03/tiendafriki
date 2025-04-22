package org.example.controller;

import org.example.model.dao.ProductoDAO;
import org.example.model.entity.Producto;
import org.example.view.UsuarioVista;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioControlador {

    private final UsuarioVista vista;
    private final ProductoDAO productoDAO;
    private final List<Producto> carrito; // Lista para manejar productos en el carrito

    public UsuarioControlador(UsuarioVista vista) {
        this.vista = vista;
        this.productoDAO = new ProductoDAO();
        this.carrito = new ArrayList<>();

        inicializarEventos();
        cargarProductosIniciales();
    }

    private void inicializarEventos() {
        // Evento para buscar productos
        vista.getBtnBuscar().addActionListener(e -> buscarProductos(vista.getCampoBusqueda().getText()));

        // Evento para finalizar compra
        vista.getBtnFinalizarCompra().addActionListener(e -> finalizarCompra());

        // Evento para vaciar el carrito
        vista.getBtnVaciarCarrito().addActionListener(e -> vaciarCarrito());

        // Evento para agregar productos al carrito (doble clic en la tabla de productos)
        vista.getTablaProductos().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int filaSeleccionada = vista.getTablaProductos().getSelectedRow();
                    if (filaSeleccionada >= 0) {
                        agregarProductoAlCarrito(filaSeleccionada);
                    }
                }
            }
        });
    }

    private void cargarProductosIniciales() {
        try {
            List<Producto> productos = productoDAO.obtenerTodosLosProductos();
            vista.mostrarProductos(productos);
        } catch (Exception e) {
            mostrarError("Error al cargar los productos.", e);
        }
    }

    private void buscarProductos(String textoBusqueda) {
        try {
            List<Producto> productos = productoDAO.buscarProductosPorNombre(textoBusqueda);
            vista.mostrarProductos(productos);
        } catch (Exception e) {
            mostrarError("Error al buscar productos.", e);
        }
    }

    private void agregarProductoAlCarrito(int filaSeleccionada) {
        try {
            int idProducto = (int) vista.getTablaProductos().getValueAt(filaSeleccionada, 0);
            String nombreProducto = (String) vista.getTablaProductos().getValueAt(filaSeleccionada, 1);
            double precio = (double) vista.getTablaProductos().getValueAt(filaSeleccionada, 2);

            // Crear un producto para el carrito
            Producto producto = new Producto(idProducto, nombreProducto, precio, 1 ); // Cantidad inicial 1
            carrito.add(producto);
            vista.mostrarCarrito(carrito); // Actualizar la tabla del carrito

            mostrarMensaje("Producto agregado al carrito: " + nombreProducto);
        } catch (Exception e) {
            mostrarError("Error al agregar producto al carrito.", e);
        }
    }

    private void vaciarCarrito() {
        carrito.clear();
        vista.vaciarCarrito();
        mostrarMensaje("El carrito ha sido vaciado.");
    }

    private void finalizarCompra() {
        if (carrito.isEmpty()) {
            mostrarMensaje("El carrito está vacío. Agrega productos antes de finalizar la compra.");
            return;
        }

        double total = carrito.stream().mapToDouble(p -> p.getPrecio() * p.getStock()).sum();

        // Mostrar resumen de compra
        StringBuilder resumen = new StringBuilder();
        resumen.append("Resumen de Compra:\n");
        for (Producto producto : carrito) {
            resumen.append(String.format("- %s: $%.2f (Cantidad: %d)\n",
                    producto.getNombre(), producto.getPrecio(), producto.getStock()));
        }
        resumen.append(String.format("\nTotal: $%.2f", total));

        JOptionPane.showMessageDialog(vista, resumen.toString(), "Compra Finalizada", JOptionPane.INFORMATION_MESSAGE);

        vaciarCarrito(); // Limpiar el carrito después de finalizar la compra
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(vista, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void mostrarError(String mensaje, Exception e) {
        JOptionPane.showMessageDialog(vista, mensaje + "\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
