package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.util.MessageBox;
import br.com.diaristaslimpo.limpo.activity.LoginActivity;

/**
 * Created by user on 24/04/2016.
 */
public class DesativarContaTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private ProgressDialog dialog;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private String mensagemRetorno;

    public DesativarContaTask(Context context) {
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
    protected Boolean doInBackground(String... params) {
        Boolean isValido = false;
        dataBase = new DataBase(context);
        conn = dataBase.getWritableDatabase();
        ScriptSQL scriptSQL = new ScriptSQL(conn);
        String idCliente = String.valueOf(scriptSQL.retornaIdCliente());

        String url = context.getResources().getString(R.string.url_prefix) +
                context.getResources().getString(R.string.url_cliente_desativar);

        JSONObject json = new ConectaWS().doGetJsonObject(url, idCliente);

        try {
            isValido = (Boolean) json.get("IsValido");
            mensagemRetorno = (String) json.get("Mensagem");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(isValido)
            scriptSQL.logof();

        return isValido;
    }

    @Override
    protected void onPostExecute(Boolean sucesso) {
        dialog.dismiss();
        if (sucesso) {
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
        } else {
            onCancelled();
            MessageBox.show(context,"Erro - Desativar",mensagemRetorno);
        }
    }
}