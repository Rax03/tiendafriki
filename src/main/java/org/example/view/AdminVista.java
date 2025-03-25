package org.example.view;

import javax.swing.*;

public class AdminVista extends JFrame {
    public AdminVista() {
        setTitle("Panel de Administración");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.add("Productos", new JPanel());
        pestañas.add("Categorías", new JPanel());
        pestañas.add("Proveedores", new JPanel());
        pestañas.add("Pedidos", new JPanel());
        add(pestañas);

        setVisible(true);
    }
}
