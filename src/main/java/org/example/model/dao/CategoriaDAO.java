package org.example.model.dao;

import org.example.model.conection.ConexionBD;
import org.example.model.entity.Categoria;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public List<Categoria> obtenerTodasLasCategorias() {
        String sql = "SELECT id_categoria, Nombre FROM categorias";
        List<Categoria> categorias = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                categorias.add(new Categoria(rs.getInt("id_categoria"), rs.getString("Nombre")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }
}

