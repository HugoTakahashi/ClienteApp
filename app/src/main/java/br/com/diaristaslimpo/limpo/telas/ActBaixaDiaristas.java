package br.com.diaristaslimpo.limpo.telas;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.webservice.ListaDiaristas;

public class ActBaixaDiaristas extends AppCompatActivity {

    private String end;
    private String servs;
    private int limpeza;
    private int passarroupa;
    private int lavarroupa;
    private String dataServico;
    private String idEndereco;
    private String outros;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private String idCliente;
    private String horainicio;
    private boolean diaria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        diaria = bundle.getBoolean("diaria");
        horainicio = bundle.getString("horainicio");
        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        ScriptSQL scriptSQL = new ScriptSQL(conn);
        idCliente = String.valueOf(scriptSQL.retornaIdCliente());

        new ListaDiaristas(this).execute(idCliente, dataServico,
                String.valueOf(limpeza), String.valueOf(passarroupa), String.valueOf(lavarroupa),
                idEndereco,end,servs,outros,String.valueOf(diaria),horainicio);
    }
}