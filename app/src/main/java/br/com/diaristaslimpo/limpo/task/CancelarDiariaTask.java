package br.com.diaristaslimpo.limpo.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.InicialActivity;

/**
 * Created by Hugo on 24/04/2016.
 */
public class CancelarDiariaTask extends AsyncTask<String, Void, Boolean> {
    private Context context;
    private ProgressDialog dialog;

    public CancelarDiariaTask(Context context) {
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
        String id = params[0];
        String url = context.getResources().getString(R.string.url_prefix) +
                context.getResources().getString(R.string.url_solicitacao_cancelar);

        new ConectaWS().doGet(url, id);
        return true;
    }

    @Override
    protected void onPostExecute(Boolean response) {
        dialog.dismiss();
        Intent intent = new Intent(context, InicialActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
        Toast.makeText(context,
                context.getResources().getString(R.string.mensagem_cancelar_solicitacao),
                Toast.LENGTH_LONG)
                .show();
    }
}