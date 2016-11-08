package br.com.diaristaslimpo.limpo.model;

import android.media.Image;
import android.widget.ImageView;

/**
 * Created by user on 18/09/2016.
 */
public class ListaDiarista {

    private int idDiarista;
    private String nome;
    private int rate;
    private String cep;
    private int distacia;
    private ImageView foto;
    private boolean itemSelecionado;

    public boolean isItemSelecionado() {
        return itemSelecionado;
    }

    public void setItemSelecionado(boolean itemSelecionado) {
        this.itemSelecionado = itemSelecionado;
    }

    public int getDistacia() {
        return distacia;
    }

    public void setDistacia(int distacia) {
        this.distacia = distacia;
    }

    public int getIdDiarista() {
        return idDiarista;
    }

    public void setIdDiarista(int idDiarista) {
        this.idDiarista = idDiarista;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public ImageView getFoto() {
        return foto;
    }

    public void setFoto(ImageView foto) {
        this.foto = foto;
    }
}
