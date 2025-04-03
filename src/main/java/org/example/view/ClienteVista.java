package org.example.view;

import org.example.model.dao.ClienteDAO;
import org.example.model.entity.Cliente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Timestamp;
import java.util.List;

public class ClienteVista extends JFrame {

    private JTable tablaClientes;
    private DefaultTableModel modeloTabla;
    private ClienteDAO clienteDAO;

    public ClienteVista() {
        setTitle("Gestión de Clientes");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        clienteDAO = new ClienteDAO();

        // Configurar el panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gestión de Clientes", JLabel.CENTER);
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        panelPrincipal.setBackground(new Color(34, 34, 34));

        // Configurar la tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Email", "Teléfono", "Dirección", "Fecha de Registro"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer las celdas no editables
            }
        };
        tablaClientes = new JTable(modeloTabla);
        tablaClientes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollTabla = new JScrollPane(tablaClientes);

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
        btnEliminar.addActionListener(e -> eliminarCliente());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        // Llenar la tabla con los datos de los clientes
        llenarTablaClientes();

        // Agregar componentes al panel principal
        panelPrincipal.add(titulo, BorderLayout.NORTH);
        panelPrincipal.add(scrollTabla, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        // Agregar panel principal al JFrame
        add(panelPrincipal);
        setVisible(true);
    }

    private void llenarTablaClientes() {
        modeloTabla.setRowCount(0); // Limpiar la tabla antes de llenarla
        List<Cliente> clientes = clienteDAO.obtenerTodosLosClientes();
        for (Cliente cliente : clientes) {
            modeloTabla.addRow(new Object[]{
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getEmail(),
                    cliente.getTelefono(),
                    cliente.getDireccion(),
                    cliente.getFecha_registro()
            });
        }
    }

    private void mostrarFormularioAgregar() {
        // Crear el formulario para agregar un cliente
        JTextField txtNombre = new JTextField();
        JTextField txtEmail = new JTextField();
        JTextField txtTelefono = new JTextField();
        JTextField txtDireccion = new JTextField();

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Email:"));
        panelFormulario.add(txtEmail);
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(txtTelefono);
        panelFormulario.add(new JLabel("Dirección:"));
        panelFormulario.add(txtDireccion);

        int opcion = JOptionPane.showConfirmDialog(this, panelFormulario, "Agregar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                Cliente cliente = new Cliente(0, txtNombre.getText(), txtEmail.getText(), txtTelefono.getText(), txtDireccion.getText(), new Timestamp(System.currentTimeMillis()));
                boolean exito = clienteDAO.agregarCliente(cliente);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Cliente agregado correctamente.");
                    llenarTablaClientes();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al agregar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarFormularioEditar() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente para editar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCliente = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        Cliente cliente = clienteDAO.obtenerClientePorId(idCliente);
        if (cliente == null) {
            JOptionPane.showMessageDialog(this, "Error al obtener el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JTextField txtNombre = new JTextField(cliente.getNombre());
        JTextField txtEmail = new JTextField(cliente.getEmail());
        JTextField txtTelefono = new JTextField(cliente.getTelefono());
        JTextField txtDireccion = new JTextField(cliente.getDireccion());

        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 10, 10));
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Email:"));
        panelFormulario.add(txtEmail);
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(txtTelefono);
        panelFormulario.add(new JLabel("Dirección:"));
        panelFormulario.add(txtDireccion);

        int opcion = JOptionPane.showConfirmDialog(this, panelFormulario, "Editar Cliente", JOptionPane.OK_CANCEL_OPTION);
        if (opcion == JOptionPane.OK_OPTION) {
            try {
                cliente.setNombre(txtNombre.getText());
                cliente.setEmail(txtEmail.getText());
                cliente.setTelefono(txtTelefono.getText());
                cliente.setDireccion(txtDireccion.getText());

                boolean exito = clienteDAO.actualizarCliente(cliente);
                if (exito) {
                    JOptionPane.showMessageDialog(this, "Cliente actualizado correctamente.");
                    llenarTablaClientes();
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error en los datos ingresados: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void eliminarCliente() {
        int filaSeleccionada = tablaClientes.getSelectedRow();
        if (filaSeleccionada < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente para eliminar.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idCliente = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar este cliente?", "Eliminar Cliente", JOptionPane.YES_NO_OPTION);
        if (confirmacion == JOptionPane.YES_OPTION) {
            boolean exito = clienteDAO.eliminarCliente(idCliente);
            if (exito) {
                JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                llenarTablaClientes();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void estilizarBoton(JButton boton) {
        boton.setFont(new Font("Arial", Font.BOLD, 14));
        boton.setBackground(new Color(0, 153, 255));
        boton.setForeground(Color.WHITE);
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }
}
