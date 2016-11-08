package br.com.diaristaslimpo.limpo.telas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import br.com.diaristaslimpo.limpo.model.Cliente;
import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.util.GeraJson;
import br.com.diaristaslimpo.limpo.webservice.AlteraCadastro;

public class TelaDadosPessoais extends AppCompatActivity {
    private EditText edtNome,edtSobrenome,edtCPF,edtDataNasc,edtEmail,edtCelular;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_dados_pessoais);

        edtNome = (EditText)findViewById(R.id.edtNome);
        edtSobrenome = (EditText)findViewById(R.id.edtSobrenome);
        edtCPF = (EditText)findViewById(R.id.edtCPF);
        edtDataNasc = (EditText)findViewById(R.id.edtDateNasc);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtCelular = (EditText)findViewById(R.id.edtCelular);

        try{
            dataBase = new DataBase(this);
            conn = dataBase.getWritableDatabase();

            ScriptSQL sql = new ScriptSQL(conn);
            ArrayList<Cliente> obj = new ArrayList<>();
            obj = sql.selectCliente();
            id = obj.get(0).getIdCliente();
            edtNome.setText(obj.get(0).getNome());
            edtSobrenome.setText(obj.get(0).getSobrenome());
            edtCPF.setText(String.valueOf(obj.get(0).getCpf()));
            edtDataNasc.setText(obj.get(0).getDataNascimento());
            edtEmail.setText((obj.get(0).getEmail()));
            edtCelular.setText(String.valueOf(obj.get(0).getCelular()));
        }catch (Exception e){

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salva_altera_dados, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enviar_alteracao_dados:

                AlertDialog dialog = new AlertDialog.Builder(this).create();
                dialog.setTitle("Deseja Alterar seus dados ?");
                dialog.setMessage("Escolha sim para alterar ou não para cancelar");

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GeraJson geraJson = new GeraJson();
                        String json = geraJson.jsonAlteraCadastro(
                                id,
                                edtNome.getText().toString(),
                                edtSobrenome.getText().toString(),
                                edtDataNasc.getText().toString(),
                                edtCPF.getText().toString(),
                                edtEmail.getText().toString(),
                                edtCelular.getText().toString()
                                );
                        new AlteraCadastro(TelaDadosPessoais.this).execute(json);
                        Toast.makeText(TelaDadosPessoais.this, json, Toast.LENGTH_SHORT).show();

                    }
                });
                dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
