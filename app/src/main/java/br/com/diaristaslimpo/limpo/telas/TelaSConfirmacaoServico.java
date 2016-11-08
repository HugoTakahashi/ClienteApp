package br.com.diaristaslimpo.limpo.telas;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.activity.InicialActivity;
import br.com.diaristaslimpo.limpo.util.GeraJson;
import br.com.diaristaslimpo.limpo.webservice.EnviaSolicitacao;

public class TelaSConfirmacaoServico extends AppCompatActivity {
    private TextView txtservisolicitados,txtdatahora,txtdiarista,txtendereco;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_confirmacao_servico);
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

        txtservisolicitados = (TextView)findViewById(R.id.txtServSolicitado);
        txtdatahora = (TextView)findViewById(R.id.txtdatahora);
        txtdiarista = (TextView)findViewById(R.id.txtdiarista);
        txtendereco = (TextView)findViewById(R.id.txtendereco);

        txtservisolicitados.setText(servs);
        txtdatahora.setText(dataServico);
        txtdiarista.setText(diarista);
        txtendereco.setText(end);

        GeraJson geraJson = new GeraJson();
        json = geraJson.jsonEnviaSolicitacao(idDiarista,idEndereco,limpeza,passarroupa,lavarroupa,dataServico,outros,diaria,horainicio);

        btok = (Button)findViewById(R.id.bt_ok);
        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(TelaSConfirmacaoServico.this,InicialActivity.class);
                new EnviaSolicitacao(TelaSConfirmacaoServico.this).execute(json);
                Toast tost = Toast.makeText(TelaSConfirmacaoServico.this,"Serviço Solicitado",Toast.LENGTH_SHORT);
                tost.show();
                startActivity(it);
            }
        });
    }
}
