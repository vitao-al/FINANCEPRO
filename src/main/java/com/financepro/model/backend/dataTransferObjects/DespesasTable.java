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
        return categoria.toString();
    }

    public SimpleStringProperty categoriaProperty() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = new SimpleStringProperty(categoria);
    }

    public float getValor() {
        return this.valor.get();
    }

    public SimpleFloatProperty valorProperty() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = new SimpleFloatProperty(valor);
    }

    public String getData() {
        return data.get();
    }

    public SimpleStringProperty dataProperty() {
        return data;
    }

    public void setData(String data) {
        this.data = new SimpleStringProperty(data);
    }

    public int getQuantidade() {
        return this.quantidade.get();
    }

    public SimpleIntegerProperty quantidadeProperty() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = new SimpleIntegerProperty(quantidade);
    }

    public DespesasTable()
    {
    }
    public void setDespesaTable(Despesa d,int quantidadeCategoria)
    {
        this.categoria = new SimpleStringProperty(d.getCategoria().toString());
        this.valor = new SimpleFloatProperty(0);
        this.data = new SimpleStringProperty(d.getData().toString());
        this.quantidade = new SimpleIntegerProperty(0);
    }

}
