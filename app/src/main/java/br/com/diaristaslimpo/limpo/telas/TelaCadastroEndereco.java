package br.com.diaristaslimpo.limpo.telas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.banco.DataBase;
import br.com.diaristaslimpo.limpo.banco.ScriptSQL;
import br.com.diaristaslimpo.limpo.util.CepUtil;
import br.com.diaristaslimpo.limpo.util.GeraJson;
import br.com.diaristaslimpo.limpo.util.MaskUtil;
import br.com.diaristaslimpo.limpo.webservice.AlteraEndereco;
import br.com.diaristaslimpo.limpo.webservice.EnviaEndereco;

public class TelaCadastroEndereco extends AppCompatActivity {

    private EditText identificacaoEndereco,cep, logradouro, numero, complemento, bairro, cidade, estado, pontoReferencia;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private String idEndereco;
    private int alteracao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_endereco);
        identificacaoEndereco = (EditText) findViewById(R.id.endereco_nome);
        cep = (EditText) findViewById(R.id.endereco_cep);
        logradouro = (EditText) findViewById(R.id.endereco_endereco);
        numero = (EditText) findViewById(R.id.endereco_numero);
        complemento = (EditText) findViewById(R.id.endereco_complemento);
        bairro = (EditText) findViewById(R.id.endereco_bairro);
        cidade = (EditText) findViewById(R.id.endereco_cidade);
        pontoReferencia = (EditText) findViewById(R.id.endereco_pontoreferencia);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        try {
            alteracao= Integer.parseInt(bundle.getString("chave"));
            idEndereco = bundle.getString("idendereco");
            identificacaoEndereco.setText(bundle.getString("identificacaoendereco"));
            cep.setText(bundle.getString("cep"));
            logradouro.setText(bundle.getString("endereco"));
            numero.setText(String.valueOf(bundle.getInt("numero")));
            complemento.setText(bundle.getString("complemento"));
            bairro.setText(bundle.getString("bairro"));
            cidade.setText(bundle.getString("cidade"));
            pontoReferencia.setText(bundle.getString("pontoreferencia"));

        }catch (RuntimeException e){

        }

        cep.addTextChangedListener(MaskUtil.insert(MaskUtil.MaskType.CEP, cep));
        cep.addTextChangedListener(CepUtil.buscarEnderecoPorCep(cep, this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_salva_endereco, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_enviar_endereco:
                dataBase = new DataBase(this);
                conn = dataBase.getWritableDatabase();

                ScriptSQL scriptSQL = new ScriptSQL(conn);
                final int idCliente = scriptSQL.retornaIdCliente();

                AlertDialog dialog = new AlertDialog.Builder(this).create();
                dialog.setTitle("Salvar ?");
                dialog.setMessage("Escolha sim para Salvar ou não para Cancelar");

                dialog.setButton(DialogInterface.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        GeraJson geraJson = new GeraJson();
                        String json = geraJson.jsonCadastraEndereço(
                                idCliente,
                                idEndereco,
                                identificacaoEndereco.getText().toString(),
                                cep.getText().toString(),
                                logradouro.getText().toString(),
                                numero.getText().toString(),
                                complemento.getText().toString(),
                                bairro.getText().toString(),
                                cidade.getText().toString(),
                                pontoReferencia.getText().toString());
                        if (alteracao==0) {
                            new EnviaEndereco(TelaCadastroEndereco.this).execute(json);
                        }
                        if (alteracao==1){
                            new AlteraEndereco(TelaCadastroEndereco.this).execute(json);
                        }


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
