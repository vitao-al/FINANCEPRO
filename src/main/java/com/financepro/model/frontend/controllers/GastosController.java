package com.financepro.model.frontend.controllers;

import com.financepro.model.backend.dataTransferObjects.Meta;
import com.financepro.model.backend.dataTransferObjects.Usuario;
import com.financepro.model.frontend.launcher.launcherPrincipal;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import com.financepro.model.backend.model.Categorias;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

// Assumindo a existência de 'dadosGlobais'

public class GastosController{

    @FXML
    private TextField nomeGasto;
    @FXML
    private TextField valueGasto;
    @FXML
    Label txtErroNomeGasto;
    @FXML
    Label txtErroValorGasto;
    @FXML
    Label txtErroCategoriaOuMeta;

    @FXML
    private Button btnBack;
    @FXML
    private Button btnCreateGasto; // O ELEMENTO QUE ESTAVA NULO

    @FXML
    private ChoiceBox<String> listaCategorias;
    @FXML
    private ChoiceBox<String> listaMetas;


    public void initialize() {
        // --- 2. CONFIGURAÇÃO DAS CHOICEBOXES ---

        // 2.1. Configuração da listaCategorias (usando strings que batem com o enum)
        if (listaCategorias != null) {
            ObservableList<String> categorias = FXCollections.observableArrayList(
                    // Estes nomes devem ser IDÊNTICOS aos do enum Categorias
                    "TRANSPORTE", "ALIMENTACAO", "MORADIA", "LAZER",
                    "ENTRETENIMENTO", "EDUCACAO", "EMPRESTIMOS", "FINANCIAMENTO",
                    "MULTAS", "TAXAS", "MENSALIDADE", "VEICULOS",
                    "SEGURO", "VIAGEM", "ROUPAS", "ACESSORIOS",
                    "INVESTIMENTO", "HOSPEDAGEM", "DOACAO", "IMPOSTO", "OUTROS"
            );
            listaCategorias.setItems(categorias);
            listaCategorias.getSelectionModel().selectFirst();
        }

        // 2.2. Configuração da listaMetas
        if (listaMetas != null) {
            // Checagem extra de nulo para dadosGlobais.user
            Usuario currentUser = dadosGlobais.user;
            List<Meta> listaDeObjetosMeta = (currentUser != null) ?
                    currentUser.pegarTodasAsMetas().getMetasList() :
                    new java.util.ArrayList<>();

            List<String> nomesList = listaDeObjetosMeta.stream()
                    .map(Meta::getNome)
                    .collect(Collectors.toList());

            ObservableList<String> nomesDasMetas = FXCollections.observableList(nomesList);

            nomesDasMetas.add(0, "Nenhuma Meta Associada");
            listaMetas.setItems(nomesDasMetas);
            listaMetas.getSelectionModel().selectFirst();
        }


        // --- 3. CONFIGURAÇÃO DO USUÁRIO E EVENTOS ---

        // CORREÇÃO: Adiciona checagem nula para btnCreateGasto para evitar NullPointerException
        if (btnCreateGasto != null) {
            btnCreateGasto.setOnAction(event -> {
                // 1. Valida campos de texto e seleção (incluindo a Label de erro agora funcional)
                if (camposValidos()){
                    float valueGastofloat = 0f;
                    String categoriaSelecionada = listaCategorias.getValue();

                    // Checagem de nulo para dadosGlobais.user antes de usar
                    if (dadosGlobais.user == null) {
                        System.err.println("Erro: Usuário global não está logado ou é nulo.");
                        mostrarErroAnimado(txtErroCategoriaOuMeta, "Erro: Usuário não logado.");
                        return; // Sai do lambda se o usuário for nulo
                    }

                    try {
                        // 2. Tenta converter a string da categoria para o tipo enum
                        Categorias categoriaEnum = Categorias.valueOf(categoriaSelecionada.trim().toUpperCase());

                        // 3. Converte o valor do gasto (já validado)
                        valueGastofloat = Float.parseFloat(valueGasto.getText());

                        // 4. Cria a despesa usando o enum
                        dadosGlobais.user.criarNovaDespesa(nomeGasto.getText(),valueGastofloat,categoriaEnum,new Date(),dadosGlobais.user.getUuid());

                        launcherPrincipal.changeView("/views/viewDashbord.fxml");

                    } catch (IllegalArgumentException e) {
                        mostrarErroAnimado(txtErroCategoriaOuMeta, "Erro interno: Categoria selecionada inválida.");
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                        mostrarErroAnimado(txtErroCategoriaOuMeta, "Erro ao criar despesa: " + e.getMessage());
                    }
                }
            });
        } else {
            // Log de erro útil para o desenvolvedor
            System.err.println("Erro FXML: btnCreateGasto não foi injetado (NULL). Verifique o fx:id.");
        }

        // CORREÇÃO: Adiciona checagem nula para btnBack
        if (btnBack != null) {
            btnBack.setOnAction(event -> {
                try {
                    launcherPrincipal.changeView("/views/viewDashbord.fxml");
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        } else {
            System.err.println("Erro FXML: btnBack não foi injetado (NULL). Verifique o fx:id.");
        }
    }

    public boolean camposValidos() {
        boolean valido = true;
        // Assume que listaCategorias foi verificada em initialize
        String categoriaselecionada = (listaCategorias != null && listaCategorias.getValue() != null) ? listaCategorias.getValue() : null;

        // --- VALIDAÇÃO DE NOME ---
        if (nomeGasto == null) { System.err.println("ERRO CRÍTICO: nomeGasto é nulo. Verifique o FXML."); return false; }
        if (nomeGasto.getText().trim().isEmpty()) {
            mostrarErroAnimado(txtErroNomeGasto, "O nome não pode ser vazio!");
            valido = false;
        }
        else if (!nomeGasto.getText().matches("[a-zA-Z\\s]+")) {
            mostrarErroAnimado(txtErroNomeGasto, "Nome inválido (apenas letras e espaços)!");
            valido = false;
        } else if (txtErroNomeGasto != null) txtErroNomeGasto.setVisible(false);


        // --- VALIDAÇÃO DE VALOR ---
        if (valueGasto == null) { System.err.println("ERRO CRÍTICO: valueGasto é nulo. Verifique o FXML."); return false; }
        if (valueGasto.getText().trim().isEmpty()) {
            mostrarErroAnimado(txtErroValorGasto, "O valor não pode ser vazio!");
            valido = false;
        }
        else if (!valueGasto.getText().matches("[-+]?[0-9]*\\.?[0-9]+")) {
            mostrarErroAnimado(txtErroValorGasto, "Digite um valor numérico válido!");
            valido = false;
        } else if (txtErroValorGasto != null) txtErroValorGasto.setVisible(false);


        // --- VALIDAÇÃO DE CATEGORIA E META (Unificado) ---
        if (listaCategorias == null) { System.err.println("ERRO CRÍTICO: listaCategorias é nulo."); return false; }

        if (categoriaselecionada == null || categoriaselecionada.isEmpty()) {
            mostrarErroAnimado(txtErroCategoriaOuMeta, "A categória não pode ser vazia!");
            valido = false;
        }
        // Verifica se listaMetas não é nulo antes de chamar getValue()
        else if (listaMetas != null && listaMetas.getValue() == null) {
            mostrarErroAnimado(txtErroCategoriaOuMeta, "Selecione uma meta ou 'Nenhuma Meta Associada'");
            valido = false;
        } else {
            if (txtErroCategoriaOuMeta != null) {
                txtErroCategoriaOuMeta.setVisible(false);
            }
        }

        return valido;
    }

    public void mostrarErroAnimado(Label label, String mensagem) {
        if (label == null) {
            System.err.println("A Label de erro está nula. Mensagem não exibida: " + mensagem);
            return;
        }

        label.setText(mensagem);
        label.setVisible(true);

        TranslateTransition tt = new TranslateTransition(Duration.millis(50), label);
        tt.setFromX(0);
        tt.setByX(10);
        tt.setCycleCount(6);
        tt.setAutoReverse(true);
        tt.play();
    }
}