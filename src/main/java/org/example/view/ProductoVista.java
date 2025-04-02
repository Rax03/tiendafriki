package org.example.view;

import org.example.model.dao.ProductoDAO;
import org.example.model.entity.Categoria;
import org.example.model.entity.Producto;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.util.List;

public class ProductoVista extends JFrame {
    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;
    private ProductoDAO productoDAO;

    public ProductoVista() {
        setTitle("Gestión de Productos");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        productoDAO = new ProductoDAO();

        // Configurar panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Productos", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panelPrincipal.setBackground(new Color(34, 34, 34));

        // Configurar la tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Descripción", "Precio", "Stock", "Categoría", "Imagen"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer las celdas no editables
            }
        };
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setRowHeight(60); // Ajustar la altura de las filas para mostrar imágenes
        tablaProductos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollTabla = new JScrollPane(tablaProductos);

        // Configurar renderizador para la columna de imágenes
        tablaProductos.getColumnModel().getColumn(6).setCellRenderer(new ImageRenderer());

        // Configurar botones CRUD
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        estilizarBoton(btnAgregar);
        estilizarBoton(btnEditar);
        estilizarBoton(btnEliminar);

        btnAgregar.addActionListener(e -> mostrarFormularioAgregar());
        btnEditar.addActionListener(e -> mostrarFormularioEditar());
        btnEliminar.addActionListener(e -> eliminarProducto());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        // Llenar la tabla con los datos de los productos
        llenarTablaProductos();

        // Agregar componentes al panel principal
        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Agregar panel principal al JFrame
        add(panelPrincipal);
        setVisible(true);
    }

    private void llenarTablaProductos() {
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de llenarla
        List<Producto> productos = productoDAO.obtenerTodosLosProductos();
        for (Producto producto : productos) {
            modeloTabla.addRow(new Object[]{
                    producto.getId(),
                    producto.getNombre(),
                    producto.getDescripcion(),
                    "$" + producto.getPrecio(),
                    producto.getStock(),
                    producto.getId_categoria().getNombre(),
                    producto.getImagen() // Ruta de la imagen
            });
        }
    }

    private void mostrarFormularioAgregar() {
        // Crear el formulario para agregar un producto
        JTextField txtNombre = new JTextField();
        JTextField txtDescripcion = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtStock = new JTextField();
        JTextField txtImagen = new JTextField();
        JComboBox<Categoria> cmbCategoria = new JComboBox<>(new Categoria[]{
                new Categoria(1, "Figuras"),
                new Categoria(2, "Videojuegos"),
                new Categoria(3, "Ropa")
        });

        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 10, 10));
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Descripción:"));
        panelFormulario.add(txtDescripcion);
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(txtPrecio);
        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(txtStock);
        panelFormulario.add(new JLabel("Imagen (ruta):"));
        panelFormulario.add(txtImagen);
        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(cmbCategoria);

        int opcion = JOptionPane.showConfirmDialog(this, panelFormulario, "Agregar Producto", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                Producto producto = new Producto(
                        0,
                        txtNombre.getText(),
                        txtDescripcion.getText(),
                        Double.parseDouble(txtPrecio.getText()),
                        Integer.parseInt(txtStock.getText()),
                        txtImagen.getText(), // Ruta de la imagen ingresada
                        (Categoria) cmbCategoria.getSelectedItem()
                );
                boolean exito = productoDAO.agregarProducto(producto);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Producto agregado correctamente.");
                    llenarTablaProductos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarFormularioEditar() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para editar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idProducto = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        Producto producto = productoDAO.obtenerProductoPorId(idProducto);
        if (producto == null) {
            JOptionPane.showMessageDialog(this, "Error al obtener el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField txtNombre = new JTextField(producto.getNombre());
        JTextField txtDescripcion = new JTextField(producto.getDescripcion());
        JTextField txtPrecio = new JTextField(String.valueOf(producto.getPrecio()));
        JTextField txtStock = new JTextField(String.valueOf(producto.getStock()));
        JTextField txtImagen = new JTextField(producto.getImagen());
        JComboBox<Categoria> cmbCategoria = new JComboBox<>(new Categoria[]{
                new Categoria(1, "Figuras"),
                new Categoria(2, "Videojuegos"),
                new Categoria(3, "Ropa")
        });

        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 10, 10));
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Descripción:"));
        panelFormulario.add(txtDescripcion);
        panelFormulario.add(new JLabel("Precio:"));
        panelFormulario.add(txtPrecio);
        panelFormulario.add(new JLabel("Stock:"));
        panelFormulario.add(txtStock);
        panelFormulario.add(new JLabel("Imagen (ruta):"));
        panelFormulario.add(txtImagen);
        panelFormulario.add(new JLabel("Categoría:"));
        panelFormulario.add(cmbCategoria);

        int opcion = JOptionPane.showConfirmDialog(this, panelFormulario, "Editar Producto", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                producto.setNombre(txtNombre.getText());
                producto.setDescripcion(txtDescripcion.getText());
                producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
                producto.setStock(Integer.parseInt(txtStock.getText()));
                producto.setImagen(txtImagen.getText());
                producto.setId_categoria((Categoria) cmbCategoria.getSelectedItem());

                boolean exito = productoDAO.actualizarProducto(producto);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.");
                    llenarTablaProductos();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarProducto() {
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un producto para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idProducto = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este producto?",
                "Eliminar Producto", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = productoDAO.eliminarProducto(idProducto);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
                llenarTablaProductos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente en negrita y tamaño 14
        boton.setBackground(new Color(0, 153, 255)); // Azul vibrante
        boton.setForeground(Color.WHITE); // Texto en blanco
        boton.setFocusPainted(false); // Quitar el foco visual del botón
        boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Borde negro
    }
    private class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER);

            if (value != null) {
                String imagePath = value.toString();
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    // Cargar y redimensionar la imagen
                    ImageIcon icon = new ImageIcon(imagePath);
                    Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    label.setIcon(new ImageIcon(scaledImage));
                } else {
                    // Mostrar texto si la imagen no existe
                    label.setText("Sin Imagen");
                    label.setForeground(Color.RED);
                }
            } else {
                label.setText("Sin Imagen");
                label.setForeground(Color.RED);
            }

            return label;
        }
    }

}