package com.financemodel.financepro;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TelaMetas  extends  Application{


        @Override
        public void start(Stage stage) throws Exception {
            // Carrega o FXML
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/financemodel/financepro/telaMetas.fxml"));



            // Cria a cena
            Scene scene = new Scene(fxmlLoader.load());

            // Aplica título e mostra a janela
            stage.setTitle("");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        }

        public static void main() {
            launch(); // inicia a aplicação JavaFX
        }
    }


