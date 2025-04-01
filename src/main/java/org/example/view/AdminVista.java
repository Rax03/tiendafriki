package org.example.view;

import org.example.model.dao.CategoriaDAO;
import org.example.model.dao.ProductoDAO;
import org.example.model.entity.Categoria;
import org.example.model.entity.Producto;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminVista extends JFrame {

    private JTable tablaProductos;
    private DefaultTableModel modeloTabla;

    public AdminVista() {
        setTitle("Panel de Administración - Tienda Friki");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel superior para el botón de "Cerrar Sesión"
        JPanel panelSuperior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        estilizarBoton(btnCerrarSesion);

        // Acción del botón "Cerrar Sesión"
        btnCerrarSesion.addActionListener(e -> cerrarSesion());

        panelSuperior.setBackground(new Color(34, 34, 34)); // Fondo oscuro
        panelSuperior.add(btnCerrarSesion);

        // Crear pestañas
        JTabbedPane pestañas = new JTabbedPane();

        // Agregar pestañas temáticas
        pestañas.addTab("Productos", crearPanelProductos());
        pestañas.addTab("Categorías", crearPanelCategorias());
        pestañas.addTab("Proveedores", crearPanelProveedores());
        pestañas.addTab("Pedidos", crearPanelPedidos());

        pestañas.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        pestañas.setForeground(new Color(255, 153, 51)); // Naranja vibrante

        // Agregar panel superior y pestañas al marco
        add(panelSuperior, BorderLayout.NORTH);
        add(pestañas, BorderLayout.CENTER);

        setVisible(true);
    }

    private void cerrarSesion() {
        System.out.println("Intentando cerrar sesión...");
        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que deseas cerrar sesión?",
                "Cerrar Sesión",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion == JOptionPane.YES_OPTION) {
            System.out.println("Cerrando ventana actual...");
            dispose();
            System.out.println("Abriendo ventana de inicio de sesión...");
            new LoginVista();
        } else {
            System.out.println("Operación de cerrar sesión cancelada.");
        }
    }

    // Panel para Productos
    private JPanel crearPanelProductos() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Productos", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panel.setBackground(new Color(34, 34, 34));

        // Configurar la tabla vacía
        modeloTabla = new DefaultTableModel(new Object[][]{}, new String[]{"ID", "Nombre", "Categoría", "Precio", "Imagen"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer las celdas no editables
            }
        };
        tablaProductos = new JTable(modeloTabla);
        tablaProductos.setRowHeight(50); // Ajustar la altura de las filas para las imágenes
        tablaProductos.getColumnModel().getColumn(4).setCellRenderer(new ImageRenderer()); // Renderizador para imágenes
        JScrollPane scroll = new JScrollPane(tablaProductos);

        JPanel botones = new JPanel(new FlowLayout());
        botones.setBackground(new Color(34, 34, 34));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        estilizarBoton(btnAgregar);
        estilizarBoton(btnEditar);
        estilizarBoton(btnEliminar);

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);

        // Acción del botón "Agregar"
        btnAgregar.addActionListener(e -> mostrarFormularioAgregarProducto());

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        // Llenar la tabla con productos existentes
        ProductoDAO productoDAO = new ProductoDAO();
        List<Producto> productos = productoDAO.obtenerTodosLosProductos();
        if (productos.isEmpty()) {
            System.out.println("No hay productos en la base de datos.");
        } else {
            for (Producto producto : productos) {
                modeloTabla.addRow(new Object[]{
                        producto.getId(),
                        producto.getNombre(),
                        producto.getId_categoria().getNombre(),
                        "$" + producto.getPrecio(),
                        producto.getImagen() // Ruta de la imagen
                });
            }
        }

        return panel;
    }


    private void mostrarFormularioAgregarProducto() {
        // Crear los campos del formulario
        JTextField txtNombre = new JTextField();
        JTextField txtDescripcion = new JTextField();
        JTextField txtPrecio = new JTextField();
        JTextField txtStock = new JTextField();
        JTextField txtImagen = new JTextField(); // Mostrará la ruta de la imagen seleccionada
        JButton btnSeleccionarImagen = new JButton("Seleccionar Imagen");

        JComboBox<Categoria> cmbCategoria = new JComboBox<>();
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        // Llenar el JComboBox con las categorías desde la base de datos
        for (Categoria categoria : categoriaDAO.obtenerTodasLasCategorias()) {
            cmbCategoria.addItem(categoria); // Agregar objetos Categoria al JComboBox
        }

        // Añadir acción para seleccionar imagen
        btnSeleccionarImagen.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("Seleccionar Imagen");

            // Filtrar solo imágenes (opcional)
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Imágenes (JPG, PNG, GIF)", "jpg", "png", "gif"));

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                txtImagen.setText(fileChooser.getSelectedFile().getAbsolutePath()); // Establece la ruta en el campo de texto
            }
        });

        // Crear un panel para contener los campos
        JPanel panel = new JPanel(new GridLayout(7, 2, 10, 10));
        panel.add(new JLabel("Nombre:"));
        panel.add(txtNombre);
        panel.add(new JLabel("Descripción:"));
        panel.add(txtDescripcion);
        panel.add(new JLabel("Precio:"));
        panel.add(txtPrecio);
        panel.add(new JLabel("Stock:"));
        panel.add(txtStock);
        panel.add(new JLabel("Imagen (Ruta):"));
        panel.add(txtImagen); // Campo que muestra la ruta seleccionada
        panel.add(new JLabel(""));
        panel.add(btnSeleccionarImagen); // Botón para abrir el explorador de archivos
        panel.add(new JLabel("Categoría:"));
        panel.add(cmbCategoria);

        // Mostrar el formulario en un cuadro de diálogo
        int result = JOptionPane.showConfirmDialog(null, panel, "Agregar Producto", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {
                // Obtener los datos ingresados
                String nombre = txtNombre.getText();
                String descripcion = txtDescripcion.getText();
                double precio = Double.parseDouble(txtPrecio.getText());
                int stock = Integer.parseInt(txtStock.getText());
                String imagen = txtImagen.getText();
                Categoria categoriaSeleccionada = (Categoria) cmbCategoria.getSelectedItem();

                // Insertar el producto en la tabla
                modeloTabla.addRow(new Object[]{modeloTabla.getRowCount() + 1, nombre, categoriaSeleccionada.getNombre(), "$" + precio, imagen});

                // Guardar el producto en la base de datos
                ProductoDAO productoDAO = new ProductoDAO();
                Producto producto = new Producto(0, nombre, descripcion, precio, stock, imagen, categoriaSeleccionada);

                boolean exito = productoDAO.agregarProducto(producto);

                if (exito) {
                    JOptionPane.showMessageDialog(null, "Producto agregado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(null, "Error al agregar el producto.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Error: verifica que los campos numéricos son válidos.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Ocurrió un error: " + ex.getMessage());
            }
        }
    }




    // Panel para Categorías
    private JPanel crearPanelCategorias() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Categorías", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panel.setBackground(new Color(34, 34, 34));

        JTextArea listaCategorias = new JTextArea("1. Videojuegos\n2. Películas\n3. Ropa Geek\n4. Figuras de Colección");
        listaCategorias.setFont(new Font("Comic Sans MS", Font.PLAIN, 16));
        listaCategorias.setBackground(Color.BLACK);
        listaCategorias.setForeground(Color.WHITE);
        listaCategorias.setEditable(false);

        JScrollPane scroll = new JScrollPane(listaCategorias);
        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        return panel;
    }

    // Panel para Proveedores
    private JPanel crearPanelProveedores() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Proveedores", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panel.setBackground(new Color(34, 34, 34));

        JTable tablaProveedores = new JTable(
                new Object[][]{},
                new String[]{"ID", "Nombre", "Contacto", "Teléfono", "Dirección"}
        );
        JScrollPane scroll = new JScrollPane(tablaProveedores);

        JPanel botones = new JPanel(new FlowLayout());
        botones.setBackground(new Color(34, 34, 34));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        estilizarBoton(btnAgregar);
        estilizarBoton(btnEditar);
        estilizarBoton(btnEliminar);

        botones.add(btnAgregar);
        botones.add(btnEditar);
        botones.add(btnEliminar);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    // Panel para Pedidos
    private JPanel crearPanelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Pedidos", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panel.setBackground(new Color(34, 34, 34));

        JTable tablaPedidos = new JTable(
                new Object[][]{},
                new String[]{"ID Pedido", "Cliente", "Fecha", "Estado"}
        );
        JScrollPane scroll = new JScrollPane(tablaPedidos);

        JPanel botones = new JPanel(new FlowLayout());
        botones.setBackground(new Color(34, 34, 34));
        JButton btnActualizar = new JButton("Actualizar");
        estilizarBoton(btnActualizar);

        botones.add(btnActualizar);

        panel.add(titulo, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Comic Sans MS", Font.BOLD, 14));
        boton.setBackground(new Color(0, 153, 76));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
    }

    private class ImageRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER);

            if (value != null) {
                String imagePath = value.toString(); // Obtener la ruta de la imagen
                ImageIcon icon = new ImageIcon(imagePath); // Cargar la imagen desde la ruta
                Image scaledImage = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Escalar la imagen
                label.setIcon(new ImageIcon(scaledImage)); // Establecer la imagen escalada en el label
            }

            return label;
        }
    }
}