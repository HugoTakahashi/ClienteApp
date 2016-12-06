package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.util.MessageBox;
import br.com.diaristaslimpo.limpo.activity.LoginActivity;

/**
 * Created by user on 24/04/2016.
 */
public class CadastraClienteTask extends AsyncTask<String, Void, String> { // linha 22
    private Context context;
    private ProgressDialog dialog;
    private ConectaWS requester;
    private DataBase dataBase;
    private SQLiteDatabase conn;

    public CadastraClienteTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context,
                context.getResources().getString(R.string.aguarde),
                context.getResources().getString(R.string.em_processamento),
                true,
                true);
    }

    @Override
    protected String doInBackground(String... params) {
        String resp = null;
        try {
            String json = params[0];

            requester = new ConectaWS();
            String url = context.getResources().getString(R.string.url_prefix) + context.getResources().getString(R.string.url_cadastrar_cliente);
            final JSONObject recebe = requester.doPostJsonObject(url, json);

            dataBase = new DataBase(context);
            conn = dataBase.getWritableDatabase();

            ScriptSQL scriptSQL = new ScriptSQL(conn);
            scriptSQL.inserirCliente(
                    recebe.getString("Id"),
                    recebe.getString("Nome"),
                    recebe.getString("Sobrenome"),
                    recebe.getString("DataNascimentoFormatada"),//verificar se web service está pronto
                    recebe.getString("Cpf"),
                    recebe.getString("Email"),
                    Integer.parseInt(recebe.getString("Celular")),
                    recebe.getString("Genero"));
            resp = null;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            resp = "Dados invalidos ou já cadastrados";
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    protected void onPostExecute(String resposta) {
        if (resposta == null) {
            dialog.dismiss();
            Intent it = new Intent(context, LoginActivity.class);
            context.startActivity(it);
        } else {
            dialog.dismiss();
            onCancelled();
            MessageBox.show(context,"Erro ao efetuar o cadastro",resposta);
        }
    }
}