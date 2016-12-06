package br.com.diaristaslimpo.limpo.banco;

/**
 * Created by Adelson on 05/08/2015.
 */
public class Tabelas {

    public static String getCreateLogin(){

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("create table if not exists login( ");
        sqlBuilder.append("_id integer not null primary key autoincrement, ");
        sqlBuilder.append("idUsuario integer,");
        sqlBuilder.append("Login TEXT,");
        sqlBuilder.append("logado integer ");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getCreateCliente() {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("create table if not exists cliente(");
        sqlBuilder.append("_id integer not null primary key autoincrement, ");
        sqlBuilder.append("IdCliente integer,");
        sqlBuilder.append("Nome TEXT,");
        sqlBuilder.append("Sobrenome TEXT,");
        sqlBuilder.append("DataNascimento TEXT,");
        sqlBuilder.append("Cpf TEXT,");
        sqlBuilder.append("Email TEXT,");
        sqlBuilder.append("Celular INTEGER,");
        sqlBuilder.append("Genero TEXT");

        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getCreateEndereco(){

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("create table if not exists endereco(");
        sqlBuilder.append("_id integer not null primary key autoincrement, ");
        sqlBuilder.append("IdCliente integer,");
        sqlBuilder.append("IdEndereco integer,");
        sqlBuilder.append("IdentificacaoEndereco TEXT,");
        sqlBuilder.append("Cep TEXT,");
        sqlBuilder.append("Logradouro TEXT,");
        sqlBuilder.append("Numero integer,");
        sqlBuilder.append("Complemento TEXT,");
        sqlBuilder.append("Bairro TEXT,");
        sqlBuilder.append("Cidade TEXT,");
        sqlBuilder.append("Pontoreferencia TEXT,");
        sqlBuilder.append("Latitude TEXT,");
        sqlBuilder.append("Longitude TEXT");
        sqlBuilder.append(");");

        return sqlBuilder.toString();

    }

    public static String getCreateTempDiarista() {

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("create table if not exists listadiaristas(");
        sqlBuilder.append("_id integer not null primary key autoincrement, ");
        sqlBuilder.append("idDiarista integer,");
        sqlBuilder.append("nome TEXT,");
        sqlBuilder.append("rate integer,");
        sqlBuilder.append("cep TEXT,");
        sqlBuilder.append("distancia integer");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }

    public static String getCreateHistorico(){
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("create table if not exists historico(");
        sqlBuilder.append("_id integer not null primary key autoincrement, ");
        sqlBuilder.append("IdSolicitacao integer,");
        sqlBuilder.append("nome TEXT,");
        sqlBuilder.append("valor integer,");
        sqlBuilder.append("status TEXT,");
        sqlBuilder.append("dataSolicitacao TEXT");
        sqlBuilder.append(");");

        return sqlBuilder.toString();
    }
}
