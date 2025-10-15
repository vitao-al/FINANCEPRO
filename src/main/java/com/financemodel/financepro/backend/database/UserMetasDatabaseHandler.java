package com.financemodel.financepro.backend.database;

import com.financemodel.financepro.backend.datawrapplers.Meta;
import com.financemodel.financepro.backend.datawrapplers.MetasList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.ArrayList;

public class UserMetasDatabaseHandler extends DatabaseHandler
{

    UserMetasDatabaseHandler()
    {

    }
    public UserMetasDatabaseHandler(String databasePath) throws SQLException
    {
        super(databasePath);
        this.createUserMetasDBTable();
    }
    public void createUserMetasDBTable() throws SQLException
    {
        try
        {
            String createTableCommand = "" +
                    "CREATE TABLE IF NOT EXISTS metas (NOME_META TEXT UNIQUE," +
                    " VALOR_META NUMERIC," +
                    " DATA_INICIAL_META TEXT," +
                    " DATA_FINAL_META TEXT," +
                    " UUID TEXT," +
                    " MUID TEXT UNIQUE)";
            this.getStatement().executeUpdate(createTableCommand);
        }
        catch (SQLException e)
        {
            System.err.println("ERRO NO BANCO DE DADOS:" + e);
        }
    }
    public void createNewMetasDatabase(String nomeMeta, float valorMeta, Date dataInicialMeta, Date dataFinalMeta,UUID uuid)
    {
        String sqlInsert = "INSERT INTO metas " +
                "(NOME_META, " +
                "VALOR_META, " +
                "DATA_INICIAL_META, " +
                "DATA_FINAL_META, " +
                "UUID, " +
                "MUID)" +
                "VALUES (?,?,?,?,?,?)";
        try{
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            UUID newMetaMUID = UUID.randomUUID();
            PreparedStatement pst = this.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,nomeMeta);
            pst.setFloat(2,valorMeta);
            pst.setString(3,fmt.format(dataInicialMeta));
            pst.setString(4,fmt.format(dataFinalMeta));
            pst.setString(5,uuid.toString());
            pst.setString(6,newMetaMUID.toString());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
        Faz uma busca de todas as metas com a coluna UUID com o mesmo valor referenciado no parametro
        Se existir, retorna um ArrayList com todos os valores encontrados com o mesmo UUID

        @param uuid Valor do uuid do usuario para fazer a busca
        @return um ArrayList<Meta> de todas as metas encontradas do usuario
     */
    public MetasList queryUserMetasInMetasDatabase(UUID uuid)
    {
        try{
            String sqlInsert = "SELECT * FROM metas WHERE UUID=(?)";
            PreparedStatement pst = this.getConnection().prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,uuid.toString());
            ResultSet resultadoBusca = pst.executeQuery();
            ArrayList<Meta> metas = new ArrayList<>();
            MetasList metasList = new MetasList();
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            while(resultadoBusca.next())
            {

                Meta m = new Meta();
                m.setNome(resultadoBusca.getString("NOME_META"));
                m.setValor(resultadoBusca.getFloat("VALOR_META"));
                m.setDataInicial(fmt.parse(resultadoBusca.getString("DATA_INICIAL_META")));
                m.setDataFinal(fmt.parse(resultadoBusca.getString("DATA_FINAL_META")));
                m.setUuid(UUID.fromString(resultadoBusca.getString("UUID")));
                m.setMuid(UUID.fromString(resultadoBusca.getString("MUID")));
                metas.add(m);
            }
            metasList.setMetasList(metas);
            return metasList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) throws SQLException {
        var db = new UserMetasDatabaseHandler("Database/userMetasDatabase.db");

        Date dataInicial = new Date(2006,11,13);
        Date dataFinal = new Date(2025,3,27);

        //db.createNewMetasDatabase("meta2",3560,dataInicial,dataFinal,"TRANSPORTE",UUID.fromString("9d54ee9a-ccbc-458a-b1b3-3db4b98e8c5a"));
        MetasList metasUser = db.queryUserMetasInMetasDatabase(UUID.fromString("9d54ee9a-ccbc-458a-b1b3-3db4b98e8c5a"));
        metasUser.printMetas();
    }

}
