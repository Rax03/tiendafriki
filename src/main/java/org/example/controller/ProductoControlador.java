package org.example.controller;

import org.example.model.dao.ProductosDAO;
import org.example.model.entity.Producto;
import org.example.view.ProductoVista;

import javax.swing.*;

public class ProductoControlador {

    private ProductoVista productoVista;
    private ProductosDAO productosDAO;

    public ProductoControlador() {
        // Inicialización de la vista y el DAO
        productoVista = new ProductoVista();
        productosDAO = new ProductosDAO();

        // Conectar acciones de la vista con el controlador
        conectarAcciones();
    }

    private void conectarAcciones() {
        // Configurar la acción de "Agregar Producto"
        productoVista.getBtnAgregar().addActionListener(e -> mostrarFormularioAgregar());

        // Configurar la acción de "Editar Producto"
        productoVista.getBtnEditar().addActionListener(e -> mostrarFormularioEditar());

        // Configurar la acción de "Eliminar Producto"
        productoVista.getBtnEliminar().addActionListener(e -> eliminarProducto());
    }

    private void mostrarFormularioAgregar() {
        // Mostrar el formulario para agregar un producto en la vista
        productoVista.mostrarFormularioAgregar();

        // Después de agregar el producto, actualizar la tabla
        productoVista.llenarTablaProductos();
    }

    private void mostrarFormularioEditar() {
        // Mostrar el formulario para editar un producto seleccionado
        productoVista.mostrarFormularioEditar();

        // Después de editar el producto, actualizar la tabla
        productoVista.llenarTablaProductos();
    }

    private void eliminarProducto() {
        // Eliminar el producto seleccionado
        productoVista.eliminarProducto();

        // Después de eliminar el producto, actualizar la tabla
        productoVista.llenarTablaProductos();
    }
}
