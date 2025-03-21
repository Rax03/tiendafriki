package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.model.dao.ProductoDAO;

import java.util.List;

public class ProductosController {

    @FXML private ListView<String> listaProductos;
    @FXML private Button btnCargar;
    @FXML private Label lblMensaje;

    private final ProductoDAO productoDAO = new ProductoDAO();

    @FXML
    private void initialize() {
        btnCargar.setOnAction(event -> cargarProductos());
    }

    private void cargarProductos() {
        List<String> productos = productoDAO.obtenerProductos();
        listaProductos.getItems().setAll(productos);
        lblMensaje.setText("Productos cargados: " + productos.size());
    }
}
