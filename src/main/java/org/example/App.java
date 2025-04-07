package org.example;

import org.example.controller.LoginControlador;
import org.example.model.dao.UsuarioDAO;
import org.example.model.service.LoginService;
import org.example.view.LoginVista;

public class App {
    public static void main(String[] args) {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        LoginService loginService = new LoginService(usuarioDAO);
        LoginVista loginVista = new LoginVista();

        new LoginControlador(loginVista, loginService);

        loginVista.setVisible(true);
    }
}
