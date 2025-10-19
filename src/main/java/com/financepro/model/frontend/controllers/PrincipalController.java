package com.financepro.model.frontend.controllers;

import com.financepro.model.frontend.launcher.launcherPrincipal;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import com.financepro.model.backend.dataTransferObjects.*;

public class PrincipalController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    public Usuario getUser() {
        return user;
    }

    public void setUser(Usuario user) {
        this.user = user;
    }

    Usuario user = null;

    public void initialize() {
        loginButton.setOnAction(e -> {
            if (camposValidos()) {
                if(Usuario.PegarLoginUsuario(usernameField.getText(),passwordField.getText()) != null)
                {
                    dadosGlobais.user = Usuario.PegarLoginUsuario(usernameField.getText(),passwordField.getText());
                    try {
                        launcherPrincipal.changeView("/views/viewDashbord.fxml");
                        // dashboard ainda não pronto
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                } else {
                    exibirErro("Preencha todos os campos corretamente!");
                }
                }

        });

        registerButton.setOnAction(e -> {
            try {
                launcherPrincipal.changeView("/views/viewSignUp1.fxml");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    // Valida os campos
    private boolean camposValidos() {
        return !usernameField.getText().isEmpty() && !passwordField.getText().isEmpty();
    }

    // Exibe mensagem de erro
    private void exibirErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
