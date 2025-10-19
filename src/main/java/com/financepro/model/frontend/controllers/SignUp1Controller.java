package com.financepro.model.frontend.controllers;

import com.financepro.model.backend.dataTransferObjects.Usuario;
import com.financepro.model.frontend.launcher.launcherPrincipal;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

import javax.swing.*;

public class SignUp1Controller {

    @FXML
    private Label txtErroUsername;
    @FXML
    private Label txtErroPassword;
    @FXML
    private Label txtErroRenda;

    @FXML
    private TextField username;
    @FXML
    private TextField newPassword;
    @FXML
    private TextField renda;

    @FXML private ToggleButton btnHomem;
    @FXML private ToggleButton btnMulher;
    @FXML private ToggleGroup grupoGenero;

    @FXML private Button btnCreateAccount;
    @FXML private Button btnBack;

    @FXML
    public void initialize() {
        // Configura o ToggleGroup para os botões de gênero
        grupoGenero = new ToggleGroup();
        btnHomem.setToggleGroup(grupoGenero);
        btnMulher.setToggleGroup(grupoGenero);

        // Botão criar conta
        btnCreateAccount.setOnAction(e -> {
            if (camposValidos()) {
                float rendafloat = 0f;
                try {
                    rendafloat = Float.parseFloat(renda.getText());
                } catch (NumberFormatException ex) {
                    mostrarErroAnimado(txtErroRenda, "Renda inválida!");
                    return;
                }
                Usuario.criarNovoUsuario(username.getText(), newPassword.getText(), setGender(), rendafloat);
                try {
                    launcherPrincipal.changeView("/views/viewCreateMeta.fxml");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                try {
                    launcherPrincipal.changeView("/views/viewDashboard.fxml");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });

        // Botão voltar
        btnBack.setOnAction(e -> {
            try {
                launcherPrincipal.changeView("/views/viewPrincipal.fxml");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private boolean camposValidos() {
        boolean valido = true;

        if (!username.getText().matches("[a-zA-Z]+")) {
            mostrarErroAnimado(txtErroUsername, "Nome inválido!");
            valido = false;
        } else txtErroUsername.setVisible(false);

        if (!renda.getText().matches("[-+]?[0-9]*\\.?[0-9]+")) {
            mostrarErroAnimado(txtErroRenda, "Digite um valor numérico!");
            valido = false;
        } else txtErroRenda.setVisible(false);

        if (!newPassword.getText().matches("[A-Za-z\\d@$!%*?&]{8,20}")) {
            mostrarErroAnimado(txtErroPassword, "Senha inválida! Digite uma senha com:\nA-Z, a-z, 0-9, Mínimo 8 caracteres!");
            valido = false;
        } else txtErroPassword.setVisible(false);

        return valido;
    }

    private String setGender() {
        if (btnHomem.isSelected()) return "M";
        if (btnMulher.isSelected()) return "F";
        return "O";
    }

    private void mostrarErroAnimado(Label label, String mensagem) {
        label.setText(mensagem);
        label.setVisible(true);

        TranslateTransition tt = new TranslateTransition(Duration.millis(50), label);
        tt.setFromX(0);
        tt.setByX(10);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.play();
    }

}
