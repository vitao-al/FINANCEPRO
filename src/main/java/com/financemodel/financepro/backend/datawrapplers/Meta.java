package com.financemodel.financepro.backend.datawrapplers;

import java.time.Duration;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class Meta
{
    String nome;
    Float valor;
    Date dataInicial;
    Date dataFinal;
    UUID uuid;
    UUID muid;

    public Meta()
    {

    }
    public Meta(String nome,Float valor,Date dataInicial,Date dataFinal ,UUID uuid, UUID muid)
    {
        this.nome = nome;
        this.valor = valor;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.uuid = uuid;
        this.muid = muid;
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
