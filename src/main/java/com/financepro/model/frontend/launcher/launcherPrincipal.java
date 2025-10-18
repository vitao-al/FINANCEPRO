package com.financepro.model.frontend.launcher;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class launcherPrincipal extends Application {

    private static Stage stagePrincipal;
    private static StackPane root; // Container das views

    @Override
    public void start(Stage primeiroStage) throws Exception {
        stagePrincipal = primeiroStage;

        // Inicializa o StackPane
        root = new StackPane();

        // Carrega a primeira view
        Parent primeiraView = FXMLLoader.load(getClass().getResource("/views/viewPrincipal.fxml"));
        root.getChildren().add(primeiraView);

        Scene scene = new Scene(root);
        primeiroStage.setScene(scene);
        primeiroStage.setResizable(false);
        primeiroStage.show();
    }

    /**
     * Troca de view com animação suave para a esquerda
     */
    public static void changeView(String nometela) throws Exception {
        Parent novaView = FXMLLoader.load(launcherPrincipal.class.getResource(nometela));

        // Coloca a nova view à direita do container
        novaView.setTranslateX(root.getWidth());
        root.getChildren().add(novaView);

        // Pane atual
        Parent atualView = (Parent) root.getChildren().get(0);

        // Animação do atual saindo para a esquerda
        TranslateTransition sair = new TranslateTransition(Duration.millis(500), atualView);
        sair.setToX(-root.getWidth());

        // Animação da nova entrando da direita
        TranslateTransition entrar = new TranslateTransition(Duration.millis(500), novaView);
        entrar.setToX(0);

        // Remove o antigo depois da animação
        entrar.setOnFinished(e -> root.getChildren().remove(atualView));

        // Executa animações
        sair.play();
        entrar.play();
    }

    public static void main(String[] args) {
        launch();
    }
}
