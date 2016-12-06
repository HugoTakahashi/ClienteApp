package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import br.com.diaristaslimpo.limpo.AsyncResponse;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.to.SolicitacaoTo;
import br.com.diaristaslimpo.limpo.util.MessageBox;

/**
 * Created by Hugo on 24/04/2016.
 */
public class ListaDiaristasDisponiveisTask extends AsyncTask<SolicitacaoTo, Void, JSONArray> {
    private Context context;
    private ProgressDialog dialog;
    public AsyncResponse delegate = null;

    public ListaDiaristasDisponiveisTask(Context context) {
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
    protected JSONArray doInBackground(SolicitacaoTo... params) {
        JSONArray response = null;

        String json = params[0].toString();
        String url = context.getResources().getString(R.string.url_prefix) +
                context.getResources().getString(R.string.url_lista_diarista_disponiveis);

        try {
            response = new ConectaWS().doPostJsonArray(url, json);
        } catch(IOException ex){
        } catch(JSONException ex){
        }

        return response;
    }

    @Override
    protected void onPostExecute(JSONArray resposta) {
        if(resposta == null || resposta.length() == 0){
            MessageBox.showClose(context,"Ops!","Não há diaristas disponíveis.");
        }
        delegate.processFinish(resposta);
        dialog.dismiss();
    }
}