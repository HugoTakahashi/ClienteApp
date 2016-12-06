package br.com.diaristaslimpo.limpo.to;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Hugo on 23/11/2016.
 */

public class DiariaTo implements Serializable {
    private int idDiarista;
    private int idSolicitacao;
    private String nome;
    private String data;
    private String identificacaoEndereco;
    private String servicos;
    private String valor;
    private String periodoDiaria;
    private String statusDiaria;

    public int getIdDiarista() {
        return idDiarista;
    }

    public void setIdDiarista(int idDiarista) {
        this.idDiarista = idDiarista;
    }

    public int getIdSolicitacao() {
        return idSolicitacao;
    }

    public void setIdSolicitacao(int idSolicitacao) {
        this.idSolicitacao = idSolicitacao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getIdentificacaoEndereco() {
        return identificacaoEndereco;
    }

    public void setIdentificacaoEndereco(String identificacaoEndereco) {
        this.identificacaoEndereco = identificacaoEndereco;
    }

    public String getServicos() {
        return servicos;
    }

    public void setServicos(String servicos) {
        this.servicos = servicos;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getPeriodoDiaria() {
        return periodoDiaria;
    }

    public void setPeriodoDiaria(String periodoDiaria) {
        this.periodoDiaria = periodoDiaria;
    }

    public String getStatusDiaria() {
        return statusDiaria;
    }

    public void setStatusDiaria(String statusDiaria) {
        this.statusDiaria = statusDiaria;
    }

    public DiariaTo jsonParaTo(DiariaTo to, JSONObject json){
        try {
            to.setIdDiarista((Integer) json.get("IdDiarista"));
            to.setIdSolicitacao((Integer) json.get("IdSolicitacao"));
            to.setNome((String) json.get("NomeDiarista"));
            to.setData((String) json.get("DataDiaria"));
            to.setIdentificacaoEndereco((String) json.get("IdentificacaoEndereco"));
            to.setServicos((String) json.get("Servicos"));
            to.setValor((String) json.get("Valor"));
            to.setPeriodoDiaria((String) json.get("PeriodoDiaria"));
            to.setStatusDiaria((String) json.get("StatusDiaria"));

        } catch(Exception ex) {
        }

        return to;
    }
}