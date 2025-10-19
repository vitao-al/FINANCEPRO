package com.financepro.model.backend.dataTransferObjects;

import com.financepro.model.backend.databaseDataObjects.TransacoesHandlerDB;
import com.financepro.model.backend.databaseDataObjects.MetasHandlerDB;
import com.financepro.model.backend.model.Categorias;
import com.financepro.model.backend.model.TipoTransacoes;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class Transacoes
{
    ArrayList<Economia> economiasList = new ArrayList<>();
    ArrayList<Despesa> despesasList = new ArrayList<>();
    TransacoesHandlerDB tdb = new TransacoesHandlerDB("Database/Transacoes.db");
    MetasHandlerDB mdb = new MetasHandlerDB("Database/Metas.db");

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
        Meta m = mdb.getMeta(muid);
        this.tdb.insertNewTransacao(
                null,
                valor,
                data,
                TipoTransacoes.ECONOMIA,
                null,
                muid
        );
        this.mdb.updateMetaInfo(muid,m.saldoAtual + valor);
    }

    /**
     * Insere uma nova Despesa no banco de dados transações baseado nos paramentros:
     * @param nome nome da despesa
     * @param valor valor em float da despesa
     * @param data data em java.util.Date da despesa
     * @param categoria categoria em string da despesa
     * @param muid id da meta a qual a despesa pertence
     */
    public void inserirNovaDespesa(String nome ,float valor, Date data,Categorias categoria, UUID muid)
    {
        Meta m = mdb.getMeta(muid);
        this.tdb.insertNewTransacao(
                nome,
                valor,
                data,
                TipoTransacoes.DESPESA,
                categoria,
                muid
        );
        this.mdb.updateMetaInfo(muid,m.saldoAtual - valor);
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
    public int getNumDespesasByCategoria(Categorias c)
    {
        int i = 0;
        for(Despesa d : getDespesasList())
        {
            if(d.getCategoria().equals(c.toString()))
            {
                i += 1;
            }
        }
        return i;
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
    public Meta getMeta(UUID muid)
    {
        return this.mdb.getMeta(muid);
    }
    public MetasList getAllMetas(UUID uuid)
    {
        return this.mdb.getMetas(uuid);
    }
    public void criarNovaMeta(String nome,float valor,String descricao,Date dataInicial,Date dataFinal,UUID uuid)
    {
        this.mdb.insertNewMeta(nome,valor,descricao,dataInicial,dataFinal,uuid);
    }
}
