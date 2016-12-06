package br.com.diaristaslimpo.limpo.to;

/**
 * Created by user on 03/09/2016.
 */
public class Endereco {

    private String endereco;
    private String cep;
    private String bairro;
    private int iconeRid;
    private String IdEndereco;
    private String IdentificacaoEndereco;
    private int Numero;
    private String  Complemento;
    private String  Cidade;
    private String Pontoreferencia;

    public Endereco(){

    }

    public Endereco(String endereco, String cep, String bairro, int ic) {
        this.endereco = endereco;
        this.cep = cep;
        this.bairro = bairro;
        this.iconeRid = ic;
    }

    public int getIconeRid() {
        return iconeRid;
    }

    public void setIconeRid(int iconeRid) {
        this.iconeRid = iconeRid;
    }


    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getIdEndereco() {
        return IdEndereco;
    }

    public void setIdEndereco(String idEndereco) {
        IdEndereco = idEndereco;
    }

    public String getIdentificacaoEndereco() {
        return IdentificacaoEndereco;
    }

    public void setIdentificacaoEndereco(String identificacaoEndereco) {
        IdentificacaoEndereco = identificacaoEndereco;
    }

    public int getNumero() {
        return Numero;
    }

    public void setNumero(int numero) {
        Numero = numero;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        Complemento = complemento;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        Cidade = cidade;
    }

    public String getPontoreferencia() {
        return Pontoreferencia;
    }

    public void setPontoreferencia(String pontoreferencia) {
        Pontoreferencia = pontoreferencia;
    }


}
