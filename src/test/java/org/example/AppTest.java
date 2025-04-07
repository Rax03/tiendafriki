package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


import org.example.controller.AdminControlador;
import org.example.view.AdminVista;

public class AppTest {
    public static void main(String[] args) {
        // Crear instancia de AdminVista
        AdminVista adminVista = new AdminVista();

        // Vincular AdminVista con su controlador
        new AdminControlador(adminVista);

        // Mostrar la vista de administrador
        adminVista.setVisible(true);
    }
}
