package br.com.diaristaslimpo.limpo.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.to.ListaDiarista;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.adapter.ListaDiaristaAdapter;
import br.com.diaristaslimpo.limpo.util.MessageBox;

public class ExibeDiaristaActivity extends AppCompatActivity {
    private ListView lst;
    private ArrayList<ListaDiarista> itens;
    private String end;
    private String servs;
    private String dataServico, idEndereco, outros;
    private int limpeza;
    private int passarroupa;
    private int lavarroupa;
    private String idCliente;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private ListaDiaristaAdapter adapterListView;
    Activity at;
    private String diaria;
    private String horainicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_exibe_diarista);

        at=this;
        lst = (ListView) findViewById(R.id.lstDiarista);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        end = bundle.getString("endereco");
        servs = bundle.getString("servicos");
        limpeza = bundle.getInt("limpeza");
        passarroupa = bundle.getInt("passarroupa");
        lavarroupa = bundle.getInt("lavarroupa");
        dataServico = bundle.getString("dataservico");
        idEndereco = bundle.getString("idendereco");
        outros = bundle.getString("outros");
        diaria = bundle.getString("diaria");
        horainicio = bundle.getString("horainicio");
        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        ScriptSQL scriptSQL = new ScriptSQL(conn);
        idCliente = String.valueOf(scriptSQL.retornaIdCliente());

        ArrayList<ListaDiarista> obj;
        itens = new ArrayList<>();
        obj = scriptSQL.selectListaDiarista();
        ListaDiarista lista = new ListaDiarista();
        for (int i=0;i<obj.size();i++){
            lista.setIdDiarista(obj.get(i).getIdDiarista());
            lista.setNome(obj.get(i).getNome());
            lista.setDistacia(Integer.parseInt(String.valueOf(obj.get(i).getDistacia())));
            lista.setRate(obj.get(i).getRate());
            itens.add(lista);
            lista = new ListaDiarista();
        }

        adapterListView = new ListaDiaristaAdapter(this, itens);
        lst.setAdapter(adapterListView);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int corAuxiliar = view.getDrawingCacheBackgroundColor();
                if(corAuxiliar == 0) {
                    view.setDrawingCacheBackgroundColor(1);
                    view.setBackgroundColor(Color.rgb(255,153,194));
                    itens.get(position).setItemSelecionado(true);
                } else {
                    view.setDrawingCacheBackgroundColor(0);
                    view.setBackgroundColor(Color.TRANSPARENT);
                    itens.get(position).setItemSelecionado(false);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salva_cadastro1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_enviar_cadastro:

                ArrayList<Integer> idDiaristasSelecionadas = new ArrayList<>();
                String nomes = "";
                for(int i = 0; i < itens.size(); i++){
                    if(itens.get(i).isItemSelecionado()) {
                        idDiaristasSelecionadas.add(itens.get(i).getIdDiarista());
                        if(nomes == ""){
                            nomes += itens.get(i).getNome();
                        } else{
                            nomes += " | " + itens.get(i).getNome();
                        }
                    }
                }

                if(idDiaristasSelecionadas.size() == 0){
                    MessageBox.show(this, "Atenção", "Selecione pelo menos uma diarista");
                } else {

                    Intent intent = new Intent(at, ConfirmacaoServicoActivity.class);
                    intent.putExtra("endereco",end);
                    intent.putExtra("servicos",servs);
                    intent.putExtra("dataservico",dataServico);
                    intent.putExtra("diarista", nomes);
                    intent.putExtra("idDiarista", idDiaristasSelecionadas);
                    intent.putExtra("idCliente",idCliente);
                    intent.putExtra("idendereco",idEndereco);
                    intent.putExtra("limpeza",limpeza);
                    intent.putExtra("passarroupa",passarroupa);
                    intent.putExtra("lavarroupa",lavarroupa);
                    intent.putExtra("outros",outros);
                    intent.putExtra("diaria",diaria);
                    intent.putExtra("horainicio",horainicio);
                    startActivity(intent);

                }
        }

        return super.onOptionsItemSelected(item);
    }
}
