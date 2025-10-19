package com.financepro.model.frontend.controllers;

import com.financepro.model.backend.dataTransferObjects.Usuario;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;


public class DashbordController {
    @FXML
    private Label mensagemBoasVindas;

    @FXML
    public void initialize() {
        Usuario user = dadosGlobais.getUser();
        System.out.println(user.getUsername());
        this.mensagemBoasVindas.setText(dadosGlobais.user.getUsername());
    }
}


