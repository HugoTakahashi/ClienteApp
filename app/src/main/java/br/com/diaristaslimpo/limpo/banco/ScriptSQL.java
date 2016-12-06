package br.com.diaristaslimpo.limpo.banco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.to.Cliente;
import br.com.diaristaslimpo.limpo.to.Endereco;
import br.com.diaristaslimpo.limpo.to.ListaDiarista;
import br.com.diaristaslimpo.limpo.to.Orcamento;
import br.com.diaristaslimpo.limpo.R;

/**
 * Created by user on 09/04/2016.
 */
public class ScriptSQL {
    private SQLiteDatabase conn;

    public ScriptSQL(SQLiteDatabase conn) {
        this.conn = conn;
    }

    public void inserirLogin(String id, String login, String tipousuario) {
        ContentValues values = new ContentValues();
        values.put("idUsuario", id);
        values.put("Login", login);
        values.put("logado", tipousuario);
        conn.insertOrThrow("login", null, values);
    }

    public void inserirCliente(String idCliente, String nome, String sobrenome, String datanasc, String cpf, String email, int celular, String genero) {
        ContentValues values = new ContentValues();
        values.put("IdCliente", idCliente);
        values.put("Nome", nome);
        values.put("Sobrenome", sobrenome);
        values.put("DataNascimento",datanasc);
        values.put("Cpf",cpf);
        values.put("Email",email);
        values.put("Celular",celular);
        values.put("Genero",genero);
        conn.insertOrThrow("cliente", null, values);
    }

    public void alterarCliente(String id, String nome, String sobrenome, String datanasc, String cpf, String email, int celular) {


        ContentValues values = new ContentValues();
        values.put("Nome", nome);
        values.put("Sobrenome", sobrenome);
        values.put("DataNascimento",datanasc);
        values.put("Cpf",cpf);
        values.put("Email",email);
        values.put("Celular",celular);
        conn.update("cliente", values, "idCliente=?", new String[]{id});

    }

    public void inserirEndereco(String idCliente,String idEndereco,String nomeEndereco, int cep, String logradouro, int numero,String complemento,String bairro,String cidade, String pontoreferencia) {


        ContentValues values = new ContentValues();
        values.put("IdCliente", idCliente);
        values.put("IdEndereco",idEndereco);
        values.put("IdentificacaoEndereco", nomeEndereco);
        values.put("Cep", cep);
        values.put("Logradouro", logradouro);
        values.put("Cidade", cidade);
        values.put("Numero",numero);
        values.put("Complemento",complemento);
        values.put("Bairro",bairro);
        values.put("Pontoreferencia", pontoreferencia);
        conn.insertOrThrow("endereco", null, values);

    }

    public void alterarEndereco(String IdEndereco,String nomeEndereco,String cep, String endereco,
                                int numero,String complemento,String bairro,String cidade, String pontoreferencia) {
        ContentValues values = new ContentValues();
        values.put("IdentificacaoEndereco", nomeEndereco);
        values.put("Cep", cep);
        values.put("Logradouro", endereco);
        values.put("Numero",numero);
        values.put("Complemento",complemento);
        values.put("Bairro",bairro);
        values.put("Cidade",cidade);
        values.put("Pontoreferencia",pontoreferencia);
        conn.update("endereco", values, "IdEndereco=?", new String[]{IdEndereco});

    }

    public void excluirEndereco(String idEndereco){
        conn.delete("endereco","idEndereco=?", new String[]{idEndereco});
    }

    public int isLogin(Context context) {
        int login = 0;
        Cursor cursor = conn.query("Login", null, null, null, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                login = (int) cursor.getLong(cursor.getColumnIndex("logado"));

                /*Contato contato = new Contato();
                contato.setId(cursor.getLong(cursor.getColumnIndex(Contato.ID)));
                contato.setNome(cursor.getString(cursor.getColumnIndex(Contato.NOME)));
                contato.setTelefone(cursor.getString(cursor.getColumnIndex(Contato.TELEFONE)));
                contato.setTipoTelefone(cursor.getString(cursor.getColumnIndex(Contato.TIPOTELEFONE)));
                contato.setEmail(cursor.getString(cursor.getColumnIndex(Contato.EMAIL)));
                contato.setTipoEmail(cursor.getString(cursor.getColumnIndex(Contato.TIPOEMAIL)));
                contato.setEndereco(cursor.getString(cursor.getColumnIndex(Contato.ENDERECO)));
                contato.setTipoEndereco(cursor.getString(cursor.getColumnIndex(Contato.TIPOENDERECO)));
                contato.setDatasEspeciais(new Date(cursor.getLong(cursor.getColumnIndex(Contato.DATASESP))));
                contato.setTiposDatasEspeciais(cursor.getString(cursor.getColumnIndex(Contato.TIPOSDATASESP)));
                contato.setGrupos(cursor.getString(cursor.getColumnIndex(Contato.GRUPOS)));
                adpContatos.add(contato);*/
            } while (cursor.moveToNext());
        }
        return login;
    }

    public void logof() {
        conn.delete("login", null, null);
        conn.delete("cliente",null,null);
        conn.delete("endereco",null,null);
        limpaListaDiarista();
    }

    public void limpaListaDiarista(){
        conn.delete("listadiaristas",null,null);
    }

    public ArrayList<Endereco> selectEndereco(){
        ArrayList<Endereco> array = new ArrayList<Endereco>();
        Endereco obj = new Endereco();
        Cursor cursor = conn.rawQuery("select * from endereco",null);
        if(cursor.getCount() >0){
            cursor.moveToFirst();


            do{
                obj.setIdEndereco(cursor.getString(cursor.getColumnIndex("IdEndereco")));
                obj.setEndereco(cursor.getString(cursor.getColumnIndex("Logradouro")));
                obj.setBairro(cursor.getString(cursor.getColumnIndex("Bairro")));
                obj.setCep(cursor.getString(cursor.getColumnIndex("Cep")));
                obj.setIdentificacaoEndereco(cursor.getString(cursor.getColumnIndex("IdentificacaoEndereco")));
                obj.setNumero(cursor.getInt(cursor.getColumnIndex("Numero")));
                obj.setComplemento(cursor.getString(cursor.getColumnIndex("Complemento")));
                obj.setCidade(cursor.getString(cursor.getColumnIndex("Cidade")));
                obj.setPontoreferencia(cursor.getString(cursor.getColumnIndex("Pontoreferencia")));
                obj.setIconeRid(R.drawable.casa);
                array.add(obj);
                obj = new Endereco();
            }while (cursor.moveToNext());

        }
        return array;
    }

    public String[] carregaLogin() {
        String[] dados = new String[1];
        Cursor cursor = conn.rawQuery("select * from Login",null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                dados[0] = cursor.getString(cursor.getColumnIndex("Login"));
            } while (cursor.moveToNext());
        }
        return dados;
    }
    public int retornaIdCliente(){
        int id=0;
        Cursor cursor = conn.rawQuery("select * from cliente",null);
        if (cursor.getCount() >0){
            cursor.moveToFirst();

                id = cursor.getInt(cursor.getColumnIndex("IdCliente"));

        }
        return id;

    }

    public void gravaListaDiarista(int idDiarista, String nome, String cep, int rate, int distacia) {
        ContentValues values = new ContentValues();
        values.put("idDiarista", idDiarista);
        values.put("nome",nome);
        values.put("rate", rate);
        values.put("cep", cep);
        values.put("distancia", distacia);
        conn.insertOrThrow("listadiaristas", null, values);

    }

    public ArrayList<Cliente> selectCliente(String idCliente){
        ArrayList<Cliente> array = new ArrayList<Cliente>();
        Cliente obj = new Cliente();
        Cursor cursor = conn.rawQuery("select * from cliente where IdCliente=" + idCliente ,null);
        if(cursor.getCount() >0){
            cursor.moveToFirst();

            do{
                obj.setIdCliente(cursor.getInt(cursor.getColumnIndex("IdCliente")));
                obj.setNome(cursor.getString(cursor.getColumnIndex("Nome")));
                obj.setSobrenome(cursor.getString(cursor.getColumnIndex("Sobrenome")));
                obj.setDataNascimento(cursor.getString(cursor.getColumnIndex("DataNascimento")));
                obj.setCpf(cursor.getString(cursor.getColumnIndex("Cpf")));
                obj.setEmail(cursor.getString(cursor.getColumnIndex("Email")));
                obj.setCelular(cursor.getInt(cursor.getColumnIndex("Celular")));
                obj.setGenero(cursor.getString(cursor.getColumnIndex("Genero")));
                array.add(obj);
                obj = new Cliente();
            }while (cursor.moveToNext());

        }
        return array;
    }

    public ArrayList<ListaDiarista> selectListaDiarista(){
        ArrayList<ListaDiarista> array = new ArrayList<ListaDiarista>();
        ListaDiarista obj = new ListaDiarista();
        Cursor cursor = conn.rawQuery("select * from listadiaristas",null);
        if(cursor.getCount() >0){
            cursor.moveToFirst();

            do{
                obj.setIdDiarista(cursor.getInt(cursor.getColumnIndex("idDiarista")));
                obj.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                obj.setCep(cursor.getString(cursor.getColumnIndex("cep")));
                obj.setDistacia(cursor.getInt(cursor.getColumnIndex("distancia")));
                obj.setRate(cursor.getInt(cursor.getColumnIndex("rate")));
                array.add(obj);
                obj = new ListaDiarista();
            }while (cursor.moveToNext());

        }
        return array;
    }

    public void insertHistorico(String idSolicitacao, String nome, double valor, String status, String dataSolicitacao) {
        ContentValues values = new ContentValues();
        values.put("IdSolicitacao", idSolicitacao);
        values.put("nome",nome);
        values.put("valor", valor);
        values.put("status", status);
        values.put("dataSolicitacao",dataSolicitacao);
        conn.insertOrThrow("historico", null, values);

    }

    public ArrayList<Orcamento> selectListaHistorico(){
        ArrayList<Orcamento> array = new ArrayList<Orcamento>();
        Orcamento obj = new Orcamento();
        Cursor cursor = conn.rawQuery("select * from historico",null);
        if(cursor.getCount() >0){
            cursor.moveToFirst();

            do{
                obj.setCodSolicitacao(cursor.getInt(cursor.getColumnIndex("IdSolicitacao")));
                obj.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                obj.setValor(cursor.getDouble(cursor.getColumnIndex("valor")));
                obj.setStatus(cursor.getString(cursor.getColumnIndex("status")));
                obj.setDataSolicitacao(cursor.getString(cursor.getColumnIndex("dataSolicitacao")));
                array.add(obj);
                obj = new Orcamento();
            }while (cursor.moveToNext());

        }
        return array;
    }

    public void inserirEndereco(Endereco obj) {
        ContentValues values = new ContentValues();
        values.put("IdCliente", retornaIdCliente());
        values.put("IdEndereco",obj.getIdEndereco());
        values.put("IdentificacaoEndereco", obj.getIdentificacaoEndereco());
        values.put("Cep", obj.getCep());
        values.put("Logradouro", obj.getEndereco());
        values.put("Cidade", obj.getCidade());
        values.put("Numero",obj.getNumero());
        values.put("Complemento",obj.getComplemento());
        values.put("Bairro",obj.getBairro());
        values.put("Pontoreferencia", obj.getPontoreferencia());
        conn.insertOrThrow("endereco", null, values);
    }
}
