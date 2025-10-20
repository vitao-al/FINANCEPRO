package com.financepro.model.backend.dataTransferObjects;

import com.financepro.model.backend.databaseDataObjects.UsuariosHandlerDB;
import com.financepro.model.backend.model.Categorias;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextField;

import java.sql.SQLException;
import java.util.*;


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
    MetasList todasAsMetas = new MetasList();
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
        this.todasAsMetas = pegarTodasAsMetas();
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
    public void criarNovaDespesa(String nome, float valor, Categorias categoria, UUID muid)
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

        ArrayList<Economia> economias = new ArrayList<>();
        if (todasAsMetas != null) {
            for (Meta m : todasAsMetas.getMetasList()) {
                if (m.getMuid() != null) { // Verifica se muid é válido
                    m.pegarTodasEconomias(); // Força a carga do banco
                    if (m.getEconomiasMeta() != null) {
                        economias.addAll(m.getEconomiasMeta());
                    }
                }
            }
            this.setTodasAsEconomias(economias); // Atualiza a lista global
        }
        return economias; // Retorna lista inicializada// Retorna lista inicializada, mesmo que vazia
    }
    public ArrayList<Despesa> pegarTodasDespesas()
    {
        this.setTodasAsDespesas(this.todasAsMetas.getAllDespesas());
        return this.getTodasAsDespesas();
    }
    public ArrayList<DespesasTable> pegarQuantidadeDespesaCategoria()
    {
        ArrayList<DespesasTable> despesasParaTabela =  new ArrayList<>();
        Map<Categorias, DespesasTable> mapaCategorias = new HashMap<>();
        for(Despesa d : this.pegarTodasDespesas())
        {
            DespesasTable dt = mapaCategorias.get(d.getCategoria());
            Categorias c = d.getCategoria();

                if(dt == null)
                {
                    dt = new DespesasTable();
                    dt.setCategoria(c.toString());
                    dt.setData(d.getData().toString());
                    dt.setQuantidade(1);
                    dt.setValor(d.getValor());
                    mapaCategorias.put(d.getCategoria(), dt);
                }
                else{
                    dt.setQuantidade(dt.getQuantidade() + 1);
                    dt.setValor(dt.getValor() + d.getValor());
                }

            }
        despesasParaTabela.addAll(mapaCategorias.values());
        return despesasParaTabela;

    }
    public float somarTodosGastos(ObservableList<PieChart.Data> lista) {
        float total = 0f;
        for (PieChart.Data d : lista) {
            total += d.getPieValue(); // pega o valor da despesa
        }
        return total;
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
    public int contarDespesasCategoria(Categorias categoria)
    {
        return this.t.getNumDespesasByCategoria(categoria);
    }
    public Despesa pegarUltimaDespesaRecentePorCategoria(Categorias categoria)
    {
        return this.t.getDespesaMaisRecenteByCategoria(categoria);
    }
    public ArrayList<DespesasTable> pegarTop3DespesasPorCategoria() {
        // Pega todas as despesas agrupadas por categoria
        ArrayList<DespesasTable> todasDespesas = pegarQuantidadeDespesaCategoria();

        // Ordena pela quantidade de despesas (descendente) e limita a 3
        todasDespesas.sort((d1, d2) -> Integer.compare(d2.getQuantidade(), d1.getQuantidade()));
        return new ArrayList<>(todasDespesas.subList(0, Math.min(3, todasDespesas.size())));
    }
    public ArrayList<Economia> pegarEconomiasPorPeriodo(Date inicio, Date fim) {
        ArrayList<Economia> economiasFiltradas = new ArrayList<>();
        ArrayList<Economia> todasEconomias = pegarTodasEconomias(); // Usa a função ajustada

        if (todasEconomias != null) {
            for (Economia e : todasEconomias) {
                if (e != null && e.getData() != null && !e.getData().before(inicio) && !e.getData().after(fim)) {
                    economiasFiltradas.add(e);
                }
            }
        }
        return economiasFiltradas;
    }
    public ArrayList<Despesa> pegarDespesasPorPeriodo(Date inicio, Date fim) {
        ArrayList<Despesa> despesasFiltradas = new ArrayList<>();
        ArrayList<Despesa> todasDespesas = pegarTodasDespesas(); // Ajuste similar

        if (todasDespesas != null) {
            for (Despesa d : todasDespesas) {
                if (d != null && d.getData() != null && !d.getData().before(inicio) && !d.getData().after(fim)) {
                    despesasFiltradas.add(d);
                }
            }
        }
        return despesasFiltradas;
    }
    /**
     * Faz uma requisição no bando de dados para verificar se o usuario existe
     * se sim, retorna um objeto Usuario se não retorna null
     * @param username nome do usuario
     * @param senha senha do usuario
     * @return
     */
    public static Usuario PegarLoginUsuario(String username,String senha)
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
