package br.com.diaristaslimpo.limpo.telas;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.model.Orcamento;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.adapter.OrcamentoAdapter;
import br.com.diaristaslimpo.limpo.util.GeraJson;
import br.com.diaristaslimpo.limpo.webservice.RecebeHistorico;

public class Tela_Historico extends AppCompatActivity {

    private ListView lstOrcamento;
    private ArrayList<Orcamento> itens;
    private OrcamentoAdapter adapterListView;
    private DataBase dataBase;
    private SQLiteDatabase conn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela__orcamento_recebido);
        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        ScriptSQL scriptSQL = new ScriptSQL(conn);

        lstOrcamento = (ListView) findViewById(R.id.lstOrcamento);
        itens = new ArrayList<Orcamento>();
        GeraJson json = new GeraJson();
        new RecebeHistorico(this).execute(json.jsonId(String.valueOf(scriptSQL.retornaIdCliente())));


        ArrayList<Orcamento> obj = scriptSQL.selectListaHistorico();
        adapterListView = new OrcamentoAdapter(this, obj);
        lstOrcamento.setAdapter(adapterListView);


        lstOrcamento.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent it = new Intent(Tela_Historico.this,Tela_DetalheOrcamento.class);
                startActivity(it);

            }

        });

    }
    }