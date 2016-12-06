package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.diaristaslimpo.limpo.activity.ConfirmacaoServicoActivity;
import br.com.diaristaslimpo.limpo.activity.InicialActivity;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.util.MessageBox;

/**
 * Created by user on 24/04/2016.
 */
public class AlteraCadastroTask extends AsyncTask<String, Void, String> { // linha 22
    private Context context;
    private ProgressDialog dialog;
    private ConectaWS requester;
    private DataBase dataBase;
    private SQLiteDatabase conn;


    public AlteraCadastroTask(Context context) {

        this.context = context;

    }

    @Override
    protected void onPreExecute() {// antes de usar a thread segundaria
      dialog = ProgressDialog.show(context, "Aguarde", "Efetuando Cadastro", true, true);

    }

    @Override
    protected String doInBackground(String... params) {// thread em segundaria
        String resp = "oi";
        try {
            String json = params[0];

            requester = new ConectaWS();

            final JSONObject recebe = requester.doPostJsonObject("http://limpo-dev.sa-east-1.elasticbeanstalk.com/api/Cliente/Editar", json);

            dataBase = new DataBase(context);
            conn = dataBase.getWritableDatabase();

            ScriptSQL scriptSQL = new ScriptSQL(conn);
            scriptSQL.alterarCliente(
                    recebe.getString("Id"),
                    recebe.getString("Nome"),
                    recebe.getString("Sobrenome"),
                    recebe.getString("DataNascimentoFormatada"),
                    recebe.getString("Cpf"),
                    recebe.getString("Email"),
                    Integer.parseInt(recebe.getString("Celular")));

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
    protected void onPostExecute(String resposta) { // thread principal

        if (resposta == null) {
            dialog.dismiss();
            Intent it = new Intent(context, InicialActivity.class);
            Toast.makeText(context,"Dados alterados",Toast.LENGTH_LONG).show();
            context.startActivity(it);
        }else{
            dialog.dismiss();
            onCancelled();
            MessageBox.show(context,"Erro ao efetuar Alteração dos Dados",resposta);

        }





    }


}

