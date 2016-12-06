package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.task.ConectaWS;
import br.com.diaristaslimpo.limpo.util.MessageBox;
import br.com.diaristaslimpo.limpo.activity.InicialActivity;

/**
 * Created by user on 24/04/2016.
 */
public class EnviaMapaTask extends AsyncTask<String, Void, String> { // linha 22
    private Context context;
    private ProgressDialog dialog;
    private ConectaWS requester;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private String origem,destino;


    public EnviaMapaTask(Context context) {

        this.context = context;

    }

    @Override
    protected void onPreExecute() {// antes de usar a thread segundaria
        dialog = ProgressDialog.show(context, "Aguarde", "Efetuando Login", true, true);

    }

    @Override
    protected String doInBackground(String... params) {// thread em segundaria
        String resp = "oi";
        try {
            origem = params[0];
            destino = params[1];
            String url = "https://maps.googleapis.com/maps/api/directions/json?origin="+origem+"&destination="+destino+"&key=AIzaSyDz0j3rEZVS95nKbQlnbeScA7zVWEdUW8Y";
            requester = new ConectaWS();

            final JSONObject recebe = requester.doPostJsonObject(url,"");

            JSONArray dados = recebe.getJSONArray("routes");
            JSONObject legs = dados.getJSONObject(0);
            JSONArray danese = legs.getJSONArray("legs");
            JSONObject depoisvejo = danese.getJSONObject(0);
            JSONObject aaa = depoisvejo.getJSONObject("distance");


            String fim = String.valueOf(aaa.get("value"));
            return fim;
        } catch (Exception e) {

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

