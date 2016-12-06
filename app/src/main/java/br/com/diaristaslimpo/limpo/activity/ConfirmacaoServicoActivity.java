package br.com.diaristaslimpo.limpo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.util.GeraJson;
import br.com.diaristaslimpo.limpo.task.EnviaSolicitacaoTask;

public class ConfirmacaoServicoActivity extends AppCompatActivity {
    private TextView txtservisolicitados,txtdatahora,txtdiarista,txtendereco,txtValorDiaria;
    private Button btok;
    private String end,servs,dataServico,diarista;
    private String idCliente;
    private String idEndereco;
    private ArrayList<Integer> idDiarista;
    private int limpeza;
    private int passarroupa;
    private int lavarroupa;
    private String json;
    private String outros;
    private String diaria;
    private String horainicio;
    private String valorDiaria;
    private String data;
    private Boolean isDiariaCompleta;
    private static ConfirmacaoServicoActivity activity;

    public static ConfirmacaoServicoActivity getInstance() {
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_confirmacao_servico);

        activity = this;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        end = bundle.getString("endereco");
        servs = bundle.getString("servicos");
        dataServico = bundle.getString("dataservico");
        diarista = bundle.getString("diarista");
        idCliente = bundle.getString("idCliente");
        idEndereco = bundle.getString("idendereco");
        idDiarista = bundle.getIntegerArrayList("idDiarista");
        limpeza = bundle.getInt("limpeza");
        passarroupa = bundle.getInt("passarroupa");
        lavarroupa = bundle.getInt("lavarroupa");
        outros = bundle.getString("outros");
        diaria = bundle.getString("diaria");
        horainicio = bundle.getString("horainicio");
        valorDiaria = bundle.getString("valorDiaria");
        data = bundle.getString("data");
        isDiariaCompleta = bundle.getBoolean("tipoDiaria");

        txtservisolicitados = (TextView)findViewById(R.id.txtServSolicitado);
        txtValorDiaria = (TextView)findViewById(R.id.txtvalorDiaria);
        txtdatahora = (TextView)findViewById(R.id.txtdatahora);
        txtdiarista = (TextView)findViewById(R.id.txtdiarista);
        txtendereco = (TextView)findViewById(R.id.txtendereco);

        txtservisolicitados.setText(servs);
        txtValorDiaria.setText("R$ " + valorDiaria);
        txtdatahora.setText(data);
        txtdiarista.setText(diarista);
        txtendereco.setText(end);

        GeraJson geraJson = new GeraJson();
        json = geraJson.jsonEnviaSolicitacao(idDiarista,idEndereco,limpeza,passarroupa,lavarroupa,dataServico,outros,isDiariaCompleta,horainicio,valorDiaria);

        btok = (Button)findViewById(R.id.bt_ok);
        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelecionarEnderecoActivity.getInstance().finish();
                SelecionarServicoActivity.getInstance().finish();
                DataServicoActivity.getInstance().finish();
                ListaDiaristaActivity.getInstance().finish();

                Intent it = new Intent(ConfirmacaoServicoActivity.this,InicialActivity.class);
                new EnviaSolicitacaoTask(ConfirmacaoServicoActivity.this).execute(json);
                startActivity(it);
            }
        });
    }
}
