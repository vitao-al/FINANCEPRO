package com.financemodel.financepro.backend.datawrapplers;

import java.sql.SQLException;
import java.util.ArrayList;

public class MetasList
{
    ArrayList<Meta> metasList = new ArrayList<>();
    public ArrayList<Meta> getMetasList() {
        return metasList;
    }

    public void setMetasList(ArrayList<Meta> metasList) {
        this.metasList = metasList;
    }

    public ArrayList<Meta> getMetasFromUUID() throws SQLException
    {
        return this.getMetasList();
    }
    public void printMetas() throws SQLException {
        for(Meta m : this.getMetasList())
        {
            System.out.println("NOME:" + m.getNome());
            System.out.println("VALOR:" + m.getValor());
            System.out.println("DATA INICIAL META:" + m.getDataInicial().toString());
            System.out.println("DATA FINAL META:" + m.getDataFinal().toString());
            System.out.println("UUID:" + m.getUuid().toString());
            System.out.println("MUID:" + m.getMuid().toString());
        }
    }

}
