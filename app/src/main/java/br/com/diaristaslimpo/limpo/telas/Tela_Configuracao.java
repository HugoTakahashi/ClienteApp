package br.com.diaristaslimpo.limpo.telas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.webservice.DesativarConta;

public class Tela_Configuracao extends AppCompatActivity {
    private Button btDesativar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);
        btDesativar = (Button) findViewById(R.id.bt_desativar);
        btDesativar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(Tela_Configuracao.this).create();
                dialog.setTitle("Deseja desativar sua conta ?");
                dialog.setMessage("Escolha sim para desativar ou não para Cancelar");

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new DesativarConta(Tela_Configuracao.this).execute();
                    }

                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });
                dialog.show();

            }
        });
    }
}

