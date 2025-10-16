package com.financemodel.financepro.backend.datawrapplers;

import java.util.Date;
import java.util.UUID;

public class Despesa implements TransacoesI
{
    String nome;
    float valor;
    Date data;
    String tipo;
    String categoria;
    UUID tuid;
    public Despesa()
    {

    }

    /**
     * cria um novo objeto despesa baseado nos seguintes parametros:
     * @param nome nome da despesa
     * @param valor valor em float da despesa
     * @param data data em Date(java.util.Date) da despesa
     * @param tipo tipo da despesa(string) futuramente em enum
     * @param categoria categoria da despesa(string) futuramente em enum
     * @param muid o id da meta que a despesa é pertencente
     */
    public Despesa(String nome, float valor, Date data,String tipo,String categoria,UUID muid)
    {
        this.nome = nome;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
        this.categoria = categoria;
        this.tuid = muid;
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

    public void setTuid(UUID tuid) {
        this.tuid = tuid;
    }

}
