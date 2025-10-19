package com.financepro.model.frontend.controllers;
import com.financepro.model.backend.dataTransferObjects.*;
import com.financepro.model.backend.model.Categorias;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.util.Date;
import java.util.UUID;


public class TabelaDespesasCategoriaController {
    @FXML
    private Label saldoText;

    @FXML
    private Label totalValueText;

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
    private PieChart graficoDespesas;

    @FXML
    public void initialize() {

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
}

