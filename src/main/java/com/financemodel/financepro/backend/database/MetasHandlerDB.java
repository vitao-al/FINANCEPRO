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

public class MetasHandlerDB extends DatabaseHandler
{
    /**
     * Construtor padrão Do banco de dados das metas
     */
    MetasHandlerDB()
    {

    }

    /**
     * Construtor do banco de dados das metas dos usuarios
     *
     * @param databasePath Caminho para o arquivo do banco de dados Metas.db
     * @throws SQLException
     */
    public MetasHandlerDB(String databasePath) throws SQLException
    {
        super(databasePath);
        this.createNewMetasTable();
    }

    /**
     * cria uma nova tabela de metas com os campos :
     * <p>NOME_META</p>
     * <p>VALOR_META</p>
     * <p>DATA_INICIAL_META</p>
     * <p>DATA_FINAL_META</p>
     * <p>UUID</p>
     * <p>MUID</p>
     * @throws SQLException
     */
    public void createNewMetasTable() throws SQLException
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

    /**
     * função ultilizada para inserir uma nova meta no banco de dados
     * @param nomeMeta o nome da meta a ser inserido
     * @param valorMeta o valor em float da meta
     * @param dataInicialMeta a data inicial de classe Date do java.util.Date
     * @param dataFinalMeta a data final de classe Date do java.util.Date
     * @param uuid o uuid do usuario
     */
    public void insertNewMeta(String nomeMeta, float valorMeta, Date dataInicialMeta, Date dataFinalMeta, UUID uuid)
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
    public MetasList getMetas(UUID uuid)
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
}
