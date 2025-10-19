package com.financepro.model.frontend.controllers;

import com.financepro.model.backend.dataTransferObjects.Usuario;
import com.financepro.model.frontend.launcher.launcherPrincipal;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.event.ActionEvent; // Importe ActionEvent

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
    private Button showMetas;
    @FXML
    private PieChart teste;

    private static final double ESPACO_DE_TRANSICAO = 10;

    private boolean isMenuOpen = false;


    public void initialize() {

        // Criando dados para o PieChart
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Categoria1", 40),
                        new PieChart.Data("Categoria2", 60),
                        new PieChart.Data("Categoria3", 80)
                );

        teste.setData(pieChartData);

        Usuario user = dadosGlobais.getUser();
        this.labelUsername.setText(dadosGlobais.user.getUsername());
        this.labelSaldo.setText(String.valueOf(user.getRenda()));

        showMetas.setOnAction(event -> {
            try {
                launcherPrincipal.changeView("/views/viewMetas.fxml");
            }catch(Exception ex){
                throw new RuntimeException(ex);
            }
        });

    }

    @FXML
    private void handleMenuClick(ActionEvent event) {

        TranslateTransition transition = new TranslateTransition(Duration.millis(300), slideMenu);

        if (!isMenuOpen) {

            transition.setToX(ESPACO_DE_TRANSICAO);


            slideMenu.setVisible(true);

            transition.play();
            isMenuOpen = true;

        } else {

            transition.setToX(0);

            transition.setOnFinished(e -> {
                slideMenu.setVisible(false);
            });

            transition.play();
            isMenuOpen = false;
        }
    }
}