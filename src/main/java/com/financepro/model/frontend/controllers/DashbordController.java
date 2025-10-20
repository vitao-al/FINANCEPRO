package com.financepro.model.frontend.controllers;

import com.financepro.model.backend.dataTransferObjects.Meta;
// Você precisará importar a classe DespesasTable
import com.financepro.model.backend.dataTransferObjects.DespesasTable;
import com.financepro.model.frontend.launcher.launcherPrincipal;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label; // Necessário para os labels das categorias
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.event.ActionEvent;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class DashbordController {

    // --- Campos FXML (Suas declarações existentes) ---
    @FXML private Text labelUsername;
    @FXML private Text labelSaldo;
    @FXML private Text labelValorMeta1;
    @FXML private Text labelValorMeta2;
    @FXML private VBox slideMenu;
    @FXML private Button btnMenu;
    @FXML private Button showMetas;
    @FXML private Button btnCreateMeta;
    @FXML private Button definirGasto;
    @FXML private Button definirEconomia;
    @FXML private Button bntVerMetasCriardas;
    @FXML private PieChart graficoCategoria;
    @FXML private Text labelNameObjective;
    @FXML private Text labelNameObjective2;
    @FXML private Button btnLogout;

    // NOVOS @FXMLs para os labels das categorias (ASSUMIDOS do FXML)
    @FXML private Label labelCategoria1;
    @FXML private Label labelCategoria2;
    @FXML private Label labelCategoria3;


    private static final double ESPACO_DE_TRANSICAO = 10;
    private boolean isMenuOpen = false;

    public void initialize() {

        // --- 1. Exibir dados do Usuário ---
         // Corrigido para a variável estática (assumida)

        if (dadosGlobais.user != null) {
            this.labelUsername.setText(dadosGlobais.user.getUsername());
            this.labelSaldo.setText(String.valueOf(dadosGlobais.user.getRenda()));

            // --- 2. Lógica para as DUAS ÚLTIMAS METAS (Mantida) ---
            List<Meta> todasAsMetas = dadosGlobais.user.pegarTodasAsMetas().getMetasList();

            if (todasAsMetas != null && !todasAsMetas.isEmpty()) {

                // Inverte a ordem para que a meta mais recente seja o índice 0
                Collections.reverse(todasAsMetas);

                // Processa a PRIMEIRA (mais recente) meta
                Meta meta1 = todasAsMetas.get(0);
                labelNameObjective.setText(meta1.getNome());
                labelValorMeta1.setText(String.format("%.2f", meta1.getValor()));

                // Processa a SEGUNDA (penúltima) meta, se ela existir
                if (todasAsMetas.size() >= 2) {
                    Meta meta2 = todasAsMetas.get(1);
                    labelNameObjective2.setText(meta2.getNome());
                    labelValorMeta2.setText(String.format("%.2f", meta2.getValor()));
                } else {
                    labelNameObjective2.setText("Somente 1 Meta.");
                    labelValorMeta2.setText("---");
                }
            } else {
                labelNameObjective.setText("Nenhuma Meta.");
                labelValorMeta1.setText("---");
                labelNameObjective2.setText("Nenhuma Meta.");
                labelValorMeta2.setText("---");
            }

            // -------------------------------------------------------------------
            // --- 3. NOVA LÓGICA: Configuração do PieChart e Ranking de Gastos ---
            // -------------------------------------------------------------------

            if (graficoCategoria != null) {

                // 3.1. Obtém as despesas agrupadas
                ArrayList<DespesasTable> despesasPorCategoria = dadosGlobais.user.pegarQuantidadeDespesaCategoria();

                // 3.2. Ranqueia a lista de DespesasTable pelo valor em ordem decrescente
                List<DespesasTable> rankingGastos = despesasPorCategoria.stream()
                        // Ranqueia pelo valor (float), do maior para o menor
                        .sorted(Comparator.comparing(DespesasTable::getValor, Comparator.reverseOrder()))
                        .collect(Collectors.toList());

                ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

                // Array para armazenar os nomes, inicializado com valores padrão para o caso de não haver 3 gastos
                String[] top3Nomes = new String[]{"Nenhuma Despesa", "Nenhuma Despesa", "Nenhuma Despesa"};

                int i = 0;
                double totalOutros = 0.0;

                for (DespesasTable dt : rankingGastos) {
                    if (i < 3) {
                        // 3.3. Adiciona os Top 3 Gastos ao PieChart
                        pieChartData.add(new PieChart.Data(dt.getCategoria(), dt.getValor()));
                        top3Nomes[i] = dt.getCategoria();
                        i++;
                    } else {
                        // 3.4. Soma os demais como "Outros"
                        totalOutros += dt.getValor();
                    }
                }

                // Adiciona a soma dos "Outros" ao gráfico, se houver
                if (totalOutros > 0.0) {
                    pieChartData.add(new PieChart.Data("Outros", totalOutros));
                }

                if (!pieChartData.isEmpty()) {
                    graficoCategoria.setData(pieChartData);
                }

                // 3.5. Atualiza os Labels do FXML com os nomes das categorias
                if (labelCategoria1 != null) {
                    labelCategoria1.setText(top3Nomes[0]);
                }
                if (labelCategoria2 != null) {
                    labelCategoria2.setText(top3Nomes[1]);
                }
                if (labelCategoria3 != null) {
                    labelCategoria3.setText(top3Nomes[2]);
                }
            }

        } else {
            // Caso user seja nulo (tratar o login/sessão)
            this.labelUsername.setText("Visitante");
            this.labelSaldo.setText("0.00");
        }


        // --- 4. Configuração de Eventos (Mantidos com checagem nula) ---
        if (showMetas != null) {
            showMetas.setOnAction(event -> {
                try {
                    launcherPrincipal.changeView("/views/viewMetas.fxml");
                }catch(Exception ex){
                    throw new RuntimeException(ex);
                }
            });
        }

        if (btnCreateMeta != null) {
            btnCreateMeta.setOnAction(event -> {
                try {
                    launcherPrincipal.changeView("/views/viewCreateMeta.fxml");
                }catch(Exception ex){
                    throw new RuntimeException(ex);
                }
            });
        }

        if (definirGasto != null) {
            definirGasto.setOnAction(event -> {
                try {
                    launcherPrincipal.changeView("/views/viewAddGastos.fxml");
                }catch (Exception ex){
                    throw new RuntimeException(ex);
                }
            });
        }

        if (definirEconomia != null) {
            definirEconomia.setOnAction(event -> {
                try {
                    launcherPrincipal.changeView("/views/viewAddEconomia.fxml");
                }catch (Exception ex){
                    throw new RuntimeException(ex);
                }
            });
        }

        if (btnLogout != null) {
            btnLogout.setOnAction(event -> {
                try {
                    launcherPrincipal.changeView("/views/viewPrincipal.fxml");
                }catch(Exception ex){
                    throw new RuntimeException(ex);
                }
            });
        }
    }

    // ... (handleMenuClick e outros métodos)
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