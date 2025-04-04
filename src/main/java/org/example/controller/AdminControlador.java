package org.example.controller;

import org.example.view.*;

import javax.swing.*;

public class AdminControlador {
    private AdminVista vista;

    public AdminControlador(AdminVista vista) {
        this.vista = vista;

        // Vincular los botones con las acciones
        this.vista.getBtnProductos().addActionListener(e -> abrirProductos());
        this.vista.getBtnProveedores().addActionListener(e -> abrirProveedores());
        this.vista.getBtnCategorias().addActionListener(e -> abrirCategorias());
        this.vista.getBtnClientes().addActionListener(e -> abrirClientes());
        this.vista.getBtnPedidos().addActionListener(e -> abrirPedidos());
        this.vista.getBtnUsuarios().addActionListener(e -> abrirUsuarios());
        this.vista.getBtnCerrarSesion().addActionListener(e -> cerrarSesion());
    }

    private void abrirProductos() {
        System.out.println("Botón Productos presionado."); // Mensaje de prueba
        try {
            ProductoVista productoVista = new ProductoVista();
            productoVista.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al abrir la vista de Productos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void abrirProveedores() {
        System.out.println("Botón Proveedores presionado."); // Mensaje de prueba
        try {
            ProveedorVista proveedorVista = new ProveedorVista();
            proveedorVista.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al abrir la vista de Proveedores: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void abrirCategorias() {
        System.out.println("Botón Categorías presionado."); // Mensaje de prueba
        try {;
            CategoriaVista categoriaVista = new CategoriaVista();
            categoriaVista.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al abrir la vista de Categorías: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void abrirClientes() {
        System.out.println("Botón Clientes presionado."); // Mensaje de prueba
        try {
            ClienteVista clienteVista = new ClienteVista();
            clienteVista.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al abrir la vista de Clientes: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void abrirPedidos() {
        System.out.println("Botón Pedidos presionado."); // Mensaje de prueba
        try {
            PedidoVista pedidoVista = new PedidoVista();
            pedidoVista.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al abrir la vista de Pedidos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void abrirUsuarios() {
        System.out.println("Botón Usuarios presionado."); // Mensaje de prueba
        try {
            UsuariosVista usuariosVista = new UsuariosVista();
            usuariosVista.setVisible(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(vista, "Error al abrir la vista de Usuarios: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void cerrarSesion() {
        System.out.println("Botón Cerrar Sesión presionado."); // Mensaje de prueba
        int confirmacion = JOptionPane.showConfirmDialog(vista, "¿Estás seguro de que deseas cerrar sesión?", "Cerrar Sesión", JOptionPane.YES_NO_OPTION);

        if (confirmacion == JOptionPane.YES_OPTION) {
            vista.dispose(); // Cierra AdminVista
            try {
                LoginVista loginVista = new LoginVista();
                loginVista.setVisible(true);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(vista, "Error al abrir la vista de Inicio de Sesión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
