package com.financepro.model.frontend.controllers;

import com.financepro.model.backend.dataTransferObjects.Economia;
import com.financepro.model.backend.dataTransferObjects.Meta;
import com.financepro.model.backend.dataTransferObjects.Usuario;
import com.financepro.model.frontend.launcher.launcherPrincipal;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class EconomiaController {

    @FXML private Button btnCreateEconomia;
    @FXML private Button btnBack;
    @FXML private Label txtErroMeta;
    @FXML private Label txtErroValorEconomia;

    // MUDANÇA CRÍTICA: Agora armazena objetos Meta!
    @FXML private ComboBox<Meta> listaMetas;

    @FXML private TextField valueEconomia;

    // MAPA REMOVIDO: Não precisamos mais dele.
    // private Map<String, UUID> metaMap = new HashMap<>();

    // Objeto fictício para a opção "Nenhuma Meta Associada"
    private final Meta META_NULA = new Meta("Nenhuma Meta Associada", 0f, null, null, null, null, UUID.randomUUID()) {
        @Override
        public ArrayList<Economia> pegarTodasEconomias() {
            return new ArrayList<>(); // Sobrescreve para retornar lista vazia sem acessar o banco
        }
    };
    public EconomiaController() throws SQLException {
    }


    public void initialize() {
        if (txtErroValorEconomia != null) txtErroValorEconomia.setVisible(false);
        if (txtErroMeta != null) txtErroMeta.setVisible(false);

        if (listaMetas != null) {
            popularListaMetas();
        }

        if (btnCreateEconomia != null) {
            btnCreateEconomia.setOnAction(e -> {
                if (!camposValidos()) {
                    return;
                }

                if (dadosGlobais.user == null) {
                    mostrarErroAnimado(txtErroMeta, "Erro: Usuário não logado na aplicação.");
                    return;
                }

                float valueEconomiafloat;
                UUID muid;

                Meta metaSelecionada = listaMetas.getValue(); // Pegando o objeto Meta selecionado
                if (metaSelecionada != null && metaSelecionada != META_NULA && metaSelecionada.getMuid() != null) {
                    muid = metaSelecionada.getMuid();
                } else {
                    muid = dadosGlobais.user.getUuid(); // Fallback para o UUID do usuário
                    mostrarErroAnimado(txtErroMeta, "Atenção: Usando UUID do usuário como MUID (meta inválida).");
                }
                try {
                    valueEconomiafloat = Float.parseFloat(valueEconomia.getText());
                } catch (NumberFormatException ex) {
                    mostrarErroAnimado(txtErroValorEconomia, "Erro: Digite um valor numérico válido.");
                    return;
                }

                // --- 2. CAPTURA DO MUID (LÓGICA SIMPLIFICADA) ---
                if (metaSelecionada != null && metaSelecionada != META_NULA) {
                    // PEGAMOS O MUID DIRETAMENTE DO OBJETO
                    muid = metaSelecionada.getMuid();
                }

                // --- 3. EXECUÇÃO DA FUNÇÃO CRÍTICA ---
                try {
                    // CHAMADA FINAL: Usando o muid da Meta (ou do Usuário no fallback)
                    dadosGlobais.user.criarNovaEconomia(valueEconomiafloat, muid);
                    launcherPrincipal.changeView("/views/viewDashbord.fxml");

                } catch (Exception ex) {
                    mostrarErroAnimado(txtErroMeta, "Falha na criação da economia: " + ex.getMessage());
                    ex.printStackTrace();
                }
            });
        }

        // CORREÇÃO DE SINTAXE E ADIÇÃO DA AÇÃO DO BOTÃO BACK
        if (btnBack != null) {
            btnBack.setOnAction(e -> {
                try {
                    launcherPrincipal.changeView("/views/viewDashbord.fxml");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    /**
     * Obtém as metas do usuário e popula o ComboBox diretamente com objetos Meta.
     */
    private void popularListaMetas() {
        Usuario currentUser = dadosGlobais.user;

        if (listaMetas == null) return;

        if (currentUser == null) {
            listaMetas.setItems(FXCollections.observableArrayList(META_NULA));
            listaMetas.getSelectionModel().selectFirst();
            return;
        }

        // Obtém a lista de objetos Meta
        List<Meta> listaDeObjetosMeta = currentUser.pegarTodasAsMetas().getMetasList();

        // Adiciona a opção NULA no início da lista
        listaDeObjetosMeta.add(0, META_NULA);

        // Cria a ObservableList a partir da lista de objetos Meta
        ObservableList<Meta> metas = FXCollections.observableList(listaDeObjetosMeta);

        listaMetas.setItems(metas);
        listaMetas.getSelectionModel().selectFirst();
        // ✅ MOSTRA NOME NO DROPDOWN!
        listaMetas.setCellFactory(lv -> new ListCell<Meta>() {
            @Override protected void updateItem(Meta meta, boolean empty) {
                super.updateItem(meta, empty);
                setText(empty || meta == null ? "" : meta.getNome());
            }
        });
    }


    public boolean camposValidos() {
        boolean valido = true;

        if (valueEconomia == null || listaMetas == null) {
            System.err.println("ERRO CRÍTICO: Componentes FXML nulos. Verifique o .fxml");
            return false;
        }

        // 1. Validação do Valor
        if (valueEconomia.getText().trim().isEmpty()) {
            mostrarErroAnimado(txtErroValorEconomia, "O valor não pode ser vazio!");
            valido = false;
        }
        else if (!valueEconomia.getText().matches("[-+]?[0-9]*\\.?[0-9]+")) {
            mostrarErroAnimado(txtErroValorEconomia, "Digite um valor numérico válido!");
            valido = false;
        } else if (txtErroValorEconomia != null) txtErroValorEconomia.setVisible(false);


        // 2. Validação da Meta
        // A validação agora checa se o objeto (Meta) selecionado não é nulo.
        if (listaMetas.getValue() == null) {
            mostrarErroAnimado(txtErroMeta, "Selecione uma meta!");
            valido = false;
        } else if (txtErroMeta != null) {
            txtErroMeta.setVisible(false);
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

    public static Date dataPickerParaUtilDate(DatePicker datePicker) {
        LocalDate localDate = datePicker.getValue();
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}