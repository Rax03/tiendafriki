package org.example.model.dao;

import org.example.model.conection.ConexionBD;
import org.example.model.entity.Categoria;
import org.example.model.entity.Producto;
import org.example.model.entity.ProductoProveedor;
import org.example.model.entity.Proveedor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ProductosDAO {

    private static final Logger logger = Logger.getLogger(ProductosDAO.class.getName());

    // Obtener todos los productos con sus proveedores
    public List<Producto> obtenerTodosLosProductos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre as categoria_nombre, pr.id, pr.nombre as proveedor_nombre " +
                "FROM Productos p " +
                "LEFT JOIN Categorias c ON p.id_categoria = c.id_categoria " +
                "LEFT JOIN Producto_Proveedor pp ON p.id_producto = pp.id_producto " +
                "LEFT JOIN Proveedores pr ON pp.id_proveedor = pr.id";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("categoria_nombre")
                );

                // Crear la lista de proveedores
                List<Proveedor> proveedores = new ArrayList<>();
                String proveedorNombre = rs.getString("proveedor_nombre");

                if (proveedorNombre != null && !proveedorNombre.isEmpty()) {
                    Proveedor proveedor = new Proveedor();
                    proveedor.setId(rs.getInt("id"));  // Cambié id_proveedor por id
                    proveedor.setNombre(proveedorNombre);
                    proveedores.add(proveedor);
                } else {
                    Proveedor proveedor = new Proveedor();
                    proveedor.setId(rs.getInt("id"));  // Cambié id_proveedor por id
                    proveedor.setNombre("Proveedor Desconocido");  // O puedes decidir no agregarlo
                    proveedores.add(proveedor);
                }

                Producto producto = new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getFloat("precio"),
                        rs.getInt("stock"),
                        rs.getString("imagen"),
                        categoria,
                        proveedores
                );

                productos.add(producto);
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener productos", e);
        }

        return productos;
    }

    // Buscar productos por nombre con proveedores
    public List<Producto> buscarProductosPorNombre(String nombre) {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT p.*, c.nombre as categoria_nombre, pr.id_proveedor, pr.nombre as proveedor_nombre " +
                "FROM Productos p " +
                "LEFT JOIN Categorias c ON p.id_categoria = c.id_categoria " +
                "LEFT JOIN Producto_Proveedor pp ON p.id_producto = pp.id_producto " +
                "LEFT JOIN Proveedores pr ON pp.id_proveedor = pr.id_proveedor " +
                "WHERE p.nombre LIKE ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, "%" + nombre + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Categoria categoria = new Categoria(
                            rs.getInt("id_categoria"),
                            rs.getString("categoria_nombre")
                    );

                    // Crear la lista de proveedores
                    List<Proveedor> proveedores = new ArrayList<>();
                    String proveedorNombre = rs.getString("proveedor_nombre");

                    if (proveedorNombre != null && !proveedorNombre.isEmpty()) {
                        Proveedor proveedor = new Proveedor();
                        proveedor.setId(rs.getInt("id_proveedor"));
                        proveedor.setNombre(proveedorNombre);
                        proveedores.add(proveedor);
                    } else {
                        Proveedor proveedor = new Proveedor();
                        proveedor.setId(rs.getInt("id_proveedor"));
                        proveedor.setNombre("Proveedor Desconocido");
                        proveedores.add(proveedor);
                    }

                    Producto producto = new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getString("descripcion"),
                            rs.getFloat("precio"),
                            rs.getInt("stock"),
                            rs.getString("imagen"),
                            categoria,
                            proveedores
                    );
                    productos.add(producto);
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar productos por nombre", e);
        }

        return productos;
    }

    private List<Proveedor> obtenerProveedoresPorProducto(int idProducto) {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT pr.id, pr.nombre FROM Proveedores pr " +
                "JOIN Producto_Proveedor pp ON pr.id = pp.id_proveedor " +
                "WHERE pp.id_producto = ?";  // Corregido 'id_proveedor' por 'id'

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idProducto);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Proveedor proveedor = new Proveedor();
                    proveedor.setId(rs.getInt("id"));
                    proveedor.setNombre(rs.getString("nombre"));
                    proveedores.add(proveedor);
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener proveedores del producto", e);
        }

        return proveedores;
    }

    // Obtener un producto por ID con sus proveedores
    public Producto obtenerProductoPorId(int idProducto) {
        String sql = "SELECT p.*, c.nombre as categoria_nombre " +
                "FROM Productos p " +
                "LEFT JOIN Categorias c ON p.id_categoria = c.id_categoria " +
                "WHERE p.id_producto = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idProducto);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getInt("id_categoria"),
                        rs.getString("categoria_nombre")
                );

                Producto producto = new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getFloat("precio"),
                        rs.getInt("stock"),
                        rs.getString("imagen"),
                        categoria,
                        obtenerProveedoresPorProducto(idProducto) // Obtener lista de proveedores para el producto
                );
                return producto;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener producto por ID", e);
        }

        return null;
    }

    public boolean agregarProducto(Producto producto) {
        String sql = "INSERT INTO Productos (nombre, descripcion, precio, stock, imagen, id_categoria) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setString(5, producto.getImagen());
            stmt.setInt(6, producto.getId_categoria().getIdCategoria());

            // Ejecutar la inserción del producto
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Obtener la ID generada para el producto
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int idProducto = generatedKeys.getInt(1);  // Obtener el id_producto generado
                    producto.setId_producto(idProducto);  // Asegúrate de tener un setter para esto

                    // Ahora debes agregar las relaciones en la tabla producto_proveedor
                    for (Proveedor proveedor : producto.getProveedores()) {
                        // Aquí se define el precio por cada relación
                        double precio = obtenerPrecioDeProveedorParaProducto(idProducto, proveedor); // Aquí puedes definir cómo obtener el precio.

                        agregarProveedorAProducto(idProducto, proveedor.getId(), precio);
                    }
                    return true;
                }
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al agregar producto", e);
        }

        return false;
    }


    private double obtenerPrecioDeProveedorParaProducto(int idProducto, Proveedor proveedor) {
        String sql = "SELECT precio FROM producto_proveedor WHERE id_producto = ? AND id_proveedor = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            // Establecer parámetros
            stmt.setInt(1, idProducto);
            stmt.setInt(2, proveedor.getId());

            // Ejecutar la consulta
            ResultSet rs = stmt.executeQuery();

            // Si encontramos el precio
            if (rs.next()) {
                return rs.getDouble("precio");
            } else {
                // Si no se encuentra, devolver un precio predeterminado (por ejemplo, 0)
                // O puedes lanzar una excepción si consideras que el precio debería existir.
                return 0.0;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al obtener el precio de proveedor para producto", e);
            return 0.0; // Puedes manejar el error como prefieras
        }
    }






    // Método para agregar un proveedor a un producto
    public boolean agregarProveedorAProducto(int idProducto, int idProveedor, double precio) {
        String sql = "INSERT INTO producto_proveedor (id_producto, id_proveedor, precio) " +
                "VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idProducto);  // ID del producto
            stmt.setInt(2, idProveedor); // ID del proveedor
            stmt.setDouble(3, precio);   // Precio específico del producto-proveedor

            // Ejecutar la inserción de la relación
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al agregar proveedor a producto", e);
            return false;
        }
    }

    // Actualizar un producto
    public boolean actualizarProducto(Producto producto) {
        String sql = "UPDATE Productos SET nombre = ?, descripcion = ?, precio = ?, stock = ?, imagen = ?, id_categoria = ? " +
                "WHERE id_producto = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStock());
            stmt.setString(5, producto.getImagen());
            stmt.setInt(6, producto.getId_categoria().getIdCategoria());
            stmt.setInt(7, producto.getId_producto());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Eliminar proveedores anteriores y agregar los nuevos
                eliminarProveedoresDeProducto(producto.getId_producto());
                for (Proveedor proveedor : producto.getProveedores()) {
                    agregarProveedorAProducto(producto.getId_producto(), proveedor.getId(), 0);
                }
                return true;
            }

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar producto", e);
        }

        return false;
    }

    // Eliminar proveedores de un producto
    private void eliminarProveedoresDeProducto(int idProducto) throws SQLException {
        String sql = "DELETE FROM Producto_Proveedor WHERE id_producto = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idProducto);
            stmt.executeUpdate();
        }
    }

    // Eliminar un producto por ID
    public boolean eliminarProducto(int idProducto) {
        // Primero eliminar las relaciones de proveedores
        try {
            eliminarProveedoresDeProducto(idProducto);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar relaciones de proveedores", e);
            return false;
        }

        // Luego eliminar el producto
        String sql = "DELETE FROM Productos WHERE id_producto = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql)) {

            stmt.setInt(1, idProducto);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar producto", e);
        }

        return false;
    }
    // Método para obtener todas las categorías
    public List<Categoria> obtenerCategorias() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM Categorias";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("id_categoria"));
                categoria.setNombre(rs.getString("nombre"));
                categorias.add(categoria);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categorias;
    }

    // Método para obtener todos los proveedores
    public List<Proveedor> obtenerProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM Proveedores";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement stmt = conexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setId(rs.getInt("id"));
                proveedor.setNombre(rs.getString("nombre"));
                proveedores.add(proveedor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proveedores;
    }

}
