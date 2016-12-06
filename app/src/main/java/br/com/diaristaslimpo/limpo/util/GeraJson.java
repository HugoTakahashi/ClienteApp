package br.com.diaristaslimpo.limpo.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by user on 24/04/2016.
 */
public class GeraJson {

    public String jsonAlteraCadastro(int id, String nome, String sobrenome, String datanasc, String cpf, String email, String celular) {
        JSONObject json = new JSONObject();

        try {
            json.put("Id",id);
            json.put("Nome", nome);
            json.put("Sobrenome", sobrenome);
            json.put("DataNascimento", datanasc);
            json.put("CPF", cpf);
            json.put("Email", email);
            json.put("Celular", celular);


        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return String.valueOf(json);

    }

    public String jsonCadastraEndereco(int IdCliente, String idEndereco, String nomeEndereco, String cep, String logradouro, String numero, String complemento, String bairro, String cidade, String pontoReferencia) {
        JSONObject json = new JSONObject();

        try {
            json.put("IdCliente",IdCliente);
            json.put("Id", idEndereco);
            json.put("IdentificacaoEndereco", nomeEndereco);
            json.put("Cep", MaskUtil.unmask(cep));
            json.put("Logradouro", logradouro);
            json.put("Numero", numero);
            json.put("Complemento", complemento);
            json.put("Bairro", bairro);
            json.put("Cidade", cidade);
            json.put("PontoReferencia", pontoReferencia);

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return String.valueOf(json);

    }

    public String jsonBuscaDiarista(String id, String data, int limpeza, int passarroupa, int lavarroupa) {
        JSONObject json = new JSONObject();

        try {
            json.put("IdCliente",id);
            json.put("DataServico",data);
            json.put("Limpeza",limpeza);
            json.put("PassarRoupa",passarroupa);
            json.put("LavarRoupa",lavarroupa);


        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return String.valueOf(json);

    }

    public String jsonEnviaSolicitacao(ArrayList<Integer> idDiarista, String idEndereco, int limpeza, int passarroupa, int lavarroupa, String dataServico, String outros, boolean diaria, String horainicio, String valorDiaria) {
        JSONObject json = new JSONObject();
        JSONArray jsonArray = new JSONArray(idDiarista);

        try {
            json.put("IdEnderecoCliente",idEndereco);
            json.put("ListaIdDiarista",jsonArray);
            json.put("DiariaCompleta",diaria);
            json.put("Limpeza",limpeza);
            json.put("PassaRoupa",passarroupa);
            json.put("LavaRoupa",lavarroupa);
            json.put("Data",dataServico + " " + horainicio);
            json.put("Observacao",outros);
            json.put("Valor", valorDiaria.replace(",", "."));
        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return String.valueOf(json);
    }

    public String jsonId(String idCliente) {
        JSONObject json = new JSONObject();

        try {
            json.put("IdCliente",idCliente);

        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return String.valueOf(json);

    }

    public String jsonEnviaAvaliacao(String nota, String obs, String idSolicitacao, String idDiarista, String idCliente) {
        JSONObject json = new JSONObject();

        try {
            json.put("IdCliente",idCliente);
            json.put("IdDiarista",idDiarista);
            json.put("IdSolicitacao",idSolicitacao);
            json.put("Observacao",obs);
            json.put("Nota",nota);


        } catch (JSONException e1) {
            e1.printStackTrace();
        }

        return String.valueOf(json);

    }
}
