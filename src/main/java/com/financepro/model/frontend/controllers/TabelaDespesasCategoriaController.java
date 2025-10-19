package com.financepro.model.frontend.controllers;
import com.financepro.model.backend.dataTransferObjects.Despesa;
import com.financepro.model.backend.dataTransferObjects.DespesasTable;
import com.financepro.model.backend.dataTransferObjects.Usuario;
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
        //user.criarNovaMeta("teste",2500,"descrição",new Date(2025 -1900,10,13));
        //user.criarNovaDespesa("Teste",250, Categorias.ALIMENTACAO, UUID.fromString("8674ee5c-28c9-491a-b459-f893b7e1b752"));
        colunaCategoria.setCellValueFactory(new PropertyValueFactory<>("Categoria"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("Quantidade"));
        colunaValor.setCellValueFactory(new PropertyValueFactory<>("Valor"));
        colunaUltimaData.setCellValueFactory(new PropertyValueFactory<>("Data"));
        ObservableList<DespesasTable> lista = tabelaDespesas.getItems();

        lista.clear();
        for(Despesa d : user.pegarTodasDespesas())
        {
            System.out.println("NOME DA DESPESA:" + d.getNome());
            DespesasTable dt = new DespesasTable();
            dt.setDespesaTable(d,user.contarDespesasCategoria(d.getCategoria()));
            lista.add(dt);

        }
        this.tabelaDespesas.setItems(lista);


    }
}

