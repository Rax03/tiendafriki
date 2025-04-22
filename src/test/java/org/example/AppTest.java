package org.example;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


import org.example.controller.AdminControlador;
import org.example.controller.UsuarioControlador;
import org.example.view.AdminVista;
import org.example.view.UsuarioVista;

public class AppTest {
    public static void main(String[] args) {
        // Crear instancia de AdminVista
        UsuarioVista usuarioVista = new UsuarioVista(16);

        // Vincular AdminVista con su controlador
        new UsuarioControlador(usuarioVista);

        // Mostrar la vista de administrador
        usuarioVista.setVisible(true);
    }
}
