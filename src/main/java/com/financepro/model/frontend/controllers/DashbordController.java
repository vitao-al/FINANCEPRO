package com.financepro.model.frontend.controllers;

import com.financepro.model.backend.dataTransferObjects.Despesa;
import com.financepro.model.backend.dataTransferObjects.Economia;
import com.financepro.model.backend.dataTransferObjects.Meta;
// Você precisará importar a classe DespesasTable
import com.financepro.model.backend.dataTransferObjects.DespesasTable;
import com.financepro.model.frontend.launcher.launcherPrincipal;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label; // Necessário para os labels das categorias
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.event.ActionEvent;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class DashbordController {

    // --- Campos FXML (Suas declarações existentes) ---
    @FXML
    private Text labelUsername;
    @FXML
    private Text labelSaldo;
    @FXML
    private Text labelValorMeta1;
    @FXML
    private Text labelValorMeta2;
    @FXML
    private VBox slideMenu;
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
    private Button bntVerMetasCriardas;
    @FXML
    private PieChart graficoCategoria;
    @FXML
    private Text labelNameObjective;
    @FXML
    private Text labelNameObjective2;
    @FXML
    private Button btnLogout;
    @FXML private LineChart<String, Number> lineChart; // Alterado de StackedAreaChart para LineChart
    @FXML private DatePicker datePickerInicio;
    @FXML private DatePicker datePickerFim;
    @FXML
    private Label labelCategoria1;
    @FXML
    private Label labelCategoria2;
    @FXML
    private Label labelCategoria3;

    ArrayList<DespesasTable> top3Despesas = new ArrayList<>();

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
            top3Despesas = dadosGlobais.user.pegarTop3DespesasPorCategoria();
            int size = top3Despesas.size();

            // Verifica e define os labels com segurança
            labelCategoria1.setText(size > 0 ? top3Despesas.get(0).getCategoria().toString() : "Nenhuma Despesa");
            labelCategoria2.setText(size > 1 ? top3Despesas.get(1).getCategoria().toString() : "Nenhuma Despesa");
            labelCategoria3.setText(size > 2 ? top3Despesas.get(2).getCategoria() : "Nenhuma Despesa");
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

        if (definirEconomia != null) {
            definirEconomia.setOnAction(event -> {
                try {
                    launcherPrincipal.changeView("/views/viewAddEconomia.fxml");
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
// ... (código existente)

        // Configurar o StackedAreaChart
        atualizarGrafico();
    }
    // Função auxiliar para limpar a hora da data
    private Date clearTime(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
    @FXML
    private void atualizarGrafico()
        {
            if (dadosGlobais.user == null) return;

            Date inicio = datePickerParaUtilDate(datePickerInicio);
            Date fim = datePickerParaUtilDate(datePickerFim);

            if (inicio == null || fim == null || inicio.after(fim)) {
                lineChart.getData().clear();
                return;
            }

            // Pegar todas as economias e despesas (sem filtro inicial, depois ordenar)
            ArrayList<Economia> todasEconomias = dadosGlobais.user.pegarTodasEconomias();
            ArrayList<Despesa> todasDespesas = dadosGlobais.user.pegarTodasDespesas();

            // Ordenar por data
            todasEconomias.sort(Comparator.comparing(Economia::getData));
            todasDespesas.sort(Comparator.comparing(Despesa::getData));

            // Series para o gráfico
            XYChart.Series<String, Number> seriesEconomias = new XYChart.Series<>();
            XYChart.Series<String, Number> seriesDespesas = new XYChart.Series<>();

            seriesEconomias.setName("Economias");
            seriesDespesas.setName("Despesas");

            // Calcular soma acumulada dentro do período
            float somaAcumuladaEconomias = 0;
            float somaAcumuladaDespesas = 0;

            // Mapa para rastrear datas únicas
            TreeMap<Date, Float> acumuladoEconomias = new TreeMap<>();
            TreeMap<Date, Float> acumuladoDespesas = new TreeMap<>();

            // Acumular Economias
            for (Economia e : todasEconomias) {
                if (e != null && e.getData() != null && !e.getData().before(inicio) && !e.getData().after(fim)) {
                    Date dia = clearTime(e.getData());
                    somaAcumuladaEconomias += Math.abs(e.getValor()); // Soma acumulada
                    acumuladoEconomias.put(dia, somaAcumuladaEconomias);
                }
            }

            // Acumular Despesas
            for (Despesa d : todasDespesas) {
                if (d != null && d.getData() != null && !d.getData().before(inicio) && !d.getData().after(fim)) {
                    Date dia = clearTime(d.getData());
                    somaAcumuladaDespesas += Math.abs(d.getValor()); // Soma acumulada
                    acumuladoDespesas.put(dia, somaAcumuladaDespesas);
                }
            }

            // Preencher séries com valores acumulados
            for (Map.Entry<Date, Float> entry : acumuladoEconomias.entrySet()) {
                String dataStr = new SimpleDateFormat("dd/MM").format(entry.getKey());
                seriesEconomias.getData().add(new XYChart.Data<>(dataStr, entry.getValue()));
            }
            for (Map.Entry<Date, Float> entry : acumuladoDespesas.entrySet()) {
                String dataStr = new SimpleDateFormat("dd/MM").format(entry.getKey());
                seriesDespesas.getData().add(new XYChart.Data<>(dataStr, entry.getValue()));
            }

            // Limpar e adicionar dados ao gráfico
            lineChart.getData().clear();
            lineChart.getData().addAll(seriesEconomias, seriesDespesas);

            // Estilizar as linhas após adicionar os dados
            Platform.runLater(() -> {
                if (seriesEconomias.getNode() != null) {
                    seriesEconomias.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #00ff00; -fx-stroke-width: 2px;");
                }
                if (seriesDespesas.getNode() != null) {
                    seriesDespesas.getNode().lookup(".chart-series-line").setStyle("-fx-stroke: #ff0000; -fx-stroke-width: 2px;");
                }
            });

            // Debug
            System.out.println("Pontos Economias: " + seriesEconomias.getData().size());
            System.out.println("Pontos Despesas: " + seriesDespesas.getData().size());
        }

    // Função auxiliar do DatePicker (copiada de EconomiaController)
    public static Date datePickerParaUtilDate(DatePicker datePicker) {
        LocalDate localDate = datePicker.getValue();
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
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

    // Configurar o StackedAreaChart
}
