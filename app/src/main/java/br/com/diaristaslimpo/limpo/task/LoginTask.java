package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import br.com.diaristaslimpo.limpo.model.Endereco;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.util.MessageBox;
import br.com.diaristaslimpo.limpo.activity.InicialActivity;
import br.com.diaristaslimpo.limpo.webservice.ConectaWS;

/**
 * Created by user on 24/04/2016.
 */
public class LoginTask extends AsyncTask<String, Void, String> {
    
    private Context context;
    private ProgressDialog dialog;
    private ConectaWS requester = new ConectaWS();
    private String pfv, url, id, login, ativo, idCliente;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private String json;

    public LoginTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, 
                context.getResources().getString(R.string.Aguarde),
                context.getResources().getString(R.string.EmProcessameto), 
                true, 
                true);
    }
    
    @Override
    protected String doInBackground(String... params) {
        String resp = null;
        try {
            json = params[0];
            url = context.getResources().getString(R.string.UrlPrefix) + context.getResources().getString(R.string.UrlLoginCliente);
            final JSONObject response = requester.conexao(url, json);

            try {
                if (response.getString("Message") != null) {
                    return resp = response.getString("Message");
                }
            }catch (JSONException e){

            }

            ativo = response.getString("Ativo");
            if (ativo.equals("true")) {
                idCliente = response.getString("Id");
                id = response.getString("Id");
                login = response.getString("Email");

                dataBase = new DataBase(context);
                conn = dataBase.getWritableDatabase();

                ScriptSQL scriptSQL = new ScriptSQL(conn);
                scriptSQL.inserirLogin(id, login, "1");
                scriptSQL.inserirCliente(
                        response.getString("Id"),
                        response.getString("Nome"),
                        response.getString("Sobrenome"),
                        response.getString("DataNascimento"),
                        response.getInt("Cpf"),
                        response.getString("Email"),
                        Integer.parseInt(response.getString("Celular")),
                        response.getString("Genero"));

                resp = null;
            } else {

            }
            final JSONArray recebe2 = requester.getJsonArray("http://limpo-dev.sa-east-1.elasticbeanstalk.com/api/ClienteEndereco/Listar", idCliente);
            ScriptSQL scriptSQL = new ScriptSQL(conn);
            for(int i=0;i < recebe2.length();i++) {
                Endereco obj = new Endereco();
                JSONObject json = recebe2.getJSONObject(i);
                obj.setIdEndereco(json.getString("Id"));
                obj.setIdentificacaoEndereco((String) json.get("IdentificacaoEndereco"));
                obj.setEndereco((String) json.get("Logradouro"));
                obj.setNumero((Integer) json.getInt("Numero"));
                obj.setComplemento((String) json.get("Complemento"));
                obj.setPontoreferencia((String) json.get("PontoReferencia"));
                obj.setBairro((String) json.get("Bairro"));
                obj.setCidade((String) json.get("Cidade"));
                obj.setCep((String) json.get("Cep"));
                scriptSQL.inserirEndereco(obj);
                obj = new Endereco();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            resp = "Usuario ou senha invalido";
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    protected void onPostExecute(String resposta) {
        if (resposta == null) {
            dialog.dismiss();
            Intent it = new Intent(context, InicialActivity.class);
            context.startActivity(it);
        } else {
            dialog.dismiss();
            onCancelled();
            MessageBox.show(context, "Erro ao efetuar o Login", resposta);
        }
    }
}