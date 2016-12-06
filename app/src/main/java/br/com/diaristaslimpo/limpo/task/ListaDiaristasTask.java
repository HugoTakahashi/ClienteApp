package br.com.diaristaslimpo.limpo.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.diaristaslimpo.limpo.activity.ListaDiaristaActivity;
import br.com.diaristaslimpo.limpo.to.ListaDiarista;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.util.GeraJson;
import br.com.diaristaslimpo.limpo.util.MessageBox;

/**
 * Created by user on 24/04/2016.
 */
public class ListaDiaristasTask extends AsyncTask<String, Void, String> { // linha 22
    private Context context;
    private ProgressDialog dialog;
    private ConectaWS requester;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private String end;
    private String servs;
    private String dataServico, idEndereco, outros;
    private int limpeza;
    private int passarroupa;
    private int lavarroupa;
    private String diaria;
    private String horainicio;


    public ListaDiaristasTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {// antes de usar a thread segundaria
        dialog = ProgressDialog.show(context, "Aguarde", "Buscando Diaristas", true, true);

    }

    @Override
    protected String doInBackground(String... params) {// thread em segundaria
        String resp = "oi";
        dataBase = new DataBase(context);
        conn = dataBase.getWritableDatabase();
        ScriptSQL scriptSQL = new ScriptSQL(conn);
        scriptSQL.limpaListaDiarista();
        ListaDiarista obj = new ListaDiarista();
        dataServico = params[1];
        limpeza = Integer.parseInt(params[2]);
        passarroupa= Integer.parseInt(params[3]);
        lavarroupa = Integer.parseInt(params[4]);
        idEndereco = params[5];
        end = params[6];
        servs = params[7];
        outros = params[8];
        diaria = params[9];
        horainicio = params[10];
        try {

            GeraJson geraJson = new GeraJson();
            requester = new ConectaWS();
            String json2 = geraJson.jsonBuscaDiarista(params[0],
                                                        params[1],
                                                        Integer.parseInt(params[2]),
                                                        Integer.parseInt(params[3]),
                                                        Integer.parseInt(params[4]));

            final JSONArray recebe = requester.doPostJsonArray("http://limpo-dev.sa-east-1.elasticbeanstalk.com/api/Diarista/ListaDiaristaDisponiveis", json2);
            for(int i=0;i < recebe.length();i++) {
                JSONObject json = recebe.getJSONObject(i);
                obj.setIdDiarista((Integer) json.get("IdDiarista"));
                obj.setNome((String) json.get("Nome"));
                obj.setCep((String) json.get("Cep"));
                obj.setRate((Integer) json.get("Rate"));

                String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" +"06624155"+"&destination=" + obj.getCep() + "&key=AIzaSyDz0j3rEZVS95nKbQlnbeScA7zVWEdUW8Y";
                requester = new ConectaWS();
                final JSONObject maps = requester.doPostJsonObject(url, "");
                JSONArray dados = maps.getJSONArray("routes");
                JSONObject legs = dados.getJSONObject(0);
                JSONArray danese = legs.getJSONArray("legs");
                JSONObject depoisvejo = danese.getJSONObject(0);
                JSONObject aaa = depoisvejo.getJSONObject("distance");
                obj.setDistacia((Integer) aaa.get("value"));
                scriptSQL.gravaListaDiarista(obj.getIdDiarista(), obj.getNome(), obj.getCep(), obj.getRate(), obj.getDistacia());
                obj = new ListaDiarista();
            }
            resp=null;

        } catch (JSONException e) {
            resp = "Usuario ou senha invalido";
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    protected void onPostExecute(String resposta) { // thread principal

        if (resposta == null) {
            dialog.dismiss();
//            Intent it = new Intent(context, ExibeDiaristaActivity.class);
            Intent it = new Intent(context, ListaDiaristaActivity.class);
            it.putExtra("endereco",end);
            it.putExtra("servicos",servs);
            it.putExtra("dataservico", dataServico);
            it.putExtra("limpeza", limpeza);
            it.putExtra("passarroupa",passarroupa);
            it.putExtra("lavarroupa", lavarroupa);
            it.putExtra("idendereco",idEndereco);
            it.putExtra("outros",outros);
            it.putExtra("diaria",diaria);
            it.putExtra("horainicio",horainicio);
            context.startActivity(it);
            ((Activity) context).finish();


        } else {
            dialog.dismiss();
            onCancelled();
            MessageBox.show(context, "Erro ao buscar diaristas", resposta);

        }


    }


}

