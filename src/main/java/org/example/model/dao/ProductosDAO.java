package org.example.model.dao;

import org.example.model.conection.ConexionBD;
import org.example.model.entity.Producto;
import org.example.model.entity.Categoria;
import org.example.model.entity.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductosDAO {

    /**
     * Agrega un producto a la base de datos y asigna el ID generado al objeto.
     *
     * @param producto El producto a agregar.
     * @return true si se agregó correctamente, false en caso contrario.
     */
    public boolean agregarProducto(Producto producto) {
        String sql = "INSERT INTO productos (nombre, descripcion, precio, stock, imagen, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setString(5, producto.getImagen());
            stmt.setInt(6, producto.getId_categoria().getIdCategoria());

            int filasAfectadas = stmt.executeUpdate();
            if (filasAfectadas > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        int idProducto = rs.getInt(1);
                        producto.setId_producto(idProducto);
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al agregar producto: " + e.getMessage());
        }
        return false;
    }

    /**
     * Actualiza los datos de un producto existente.
     *
     * @param producto El producto con los datos actualizados.
     * @return true si la actualización fue exitosa, false en caso contrario.
     */
    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, descripcion = ?, precio = ?, stock = ?, imagen = ?, id_categoria = ? WHERE id_producto = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setString(5, producto.getImagen());
            stmt.setInt(6, producto.getId_categoria().getIdCategoria());
            stmt.setInt(7, producto.getId_producto());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Elimina un producto de la base de datos por su ID.
     *
     * @param idProducto El ID del producto a eliminar.
     * @return true si se eliminó correctamente, false en caso contrario.
     */
    public boolean eliminarProducto(int idProducto) {
        String sql = "DELETE FROM productos WHERE id_producto = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idProducto);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar producto: " + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todos los productos disponibles en la base de datos.
     *
     * @return Lista de productos.
     */
    public List<Producto> obtenerTodosLosProductos() {
        String sql = "SELECT p.id_producto, p.nombre, p.descripcion, p.precio, p.stock, p.imagen, " +
                "c.id_categoria, c.nombre AS nombre_categoria " +
                "FROM productos p " +
                "JOIN categorias c ON p.id_categoria = c.id_categoria";

        List<Producto> productos = new ArrayList<>();

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Producto producto = new Producto();
                producto.setId_producto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getFloat("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setImagen(rs.getString("imagen"));

                // ✅ Recuperar la categoría completa
                int idCat = rs.getInt("id_categoria");
                String nombreCategoria = rs.getString("nombre_categoria");
                producto.setId_categoria(new Categoria(idCat, nombreCategoria));

                // ✅ Recuperar los proveedores de este producto
                producto.setProveedores(obtenerProveedoresPorProducto(conexion, producto.getId_producto()));

                if (producto.getProveedores() == null || producto.getProveedores().isEmpty()) {
                    System.err.println("⚠ Producto " + producto.getNombre() + " sin proveedores asociados.");
                }

                productos.add(producto);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener todos los productos: " + e.getMessage());
        }
        return productos;
    }

    /**
     * Verifica si un producto existe en la base de datos por su ID.
     *
     * @param idProducto El ID del producto a verificar.
     * @return true si el producto existe, false en caso contrario.
     */
    public boolean existeProducto(int idProducto) {
        String sql = "SELECT COUNT(*) FROM productos WHERE id_producto = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al verificar existencia del producto: " + e.getMessage());
        }
        return false;
    }

    /**
     * Obtiene un producto por su ID, incluyendo su categoría (con nombre) y proveedores asociados.
     *
     * @param idProducto El ID del producto buscado.
     * @return El producto completo, o null si no se encontró.
     */
    public Producto obtenerProductoPorId(int idProducto) {
        String sql = "SELECT p.*, c.nombre AS categoria_nombre FROM productos p " +
                "JOIN categorias c ON p.id_categoria = c.id_categoria " +
                "WHERE p.id_producto = ?";

        Producto producto = null;

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // ✅ Obtener la categoría completa
                    Categoria cat = new Categoria(rs.getInt("id_categoria"), rs.getString("categoria_nombre"));

                    // ✅ Crear el producto con los datos recuperados
                    producto = new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getFloat("precio"),
                            rs.getInt("stock"),
                            rs.getString("imagen"),
                            cat,
                            new ArrayList<>()
                    );

                    // ✅ Recuperar proveedores con la conexión activa
                    producto.setProveedores(obtenerProveedoresPorProducto(conexion, idProducto));
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener producto por ID: " + e.getMessage());
        }

        return producto;
    }


    /**
     * Obtiene los proveedores asociados a un producto específico.
     *
     * @param idProducto El ID del producto.
     * @return Lista de proveedores relacionados.
     */
    private List<Proveedor> obtenerProveedoresPorProducto(Connection conexion, int idProducto) {
        String sql = "SELECT pr.id, pr.nombre FROM proveedores pr " +
                "JOIN producto_proveedor pp ON pr.id = pp.id_proveedor " +
                "WHERE pp.id_producto = ?";

        List<Proveedor> proveedores = new ArrayList<>();

        try (PreparedStatement stmt = conexion.prepareStatement(sql)) {
            stmt.setInt(1, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Proveedor proveedor = new Proveedor(rs.getInt("id"), rs.getString("nombre"));
                    proveedores.add(proveedor);
                    System.out.println("✅ Proveedor agregado: " + proveedor.getNombre()); // Depuración
                }
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener proveedores para producto " + idProducto + ": " + e.getMessage());
        }

        System.out.println("🔍 Total de proveedores obtenidos: " + proveedores.size());
        return proveedores;
    }




    /**
     * Obtiene todas las categorías disponibles.
     *
     * @return Lista de categorías.
     */
    public List<Categoria> obtenerCategorias() {
        String sql = "SELECT * FROM categorias";
        List<Categoria> categorias = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria cat = new Categoria(rs.getInt("id_categoria"), rs.getString("nombre"));
                categorias.add(cat);
                System.out.println("✅ Categoría encontrada: " + cat.getNombre()); // Depuración
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener categorías: " + e.getMessage());
        }
        return categorias;
    }

    /**
     * Obtiene todos los proveedores disponibles.
     *
     * @return Lista de proveedores.
     */
    public List<Proveedor> obtenerProveedores() {
        String sql = "SELECT * FROM proveedores";
        List<Proveedor> proveedores = new ArrayList<>();
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Proveedor prov = new Proveedor(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("direccion"),
                        rs.getString("telefono"),
                        rs.getString("email")
                );
                proveedores.add(prov);
            }
        } catch (SQLException e) {
            System.err.println("❌ Error al obtener proveedores: " + e.getMessage());
        }
        return proveedores;
    }
}
