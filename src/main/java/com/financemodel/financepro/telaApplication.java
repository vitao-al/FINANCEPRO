package com.testeexemplo.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class telaApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(telaApplication.class.getResource("doLoginView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 861, 457);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }
}
