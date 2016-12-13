package br.com.diaristaslimpo.limpo.to;

import org.json.JSONObject;

/**
 * Created by Hugo on 23/11/2016.
 */

public class DiaristaDisponivelTo {
    private int idDiarista;
    private int idEnderecoDiarista;
    private String nome;
    private double distancia;
    private String distanciaEmKm;
    private double rank;
    private String rankFormatado;
    private boolean itemSelecionado;

    public String getNumeroAvaliacoes() {
        return numeroAvaliacoes;
    }

    public void setNumeroAvaliacoes(String numeroAvaliacoes) {
        this.numeroAvaliacoes = numeroAvaliacoes;
    }

    private String numeroAvaliacoes;

    public boolean isItemSelecionado() {
        return itemSelecionado;
    }

    public void setItemSelecionado(boolean itemSelecionado) {
        this.itemSelecionado = itemSelecionado;
    }

    public int getIdDiarista() {
        return idDiarista;
    }

    public void setIdDiarista(int idDiarista) {
        this.idDiarista = idDiarista;
    }

    public int getIdEnderecoDiarista() {
        return idEnderecoDiarista;
    }

    public void setIdEnderecoDiarista(int idEnderecoDiarista) {
        this.idEnderecoDiarista = idEnderecoDiarista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getDistancia() {
        return distancia;
    }

    public void setDistancia(double distancia) {
        this.distancia = distancia;
    }

    public String getDistanciaEmKm() {
        return distanciaEmKm;
    }

    public void setDistanciaEmKm(String distanciaEmKm) {
        this.distanciaEmKm = distanciaEmKm;
    }

    public double getRank() {
        return rank;
    }

    public void setRank(double rank) {
        this.rank = rank;
    }

    public String getRankFormatado() {
        return rankFormatado;
    }

    public void setRankFormatado(String rankFormatado) {
        this.rankFormatado = rankFormatado;
    }

    public DiaristaDisponivelTo jsonParaTo(DiaristaDisponivelTo to, JSONObject json){
        try {
            to.setIdDiarista((Integer) json.get("IdDiarista"));
            to.setIdEnderecoDiarista((Integer) json.get("IdEnderecoDiarista"));
            to.setNome((String) json.get("Nome"));
            to.setDistancia((Double) json.get("Distancia"));
            to.setDistanciaEmKm((String) json.get("DistanciaEmKm"));
            to.setRankFormatado((String) json.get("RankFormatado"));
            to.setRank((Double) json.get("Rank"));
            to.setNumeroAvaliacoes((String) json.get("NumeroAvaliacoes"));

        } catch(Exception ex) {
            ex.getMessage();
        }

        return to;
    }
}
