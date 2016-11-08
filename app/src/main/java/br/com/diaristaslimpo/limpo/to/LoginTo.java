package br.com.diaristaslimpo.limpo.to;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Hugo on 29/10/2016.
 */

public class LoginTo {

    private String Email;
    private String Senha;

    public LoginTo(String email, String senha) {
        setEmail(email);
        setSenha(senha);
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put("Email", getEmail());
            json.put("Senha", getSenha());
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        return String.valueOf(json);
    }
}