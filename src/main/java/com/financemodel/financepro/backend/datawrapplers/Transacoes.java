package com.financemodel.financepro.backend.datawrapplers;

import com.financemodel.financepro.backend.database.TransacoesHandlerDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Transacoes
{
    ArrayList<Economia> economiasList = new ArrayList<>();
    ArrayList<Despesa> despesasList = new ArrayList<>();
    TransacoesHandlerDB tdb = new TransacoesHandlerDB("Database/Transacoes.db");

    public Transacoes() throws SQLException {
    }

    public ArrayList<Economia> getEconomiasList() {
        return economiasList;
    }

    public void setEconomiasList(ArrayList<Economia> economiasList) {
        this.economiasList = economiasList;
    }

    /**
     * Insere uma nova economia no banco de dados transações baseado nos paramentros:
     * @param valor valor da economia(float)
     * @param data a data da economia(java.util.Date)
     * @param muid o muid da meta a qual a economia é pertencente
     */
    public void inserirNovaEconomia(float valor, Date data, UUID muid)
    {
        this.tdb.insertNewTransacao(
                null,
                valor,
                data,
                "ECONOMIA",
                null,
                muid
        );
    }

    /**
     * Insere uma nova Despesa no banco de dados transações baseado nos paramentros:
     * @param nome nome da despesa
     * @param valor valor em float da despesa
     * @param data data em java.util.Date da despesa
     * @param categoria categoria em string da despesa
     * @param muid id da meta a qual a despesa pertence
     */
    public void inserirNovaDespesa(String nome ,float valor, Date data,String categoria, UUID muid)
    {
        this.tdb.insertNewTransacao(
                nome,
                valor,
                data,
                "DESPESA",
                categoria,
                muid
        );
    }
    /**
     * Pega todas as despesas relacionadas a uma meta especifica com base no id da mesma
     * @param muid o id da meta
     * @return Arraylist<Despesas> uma lista de objetos Despesa
     */
    public ArrayList<Despesa> pegarDespesasDB(UUID muid)
    {
        this.setDespesasList(this.tdb.getDespesasOfTransacoes(muid));
        return getDespesasList();
    }

    /**
     * Pega todas as economias relacionadas a uma meta especifica com base no id da mesma
     * @param muid o id da meta
     * @return Arraylist<Economia> uma lista de objetos Economia
     */
    public ArrayList<Economia> pegarEconomiasDB(UUID muid)
    {
        this.setEconomiasList(this.tdb.getEconomiasOfTransacoes(muid));
        return this.getEconomiasList();
    }

    public ArrayList<Despesa> getDespesasList() {
        return despesasList;
    }

    public void setDespesasList(ArrayList<Despesa> despesasList) {
        this.despesasList = despesasList;
    }


    public static void main(String[] args) throws SQLException {
        Transacoes t = new Transacoes();
        //t.inserirNovaEconomia(1200,new Date(2025 - 1900,10,15),UUID.fromString("d1b340a7-f362-464d-856b-883414178521"));
        for(Economia e : t.pegarEconomiasDB(UUID.fromString("d1b340a7-f362-464d-856b-883414178521")))
        {
            System.out.println("NOME DA ECONOMIA:" + e.getNome());
            System.out.println("VALOR DA ECONOMIA:" + e.getValor());
            System.out.println("TIPO:" + e.getTipo());
            System.out.println("CATEGORIA:" + e.getCategoria());
            System.out.println("DATA:" + e.getData());
            System.out.println("MUID:" + e.getMuid());
        }
    }
}
