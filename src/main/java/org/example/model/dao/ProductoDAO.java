package org.example.model.dao;

import org.example.model.conection.ConexionBD;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    public List<String> obtenerProductos() {
        List<String> productos = new ArrayList<>();
        String sql = "SELECT nombre FROM Producto";

        try (Connection conn = ConexionBD.conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                productos.add(rs.getString("nombre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
}
