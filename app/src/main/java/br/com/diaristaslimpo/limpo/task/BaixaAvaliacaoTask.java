package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.AvaliacaoActivity;
import br.com.diaristaslimpo.limpo.util.MessageBox;

/**
 * Created by user on 27/11/2016.
 */
public class BaixaAvaliacaoTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private ProgressDialog dialog;
    private String json;
    private String idDiarista,idSolicitacao,NomeDiarista,idCliente;
    private String mensagem;

    public BaixaAvaliacaoTask(Context context) {
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
        try {
            json = params[0];
            String url = context.getResources().getString(R.string.url_prefix) +
                    context.getResources().getString(R.string.url_baixa_avaliacao);
            JSONObject recebe =  new ConectaWS().doGetJsonObject(url, params[0]);

            idDiarista= recebe.getString("IdDiarista");
            idSolicitacao = recebe.getString("IdSolicitacao");
            NomeDiarista = recebe.getString("NomeDiarista");
            idCliente = params[1];

            return true;
        } catch (JSONException e) {
            mensagem = "Não existe nenhuma avaliação";
            e.printStackTrace();
        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean isValido) {
        dialog.dismiss();

        if (isValido) {
            Intent it = new Intent(context, AvaliacaoActivity.class);
            it.putExtra("idDiarista", idDiarista);
            it.putExtra("idSolicitacao", idSolicitacao);
            it.putExtra("nomeDiarista", NomeDiarista);
            it.putExtra("idCliente", idCliente);
            context.startActivity(it);
        }
    }
}