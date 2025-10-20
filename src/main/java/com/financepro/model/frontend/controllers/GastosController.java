package com.financepro.model.frontend.controllers;

import com.financepro.model.frontend.launcher.launcherPrincipal;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

// A classe 'dadosGlobais' não foi fornecida, mas o código presume sua existência.

public class GastosController {

    @FXML
    private Button btnCreateMeta;
    @FXML
    private Button btnNoCreateMeta;

    // --- LABELS DE ERRO ---
    @FXML
    private Label txtErroNomeMeta;
    @FXML
    private Label txtErroValorMeta;
    @FXML
    private Label txtErroDescMeta; // Para a descrição
    @FXML
    private Label txtErroDataFinal; // Para a data

    // --- CAMPOS DE ENTRADA ---
    @FXML
    private TextField nameMeta;
    @FXML
    private TextField valueMeta;
    @FXML
    private TextField descricaoMeta; // Nome CORRETO da variável

    // --- DATAPICKERS ---
    @FXML
    private DatePicker dataFinal;
    @FXML
    private DatePicker dataInicial;

    public void initialize() {
        // Inicializa as Labels de erro como invisíveis (com verificação de segurança)
        // Isso previne o NullPointerException se uma Label não for injetada.
        if (txtErroNomeMeta != null) txtErroNomeMeta.setVisible(false);
        if (txtErroValorMeta != null) txtErroValorMeta.setVisible(false);
        if (txtErroDescMeta != null) txtErroDescMeta.setVisible(false);
        if (txtErroDataFinal != null) txtErroDataFinal.setVisible(false);


        btnCreateMeta.setOnAction(e -> {
            if (camposValidos()) {
                float valueMetafloat = 0f;
                // A conversão float só é tentada se camposValidos() for true
                try {
                    valueMetafloat = Float.parseFloat(valueMeta.getText());
                } catch (NumberFormatException ex) {
                    // Esta exceção é improvável se o regex for bom, mas a mantemos.
                    mostrarErroAnimado(txtErroValorMeta, "Erro de conversão de valor.");
                    return;
                }

                // Tentativa de criar a meta
                try {
                    dadosGlobais.user.criarNovaMeta(
                            nameMeta.getText(),
                            valueMetafloat,
                            descricaoMeta.getText(), // Agora usando a variável CORRETA
                            dataPickerParaUtilDate(dataInicial),
                            dataPickerParaUtilDate(dataFinal)
                    );
                    launcherPrincipal.changeView("/views/viewDashbord.fxml");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // Lidar com falhas na criação de meta (ex: erro de DB)
                }
            }
        });

        // Adicionar a ação para o botão "Não criar meta"
        btnNoCreateMeta.setOnAction(e -> {
            try {
                launcherPrincipal.changeView("/views/viewDashbord.fxml");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public boolean camposValidos() {
        boolean valido = true;

        // --- VALIDAÇÃO DE NOME ---
        if (nameMeta.getText().trim().isEmpty()) {
            mostrarErroAnimado(txtErroNomeMeta, "O nome não pode ser vazio!");
            valido = false;
        }
        else if (!nameMeta.getText().matches("[a-zA-Z\\s]+")) {
            mostrarErroAnimado(txtErroNomeMeta, "Nome inválido (apenas letras e espaços)!");
            valido = false;
        } else if (txtErroNomeMeta != null) txtErroNomeMeta.setVisible(false); // Segurança


        // --- VALIDAÇÃO DE VALOR ---
        if (valueMeta.getText().trim().isEmpty()) {
            mostrarErroAnimado(txtErroValorMeta, "O valor não pode ser vazio!");
            valido = false;
        }
        else if (!valueMeta.getText().matches("[-+]?[0-9]*\\.?[0-9]+")) {
            mostrarErroAnimado(txtErroValorMeta, "Digite um valor numérico válido!");
            valido = false;
        } else if (txtErroValorMeta != null) txtErroValorMeta.setVisible(false); // Segurança


        // --- VALIDAÇÃO DE DESCRIÇÃO ---
        if (descricaoMeta == null) {
            // Se esta mensagem aparecer, é um ERRO GRAVE no FXML.
            System.err.println("ERRO CRÍTICO DE INJEÇÃO: descricaoMeta está nulo. Verifique o fx:id no FXML.");
        }
        if (descricaoMeta != null && descricaoMeta.getText().trim().isEmpty()) {
            // Você pode ou não exigir descrição. Se sim:
            // mostrarErroAnimado(txtErroDescMeta, "A descrição não pode ser vazia!");
            // valido = false;
        }


        // --- VALIDAÇÃO DE DATAS (COMPLETA) ---
        if (txtErroDataFinal != null) { // Somente tenta usar se a Label de erro foi injetada
            if (dataInicial.getValue() == null) {
                mostrarErroAnimado(txtErroDataFinal, "Selecione a Data Inicial!");
                valido = false;
            } else if (dataFinal.getValue() == null) {
                mostrarErroAnimado(txtErroDataFinal, "Selecione a Data Final!");
                valido = false;
            } else if (dataFinal.getValue().isBefore(dataInicial.getValue())) {
                mostrarErroAnimado(txtErroDataFinal, "Data Final não pode ser anterior à Data Inicial!");
                valido = false;
            } else {
                txtErroDataFinal.setVisible(false);
            }
        }

        return valido;
    }

    public void mostrarErroAnimado(Label label, String mensagem) {
        if (label == null) {
            // Evita NullPointerException se a Label não for injetada pelo FXML
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

    // O método foi movido para dentro da classe (Correção de Sintaxe #1)
    public static Date dataPickerParaUtilDate(DatePicker datePicker) {
        LocalDate localDate = datePicker.getValue();
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}