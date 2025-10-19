package com.financepro.model.frontend.controllers;

import com.financepro.model.backend.dataTransferObjects.Usuario;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DashbordController {
    @FXML
    private Text labelUsername;
    @FXML
    private Text labelSaldo;
    @FXML
    private VBox slideMenu;
    @FXML
    private Button btnMenu;

    @FXML
    private PieChart teste;


    private boolean isMenuOpen = false;

    public void initialize() {

        // Criando dados para o PieChart
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Entradas", 40),
                        new PieChart.Data("Despesas", 60)
                );

        teste.setData(pieChartData);
        teste.setTitle("Saldo Mensal");


        Usuario user = dadosGlobais.getUser();
        this.labelUsername.setText(dadosGlobais.user.getUsername());
        this.labelSaldo.setText(String.valueOf(user.getRenda()));

        btnMenu.setOnAction(e -> {
            TranslateTransition transition = new TranslateTransition(Duration.millis(300), slideMenu);

            if (isMenuOpen == false) {
                // Abrir
                transition.setToX(250); // desloca o menu 250px para a direita
                transition.play();
                isMenuOpen = true;

            } else {
                // Fechar
                transition.setToX(0);
                transition.setOnFinished(event -> slideMenu.setLayoutX(-250)); // volta posição
                slideMenu.setLayoutX(0);
                transition.play();
                isMenuOpen = false;
            }
        });


    }

}


