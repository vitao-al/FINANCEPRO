package com.financemodel.financepro.backend.database;

import com.financemodel.financepro.backend.datawrapplers.Usuario;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

public class UsuariosHandlerDB extends DatabaseHandler
{
    /**
     * Cria um Handler para o banco de dados de usuarios, para poder usar as funções do banco de dados
     * @param databasePath - O caminho (‘String’) do banco de dados dos usuarios
     * @throws SQLException - Caso de algum erro joga o SQLException como valor padrão
     */

    public UsuariosHandlerDB(String databasePath) throws SQLException
    {
        super(databasePath);
        this.createUserDBTable();
    }

    /**
     * Cria uma tabela de banco de dados dos usuarios caso não exista,
     * O banco de dados contem as seguintes colunas:
     * <p>
     * - USUARIO (TEXT) <br>
     * - SENHA (TEXT) <br>
     * - SEXO (TEXT COM 1 CARACTERE DE TAMANHO) <br>
     * - RENDA (NUMERIC) <br>
     * - UUID (TEXT)
     * @throws SQLException
     */
    public void createUserDBTable() throws SQLException
    {
        try
        {
            String createTableCommand = "" +
                    "CREATE TABLE IF NOT EXISTS usuarios (USERNAME TEXT UNIQUE," +
                    " SENHA TEXT," +
                    " SEXO TEXT CHECK(length(SEXO) = 1)," +
                    " RENDA NUMERIC," +
                    " UUID TEXT UNIQUE)";
            this.getStatement().executeUpdate(createTableCommand);
        }
        catch (SQLException e)
        {
            System.err.println("ERRO NO BANCO DE DADOS:" + e);
        }
    }

    /**
     * Adiciona um novo usuario ao banco de dados de usuários
     * @param usuario - Tipo ´string´, representa o nome do usuário(no shit sherlock!)
     * @param senha - Tipo ‘string’, representa a senha do usuário
     * @param sexo - Tipo char, So pode ter dois valores: 'M' maiúsculo de masculino, e 'F' maiúsculo de feminino
     * @param renda - Tipo BigDecimal, serve para representar a quantia de renda(obs: na hora de usar como parameter lembre-se de
     *              converter o valor que o usuário digitou para BigDecimal)
     */
    public void insertNewUsuario(String usuario, String senha, String sexo, float renda)
    {
        String sqlInsert = "INSERT INTO usuarios (USERNAME, SENHA, SEXO, RENDA, UUID) VALUES (?,?,?,?,?)";
        try{
            UUID newUserUUID = UUID.randomUUID();
            PreparedStatement pst = this.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,usuario);
            pst.setString(2,senha);
            pst.setString(3,sexo);
            pst.setFloat(4,renda);
            pst.setString(5,newUserUUID.toString());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Verifica se o usuario existe, com base no usuario digitado e na senha, caso os dois estejam corretos, retorna o objeto Usuario
     * Caso o contrário retorna NULL
     * @param usuario ‘String’ do nome do usuário
     * @param senha ‘String’ da senha do usuário
     * @return Se o usuário existir retorna uma ‘string’ com UUID do usuário, caso contrario retorna um valor null;
     */
    public Usuario getLogin(String usuario, String senha)
    {
        try
        {
            Usuario user = new Usuario();
            String sqlInsert = "SELECT * FROM usuarios WHERE USERNAME=(?) AND SENHA=(?)";
            PreparedStatement pst = this.getConnection().prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,usuario);
            pst.setString(2,senha);
            ResultSet resultadoBusca = pst.executeQuery();
            if(resultadoBusca.next())
            {
                user.setUsername(resultadoBusca.getString("USERNAME"));
                user.setPassword(resultadoBusca.getString("SENHA"));
                user.setSexo(resultadoBusca.getString("SEXO"));
                user.setRenda(resultadoBusca.getFloat("RENDA"));
                user.setUuid(UUID.fromString(resultadoBusca.getString("UUID")));
                return user;
            }
            else{
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
