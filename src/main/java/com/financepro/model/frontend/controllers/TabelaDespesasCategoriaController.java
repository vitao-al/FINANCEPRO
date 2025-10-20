package com.financepro.model.frontend.controllers;
import com.financepro.model.backend.dataTransferObjects.*;
import com.financepro.model.backend.model.Categorias;
import com.financepro.model.frontend.launcher.launcherPrincipal;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Date;
import java.util.UUID;


public class TabelaDespesasCategoriaController {
    @FXML
    private Label saldoText;
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
    private Button analiseDeDespesas;
    @FXML
    private Button bntVerMetasCriardas;
    @FXML
    private Label totalValueText;
    @FXML
    private Button dashBoardbnt;
    @FXML
    private Button botaoMenu;

    @FXML
    TableView<DespesasTable> tabelaDespesas;

    @FXML
    TableColumn<DespesasTable,String> colunaCategoria;
    @FXML
    TableColumn<DespesasTable,Integer> colunaQuantidade;
    @FXML
    TableColumn<DespesasTable,Float> colunaValor;
    @FXML
    TableColumn<DespesasTable,String> colunaUltimaData;
    @FXML
    private Button btnLogout;
    @FXML
    private PieChart graficoDespesas;
    private static final double ESPACO_DE_TRANSICAO = 10;
    private boolean isMenuOpen = false;
    @FXML
    public void initialize() {
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
        if (dashBoardbnt != null) {
            dashBoardbnt.setOnAction(event -> {
                try {
                    launcherPrincipal.changeView("/views/viewDashbord.fxml");
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
        Usuario user = dadosGlobais.getUser();
        System.out.println("Nome do usuario:" + user.getUsername());
        for(Meta m : user.pegarTodasAsMetas().getMetasList())
        {
            System.out.println(user.pegarTodasAsMetas().getMetasList());
            System.out.println("NOME DA META:" + m.getNome());
            System.out.println("DESPESAS DA META:" + user.pegarTodasDespesas());
            for(Despesa d : user.pegarTodasDespesas())
            {
                System.out.println("NOME  DA DESPESA :" + d.getNome());
                System.out.println("CATEGORIA DA DESPESA :" + d.getCategoria());
            }
        }
        //user.criarNovaMeta("teste",2500,"descrição",new Date(2025 -1900,10,13));
        //user.criarNovaDespesa("Teste",50, Categorias.TRANSPORTE, UUID.fromString("8674ee5c-28c9-491a-b459-f893b7e1b752"));
        colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        colunaUltimaData.setCellValueFactory(new PropertyValueFactory<>("data"));
        ObservableList<DespesasTable> lista = tabelaDespesas.getItems();
        ObservableList<PieChart.Data> dados = FXCollections.observableArrayList();
        lista.clear();

        for(DespesasTable dt : user.pegarQuantidadeDespesaCategoria())
        {
            lista.add(dt);
        }
        for (DespesasTable d : lista) {
            PieChart.Data fatia = new PieChart.Data(d.getCategoria(), d.getValor());
            // Aqui você liga o texto que quer mostrar no gráfico
            fatia.nameProperty().set(d.getCategoria() + " (" + d.getQuantidade() + "x)");
            dados.add(fatia);
        }
        this.tabelaDespesas.setItems(lista);
        this.graficoDespesas.setData(dados);
        this.graficoDespesas.setTitle("Despesas por categoria:");
        this.graficoDespesas.setLegendVisible(true);
        this.graficoDespesas.setLabelsVisible(true);
        float gastoTotal = user.somarTodosGastos(graficoDespesas.getData());
        this.saldoText.setText("R$: " + String.valueOf(gastoTotal));

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

