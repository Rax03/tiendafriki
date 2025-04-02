package org.example.view;

import org.example.model.dao.CategoriaDAO;
import org.example.model.entity.Categoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CategoriaVista extends JFrame {

    private JTable tablaCategorias;
    private DefaultTableModel modeloTabla;
    private CategoriaDAO categoriaDAO;

    public CategoriaVista() {
        setTitle("Gestión de Categorías");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        categoriaDAO = new CategoriaDAO();

        // Configurar panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Categorías", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panelPrincipal.setBackground(new Color(34, 34, 34));

        // Configurar la tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer las celdas no editables
            }
        };
        tablaCategorias = new JTable(modeloTabla);
        tablaCategorias.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollTabla = new JScrollPane(tablaCategorias);

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
        btnEliminar.addActionListener(e -> eliminarCategoria());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        // Llenar la tabla con los datos de las categorías
        llenarTablaCategorias();

        // Agregar componentes al panel principal
        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Agregar panel principal al JFrame
        add(panelPrincipal);
        setVisible(true);
    }

    private void llenarTablaCategorias() {
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de llenarla
        List<Categoria> categorias = categoriaDAO.obtenerTodasLasCategorias();
        for (Categoria categoria : categorias) {
            modeloTabla.addRow(new Object[]{
                    categoria.getIdCategoria(),
                    categoria.getNombre()
            });
        }
    }

    private void mostrarFormularioAgregar() {
        // Crear el formulario para agregar una categoría
        JTextField txtNombre = new JTextField();

        JPanel panelFormulario = new JPanel(new GridLayout(1, 2, 10, 10));
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);

        int opcion = JOptionPane.showConfirmDialog(this, panelFormulario, "Agregar Categoría", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                Categoria categoria = new Categoria(0, txtNombre.getText());
                boolean exito = categoriaDAO.agregarCategoria(categoria);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Categoría agregada correctamente.");
                    llenarTablaCategorias();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarFormularioEditar() {
        int filaSeleccionada = tablaCategorias.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una categoría para editar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCategoria = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        Categoria categoria = categoriaDAO.obtenerCategoriaPorId(idCategoria);
        if (categoria == null) {
            JOptionPane.showMessageDialog(this, "Error al obtener la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField txtNombre = new JTextField(categoria.getNombre());

        JPanel panelFormulario = new JPanel(new GridLayout(1, 2, 10, 10));
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);

        int opcion = JOptionPane.showConfirmDialog(this, panelFormulario, "Editar Categoría", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                categoria.setNombre(txtNombre.getText());
                boolean exito = categoriaDAO.actualizarCategoria(categoria);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Categoría actualizada correctamente.");
                    llenarTablaCategorias();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarCategoria() {
        int filaSeleccionada = tablaCategorias.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona una categoría para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCategoria = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar esta categoría?",
                "Eliminar Categoría", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = categoriaDAO.eliminarCategoria(idCategoria);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Categoría eliminada correctamente.");
                llenarTablaCategorias();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la categoría.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Arial", Font.BOLD, 14)); // Fuente en negrita
        boton.setBackground(new Color(0, 153, 255)); // Azul vibrante
        boton.setForeground(Color.WHITE); // Texto en blanco
        boton.setFocusPainted(false); // Quitar el borde de enfoque
        boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Borde negro
    }
}
