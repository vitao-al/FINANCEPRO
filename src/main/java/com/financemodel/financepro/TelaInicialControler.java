package com.financemodel.financepro;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class TelaInicialControler {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;

    public void initialize() {
        loginButton.setOnAction(e -> {
            if (camposValidos()) {
                try {
                    LauncherPrincipal.iniciartela2("Dashbord.fxml"); // dashboard ainda não pronto
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                exibirErro("Preencha todos os campos corretamente!");
            }
        });

        registerButton.setOnAction(e -> {
            if (camposValidos()) {
                try {
                    LauncherPrincipal.iniciartela3("TelaLogin.fxml");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }

            } else {
                exibirErro("Preencha todos os campos corretamente!");
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
