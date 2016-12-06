package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.InicialActivity;
import br.com.diaristaslimpo.limpo.activity.LoginActivity;
import br.com.diaristaslimpo.limpo.activity.MeusEnderecosActivity;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.util.MessageBox;

/**
 * Created by user on 24/04/2016.
 */
public class DeletarEnderecoTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private ProgressDialog dialog;
    private String mensagemRetorno;
    private DataBase dataBase;
    private SQLiteDatabase conn;

    public DeletarEnderecoTask(Context context) {
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

        String url = context.getResources().getString(R.string.url_prefix) +
                context.getResources().getString(R.string.url_clienteEndereco_Excluir);

        JSONObject json = new ConectaWS().doGetJsonObject(url, params[0]);

        try {
            isValido = (Boolean) json.get("IsValido");
            if(isValido){
                scriptSQL.excluirEndereco(params[0]);
            }
            mensagemRetorno = (String) json.get("Mensagem");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return isValido;
    }

    @Override
    protected void onPostExecute(Boolean sucesso) {
        dialog.dismiss();
        if (sucesso) {
            Intent intent = new Intent(context, InicialActivity.class);
            Toast.makeText(context,"Endereço excluído", Toast.LENGTH_LONG).show();
            context.startActivity(intent);
        } else {
            onCancelled();
            MessageBox.show(context,"Erro", mensagemRetorno);
        }
    }
}