package br.com.diaristaslimpo.limpo.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.to.Endereco;
import br.com.diaristaslimpo.limpo.util.MessageBox;

/**
 * Created by user on 24/04/2016.
 */
public class BuscarEnderecoPorCepTask extends AsyncTask<String, Void, Boolean> {

    private Context context;
    private ProgressDialog dialog;
    private ConectaWS requester = new ConectaWS();
    private String cep;
    private Endereco obj = new Endereco();

    public BuscarEnderecoPorCepTask(Context context) {
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
            cep = params[0];

            final JSONObject response = requester.doGetJsonObject("http://limpo-dev.sa-east-1.elasticbeanstalk.com/api/CorreiosApi/BuscarEndereco", cep);

            obj.setEndereco(response.get("endereco").toString());
            obj.setBairro(response.get("bairro").toString());
            obj.setCidade(response.get("cidade").toString());

        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean sucesso) {
        if (sucesso) {
            if(!obj.getEndereco().isEmpty() && !obj.getBairro().isEmpty() && !obj.getCidade().isEmpty()){
                TextView endereco = (TextView)((Activity)context).findViewById(R.id.endereco_endereco);
                TextView bairro = (TextView)((Activity)context).findViewById(R.id.endereco_bairro);
                TextView cidade = (TextView)((Activity)context).findViewById(R.id.endereco_cidade);

                endereco.setText(obj.getEndereco());
                bairro.setText(obj.getBairro());
                cidade.setText(obj.getCidade());
                dialog.dismiss();
            }
        } else {
            dialog.dismiss();
            onCancelled();
            MessageBox.show(context, "CEP inválido", "Insira outro cep");
        }
    }
}