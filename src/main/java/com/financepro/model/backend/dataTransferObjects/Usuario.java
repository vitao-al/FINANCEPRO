package com.financepro.model.backend.dataTransferObjects;

import com.financepro.model.backend.databaseDataObjects.UsuariosHandlerDB;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;



// ---- Interface de contato principal -----

public class Usuario
{
    static UsuariosHandlerDB udb;
    Transacoes t = new Transacoes();
    static {
        try {
            udb = new UsuariosHandlerDB("Database/Usuarios.db");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    String username;
    String password;
    String sexo;
    float renda;
    UUID uuid;
    MetasList todasAsMetas;
    ArrayList<Economia> todasAsEconomias;
    ArrayList<Despesa> todasAsDespesas;
    public Usuario() throws SQLException {

    }

    /**
     * Construtor da classe Usuario, usado para construir e manipular dados de usuarios importados
     * Da query do banco de dados, de form mais facil
     *
     * @param username nome do usuario (String)
     * @param password senha do usuario (String)
     * @param sexo sexo do usuario (String)
     * @param renda renda do usuario (float)
     * @param uuid uuid do usuario (uuid)
     */
    public Usuario(String username, String password, String sexo, float renda, UUID uuid) throws SQLException {
        this.username = username;
        this.password = password;
        this.sexo = sexo;
        this.renda = renda;
        this.uuid = uuid;
    }
    public UUID getUuid()
    {
        return uuid;
    }
    public void criarNovaMeta(String nome, float valor,String descricao, Date dataFinal)
    {
        this.t.criarNovaMeta(nome,valor,descricao,new Date(),dataFinal,this.getUuid());
    }
    public void criarNovaMeta(String nome, float valor,String descricao,Date dataInicial,Date dataFinal)
    {
        this.t.criarNovaMeta(nome,valor,descricao,dataInicial,dataFinal,this.getUuid());
    }
    public void criarNovaEconomia(float valor,UUID muid)
    {
        this.t.inserirNovaEconomia(valor,new Date(),muid);
    }
    public void criarNovaDespesa(String nome,float valor,String categoria,UUID muid)
    {
        this.t.inserirNovaDespesa(nome,valor,new Date(),categoria,muid);
    }
    public void setUuid(UUID uuid)
    {
        this.uuid = uuid;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getSexo()
    {
        return sexo;
    }

    public void setSexo(String sexo)
    {
        this.sexo = sexo;
    }
    public Meta PegarMeta(UUID muid)
    {
        return this.t.getMeta(muid);
    }
    public float getRenda() {
        return renda;
    }

    public MetasList getTodasAsMetas() {
        return todasAsMetas;
    }

    private void setTodasAsMetas(MetasList todasAsMetas) {
        this.todasAsMetas = todasAsMetas;
    }

    public MetasList pegarTodasAsMetas()
    {
        this.setTodasAsMetas(this.t.getAllMetas(this.uuid));
        return this.todasAsMetas;
    }

    public ArrayList<Economia> getTodasAsEconomias() {
        return todasAsEconomias;
    }

    private void setTodasAsEconomias(ArrayList<Economia> todasAsEconomias) {
        this.todasAsEconomias = todasAsEconomias;
    }

    public ArrayList<Despesa> getTodasAsDespesas() {
        return todasAsDespesas;
    }

    private void setTodasAsDespesas(ArrayList<Despesa> todasAsDespesas) {
        this.todasAsDespesas = todasAsDespesas;
    }

    public ArrayList<Economia> pegarTodasEconomias()
    {
        this.setTodasAsEconomias(this.todasAsMetas.getAllEconomias());
        return this.getTodasAsEconomias();
    }
    public ArrayList<Despesa> pegarTodasDespesas()
    {
        this.setTodasAsDespesas(this.todasAsMetas.getAllDespesas());
        return this.getTodasAsDespesas();
    }

    /**
     * cria um novo usuario e adiciona no banco de dados baseado nos parametros abaixo:
     * @param username nome do usuario
     * @param senha senha do usuario
     * @param sexo genero do usuario: 'M' ou 'F'
     * @param renda renda do usuario em float
     */
    public static void criarNovoUsuario(String username, String senha, String sexo, float renda)
    {
        udb.insertNewUsuario(username,senha,sexo,renda);
    }

    /**
     * Faz uma requisição no bando de dados para verificar se o usuario existe
     * se sim, retorna um objeto Usuario se não retorna null
     * @param username nome do usuario
     * @param senha senha do usuario
     * @return
     */
    public Usuario PegarLoginUsuario(String username,String senha)
    {
        return udb.getLogin(username,senha);
    }
    public void setRenda(float renda) {
        this.renda = renda;
    }

    public static void main(String[] args) throws SQLException
    {
       // Usuario.criarNovoUsuario("handrey","1234","M",2500f);
        //Usuario u1 = Usuario.PegarLoginUsuario("handrey","12345");
    }
}
