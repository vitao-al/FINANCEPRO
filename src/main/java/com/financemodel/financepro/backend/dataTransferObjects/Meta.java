package com.financemodel.financepro.backend.dataTransferObjects;

import com.financemodel.financepro.backend.databaseDataObjects.TransacoesHandlerDB;

import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Meta
{
    TransacoesHandlerDB tdb = new TransacoesHandlerDB("Database/Transacoes.db");
    String nome;
    Float valor;
    String descricao;
    Date dataInicial;
    Date dataFinal;
    Float saldoAtual;
    UUID uuid;
    UUID muid;
    ArrayList<Economia> economiasMeta;
    ArrayList<Despesa> despesasMeta;
    public Meta() throws SQLException {

    }
    public Meta(String nome,Float valor,String descricao,Date dataInicial,Date dataFinal ,UUID uuid, UUID muid) throws SQLException {
        this.nome = nome;
        this.valor = valor;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.uuid = uuid;
        this.muid = muid;
        this.saldoAtual = (float) 0;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Float getValor() {
        return valor;
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    public Date getDataInicial() {
        return dataInicial;
    }

    public void setDataInicial(Date dataInicial) {
        this.dataInicial = dataInicial;
    }

    public Date getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(Date dataFinal) {
        this.dataFinal = dataFinal;
    }

    public Float getSaldoAtual() {
        return saldoAtual;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setSaldoAtual(Float saldoAtual) {
        this.saldoAtual = saldoAtual;
    }

    public ArrayList<Economia> getEconomiasMeta() {
        return economiasMeta;
    }

    public void setEconomiasMeta(ArrayList<Economia> economiasMeta) {
        this.economiasMeta = economiasMeta;
    }

    public ArrayList<Despesa> getDespesasMeta() {
        return despesasMeta;
    }

    public void setDespesasMeta(ArrayList<Despesa> despesasMeta) {
        this.despesasMeta = despesasMeta;
    }

    public ArrayList<Economia> pegarTodasEconomias()
    {
        this.setEconomiasMeta(this.tdb.getEconomiasOfTransacoes(this.muid));
        return this.getEconomiasMeta();
    }
    public ArrayList<Despesa> pegarTodasDespesas()
    {
        this.setDespesasMeta(this.tdb.getDespesasOfTransacoes(this.muid));
        return this.getDespesasMeta();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getMuid() {
        return muid;
    }

    public void setMuid(UUID muid)
    {
        this.muid = muid;
    }
    public Long getMetaDaysCount()
    {
        TimeUnit time = TimeUnit.DAYS;
        return time.convert(Duration.ofDays(this.getDataFinal().getTime() - this.getDataInicial().getTime()));
    }

}
