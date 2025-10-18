package com.financepro.model.backend.databaseDataObjects;

import java.sql.*;

public class DatabaseHandler
{
    // Usar o super para adicionar o path do DatabaseHandler em classes filhas
    DatabaseHandler(String databasePath) throws SQLException
    {
        this.connectDatabase(databasePath=databasePath);
    }

    public Statement getStatement()
    {
        return statement;
    }

    public Connection getConnection()
    {
        return connection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }

    public void setStatement(Statement statement)
    {
        this.statement = statement;
    }

    public Connection connection = null;
    public Statement statement = null;

    public DatabaseHandler()
    {
    }

    /**
     *  Inicia o banco de dados com base no no atributo connection e no parametro do caminho do arquivo do banco de dados
     *  E com o caminho para o arquivo do banco de dados, ex: (Database.db)
     *
     * @param databasePath uma string contendo o caminho para o local do arquivo do banco de dados
     *
     */
    protected void connectDatabase(String databasePath) throws SQLException
    {
        try
        {
            this.setConnection(DriverManager.getConnection("jdbc:sqlite:" + databasePath));
            this.setStatement(this.getConnection().createStatement());
            this.getStatement().setQueryTimeout(30);

        }
        catch (SQLException e)
        {
            System.err.println("ERRO NO BANCO DE DADOS:" + e.getMessage());
        }

    }

}
