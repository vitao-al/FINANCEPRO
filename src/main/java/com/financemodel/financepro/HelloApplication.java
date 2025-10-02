package com.financemodel.financepro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Carrega o FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/financemodel/financepro/TelaLogin2.fxml"));



        // Cria a cena
        Scene scene = new Scene(fxmlLoader.load());

        // Aplica título e mostra a janela
        stage.setTitle("Meu Projeto JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    public static void main() {
        launch(); // inicia a aplicação JavaFX
    }
}
