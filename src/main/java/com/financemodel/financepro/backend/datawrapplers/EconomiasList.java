package com.financemodel.financepro.backend.datawrapplers;

import java.sql.SQLException;
import java.util.ArrayList;

public class EconomiasList
{
    ArrayList<Economia> economiasList = new ArrayList<>();

    public ArrayList<Economia> getEconomiasList() {
        return economiasList;
    }

    public void setEconomiasList(ArrayList<Economia> economiasList) {
        this.economiasList = economiasList;
    }
    public void printEconomias() throws SQLException {
        for(Economia m : this.getEconomiasList())
        {
            System.out.println("NOME:" + m.getNome());
            System.out.println("VALOR:" + m.getValor());
            System.out.println("DATA ECONOMIA:" + m.getData().toString());
            System.out.println("TIPO ECONOMIA:" + m.getTipo());
            System.out.println("CATEGORIA:" + m.getCategoria());
            System.out.println("MUID:" + m.getMuid().toString());
        }
    }
    public int contarCategorias()
    {
        int i = 0;
        for(Economia m : this.getEconomiasList())
        {
            if(m.getCategoria().equals("COMIDA"))
            {
                i += 1;
            }
        }
        return i;
    }

}
