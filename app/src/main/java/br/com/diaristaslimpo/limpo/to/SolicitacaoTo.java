package br.com.diaristaslimpo.limpo.to;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Hugo on 23/11/2016.
 */

public class SolicitacaoTo implements Serializable {

    private boolean limpeza;
    private String data;
    private boolean passaRoupa;
    private boolean lavaRoupa;
    private int idEnderecoCliente;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isLimpeza() {
        return limpeza;
    }

    public void setLimpeza(boolean limpeza) {
        this.limpeza = limpeza;
    }

    public boolean isPassaRoupa() {
        return passaRoupa;
    }

    public void setPassaRoupa(boolean passaRoupa) {
        this.passaRoupa = passaRoupa;
    }

    public boolean isLavaRoupa() {
        return lavaRoupa;
    }

    public void setLavaRoupa(boolean lavaRoupa) {
        this.lavaRoupa = lavaRoupa;
    }

    public int getIdEnderecoCliente() {
        return idEnderecoCliente;
    }

    public void setIdEnderecoCliente(int idEnderecoCliente) {
        this.idEnderecoCliente = idEnderecoCliente;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put("IdEnderecoCliente", getIdEnderecoCliente());
            json.put("Limpeza", isLimpeza());
            json.put("LavarRoupa", isLavaRoupa());
            json.put("PassarRoupa", isPassaRoupa());
            json.put("DataDiaria", getData());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return String.valueOf(json);
    }
}