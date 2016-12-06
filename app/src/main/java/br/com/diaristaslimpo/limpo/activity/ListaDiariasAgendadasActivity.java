package br.com.diaristaslimpo.limpo.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.AsyncResponse;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.adapter.DiariasAgendadasAdapter;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.task.ListaDiariasAgendadasTask;
import br.com.diaristaslimpo.limpo.to.DiariaTo;

public class ListaDiariasAgendadasActivity extends AppCompatActivity implements AsyncResponse {
    private ListView listView;
    private ArrayList<DiariaTo> items;
    private DiariasAgendadasAdapter listViewAdapter;
    private ListaDiariasAgendadasTask asyncTask = new ListaDiariasAgendadasTask(this);
    private String idCliente;
    private DataBase dataBase;
    private SQLiteDatabase conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibe_diarias_agendadas);

        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        ScriptSQL scriptSQL = new ScriptSQL(conn);
        idCliente = String.valueOf(scriptSQL.retornaIdCliente());

        listView = (ListView) findViewById(R.id.listview_diarias_agendadas);
        items = new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListaDiariasAgendadasActivity.this, DetalheDiariaAgendadaActivity.class);
                intent.putExtra("diaria", items.get(position));
                startActivity(intent);
            }
        });

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

        listViewAdapter = new DiariasAgendadasAdapter(this, items);
        listView.setAdapter(listViewAdapter);
    }
}