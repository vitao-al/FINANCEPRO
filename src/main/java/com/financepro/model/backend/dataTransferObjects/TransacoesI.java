package com.financepro.model.backend.dataTransferObjects;

import java.util.Date;
import java.util.UUID;

public interface TransacoesI 
{
    String nome = "";
    float valor = 0;
    Date data = new Date();
    String tipo = "";
    String categoria = "";
    UUID muid = null;

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
        return muid;
    }

    default void setMuid(UUID muid) {

    }
}
