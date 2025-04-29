package org.example.controller;

import org.example.model.dao.DetallePedidoDAO;
import org.example.model.dao.PedidoDAO;
import org.example.model.dao.UsuarioDAO;
import org.example.model.entity.Pedido;
import org.example.model.entity.DetallesPedido;
import org.example.model.entity.Usuario;
import org.example.model.service.LoginService;
import org.example.view.LoginVista;
import org.example.view.UsuarioVista;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.List;

public class UsuarioControlador {

    private UsuarioVista vista;
    private DetallePedidoDAO detallesPedidoDAO;
    private PedidoDAO pedidoDAO;
    private Usuario usuario;
    private List<String[]> carrito;

    public UsuarioControlador(UsuarioVista vista, Usuario usuario) {
        this.vista = vista;
        this.usuario = usuario;
        this.detallesPedidoDAO = new DetallePedidoDAO();
        this.pedidoDAO = new PedidoDAO(); // Agregamos PedidoDAO
        this.carrito = vista.getCarrito(); // Obtener referencia al carrito

        inicializarEventos();
        cargarProductos();
    }

    private void inicializarEventos() {
        vista.getBtnFinalizarPedido().addActionListener(e -> finalizarPedido());
        vista.getBtnVerHistorial().addActionListener(e -> verHistorial());
        vista.getBtnCerrarSesion().addActionListener(e -> cerrarSesion());
    }

    private void cargarProductos() {
        List<String[]> productos = detallesPedidoDAO.obtenerProductosDesdeBD();
        if (productos != null && !productos.isEmpty()) {
            vista.actualizarTablaProductos(productos);
        } else {
            JOptionPane.showMessageDialog(vista, "No hay productos disponibles en la base de datos.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void finalizarPedido() {
        if (carrito.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "El carrito está vacío. Agrega productos antes de finalizar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear un nuevo pedido
        Pedido pedido = new Pedido(0, usuario.getId(), LocalDateTime.now());
        int idPedido = pedidoDAO.registrarPedido(pedido); // ✅ Ahora es un `int`

        if (idPedido > 0) { // ✅ Verificamos que el pedido fue registrado
            for (String[] producto : carrito) {
                DetallesPedido detalle = new DetallesPedido(idPedido, usuario.getId(),
                        Integer.parseInt(producto[0]), 1, Float.parseFloat(producto[2]));

                detallesPedidoDAO.registrarDetallePedido(detalle);
            }

            JOptionPane.showMessageDialog(vista, "Pedido registrado con éxito.", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            carrito.clear();
            vista.actualizarTablaCarrito();
        } else {
            JOptionPane.showMessageDialog(vista, "Error al registrar el pedido.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }
    private void verHistorial() {
        List<Pedido> historial = pedidoDAO.obtenerHistorialPedidos(usuario.getId());
        if (historial != null && !historial.isEmpty()) {
            vista.mostrarHistorial(historial);
        } else {
            JOptionPane.showMessageDialog(vista, "No hay historial de pedidos.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void cerrarSesion() {
        int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Estás seguro de que deseas cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            vista.dispose();
            LoginVista loginVista = new LoginVista();
            new LoginControlador(loginVista, new LoginService(new UsuarioDAO()));
            loginVista.setVisible(true);
        }
    }


    private void mostrarError(String cerrarSesión, Exception e) {

        JOptionPane.showMessageDialog(vista, "Error al cerrar sesión: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
