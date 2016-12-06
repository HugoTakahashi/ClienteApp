package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;

import br.com.diaristaslimpo.limpo.AsyncResponse;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.util.MessageBox;

/**
 * Created by Hugo on 24/04/2016.
 */
public class ListaHistoricoDiariasTask extends AsyncTask<String, Void, JSONArray> {
    private Context context;
    private ProgressDialog dialog;
    public AsyncResponse delegate = null;

    public ListaHistoricoDiariasTask(Context context) {
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
    protected JSONArray doInBackground(String... params) {
        String idCliente = params[0];
        String url = context.getResources().getString(R.string.url_prefix) +
                context.getResources().getString(R.string.url_lista_historico_diaria);

        return new ConectaWS().doGetJsonArray(url, idCliente);
    }

    @Override
    protected void onPostExecute(JSONArray resposta) {
        if(resposta == null || resposta.length() == 0){
            MessageBox.showClose(context,"Ops!","Não há registros no momento.");
        }
        delegate.processFinish(resposta);
        dialog.dismiss();
    }
}