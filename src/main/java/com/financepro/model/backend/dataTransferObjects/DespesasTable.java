package com.financepro.model.backend.dataTransferObjects;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DespesasTable{
    private SimpleStringProperty categoria;
    private SimpleFloatProperty valor;
    private SimpleStringProperty data;// atributo extra que você não quer mostrar
    private SimpleIntegerProperty quantidade;

    public String getCategoria() {
        return categoria.get();
    }

    public SimpleStringProperty categoriaProperty() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria.set(categoria);
    }

    public float getValor() {
        return valor.get();
    }

    public SimpleFloatProperty valorProperty() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor.set(valor);
    }

    public String getData() {
        return data.get();
    }

    public SimpleStringProperty dataProperty() {
        return data;
    }

    public void setData(String data) {
        this.data.set(data);
    }

    public int getQuantidade() {
        return quantidade.get();
    }

    public SimpleIntegerProperty quantidadeProperty() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade.set(quantidade);
    }

    public DespesasTable()
    {
    }
    public void setDespesaTable(Despesa d,int quantidadeCategoria)
    {
        this.categoria = new SimpleStringProperty(d.getCategoria().toString());
        this.valor = new SimpleFloatProperty(d.getValor());
        this.data = new SimpleStringProperty(d.getData().toString());
        this.quantidade = new SimpleIntegerProperty(quantidadeCategoria);
    }

}
