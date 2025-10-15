package com.financemodel.financepro.backend.datawrapplers;

import java.util.Date;
import java.util.UUID;

public class Economia
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
    public Economia(String nome, float valor, Date data, String tipo, String categoria, UUID muid)
    {
        this.nome = nome;
        this.valor = valor;
        this.data = data;
        this.tipo = tipo;
        this.categoria = categoria;
        this.muid = muid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public UUID getMuid() {
        return muid;
    }

    public void setMuid(UUID muid) {
        this.muid = muid;
    }
}
