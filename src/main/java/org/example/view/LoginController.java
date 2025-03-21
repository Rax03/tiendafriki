package org.example.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.example.model.entity.Usuario;
import org.example.model.service.UsuarioService;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label messageLabel;

    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = usuarioService.authenticate(username, password);

        if (usuario != null) {
            messageLabel.setText("Inicio de sesión exitoso");
        } else {
            messageLabel.setText("Usuario o contraseña incorrectos");
        }
    }
}