package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.InicialActivity;
import br.com.diaristaslimpo.limpo.util.GeraJson;
import br.com.diaristaslimpo.limpo.util.MessageBox;

/**
 * Created by user on 27/11/2016.
 */
public class EnviaAvaliacaoTask extends AsyncTask<String, Void, String> { // linha 22
    private Context context;
    private ProgressDialog dialog;
    private String json;

    public EnviaAvaliacaoTask(Context context) {

        this.context = context;

    }

    @Override
    protected void onPreExecute() {// antes de usar a thread segundaria
        dialog = ProgressDialog.show(context,
                context.getResources().getString(R.string.aguarde),
                context.getResources().getString(R.string.em_processamento),
                true,
                true);
    }

    @Override
    protected String doInBackground(String... params) {// thread em segundaria
        String resp = "oi";
        try {
            GeraJson geraJson = new GeraJson();
            json = geraJson.jsonEnviaAvaliacao(params[0],params[1],params[2],params[3],params[4]);
            String url = context.getResources().getString(R.string.url_prefix) +
                    context.getResources().getString(R.string.url_envia_avaliacao);
            JSONObject recebe =  new ConectaWS().doPostJsonObject(url, json);


            resp = null;



        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            resp = "Não existe nenhuma avaliação";
            e.printStackTrace();
        }
        return resp;
    }

    @Override
    protected void onPostExecute(String resposta) { // thread principal

        if (resposta == null) {
            dialog.dismiss();
            Intent it = new Intent(context, InicialActivity.class);
            context.startActivity(it);

        }
//        else {
//            dialog.dismiss();
//            onCancelled();
//            MessageBox.show(context, "Mensagem", resposta);
//
//        }


    }

}
