package com.financepro.model.backend.databaseDataObjects;

import com.financepro.model.backend.dataTransferObjects.Despesa;
import com.financepro.model.backend.dataTransferObjects.Economia;
import com.financepro.model.backend.model.Categorias;
import com.financepro.model.backend.model.TipoTransacoes;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class TransacoesHandlerDB extends DatabaseHandler
{
    /**
     * Construtor padrão do handler de banco de dados das transações
     * @param databasePath caminho do banco de dados das transações
     * @throws SQLException
     */
    public TransacoesHandlerDB(String databasePath) throws SQLException
    {
        super(databasePath);
        this.createNewTransacoesTable();
    }

    /**
     * Cria a nova tabela de transações
     * @throws SQLException
     */
    public void createNewTransacoesTable() throws SQLException
    {
        try
        {
            String createTableCommand = "" +
                    "CREATE TABLE IF NOT EXISTS transacoes (NOME_TRANSACAO TEXT ," +
                    " VALOR_TRANSACAO NUMERIC," +
                    " DATA_TRANSACAO TEXT," +
                    " TIPO_TRANSACAO TEXT,"+
                    " CATEGORIA TEXT," +
                    " MUID TEXT)";
            this.getStatement().executeUpdate(createTableCommand);
        }
        catch (SQLException e)
        {
            System.err.println("ERRO NO BANCO DE DADOS:" + e);
        }
    }

    /**
     * Insere uma nova transação no banco de dados
     * @param nomeTransacao nome da transação
     * @param valorTransacao valor da transação em float
     * @param dataTransacao data da transação em Date(java.util.Date)
     * @param tipoTransacao tipo da transação(String) - futuramente se possivel em enum
     * @param categoria Categoria da transação(String) - futuramente se possivel em enum
     * @param muid muid da transação
     */
    public void insertNewTransacao(String nomeTransacao, float valorTransacao, Date dataTransacao, TipoTransacoes tipoTransacao, Categorias categoria, UUID muid)
    {
        String sqlInsert = "INSERT INTO transacoes " +
                "(NOME_TRANSACAO, " +
                "VALOR_TRANSACAO, " +
                "DATA_TRANSACAO, " +
                "TIPO_TRANSACAO,"+
                "CATEGORIA, "+
                "MUID)" +
                "VALUES (?,?,?,?,?,?)";
        try{
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            PreparedStatement pst = this.getConnection().prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,nomeTransacao);
            pst.setFloat(2,valorTransacao);
            pst.setString(3,fmt.format(dataTransacao));
            pst.setString(4,tipoTransacao.toString());
            pst.setString(5,categoria.toString());
            pst.setString(6,muid.toString());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * retorna um array list de todas as economias do usuario baseadas no muid
     * transformadas em objetos Economia
     * @param muid o id da meta do usuario
     * @return
     */
    public ArrayList<Economia> getEconomiasOfTransacoes(UUID muid)
    {
        try{
            String sqlInsert = "SELECT * FROM transacoes WHERE MUID=(?) AND TIPO_TRANSACAO=(?)";
            PreparedStatement pst = this.getConnection().prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,muid.toString());
            pst.setString(2,"ECONOMIA");
            ResultSet resultadoBusca = pst.executeQuery();
            ArrayList<Economia> economias = new ArrayList<>();
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            while(resultadoBusca.next())
            {

                Economia m = new Economia();
                m.setNome(resultadoBusca.getString("NOME_TRANSACAO"));
                m.setValor(resultadoBusca.getFloat("VALOR_TRANSACAO"));
                m.setData(fmt.parse(resultadoBusca.getString("DATA_TRANSACAO")));
                m.setTipo(TipoTransacoes.valueOf(resultadoBusca.getString("TIPO_TRANSACAO")));
                m.setCategoria(null);
                m.setMuid(UUID.fromString(resultadoBusca.getString("MUID")));
                economias.add(m);
            }
            return economias;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * retorna um array list de todas as despesas do usuario baseadas no muid
     * transformadas em objetos Despesa
     * @param muid o id da meta do usuario
     * @return
     */
    public ArrayList<Despesa> getDespesasOfTransacoes(UUID muid)
    {
        try{
            String sqlInsert = "SELECT * FROM transacoes WHERE MUID=(?) AND TIPO_TRANSACAO=(?)";
            PreparedStatement pst = this.getConnection().prepareStatement(sqlInsert,Statement.RETURN_GENERATED_KEYS);
            pst.setString(1,muid.toString());
            pst.setString(2,"DESPESA");
            ResultSet resultadoBusca = pst.executeQuery();
            ArrayList<Despesa> despesas = new ArrayList<>();
            SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
            while(resultadoBusca.next())
            {

                Despesa d = new Despesa();
                d.setNome(resultadoBusca.getString("NOME_TRANSACAO"));
                d.setValor(resultadoBusca.getFloat("VALOR_TRANSACAO"));
                d.setData(fmt.parse(resultadoBusca.getString("DATA_TRANSACAO")));
                d.setTipo(TipoTransacoes.valueOf(resultadoBusca.getString("TIPO_TRANSACAO")));
                d.setCategoria(Categorias.valueOf(resultadoBusca.getString("CATEGORIA")));
                d.setMuid(UUID.fromString(resultadoBusca.getString("MUID")));
                despesas.add(d);
            }
            return despesas;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
