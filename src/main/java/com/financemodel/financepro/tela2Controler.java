package com.financemodel.financepro;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;

public class tela2Controler {
    @FXML
    public TextField txtNome;

    @FXML
    public TextField txtPronomes;

    @FXML
    public TextField txtRenda;

    @FXML
    public ToggleButton btnSim;

    @FXML
    public ToggleButton btnNao;


    private ToggleGroup grupoMeta;



    @FXML
    public void initialize() {
        grupoMeta = new ToggleGroup();
        btnSim.setToggleGroup(grupoMeta);
        btnNao.setToggleGroup(grupoMeta);

        btnSim.setOnAction(e->{
            if (camposValidos()){
                try {
                    LauncherPrincipal.iniciartela3("TelaMetas.fxml");

        }catch (Exception ex) {
            ex.printStackTrace();
        }}
        else {
                exibirErro("Preencha todos os campos corretamente!");
            }  });

    }

    private boolean camposValidos() {
        return !txtPronomes.getText().isEmpty() && !txtRenda.getText().isEmpty() && !txtNome.getText().isEmpty();}


    private void exibirErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();


}}
