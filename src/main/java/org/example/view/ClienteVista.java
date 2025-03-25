package org.example.view;

import javax.swing.*;
import java.awt.*;

public class ClienteVista extends JFrame {
    private JPanel panelDatos;
    private JPanel panelPedidos;

    public ClienteVista() {
        setTitle("Panel de Cliente");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Crear panel principal con pestañas
        JTabbedPane pestañas = new JTabbedPane();

        // Configurar pestaña "Mis Datos"
        panelDatos = crearPanelMisDatos();
        pestañas.addTab("Mis Datos", new ImageIcon(getClass().getResource("/icons/user.png")), panelDatos);

        // Configurar pestaña "Pedidos"
        panelPedidos = crearPanelPedidos();
        pestañas.addTab("Pedidos", new ImageIcon(getClass().getResource("/icons/orders.png")), panelPedidos);

        // Agregar pestañas al marco
        add(pestañas);

        setVisible(true);
    }

    private JPanel crearPanelMisDatos() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Etiqueta de Título
        JLabel titulo = new JLabel("Mis Datos Personales");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titulo, gbc);

        // Etiqueta y Campo: Nombre
        gbc.gridwidth = 1;
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Nombre:"), gbc);

        gbc.gridx = 1;
        JTextField campoNombre = new JTextField(20);
        campoNombre.setText("Juan Pérez"); // Ejemplo de texto predeterminado
        panel.add(campoNombre, gbc);

        // Etiqueta y Campo: Email
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        JTextField campoEmail = new JTextField(20);
        campoEmail.setText("juan.perez@example.com"); // Ejemplo de texto predeterminado
        panel.add(campoEmail, gbc);

        // Etiqueta y Campo: Teléfono
        gbc.gridy++;
        gbc.gridx = 0;
        panel.add(new JLabel("Teléfono:"), gbc);

        gbc.gridx = 1;
        JTextField campoTelefono = new JTextField(20);
        campoTelefono.setText("123456789"); // Ejemplo de texto predeterminado
        panel.add(campoTelefono, gbc);

        // Botón para actualizar datos
        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        JButton botonActualizar = new JButton("Actualizar Datos");
        botonActualizar.setBackground(new Color(0, 204, 102));
        botonActualizar.setForeground(Color.WHITE);
        panel.add(botonActualizar, gbc);

        return panel;
    }

    private JPanel crearPanelPedidos() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Etiqueta de Título
        JLabel titulo = new JLabel("Historial de Pedidos");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(new Color(0, 102, 204));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titulo, BorderLayout.NORTH);

        // Tabla de pedidos
        String[] columnas = {"ID Pedido", "Fecha", "Estado", "Total"};
        Object[][] datos = {
                {101, "2023-05-01", "Completado", "$200.00"},
                {102, "2023-05-02", "Pendiente", "$150.00"},
                {103, "2023-05-03", "Cancelado", "$50.00"}
        };

        JTable tablaPedidos = new JTable(datos, columnas);
        tablaPedidos.setFont(new Font("Arial", Font.PLAIN, 14));
        tablaPedidos.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(tablaPedidos);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
}
