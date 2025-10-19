package com.financepro.model.backend.dataTransferObjects;

import com.financepro.model.backend.model.Categorias;
import com.financepro.model.backend.model.TipoTransacoes;

import java.util.Date;
import java.util.UUID;

public interface TransacoesI 
{
    String nome = "";
    float valor = 0;
    Date data = new Date();
    TipoTransacoes tipo = null;
    Categorias categoria = null;
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

    default TipoTransacoes getTipo() {
        return tipo;
    }

    default void setTipo(TipoTransacoes tipo) {

    }

    default Categorias getCategoria() {
        return categoria;
    }

    default void setCategoria(Categorias categoria) {

    }

    default UUID getMuid() {
        return muid;
    }

    default void setMuid(UUID muid) {

    }
}
