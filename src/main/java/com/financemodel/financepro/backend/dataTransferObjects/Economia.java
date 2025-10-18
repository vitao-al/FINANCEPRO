package com.financemodel.financepro.backend.dataTransferObjects;

import java.util.Date;
import java.util.UUID;

public class Economia implements TransacoesI
{
    String nome;
    float valor;
    Date data;
    String tipo;
    String categoria;
    UUID muid;
    public Economia()
    {

    }
    /**
     * cria um novo objeto Economia baseado nos seguintes parametros:
     * @param nome nome da economia
     * @param valor valor em float da economia
     * @param data data em Date(java.util.Date) da economia
     * @param tipo tipo da despesa(string) futuramente em enum
     * @param categoria categoria da despesa(string) futuramente em enum
     * @param muid o id da meta que a economia é pertencente
     */
    public Economia(String nome, float valor, Date data, String tipo, String categoria, UUID muid)
    {
        this.nome = nome;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
        this.categoria = categoria;
        this.muid = muid;
    }


    @Override
    public String getNome() {
        return nome;
    }

    @Override
    public float getValor() {
        return valor;
    }

    @Override
    public Date getData() {
        return data;
    }

    @Override
    public String getTipo() {
        return tipo;
    }

    @Override
    public String getCategoria() {
        return categoria;
    }

    public UUID getMuid() {
        return muid;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setMuid(UUID muid) {
        this.muid = muid;
    }
}
