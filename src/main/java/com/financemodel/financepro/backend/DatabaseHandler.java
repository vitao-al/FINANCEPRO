package com.financemodel.financepro.backend;

import java.math.BigDecimal;
import java.sql.*;

public class DatabaseHandler
{
    DatabaseHandler(String databasePath) throws SQLException
    {
        this.connectDatabase(databasePath=databasePath);
    }

    // VerifyLogin(Usuario,senha) -> [true,userid]
    // saldo - global
    // Metas - handrey -> m1 - 2500, m2 1000 ,m3 500 - MetasHandrey.db - (META,VALOR,DATA INICIAL,DATA FINAL,UUID,MUID)
    // SaldoHandrey.db -> (VALOR,CATEGORIA,MUID,DATA)

    public Statement getStatement() {
        return statement;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void setStatement(Statement statement) {
        this.statement = statement;
    }

    public Connection connection = null;
    public Statement statement = null;

    public DatabaseHandler() {
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
    /**
     *  Verifica se a conexão com o banco de dados foi sucedida e se o atributo statement é diferente de nulo
     *  Caso seja nulo, lança uma exception RuntimeException
     *
     * @throws RuntimeException Caso o atributo statement responsavel por rodar comandos no banco de dados seja nulo;
     *
     */
    void verifyStatement()
    {
        // HF : o ideal é rodar isso em todas as funções do banco de dados para verificar se o statement é nulo ou nn
        if(this.getStatement() == null)
        {
            System.err.println("ERROR, STATEMENT TEM QUE TER UM VALOR DIFERENTE DE NULL");
            throw new RuntimeException("DatabaseHandler statement IGUAL A NULL!");
        }
    }



}
