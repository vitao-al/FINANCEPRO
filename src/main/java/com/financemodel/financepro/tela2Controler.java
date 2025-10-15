package com.financemodel.financepro;

import javafx.fxml.FXML;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;

public class tela2Controler {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtPronomes;

    @FXML
    private TextField txtRenda;

    @FXML
    private ToggleButton btnSim;

    @FXML
    private ToggleButton btnNao;

    private ToggleGroup grupoMeta;

    @FXML
    public void initialize() {
        grupoMeta = new ToggleGroup();
        btnSim.setToggleGroup(grupoMeta);
        btnNao.setToggleGroup(grupoMeta);
    }
}
