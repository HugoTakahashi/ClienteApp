package br.com.diaristaslimpo.limpo.activity;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.AsyncResponse;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.adapter.HistoricoDiariasAdapter;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.task.ListaHistoricoDiariasTask;
import br.com.diaristaslimpo.limpo.to.DiariaTo;

public class ListaHistoricoActivity extends AppCompatActivity implements AsyncResponse {
    private ListView listView;
    private ArrayList<DiariaTo> items;
    private HistoricoDiariasAdapter listViewAdapter;
    private ListaHistoricoDiariasTask asyncTask = new ListaHistoricoDiariasTask(this);
    private String idCliente;
    private DataBase dataBase;
    private SQLiteDatabase conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibe_historico_diaria);

        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        ScriptSQL scriptSQL = new ScriptSQL(conn);
        idCliente = String.valueOf(scriptSQL.retornaIdCliente());

        listView = (ListView) findViewById(R.id.listview_historico_diaria);
        items = new ArrayList<>();

        asyncTask.delegate = this;
        asyncTask.execute(idCliente);
    }

    @Override
    public void processFinish(JSONArray jsonArray){
        try {
            for(int i=0;i < jsonArray.length();i++) {
                DiariaTo to = new DiariaTo();
                JSONObject json = jsonArray.getJSONObject(i);
                to.jsonParaTo(to, json);
                items.add(to);
            }
        } catch(JSONException e){
        }

        listViewAdapter = new HistoricoDiariasAdapter(this, items);
        listView.setAdapter(listViewAdapter);
    }
}