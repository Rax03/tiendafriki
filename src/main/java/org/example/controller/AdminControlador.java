package org.example.controller;

import org.example.model.dao.UsuarioDAO;
import org.example.model.service.LoginService;
import org.example.view.*;

import javax.swing.*;

public class AdminControlador {
    private AdminVista vista;

    public AdminControlador(AdminVista vista) {
        this.vista = vista;

        // Vincular los eventos de los botones
        inicializarEventos();
    }

    private void inicializarEventos() {
        vista.getBtnProductos().addActionListener(e -> abrirProductos());
        vista.getBtnProveedores().addActionListener(e -> abrirProveedores());
        vista.getBtnCategorias().addActionListener(e -> abrirCategorias());
        vista.getBtnClientes().addActionListener(e -> abrirClientes());
        vista.getBtnPedidos().addActionListener(e -> abrirPedidos());
        vista.getBtnUsuarios().addActionListener(e -> abrirUsuarios());
        vista.getBtnCerrarSesion().addActionListener(e -> cerrarSesion());
    }

    private void abrirProductos() {
        try {
            System.out.println("Botón Productos presionado."); // Depuración
            ProductoVista productoVista = new ProductoVista();
            productoVista.setVisible(true);
        } catch (Exception e) {
            mostrarError("Productos", e);
        }
    }

    private void abrirProveedores() {
        try {
            System.out.println("Botón Proveedores presionado."); // Depuración
            ProveedorVista proveedorVista = new ProveedorVista();
            proveedorVista.setVisible(true);
        } catch (Exception e) {
            mostrarError("Proveedores", e);
        }
    }

    private void abrirCategorias() {
        try {
            System.out.println("Botón Categorías presionado."); // Depuración
            CategoriaVista categoriaVista = new CategoriaVista();
            categoriaVista.setVisible(true);
        } catch (Exception e) {
            mostrarError("Categorías", e);
        }
    }

    private void abrirClientes() {
        try {
            System.out.println("Botón Clientes presionado."); // Depuración
            ClienteVista clienteVista = new ClienteVista();
            clienteVista.setVisible(true);
        } catch (Exception e) {
            mostrarError("Clientes", e);
        }
    }

    private void abrirPedidos() {
        try {
            System.out.println("Botón Pedidos presionado."); // Depuración
            PedidoVista pedidoVista = new PedidoVista();
            pedidoVista.setVisible(true);
        } catch (Exception e) {
            mostrarError("Pedidos", e);
        }
    }

    private void abrirUsuarios() {
        try {
            System.out.println("Botón Usuarios presionado."); // Depuración
            UsuariosVista usuariosVista = new UsuariosVista();
            usuariosVista.setVisible(true);
        } catch (Exception e) {
            mostrarError("Usuarios", e);
        }
    }

    private void cerrarSesion() {
        try {
            System.out.println("Botón Cerrar Sesión presionado."); // Depuración
            int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Estás seguro de que deseas cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);

            if (confirmacion == JOptionPane.YES_OPTION) {
                vista.dispose();
                LoginVista loginVista = new LoginVista();
                new LoginControlador(loginVista, new LoginService(new UsuarioDAO()));
                loginVista.setVisible(true);
            }
        } catch (Exception e) {
            mostrarError("Cerrar Sesión", e);
        }
    }

    private void mostrarError(String modulo, Exception e) {
        JOptionPane.showMessageDialog(vista, "Error al abrir la vista de " + modulo + ": " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        e.printStackTrace();
    }
}
