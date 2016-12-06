package br.com.diaristaslimpo.limpo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.task.DownloadImageTask;
import br.com.diaristaslimpo.limpo.task.EnviaAvaliacaoTask;

public class AvaliacaoActivity extends AppCompatActivity {

    RatingBar nota;
    EditText obs;
    TextView nomeCliente;
    private String idSolicitacao, idCliente;
    private String nomeDiarista, idDiarista;
    private int notaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_avalicao);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        idSolicitacao = (String) bundle.get("idSolicitacao");
        nomeDiarista = (String)bundle.get("nomeDiarista");
        idDiarista = (String)bundle.get("idDiarista");
        idCliente = (String)bundle.get("idCliente");

        nota = (RatingBar) findViewById(R.id.ratingBar2);
        obs = (EditText) findViewById(R.id.avalicao_obs);
        nomeCliente = (TextView) findViewById(R.id.textView18);
        nomeCliente.setText(nomeDiarista);

        String url = "https://s3-sa-east-1.amazonaws.com/arquivo-e-imagens/diarista/" + idDiarista + "/foto.jpg";
        new DownloadImageTask((ImageView) findViewById(R.id.foto_diarista)).execute(url);

        findViewById(R.id.bt_avaliar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validarPreenchimentoObrigatorio()) {
                    notaint = (int) nota.getRating();
                    new EnviaAvaliacaoTask(AvaliacaoActivity.this).execute(String.valueOf(notaint), obs.getText().toString(),
                            idSolicitacao, idDiarista, idCliente);
                }
            }
        });
    }

    private boolean validarPreenchimentoObrigatorio(){
        if(obs.getText().toString().matches("")){
            obs.setError("Campo obrigatório");
            return false;
        }

        return true;
    }
}