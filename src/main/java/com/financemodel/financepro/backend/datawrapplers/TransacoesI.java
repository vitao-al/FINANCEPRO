package com.financemodel.financepro.backend.datawrapplers;

import java.util.Date;
import java.util.UUID;

public interface TransacoesI 
{
    String nome = "";
    float valor = 0;
    Date data = new Date();
    String tipo = "";
    String categoria = "";
    UUID tuid = null;

    default String getNome() {
        return nome;
    }

    default void setNome(String nome) {

    }

    default float getValor() {
        return valor;
    }

    default void setValor(float valor) {

    }

    default Date getData() {
        return data;
    }

    default void setData(Date data) {

    }

    default String getTipo() {
        return tipo;
    }

    default void setTipo(String tipo) {

    }

    default String getCategoria() {
        return categoria;
    }

    default void setCategoria(String categoria) {

    }

    default UUID getMuid() {
        return tuid;
    }

    default void setTuid(UUID tuid) {

    }
}
