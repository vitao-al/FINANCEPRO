package com.financepro.model.frontend.controllers;

import com.financepro.model.frontend.launcher.launcherPrincipal;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class SignUp1Controller {
    @FXML
    public TextField txtRenda1;
    @FXML
    private TextField txtNome;

    @FXML
    private ToggleButton bntcriarconta;

    @FXML
    private ToggleButton bntvoltaratelainicial;

    @FXML
    private ToggleButton btnhomem;

    @FXML
    private ToggleButton btnmulher;
    @FXML
    private TextField txtSenha;
    @FXML
    private ToggleGroup grupoGenero;

    @FXML
    public void initialize() {
        // Grupo de gênero
        grupoGenero = new ToggleGroup();
        btnhomem.setToggleGroup(grupoGenero);
        btnmulher.setToggleGroup(grupoGenero);

        // Botão de criar conta
        bntcriarconta.setOnAction(e -> {
            if (camposValidos()) {
                try {
                    launcherPrincipal.iniciartela3("/views/TelaLogin2.fxml");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                exibirErro("Preencha todos os campos corretamente!");
            }
        });

        // Botão de voltar — sem verificação de campos
        bntvoltaratelainicial.setOnAction(e -> {
            try {
                launcherPrincipal.iniciartela3("viewPrincipal.fxml");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    private boolean camposValidos() {
        boolean nomePreenchido = txtNome.getText().matches("[a-zA-Z]+");
        boolean rendaPreenchida = txtRenda1.getText().matches("[0-9]+");
        boolean generoSelecionado = grupoGenero.getSelectedToggle() != null;

        return nomePreenchido && rendaPreenchida && generoSelecionado;
    }

    private void exibirErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
