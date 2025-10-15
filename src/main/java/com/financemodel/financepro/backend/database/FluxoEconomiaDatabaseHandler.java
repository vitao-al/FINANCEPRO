package com.financemodel.financepro.backend.database;

import com.financemodel.financepro.backend.datawrapplers.Economia;
import com.financemodel.financepro.backend.datawrapplers.EconomiasList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class FluxoEconomiaDatabaseHandler extends DatabaseHandler
{
    public FluxoEconomiaDatabaseHandler(String databasePath) throws SQLException
    {
        super(databasePath);
        this.createFluxoEconomiaDBTable();
    }
    public void createFluxoEconomiaDBTable() throws SQLException
    {
        try
        {
            String createTableCommand = "" +
                    "CREATE TABLE IF NOT EXISTS fluxo_economias (NOME_ECONOMIA TEXT ," +
                    " VALOR_ECONOMIA NUMERIC," +
                    " DATA_ECONOMIA TEXT," +
                    "TIPO_ECONOMIA TEXT,"+
                    " CATEGORIA TEXT," +
                    " MUID TEXT )";
            this.getStatement().executeUpdate(createTableCommand);
        }
        catch (SQLException e)
        {
            System.err.println("ERRO NO BANCO DE DADOS:" + e);
        }
    }
    public void createNewFluxoEconomia(String nomeEconomia, float valorEconomia, Date dataEconomia, String tipoEconomia,String categoria,UUID muid)
    {
        String sqlInsert = "INSERT INTO fluxo_economias " +
                "(NOME_ECONOMIA, " +
                "VALOR_ECONOMIA, " +
                "DATA_ECONOMIA, " +
                "TIPO_ECONOMIA,"+
                "CATEGORIA, "+
                "MUID)" +
                "VALUES (?,?,?,?,?,?)";
        try{
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            PreparedStatement pst = this.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,nomeEconomia);
            pst.setFloat(2,valorEconomia);
            pst.setString(3,fmt.format(dataEconomia));
            pst.setString(4,tipoEconomia);
            pst.setString(5,categoria);
            pst.setString(6,muid.toString());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public EconomiasList queryUserEconomiasInDatabase(UUID muid)
    {
        try{
            String sqlInsert = "SELECT * FROM fluxo_economias WHERE MUID=(?)";
            PreparedStatement pst = this.getConnection().prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,muid.toString());
            ResultSet resultadoBusca = pst.executeQuery();
            ArrayList<Economia> economias = new ArrayList<>();
            EconomiasList economiasList = new EconomiasList();
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            while(resultadoBusca.next())
            {

                Economia m = new Economia();
                m.setNome(resultadoBusca.getString("NOME_ECONOMIA"));
                m.setValor(resultadoBusca.getFloat("VALOR_ECONOMIA"));
                m.setData(fmt.parse(resultadoBusca.getString("DATA_ECONOMIA")));
                m.setTipo(resultadoBusca.getString("TIPO_ECONOMIA"));
                m.setCategoria(resultadoBusca.getString("CATEGORIA"));
                m.setMuid(UUID.fromString(resultadoBusca.getString("MUID")));
                economias.add(m);
            }
            economiasList.setEconomiasList(economias);
            return economiasList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws SQLException {
        FluxoEconomiaDatabaseHandler db = new FluxoEconomiaDatabaseHandler("Database/fluxoEconomia.db");
        EconomiasList economias = db.queryUserEconomiasInDatabase(UUID.fromString("0faf06a1-fe42-4752-86b5-923629fba42c"));
        economias.printEconomias();
        System.out.println("DESPESAS RELACIONADAS A COMIDA:" + economias.contarCategorias());
    }

}
