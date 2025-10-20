package com.financepro.model.frontend.controllers;

import com.financepro.model.backend.dataTransferObjects.Economia;
import com.financepro.model.backend.dataTransferObjects.Meta;
import com.financepro.model.backend.dataTransferObjects.Usuario;
import com.financepro.model.frontend.launcher.launcherPrincipal;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MinhasMetasController
{
    @FXML
    private VBox slideMenu;
    @FXML
    private Button dashBoardbnt;
    @FXML
    private Button btnMenu;
    @FXML
    private Button showMetas;
    @FXML
    private Button btnCreateMeta;
    @FXML
    private Button definirGasto;
    @FXML
    private Button definirEconomia;
    @FXML
    private Button analiseDeDespesas;
    private Button btnLogout;
    @FXML
    private boolean isMenuOpen = false;
    private Button bntVerMetasCriardas;
    @FXML private VBox metasContainer;
    private static final double ESPACO_DE_TRANSICAO = 10;
    public void initialize()
    {
        // --- 4. Configuração de Eventos (Mantidos com checagem nula) ---
        if (showMetas != null) {
            showMetas.setOnAction(event -> {
                try {
                    launcherPrincipal.changeView("/views/viewMetas.fxml");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

        if (btnCreateMeta != null) {
            btnCreateMeta.setOnAction(event -> {
                try {
                    launcherPrincipal.changeView("/views/viewCreateMeta.fxml");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

        if (definirGasto != null) {
            definirGasto.setOnAction(event -> {
                try {
                    launcherPrincipal.changeView("/views/viewAddGastos.fxml");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        if (dashBoardbnt != null) {
            dashBoardbnt.setOnAction(event -> {
                try {
                    launcherPrincipal.changeView("/views/viewDashbord.fxml");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

        if (definirEconomia != null) {
            definirEconomia.setOnAction(event -> {
                try {
                    launcherPrincipal.changeView("/views/viewAddEconomia.fxml");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        if (analiseDeDespesas != null) {
            analiseDeDespesas.setOnAction(event -> {
                try {
                    launcherPrincipal.changeView("/views/viewCategoriasValues.fxml");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }

        if (btnLogout != null) {
            btnLogout.setOnAction(event -> {
                try {
                    launcherPrincipal.changeView("/views/viewPrincipal.fxml");
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
        if (dadosGlobais.user != null) {
            // Pegar todas as metas do usuário
            for (Meta meta : dadosGlobais.user.pegarTodasAsMetas().getMetasList()) {
                adicionarMetaNaTela(meta);
            }
        } else {
            Label erro = new Label("Nenhum usuário logado!");
            metasContainer.getChildren().add(erro);
        }

    }
    private void adicionarMetaNaTela(Meta meta) {
        // Calcular porcentagem de conclusão
        float porcentagem = 0f;
        if (meta.getValor() != 0) { // Evitar divisão por zero
            porcentagem = (meta.getSaldoAtual() / meta.getValor()) * 100;
            if (porcentagem > 100) porcentagem = 100; // Limita a 100%
        }

        // Criar HBox para cada meta
        HBox metaBox = new HBox(10); // Espaçamento de 10
        metaBox.setStyle("-fx-padding: 5px; -fx-border-color: white; -fx-border-width: 1px;");

        // Label para o nome da meta
        Label nomeMeta = new Label(meta.getNome());
        nomeMeta.setStyle("-fx-font-size: 14px;");

        // Label para a porcentagem
        Label porcentagemLabel = new Label(String.format("%.1f%%", porcentagem));
        porcentagemLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: green;"); // Verde para progresso

        // Adicionar ao HBox
        metaBox.getChildren().addAll(nomeMeta, porcentagemLabel);

        // Adicionar ao container
        metasContainer.getChildren().add(metaBox);
    }
    @FXML
    private void handleMenuClick(ActionEvent event) {
        // ... (Seu código de transição de menu)
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

