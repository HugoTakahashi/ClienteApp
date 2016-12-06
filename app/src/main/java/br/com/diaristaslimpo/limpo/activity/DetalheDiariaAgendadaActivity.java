package br.com.diaristaslimpo.limpo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.task.CancelarDiariaTask;
import br.com.diaristaslimpo.limpo.to.DiariaTo;

public class DetalheDiariaAgendadaActivity extends AppCompatActivity {

    private DiariaTo to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_diaria_agendada);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        to = (DiariaTo) bundle.getSerializable("diaria");

        ((TextView) findViewById(R.id.detalhe_diaria_agendada_nome)).setText(to.getNome());
        ((TextView) findViewById(R.id.detalhe_diaria_agendada_data_hora)).setText(to.getData() + " " + to.getPeriodoDiaria());
        ((TextView) findViewById(R.id.detalhe_diaria_agendada_servicos)).setText(to.getServicos());
        ((TextView) findViewById(R.id.detalhe_diaria_agendada_local)).setText(to.getIdentificacaoEndereco());
        ((TextView) findViewById(R.id.detalhe_diaria_agendada_valor)).setText(to.getValor());

        findViewById(R.id.bt_cancelar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(DetalheDiariaAgendadaActivity.this).create();
                dialog.setTitle("Atenção");
                dialog.setMessage("Deseja realmente cancelar?");

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        new CancelarDiariaTask(DetalheDiariaAgendadaActivity.this)
                                .execute(String.valueOf(to.getIdSolicitacao()));
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