package br.com.diaristaslimpo.limpo.to;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.diaristaslimpo.limpo.util.MaskUtil;

/**
 * Created by Hugo on 01/11/2016.
 */

public class FormularioClienteTo {
    private String nome, sobrenome, dataNascimento, cpf, email, senha, confirmacaoSenha, celular, genero;
    private int id;

    public FormularioClienteTo(){

    }

    public FormularioClienteTo(String nome, String sobrenome, String dataNascimento, String cpf, String email, String senha, String confirmacaoSenha, String celular, String genero) {
        setNome(nome);
        setSobrenome(sobrenome);
        setDataNascimento(dataNascimento);
        setCpf(cpf);
        setEmail(email);
        setSenha(senha);
        setConfirmacaoSenha(confirmacaoSenha);
        setCelular(celular);
        setGenero(genero);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobronome) {
        this.sobrenome = sobronome;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfirmacaoSenha() {
        return confirmacaoSenha;
    }

    public void setConfirmacaoSenha(String confirmacaoSenha) {
        this.confirmacaoSenha = confirmacaoSenha;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();

        try {
            json.put("Nome", nome);
            json.put("Sobrenome", sobrenome);
            json.put("DataNascimento", dataNascimento);
            json.put("Cpf", MaskUtil.unmask(cpf));
            json.put("Email", email);
            json.put("Senha", senha);
            json.put("Celular", MaskUtil.unmask(celular));
            json.put("Genero", genero);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return String.valueOf(json);
    }
}