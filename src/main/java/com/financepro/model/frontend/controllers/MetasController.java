package com.financepro.model.frontend.controllers;

import com.financepro.model.frontend.launcher.launcherPrincipal;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class MetasController {

    @FXML
    private Button btnCreateMeta;
    @FXML
    private Button btnNoCreateMeta;

    @FXML
    private Label txtErroNomeMeta;
    @FXML
    private Label txtErroValorMeta;

    @FXML
    private TextField nameMeta;
    @FXML
    private TextField valueMeta;
    @FXML
    private TextField descMeta;

    public void initialize() {
        btnCreateMeta.setOnAction(e -> {
            if (camposValidos()) {
                float valueMetafloat = 0f;
                try {
                    valueMetafloat = Float.parseFloat(valueMeta.getText());
                } catch (NumberFormatException ex) {
                    SignUp1Controller.mostrarErroAnimado(txtErroValorMeta, "Valor de meta inválida!");
                    return;
                }
                try {
                    launcherPrincipal.changeView("/views/viewDashboard.fxml");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });
    }

    public boolean camposValidos() {
        boolean valido = true;

        if (!nameMeta.getText().matches("[a-zA-Z]+")) {
            mostrarErroAnimado(txtErroNomeMeta, "Nome inválido!");
            valido = false;
        } else txtErroNomeMeta.setVisible(false);

        if (!valueMeta.getText().matches("[-+]?[0-9]*\\.?[0-9]+")) {
            mostrarErroAnimado(txtErroValorMeta, "Digite um valor numérico!");
            valido = false;
        } else txtErroValorMeta.setVisible(false);
        return valido;
    }

    public void mostrarErroAnimado(Label label, String mensagem) {
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
