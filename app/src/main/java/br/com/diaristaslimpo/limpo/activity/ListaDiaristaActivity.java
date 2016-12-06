package br.com.diaristaslimpo.limpo.activity;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.AsyncResponse;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.adapter.ListaDiaristaDisponivelAdapter;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.task.ListaDiaristasDisponiveisTask;
import br.com.diaristaslimpo.limpo.to.DiaristaDisponivelTo;
import br.com.diaristaslimpo.limpo.to.SolicitacaoTo;
import br.com.diaristaslimpo.limpo.util.MessageBox;

public class ListaDiaristaActivity extends AppCompatActivity implements AsyncResponse {
    private ListView listView;
    private ArrayList<DiaristaDisponivelTo> items;
    private ListaDiaristaDisponivelAdapter listViewAdapter;
    private ListaDiaristasDisponiveisTask asyncTask = new ListaDiaristasDisponiveisTask(this);

    private String end, servs, dataServico, idEndereco, outros,
            valorDiaria, data,idCliente, diaria, horainicio;
    private int limpeza, passarroupa, lavarroupa;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private boolean isDiariaCompleta;
    private static ListaDiaristaActivity activity;

    public static ListaDiaristaActivity getInstance(){
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_exibe_diarista);

        activity = this;

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        end = bundle.getString("endereco");
        servs = bundle.getString("servicos");
        limpeza = bundle.getInt("limpeza");
        passarroupa = bundle.getInt("passarroupa");
        lavarroupa = bundle.getInt("lavarroupa");
        dataServico = bundle.getString("dataservico");
        data = bundle.getString("data");
        idEndereco = bundle.getString("idendereco");
        outros = bundle.getString("outros");
        diaria = bundle.getString("diaria");
        horainicio = bundle.getString("horainicio");
        valorDiaria = bundle.getString("valorDiaria");
        isDiariaCompleta = bundle.getBoolean("tipoDiaria");
        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        ScriptSQL scriptSQL = new ScriptSQL(conn);
        idCliente = String.valueOf(scriptSQL.retornaIdCliente());

        listView = (ListView) findViewById(R.id.lstDiarista);
        items = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int corAuxiliar = view.getDrawingCacheBackgroundColor();
                if(corAuxiliar == 0) {
                    view.setDrawingCacheBackgroundColor(1);
                    view.setBackgroundColor(Color.rgb(255,153,194));
                    items.get(position).setItemSelecionado(true);
                } else {
                    view.setDrawingCacheBackgroundColor(0);
                    view.setBackgroundColor(Color.TRANSPARENT);
                    items.get(position).setItemSelecionado(false);
                }
            }
        });

        SolicitacaoTo to = new SolicitacaoTo();
        to.setIdEnderecoCliente(Integer.parseInt(idEndereco));
        to.setLavaRoupa((lavarroupa != 0));
        to.setPassaRoupa((passarroupa != 0));
        to.setLimpeza((limpeza != 0));
        to.setData(dataServico);

        asyncTask.delegate = this;
        asyncTask.execute(to);
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
                for(int i = 0; i < items.size(); i++){
                    if(items.get(i).isItemSelecionado()) {
                        idDiaristasSelecionadas.add(items.get(i).getIdDiarista());
                        if(nomes == ""){
                            nomes += items.get(i).getNome();
                        } else{
                            nomes += " | " + items.get(i).getNome();
                        }
                    }
                }

                if(idDiaristasSelecionadas.size() == 0){
                    MessageBox.show(this, "Atenção", "Selecione pelo menos uma diarista");
                } else {

                    Intent intent = new Intent(ListaDiaristaActivity.this, ConfirmacaoServicoActivity.class);
                    intent.putExtra("endereco",end);
                    intent.putExtra("servicos",servs);
                    intent.putExtra("dataservico",dataServico);
                    intent.putExtra("data",data);
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
                    intent.putExtra("valorDiaria", valorDiaria);
                    intent.putExtra("tipoDiaria", isDiariaCompleta);
                    startActivity(intent);
                }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(JSONArray jsonArray){
        try {
            for(int i=0;i < jsonArray.length();i++) {
                DiaristaDisponivelTo to = new DiaristaDisponivelTo();
                JSONObject json = jsonArray.getJSONObject(i);
                to.jsonParaTo(to, json);
                items.add(to);
            }
        } catch(JSONException e){
        }

        listViewAdapter = new ListaDiaristaDisponivelAdapter(this, items);
        listView.setAdapter(listViewAdapter);
    }
}