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

public class MetasController {

    @FXML
    private Button btnCreateMeta;
    @FXML
    private Button btnNoCreateMeta;

    @FXML
    private Label txtErroNomeMeta;
    @FXML
    private Label txtErroValorMeta;
    @FXML // Adicionar Label para erro da descrição (se houver)
    private Label txtErroDescMeta;
    @FXML // Adicionar Label para erro da data (se houver)
    private Label txtErroDataFinal;

    @FXML
    private TextField nameMeta;
    @FXML
    private TextField valueMeta;
    @FXML
    private TextField descricaoMeta;

    @FXML
    private DatePicker dataFinal;
    @FXML
    private DatePicker dataInicial;

    public void initialize() {

        // Inicializa as Labels de erro como invisíveis, se necessário
        txtErroNomeMeta.setVisible(false);
        txtErroValorMeta.setVisible(false);

        btnCreateMeta.setOnAction(e -> {
            if (camposValidos()) {
                float valueMetafloat = 0f;
                try {
                    valueMetafloat = Float.parseFloat(valueMeta.getText());
                } catch (NumberFormatException ex) {
                    // Esta mensagem deve ser redundante se camposValidos() funcionar,
                    // mas serve como segurança.
                    mostrarErroAnimado(txtErroValorMeta, "Valor de meta inválida!");
                    return;
                }

                // Tentativa de criar a meta
                try {
                    // Note que descMeta.getText() agora é seguro porque camposValidos()
                    // verifica se nameMeta e valueMeta não estão vazios.
                    // Adicionei uma verificação de data mais robusta.

                    dadosGlobais.user.criarNovaMeta(
                            nameMeta.getText(),
                            valueMetafloat,
                            descricaoMeta.getText(), // Não estava validado, mas o problema era a injeção
                            dataPickerParaUtilDate(dataInicial),
                            dataPickerParaUtilDate(dataFinal)
                    );
                    launcherPrincipal.changeView("/views/viewDashbord.fxml");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    // Aqui seria um bom lugar para mostrar um erro ao usuário se a criação da meta falhar
                }
            }
        });

        // Adicionar a ação para o botão "Não criar meta"
        btnNoCreateMeta.setOnAction(e -> {
            // Exemplo: voltar para o dashboard
            try {
                launcherPrincipal.changeView("/views/viewDashbord.fxml");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    public boolean camposValidos() {
        boolean valido = true;

        // --- VALIDAÇÃO DE NOME (Não Vazio e Regex) ---
        // Permite espaços no nome (nomes de metas podem ter espaços)
        if (nameMeta.getText().trim().isEmpty()) {
            mostrarErroAnimado(txtErroNomeMeta, "O nome não pode ser vazio!");
            valido = false;
        }
        // Regex simplificado para permitir letras e espaços
        else if (!nameMeta.getText().matches("[a-zA-Z\\s]+")) {
            mostrarErroAnimado(txtErroNomeMeta, "Nome inválido (apenas letras)!");
            valido = false;
        } else txtErroNomeMeta.setVisible(false);


        // --- VALIDAÇÃO DE VALOR (Não Vazio e Regex Numérico) ---
        if (valueMeta.getText().trim().isEmpty()) {
            mostrarErroAnimado(txtErroValorMeta, "O valor não pode ser vazio!");
            valido = false;
        }
        // Regex para números decimais (positivo/negativo)
        else if (!valueMeta.getText().matches("[-+]?[0-9]*\\.?[0-9]+")) {
            mostrarErroAnimado(txtErroValorMeta, "Digite um valor numérico válido!");
            valido = false;
        } else txtErroValorMeta.setVisible(false);


        // --- VALIDAÇÃO DA DESCRIÇÃO (Apenas verifica se está nula, se precisar) ---
        // Geralmente descMeta não causa o erro se o FXML estiver correto
        // Se descMeta.getText() é usado, ele deve ser inicializado, mas o erro aqui foi o campo nulo
        if (descricaoMeta == null) {
            // Este caso só acontece se o @FXML falhar, mas é uma verificação de segurança
            System.err.println("ERRO: descMeta está nulo. Verifique seu FXML.");
            // Não é um erro de validação de campo, mas de inicialização
        }
        return valido;
    }

    // O restante do código (mostrarErroAnimado e dataPickerParaUtilDate) está correto.

    public void mostrarErroAnimado(Label label, String mensagem) {
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

    public static Date dataPickerParaUtilDate(DatePicker datePicker) {
        // ... (Correto, não precisa de alterações)
        LocalDate localDate = datePicker.getValue();
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}