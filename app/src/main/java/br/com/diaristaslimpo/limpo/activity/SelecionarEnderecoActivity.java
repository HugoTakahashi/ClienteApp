package br.com.diaristaslimpo.limpo.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.to.Endereco;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.adapter.EnderecoAdapter;

public class SelecionarEnderecoActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView lst;
    private Button btn;
    private ArrayList<Endereco> itens;
    private ArrayList<Endereco> select;
    private EnderecoAdapter adapterListView;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private static SelecionarEnderecoActivity activity;

    public static SelecionarEnderecoActivity getInstance(){
        return activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_endereco);

        activity = this;

        lst = (ListView) findViewById(R.id.lstEndereco);
        btn = (Button) findViewById(R.id.btEndereco);
        btn.setOnClickListener(this);

        itens = new ArrayList<>();
        select = new ArrayList<>();

        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        ScriptSQL scriptSQL = new ScriptSQL(conn);
        select=scriptSQL.selectEndereco();
        for (int i = 0;i<select.size();i++){
            itens.add(select.get(i));
        }
        adapterListView = new EnderecoAdapter(this, itens);
        lst.setAdapter(adapterListView);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                Intent intent = new Intent(SelecionarEnderecoActivity.this, SelecionarServicoActivity.class);
                intent.putExtra("ENDERECO", itens.get(position).getEndereco() +
                    ", " + itens.get(position).getNumero() +
                    " - " + itens.get(position).getBairro()+
                    " - " + itens.get(position).getCidade());
                intent.putExtra("idendereco", itens.get(position).getIdEndereco());
                startActivityForResult(intent,0);
            }
        });
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(this,CadastroEnderecoActivity.class);
        intent.putExtra("chave","0");
        startActivity(intent);
        finish();

    }
}
